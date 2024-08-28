package com.example.shopclothes.presentation.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import android.widget.Toast
import com.example.shopclothes.R
import com.example.shopclothes.databinding.MapLayoutBinding
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.MapObjectTapListener
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider

class MapActivity : Activity() {
    private lateinit var mapView: MapView

    private val placemarkTapListener = MapObjectTapListener { _, point ->
        //showToast("Tapped the point (${point.longitude}, ${point.latitude})")
        Toast.makeText(this, "Text", Toast.LENGTH_SHORT).show()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)
        setContentView(R.layout.map_layout)
        mapView = findViewById(R.id.map)

        val map = mapView.mapWindow.map
        map.move(POSITION)

        val imageProvider = ImageProvider.fromResource(this, R.drawable.ic_dollar_pin)
        map.mapObjects.addPlacemark().apply {
            geometry = POINT
            setIcon(imageProvider)
            addTapListener(placemarkTapListener)
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView.onStart()
    }

    override fun onStop() {
        mapView.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    companion object {
        private val POINT = Point(55.751280, 37.629720)
        private val POSITION = CameraPosition(POINT, 17.0f, 150.0f, 30.0f)
    }
}