package com.jaemin.hermes.main.view.fragment

import android.Manifest
import android.app.Dialog
import android.content.pm.PackageManager
import android.os.Bundle
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
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
    private lateinit var fusedLocationProvider: FusedLocationProviderClient
    private lateinit var adapter : PlaceAdapter

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
        fusedLocationProvider = LocationServices.getFusedLocationProviderClient(requireActivity())
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
        ) {
            if (this::fusedLocationProvider.isInitialized) {
//                    fusedLocationProvider.lastLocation.addOnSuccessListener {
//                    naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude)).animate(CameraAnimation.Easing))
//                    viewModel.searchCurrentLocationPlace(it.longitude, it.latitude)
//                }
            }
            else{
                Log.d("dddd","not works")
            }

        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        val uiSettings = naverMap.uiSettings
        uiSettings.isLocationButtonEnabled = true
        viewModel.getCurrentLocation()

    }

    override fun onItemClick(item: Place) {
        setCurrentLocation(item)
        viewModel.currentPlace.value = item
    }
    private fun setCurrentLocation(item: Place){
        binding.etLocationRegister.setText(item.name)
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(item.latitude, item.longitude)).animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
        val marker = Marker()
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
    private fun getCurrentLocation(){

    }
    companion object{
        const val CLASS_NAME = "LocationRegisterBottomSheetFragment"
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1000
    }



}