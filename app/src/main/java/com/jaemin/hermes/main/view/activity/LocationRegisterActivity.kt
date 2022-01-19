package com.jaemin.hermes.main.view.activity

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.provider.Settings
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingActivity
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.databinding.ActivityLocationRegisterBinding
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.main.view.adapter.PlaceAdapter
import com.jaemin.hermes.main.viewmodel.LocationRegisterViewModel
import com.jaemin.hermes.util.LocationUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import org.koin.androidx.viewmodel.ext.android.viewModel

class LocationRegisterActivity : BaseViewBindingActivity<ActivityLocationRegisterBinding>(), OnMapReadyCallback,
    PlaceAdapter.OnItemClickListener, LocationUtil.CurrentLocationCallback {
    private val viewModel: LocationRegisterViewModel by viewModel()
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var adapter: PlaceAdapter
    private lateinit var marker: Marker
    private lateinit var locationUtil: LocationUtil
    private var isMovingMapUnLocked = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        locationUtil = LocationUtil(this,this)
        setMapFragment()
        locationUtil.requestLocationPermission(this)
        setSearchLocationView()
        viewModel.getCurrentLocation()
        binding.tvSetToCurrentLocation.setOnClickListener {
            isMovingMapUnLocked = true
            locationUtil.getCurrentLocation()
        }
        binding.btnRegisterLocation.setOnClickListener {
            viewModel.saveCurrentLocation()
        }
        adapter = PlaceAdapter(this)
        binding.rvLocation.adapter = adapter


        with(viewModel) {
            location.observe(this@LocationRegisterActivity) {
                searchPlaces()
            }
            places.observe(this@LocationRegisterActivity) {
                adapter.submitList(it)
            }
            saveLocationSuccessEvent.observe(this@LocationRegisterActivity, EventObserver {
                Toast.makeText(this@LocationRegisterActivity, "위치 등록에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
                finish()
            })

        }


    }

    private fun setSearchLocationView() {
        binding.etLocationRegister.setOnEditorActionListener { p0, actionId, p2 ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                viewModel.searchPlaces()
                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        }
        binding.etLocationRegister.addTextChangedListener(object : TextWatcher {
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



    private fun setCurrentLocationToMap(location: Location) {
        naverMap.moveCamera(
            CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))
                .animate(CameraAnimation.Easing)
        )
        viewModel.searchCurrentLocationPlace(location.longitude, location.latitude)
    }

    override fun onResume() {
        super.onResume()
        if (!locationUtil.isLocationEnabled()) {
            Toast.makeText(this,"앱 > 설정에서 위치 접근 권한을 허용해주세요.", Toast.LENGTH_SHORT).show()
            launchLocationSettings()
        }
    }

    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        with(viewModel){
            currentPlace.observe(this@LocationRegisterActivity) {
                isMovingMapUnLocked = false
                setCurrentLocation(it)
            }
            emptySavedLocationEvent.observe(this@LocationRegisterActivity, EventObserver {
                locationUtil.getCurrentLocation()
            })
        }

    }

    override fun onItemClick(item: Place) {
        setCurrentLocation(item)
        binding.etLocationRegister.setText(item.name)
        viewModel.currentPlace.value = item
    }

    private fun setCurrentLocation(item: Place) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(item.latitude, item.longitude))
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
        if (this::marker.isInitialized) {
            marker.map = null
        }
        setMarker(item)
        hideKeyboard()
        binding.rvLocation.visibility = View.GONE
        binding.tvPlaceName.text = item.name
        binding.tvPlaceAddress.text = item.roadAddress
        binding.clRegisterLocation.visibility = View.VISIBLE
    }

    private fun setMarker(item: Place) {
        marker = Marker()
        marker.position = LatLng(item.latitude, item.longitude)
        marker.map = naverMap
        marker.captionText = item.name
    }

    private fun setMapFragment() {
        mapFragment = supportFragmentManager.findFragmentById(R.id.fl_location) as MapFragment?
            ?: MapFragment.newInstance().also {
                supportFragmentManager.beginTransaction().add(R.id.fl_location, it).commit()
            }
        mapFragment.getMapAsync(this)
    }

    private fun hideKeyboard() {
        (getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager)
            ?.hideSoftInputFromWindow(binding.root.windowToken, 0)
    }

    private fun launchLocationSettings() {
        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
        startActivity(intent)
    }

    override fun setViewBinding(inflater: LayoutInflater): ActivityLocationRegisterBinding {
        return ActivityLocationRegisterBinding.inflate(inflater)
    }

    override fun onGetCurrentLocationSuccess(location: Location) {
        if (isMovingMapUnLocked){
           setCurrentLocationToMap(location)
        }

    }


}