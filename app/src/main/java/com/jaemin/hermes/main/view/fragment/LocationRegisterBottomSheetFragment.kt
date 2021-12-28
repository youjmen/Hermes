package com.jaemin.hermes.main.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaemin.hermes.R
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.databinding.FragmentLocationRegisterBottomSheetBinding
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.main.view.adapter.PlaceAdapter
import com.jaemin.hermes.main.viewmodel.LocationRegisterViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationRegisterBottomSheetFragment : BottomSheetDialogFragment(), OnMapReadyCallback, PlaceAdapter.OnItemClickListener {
    private val viewModel : LocationRegisterViewModel by viewModel()
    private lateinit var binding: FragmentLocationRegisterBottomSheetBinding
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var adapter : PlaceAdapter
    private lateinit var marker: Marker

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
        binding.tvSetToCurrentLocation.setOnClickListener {
            try {
                setCurrentLocationToMap()
            } catch (e: Exception){
                Log.d("dsadsaf","dsafas")
                e.printStackTrace()
            }
        }
        binding.btnRegisterLocation.setOnClickListener {
            viewModel.saveCurrentLocation()
        }
        setSearchLocationView()
        adapter = PlaceAdapter(this)
        binding.rvLocation.adapter = adapter


        with(viewModel){
            location.observe(viewLifecycleOwner){
                searchPlaces()
            }
            places.observe(viewLifecycleOwner){
                adapter.submitList(it)
            }
            currentPlace.observe(viewLifecycleOwner){
                setCurrentLocation(it)
            }
            saveLocationSuccessEvent.observe(viewLifecycleOwner, EventObserver{
                Toast.makeText(requireContext(), "위치 등록에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                dismiss()
            })
            emptySavedLocationEvent.observe(viewLifecycleOwner, EventObserver{
                setCurrentLocationToMap()
            })
        }

    }
    private fun setSearchLocationView(){
        binding.etLocationRegister.setOnEditorActionListener { p0, actionId, p2 ->
            if(actionId == EditorInfo.IME_ACTION_SEARCH){
                viewModel.searchPlaces()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.etLocationRegister.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.location.value = binding.etLocationRegister.text.toString()
                binding.rvLocation.visibility = View.VISIBLE
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })
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

                fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())
                if (this::fusedLocationProviderClient.isInitialized) {
                    getCurrentLocation()
                }
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
    private fun setCurrentLocationToMap(){
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity())

            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location?->
                    location?.let {
                        naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude))
                            .animate(CameraAnimation.Easing))
                        viewModel.searchCurrentLocationPlace(it.longitude, it.latitude)

                    }
                    Log.d("DSafas", location.toString())
                }
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0


    }

    override fun onItemClick(item: Place) {
        setCurrentLocation(item)
        viewModel.currentPlace.value = item
    }
    private fun setCurrentLocation(item: Place){
        binding.etLocationRegister.setText(item.name)
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(item.latitude, item.longitude)).animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
        if (this::marker.isInitialized) {
            marker.map = null
        }
        marker = Marker()
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
        marker.captionText = item.name
        hideKeyboard()
        binding.rvLocation.visibility = View.GONE
        binding.tvPlaceName.text = item.name
        binding.tvPlaceAddress.text = item.roadAddress
        binding.clRegisterLocation.visibility = View.VISIBLE
    }
    private fun hideKeyboard(){
        (context?.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(view?.windowToken, 0)
    }
    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            val locationRequest = LocationRequest.create()
            locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest.interval = 20 * 1000
            val locationCallback = object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    Log.d("lresult","ddd")
                    if (!locationResult.equals(null)) {
                        return
                    }
                    for (location in locationResult.locations) {
                        if (location != null) {
                            val latitude = location.latitude
                            val longitude = location.longitude
                            Log.d(
                                "Test", "GPSLocation changed, Latitude: $latitude" +
                                        ", Longitude: $longitude"
                            )
                            fusedLocationProviderClient.removeLocationUpdates(this)

                        }
                        else{
                            Log.d(
                                "Test", "GPSLocation not changed"
                            )
                        }
                    }
                }
            }
            fusedLocationProviderClient.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }
    }
    companion object{
        const val CLASS_NAME = "LocationRegisterBottomSheetFragment"
    }



}