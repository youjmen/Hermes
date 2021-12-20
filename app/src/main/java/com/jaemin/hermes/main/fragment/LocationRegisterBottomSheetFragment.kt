package com.jaemin.hermes.main.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaemin.hermes.R
import com.jaemin.hermes.databinding.FragmentLocationRegisterBottomSheetBinding
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.MapFragment
import com.naver.maps.map.NaverMap
import com.naver.maps.map.OnMapReadyCallback
import com.naver.maps.map.util.FusedLocationSource

class LocationRegisterBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentLocationRegisterBottomSheetBinding
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var locationSource : FusedLocationSource

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

        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true &&
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true){
            }
            else{
                Toast.makeText(requireContext(), "위치 접근 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
                requireActivity().finish()
            }

        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))

    }
    companion object{
        const val CLASS_NAME = "LocationRegisterBottomSheetFragment"
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        locationSource = FusedLocationSource(this, 100)
        map.locationSource = locationSource
        val uiSettings = map.uiSettings
        uiSettings.isLocationButtonEnabled = true


    }


}