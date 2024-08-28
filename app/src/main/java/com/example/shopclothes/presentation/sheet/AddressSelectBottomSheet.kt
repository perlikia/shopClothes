package com.example.shopclothes.presentation.sheet

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.domain.models.shop.ShopPoint
import com.example.shopclothes.R
import com.example.shopclothes.databinding.AddressSelectSheetBinding
import com.example.shopclothes.presentation.activity.MapActivity
import com.example.shopclothes.presentation.activity.MapActivity.Companion
import com.example.shopclothes.presentation.viewmodel.AddressSelectViewModel
import com.example.shopclothes.utils.address_map.AddressCluster
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectDragListener
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.runtime.image.ImageProvider
import com.yandex.runtime.ui_view.ViewProvider
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddressSelectBottomSheet(private val listener: (ShopPoint)->Unit) : BottomSheetDialogFragment() {

    private val placemarkTapListener = MapObjectTapListener { obj, point ->
        val data = obj.userData as ShopPoint
        model.updateSelected(data)
        true
    }

    val model by viewModel<AddressSelectViewModel>()

    var _binding : AddressSelectSheetBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        MapKitFactory.initialize(requireContext())
        _binding = AddressSelectSheetBinding.inflate(inflater)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.map.onStart()
    }

    override fun onStop() {
        binding.map.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initMap()

        binding.acceptBtn.setOnClickListener{
            model.selectedPoint.value?.let(listener)
            dismiss()
        }
    }

    fun initMap(){
        val mapWindow = binding.map.mapWindow
        val mapObjects = mapWindow.map.mapObjects

        mapWindow.map.move(
            CameraPosition(
                Point(
                    59.824726,
                    30.179035,
                ),
                17f,
                150f,
                30f
            )
        )

        val clusterListener = ClusterListener { cluster ->
            cluster.appearance.setView(
                ViewProvider(
                    AddressCluster(requireContext())
                )
            )
        }
        val clusteredCollection = mapObjects.addClusterizedPlacemarkCollection(clusterListener)

        model.points.observe(viewLifecycleOwner){
            model.points.value?.forEach{ shopPoint ->
                clusteredCollection.addPlacemark().apply {
                    geometry = Point(shopPoint.lat, shopPoint.lng)
                    setIcon(ImageProvider.fromResource(this@AddressSelectBottomSheet.requireContext(), R.drawable.store_icon))
                    addTapListener(placemarkTapListener)
                    userData = shopPoint
                }
            }
            clusteredCollection.clusterPlacemarks(1.0, 15)
        }


        model.selectedPoint.observe(viewLifecycleOwner){
            val selectedPoint = model.selectedPoint.value
            binding.acceptBtn.visibility = View.GONE
            binding.streetInput.text = null
            selectedPoint?.let {
                binding.streetInput.setText(selectedPoint.address)
                binding.acceptBtn.visibility = View.VISIBLE
            }
        }

        model.mapStyle.observe(viewLifecycleOwner){
            mapWindow.map.setMapStyle(it)
        }

        if(model.mapStyle.isInitialized.not()){
            model.updateMapStyle(requireContext())
        }

        if(model.points.isInitialized.not()){
            model.updatePoints()
        }
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