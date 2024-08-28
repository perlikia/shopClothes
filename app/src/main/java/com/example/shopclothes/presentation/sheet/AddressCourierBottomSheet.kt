package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.example.dadata.model.Address
import com.example.domain.models.user.UserAddress
import com.example.shopclothes.R
import com.example.shopclothes.databinding.AddressCourierBottomBinding
import com.example.shopclothes.presentation.viewmodel.AddressCourierViewModel
import com.example.shopclothes.utils.GPSHandler
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.InputListener
import com.yandex.mapkit.map.Map
import com.yandex.runtime.image.ImageProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressCourierBottomSheet(
    val callbackAddress: (UserAddress) -> Unit
) : BottomSheetDialogFragment() {

    private var cameraMove = false
    private var locationHomeUse: Boolean = false
        get(){
            return field
        }
        set(value){
            field = value
            updateProgressContainer()
        }

    private val gpsHandler = GPSHandler(this){ location ->
        if(locationHomeUse){
            locationHomeUse = false
            cameraMove = true
            model.updateAddressCourierPoint(location.latitude, location.longitude)
        }
    }

    val listenerMap = object : InputListener{
        override fun onMapTap(map: Map, point: Point) {
            model.updateAddressCourierPoint(point.latitude, point.longitude)
        }

        override fun onMapLongTap(map: Map, point: Point) {}
    }

    val model by viewModel<AddressCourierViewModel>()

    var _binding : AddressCourierBottomBinding? = null
    val binding get() = _binding!!

    fun updateProgressContainer(){
        if(locationHomeUse){
            binding.btnMapNav.visibility = View.GONE
            binding.progressBarMap.visibility = View.VISIBLE
        }
        else{
            binding.btnMapNav.visibility = View.VISIBLE
            binding.progressBarMap.visibility = View.GONE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MapKitFactory.initialize(requireContext())
        _binding = AddressCourierBottomBinding.inflate(inflater)
        updateProgressContainer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val map = binding.map.mapWindow.map

        if(model.mapStyle.isInitialized.not()){
            model.updateMapStyle()
        }

        model.mapStyle.observe(viewLifecycleOwner){
            model.mapStyle.value?.let{
                map.setMapStyle(it)
            }
        }

        map.addInputListener(listenerMap)
        model.address.observe(viewLifecycleOwner){
            val address = model.address.value
            val mapObjects = map.mapObjects
            mapObjects.clear()
            address?.let {
                binding.streetInput.setText(address.street)
                binding.houseInput.setText(address.house)
                binding.entranceInput.setText(address.entrance)

                val point = Point(address.geo_lat.toDouble(), address.geo_lon.toDouble())

                if(cameraMove){
                    map.move(
                        CameraPosition(
                            point,
                            17f,
                            0f,
                            0f
                        )
                    )
                }

                mapObjects.addPlacemark().apply {
                    setIcon(ImageProvider.fromResource(requireContext(), R.drawable.ic_dollar_pin))
                    geometry = point
                }

                cameraMove = false
            }
        }

        binding.btnMapNav.setOnClickListener{
            locationHomeUse = true
        }

        binding.acceptBtn.setOnClickListener{
            callbackAddress(UserAddress(
                model.address.value?.geo_lat,
                model.address.value?.geo_lon,

                binding.streetInput.text.toString(),
                binding.houseInput.text.toString(),
                binding.apartInput.text.toString(),
                binding.entranceInput.text.toString(),
                binding.intercomInput.text.toString(),
            ))
            dismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gpsHandler.onCreate()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.map.onStart()
    }

    override fun onResume() {
        super.onResume()
        gpsHandler.onResume()
    }

    override fun onPause() {
        super.onPause()
        gpsHandler.onPause()
    }

    override fun onStop() {
        binding.map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            parentLayout?.let { view ->
                val behaviour = BottomSheetBehavior.from(view)
                setupFullHeight(view)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }
}