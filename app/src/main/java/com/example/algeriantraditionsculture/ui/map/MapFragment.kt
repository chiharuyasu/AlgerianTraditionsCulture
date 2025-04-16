package com.example.algeriantraditionsculture.ui.map

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.algeriantraditionsculture.R
import com.example.algeriantraditionsculture.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MapViewModel by viewModels()
    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null

    private val locationPermissionRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                enableMyLocation()
            }
            permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                enableMyLocation()
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "Location permission is required to show your location on the map",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding.mapView
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        setupSearchView()
        setupFilterChips()
        checkLocationPermission()
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { viewModel.searchLocations(it) }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun setupFilterChips() {
        binding.regionFilterChip.setOnClickListener {
            viewModel.filterByRegion()
        }

        binding.seasonFilterChip.setOnClickListener {
            viewModel.filterBySeason()
        }
    }

    private fun checkLocationPermission() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED -> {
                enableMyLocation()
            }
            else -> {
                locationPermissionRequest.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        }
    }

    private fun enableMyLocation() {
        try {
            googleMap?.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            Toast.makeText(
                requireContext(),
                "Location permission is required to show your location on the map",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        enableMyLocation()

        // Set initial camera position to Algeria
        val algeria = LatLng(28.0339, 1.6596)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(algeria, 5f))

        // Observe locations from ViewModel
        viewModel.locations.observe(viewLifecycleOwner) { locations ->
            map.clear()
            locations.forEach { location ->
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(location.latitude, location.longitude))
                        .title(location.name)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
        _binding = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
} 