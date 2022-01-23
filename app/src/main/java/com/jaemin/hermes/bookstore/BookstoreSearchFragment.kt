package com.jaemin.hermes.bookstore

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.jaemin.hermes.R
import com.jaemin.hermes.base.BaseViewBindingFragment
import com.jaemin.hermes.base.EventObserver
import com.jaemin.hermes.book.view.data.BookUiModel
import com.jaemin.hermes.book.view.fragment.BookDetailFragment
import com.jaemin.hermes.bookstore.viewmodel.BookstoreSearchViewModel
import com.jaemin.hermes.databinding.FragmentBookstoreSearchBinding
import com.jaemin.hermes.entity.Bookstore
import com.jaemin.hermes.entity.Place
import com.jaemin.hermes.util.LocationUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import org.koin.androidx.viewmodel.ext.android.viewModel


class BookstoreSearchFragment : BaseViewBindingFragment<FragmentBookstoreSearchBinding>(),
    OnMapReadyCallback,
    LocationUtil.CurrentLocationCallback {

    private val viewModel: BookstoreSearchViewModel by viewModel()
    private lateinit var locationUtil: LocationUtil
    private lateinit var mapFragment: MapFragment
    private lateinit var naverMap: NaverMap
    private lateinit var marker: Marker
    private lateinit var bookstoreUrl: String
    private val markers = mutableListOf<Marker>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationUtil = LocationUtil(requireActivity(), this)
        setMap()
        binding.layoutSearchBookstore.clBookstoreInformation.setOnClickListener {
            if (this::bookstoreUrl.isInitialized) {
                val customTabIntent = CustomTabsIntent.Builder().build()
                customTabIntent.launchUrl(requireContext(), bookstoreUrl.toUri())
            }

        }
        if (!locationUtil.checkLocationPermission()) {
            locationUtil.requestLocationPermission(this)
        } else {
            viewModel.getCurrentLocation()
        }
    }

    override fun onResume() {
        super.onResume()
        if (!locationUtil.isLocationEnabled()) {
            Toast.makeText(requireContext(), "앱 > 설정에서 위치 접근 권한을 허용해주세요.", Toast.LENGTH_SHORT)
                .show()
            locationUtil.launchLocationSettings(requireActivity())
        }
    }

    override fun setViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentBookstoreSearchBinding = FragmentBookstoreSearchBinding.inflate(inflater, container, false)

    private fun setMap() {
        mapFragment = childFragmentManager.findFragmentById(R.id.fl_bookstore_map) as MapFragment?
            ?: MapFragment.newInstance().also {
                childFragmentManager.beginTransaction().add(R.id.fl_bookstore_map, it).commit()
            }
        mapFragment.getMapAsync(this)
    }
    override fun onMapReady(map: NaverMap) {
        naverMap = map
        naverMap.maxZoom = 18.0
        naverMap.minZoom = 10.0
        with(viewModel) {
            currentPlace.observe(viewLifecycleOwner) {
                setCurrentLocation(it)
                searchBookstoreByAddressWithRadius(it.longitude, it.latitude, 5000)
            }
            emptySavedLocationEvent.observe(viewLifecycleOwner, EventObserver {
                locationUtil.getCurrentLocation()
            })
            bookstores.observe(viewLifecycleOwner) {
                markers.forEach { marker ->
                    marker.map = null
                }
                it.forEachIndexed { index, place ->
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

    private fun setCurrentLocation(item: Place) {
        val cameraUpdate = CameraUpdate.scrollTo(LatLng(item.latitude, item.longitude))
            .animate(CameraAnimation.Easing)
        naverMap.moveCamera(cameraUpdate)
        if (this::marker.isInitialized) {
            marker.map = null
        }
        setMarker(item)
    }


    private fun setBookstoreMarker(item: Bookstore, index: Int) {

        setMarker(
            Place(
                item.name,
                item.roadAddress,
                item.latitude,
                item.longitude,
                item.phoneNumber
            )
        )
        marker.icon = MarkerIcons.BLACK
        marker.iconTintColor = ContextCompat.getColor(requireContext(), R.color.calmingCoral)
        if (!item.bookStock.isNullOrEmpty()) {
            val infoWindow = InfoWindow()
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(requireContext()) {
                override fun getText(p0: InfoWindow): CharSequence {
                    return item.bookStock!!
                }

            }
            infoWindow.open(marker)
        }
        marker.setOnClickListener {
            viewModel.bookstores.value?.get(index)?.let {
                binding.layoutSearchBookstore.clBookstoreInformation.animate()
                    .translationY(-binding.layoutSearchBookstore.clBookstoreInformation
                        .height.toFloat())
                setBookstoreInformation(it)
            }
            true
        }
        markers.add(marker)
    }

    private fun setCurrentLocationToMap(location: Location) {
        naverMap.moveCamera(
            CameraUpdate.scrollTo(LatLng(location.latitude, location.longitude))
                .animate(CameraAnimation.Easing)

        )
        viewModel.searchCurrentLocationPlace(location.longitude, location.latitude)
    }

    private fun setBookstoreInformation(bookstore: Bookstore) {
        binding.layoutSearchBookstore.tvBookstoreName.text = bookstore.name
        binding.layoutSearchBookstore.tvBookstoreAddress.text = bookstore.roadAddress
        binding.layoutSearchBookstore.tvBookstorePhone.text = bookstore.phoneNumber

        bookstoreUrl = bookstore.bookstoreUrl

    }

    override fun onGetCurrentLocationSuccess(location: Location) {
        setCurrentLocationToMap(location)
    }


}