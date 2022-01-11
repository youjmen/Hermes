package com.jaemin.hermes.book.view.fragment

import android.Manifest
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.gms.location.*
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.book.view.data.BookUiModel
import com.jaemin.hermes.book.viewmodel.CheckStockViewModel
import com.jaemin.hermes.databinding.FragmentCheckStockBinding
import com.jaemin.hermes.entity.Book
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.util.LocationUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import org.koin.androidx.viewmodel.ext.android.viewModel


class CheckStockFragment : BaseViewBindingFragment<FragmentCheckStockBinding>(), OnMapReadyCallback {

    private val viewModel : CheckStockViewModel by viewModel()
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var marker: Marker
    private val bookstoresInformation : MutableList<Place> = mutableListOf()


    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            if (locationResult.equals(null)) {
                return
            }
            for (location in locationResult.locations) {
                if (location != null) {
                    setCurrentLocationToMap()
                    fusedLocationProviderClient.removeLocationUpdates(this)

                }
            }
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setMap()
        if (!LocationUtil.checkLocationPermission(requireContext())){
            requestLocationPermission()
        }
        else{
            viewModel.getCurrentLocation()
        }
    }
    override fun onResume() {
        super.onResume()
        if (!LocationUtil.isLocationEnabled(requireContext())) {
            Toast.makeText(requireContext(),"앱 > 설정에서 위치 접근 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            LocationUtil.launchLocationSettings(requireActivity())
        }
    }

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentCheckStockBinding =
        FragmentCheckStockBinding.inflate(inflater, container, false)

    private fun setMap(){
        mapFragment = childFragmentManager.findFragmentById(R.id.fl_bookstore_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fl_bookstore_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun setCurrentLocation(item: Place) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(item.latitude, item.longitude))
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
        if (this::marker.isInitialized) {
            marker.map = null
        }
        setMarker(item)
    }



    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        with(viewModel){
            currentPlace.observe(viewLifecycleOwner) {
                setCurrentLocation(it)
                searchBookstoreByAddressWithRadius(it.longitude, it.latitude, 20000)
            }
            emptySavedLocationEvent.observe(viewLifecycleOwner, EventObserver {
                setCurrentLocationToMap()

            })
            bookstores.observe(viewLifecycleOwner){
                it.forEachIndexed { index , place->
                    bookstoresInformation.add(place)
                    setBookstoreMarker(place, index)
                }
            }
        }
    }
    private fun setMarker(item: Place) {
        marker = Marker()
        marker.width = 60
        marker.height = 80
        marker.isHideCollidedSymbols = true
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
        marker.captionText = item.name

    }
    private fun setBookstoreMarker(item: Place, index : Int) {
        setMarker(item)
        marker.setOnClickListener {
            bookstoresInformation[index].let {
                binding.clBookstoreInformation.animate()
                    .translationY(-binding.clBookstoreInformation.height.toFloat())
                binding.tvBookstoreName.text = it.name
                binding.tvBookstoreAddress.text = it.roadAddress
                binding.tvBookstorePhone.text = it.phoneNumber
            }
            true
        }
    }

    private fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {

                fusedLocationProviderClient =
                    LocationServices.getFusedLocationProviderClient(requireContext())
                if (this::fusedLocationProviderClient.isInitialized) {
                    getCurrentLocation()
                }
            } else {
                Toast.makeText(requireContext(), getString(R.string.please_allow_location_app_permission), Toast.LENGTH_SHORT).show()
                requireActivity().onBackPressed()
            }

        }
        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }
    private fun getCurrentLocation() {
        if (LocationUtil.checkLocationPermission(requireContext())) {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }
    private fun setCurrentLocationToMap() {
        if (LocationUtil.checkLocationPermission(requireContext())) {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    naverMap.moveCamera(
                        CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))
                            .animate(CameraAnimation.Easing)

                    )
                    viewModel.searchCurrentLocationPlace(location.longitude, location.latitude)
                } else {
                    getCurrentLocation()
                }
            }
        }
    }
}