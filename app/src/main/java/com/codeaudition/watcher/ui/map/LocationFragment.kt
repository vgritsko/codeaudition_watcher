package com.codeaudition.watcher.ui.map
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.codeaudition.watcher.data.FirebaseLocation
import com.codeaudition.watcher.databinding.FragmentLocationBinding
import com.codeaudition.watcher.ui.base.AppBaseFragment
import com.codeaudition.watcher.utils.extensions.DateExtensions
import com.codeaudition.watcher.utils.extensions.currentDate
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*


class LocationFragment : AppBaseFragment<FragmentLocationBinding>(), FirebaseDatabaseListener {
    private lateinit var mapView: MapView
    private lateinit var map: GoogleMap
    private lateinit var polyline: Polyline
    private var currLocationMarker: Marker? = null
    private val polylinePoints = mutableListOf<LatLng>()
    private var currentLocation: LatLng? = null
    private var previousLocation: LatLng? = null

    private lateinit var firebaseEventListener: FirebaseEventListenerHelper
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseEventListener = FirebaseEventListenerHelper(this)
        val date = Date().currentDate(DateExtensions.appDateFormat)
        database = Firebase.database.getReference("locations/$date")
        database.orderByKey().limitToLast(1)
        database.addChildEventListener(firebaseEventListener)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView = binding!!.mapView
        mapView.onCreate(savedInstanceState)
        mapView.onResume()

        try {
            MapsInitializer.initialize(requireContext())
        } catch (e: Exception) {
            e.printStackTrace()
        }
        mapView.getMapAsync {
            map = it
            polyline = map.addPolyline(PolylineOptions().addAll(polylinePoints))
        }
    }

    override fun onLocationAdded(location: FirebaseLocation) {
        currentLocation = LatLng(location.latitude, location.longitude)
        if (previousLocation == null || previousLocation != currentLocation) {

            previousLocation = currentLocation;
            polylinePoints.add(currentLocation!!);
            polyline.points = polylinePoints;

            if (currLocationMarker != null) {
                currLocationMarker!!.position = currentLocation!!;
            } else {
                currLocationMarker = map.addMarker(
                    MarkerOptions()
                        .position(currentLocation!!)
                )
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation!!, 12.0f));
            }

        }
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        database.removeEventListener(firebaseEventListener)
        mapView.onDestroy()
    }

    override fun setBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentLocationBinding = FragmentLocationBinding.inflate(inflater, container, false)
}
