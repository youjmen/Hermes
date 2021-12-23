package com.jaemin.hermes.main.fragment

import android.Manifest
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.PointF
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaemin.hermes.R
import com.jaemin.hermes.databinding.FragmentLocationRegisterBottomSheetBinding
import com.jaemin.hermes.main.viewmodel.LocationRegisterViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.lang.Exception

class LocationRegisterBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback {
    private val viewModel : LocationRegisterViewModel by viewModel()
    private lateinit var binding: FragmentLocationRegisterBottomSheetBinding
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource: FusedLocationSource

    private lateinit var fusedLocationProvider: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  FragmentLocationRegisterBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.fl_location) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fl_location, it).commit()
            }
        mapFragment.getMapAsync(this)
        requestLocationPermission()
        binding.tvCurrentLocation.setOnClickListener {
            try {
                viewModel.searchLocation()
//                setCurrentLocationToMap()
            } catch (e: Exception){
                Log.d("dsadsaf","dsafas")
                e.printStackTrace()
            }
        }


    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener{
            val behavior = BottomSheetBehavior.from(dialog.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet))
            behavior.skipCollapsed = true
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
        return dialog
    }

    private fun requestLocationPermission(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){
                setCurrentLocationToMap()
            }
            else{
                Toast.makeText(requireContext(), "위치 접근 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                dismiss()
            }

        }
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }
    fun setCurrentLocationToMap(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            if (this::fusedLocationProvider.isInitialized) {
                fusedLocationProvider.lastLocation.addOnSuccessListener {
                    naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude)))
                }
            }

        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
        locationSource = FusedLocationSource(requireActivity(), LOCATION_PERMISSION_REQUEST_CODE)
        naverMap.locationSource = locationSource
        val uiSettings = map.uiSettings
        uiSettings.isLocationButtonEnabled = true
        setCurrentLocationToMap()

    }

    private fun getCurrentLocation(){

    }
    companion object{
        const val CLASS_NAME = "LocationRegisterBottomSheetFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }


}