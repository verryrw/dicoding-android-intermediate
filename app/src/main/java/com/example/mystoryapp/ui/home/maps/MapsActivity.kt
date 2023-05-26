package com.example.mystoryapp.ui.home.maps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mystoryapp.R
import com.example.mystoryapp.data.local.sharedpreference.AuthPreferences
import com.example.mystoryapp.data.remote.Result
import com.example.mystoryapp.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private val mapsViewModel: MapsViewModel by viewModels {
        MapsViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.apply {
            uiSettings.isZoomControlsEnabled = true
            uiSettings.isIndoorLevelPickerEnabled = true
            uiSettings.isCompassEnabled = true
            uiSettings.isMapToolbarEnabled = true
        }

        val token = AuthPreferences(this).getToken()!!
        mapsViewModel.getAllStoriesWithLocation(token).observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {}
                    is Result.Success -> {
                        val bounds = LatLngBounds.Builder()
                        val locations = result.data
                        locations.forEach { story ->
                            val lat = story.lat.toDouble()
                            val lon = story.lon.toDouble()
                            val loc = LatLng(lat, lon)
                            map.addMarker(MarkerOptions().position(loc).title(story.name))
                            bounds.include(loc)
                        }

                        val padding = 100
                        val cameraUpdate =
                            CameraUpdateFactory.newLatLngBounds(bounds.build(), padding)
                        map.moveCamera(cameraUpdate)
                    }

                    is Result.Error -> {
                        Toast.makeText(this, "Couldn't get locations", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}