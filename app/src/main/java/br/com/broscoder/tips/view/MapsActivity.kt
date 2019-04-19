package br.com.broscoder.tips.view

import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.factory.ItemsFactory
import br.com.broscoder.tips.factory.RestaurantFactory
import br.com.broscoder.tips.recycler.RestaurantViewAdapter
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.model.RestaurantItems
import br.com.broscoder.tips.recycler.RestaurantScrollListener
import br.com.broscoder.tips.service.RestaurantItemsService
import br.com.broscoder.tips.service.RestaurantService
import br.com.broscoder.tips.service.TipsService
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kotlinx.android.synthetic.main.activity_maps.*
import okio.Okio


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {

    private val scenario = Scenario.MOCK

    private var mMap: GoogleMap? = null
    private lateinit var restaurants: List<Restaurant>
    private lateinit var myRecycler: RecyclerView
    private lateinit var myAdapter: RestaurantViewAdapter
    private lateinit var myLayout: LinearLayoutManager
    private lateinit var service: TipsService

    var positionCard = 0

    companion object {
       private val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private var mPermissionDeined = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        service = RestaurantFactory(this).createServiceBy(scenario)
        this.restaurants = (service as RestaurantService).getRestaurants()
        service = ItemsFactory(this).createServiceBy(scenario)

        myRecycler = findViewById(recycler_restaurants.id)
        myAdapter = RestaurantViewAdapter(this, restaurants) {

            val item = (service as RestaurantItemsService)
                    .getRestaurantItemsByRestaurantId(it.id)
                    .stream()
                    .findFirst()
                    .orElseThrow {RestaurantItemNotFoundException()}

            val intent = Intent(this,RestaurantActivity::class.java)
            intent.putExtra("restaurant", it)
            intent.putExtra("item", item)
            startActivity(intent)
            finish()
        }
        myLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        myRecycler.layoutManager = myLayout
        myRecycler.adapter = myAdapter
        var myScrollListener = RestaurantScrollListener()
        myScrollListener.registerMap(this)
        myRecycler.addOnScrollListener(myScrollListener)

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near res, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.setOnMyLocationButtonClickListener(this)
        mMap?.setOnMapClickListener(this)
        mMap?.setOnMarkerClickListener(this)
        val style = MapStyleOptions.loadRawResourceStyle(
                this, R.raw.style_map)
        googleMap.setMapStyle(style)

        enableMyLocation()
        // Add a marker in res and move the camera
        markMainRestaurant()

    }

    fun markMainRestaurant() {
        mMap?.clear()
        restaurants.stream().forEach {
            if (it != restaurants[positionCard]) {
                val coordinate = LatLng(it.latitude, it.longitude)
                mMap?.addMarker(MarkerOptions().position(coordinate).title(it.name)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_unselected)))
            }
        }
        val coordinate = LatLng(restaurants[positionCard].latitude, restaurants[positionCard].longitude)
        mMap?.addMarker(MarkerOptions().position(coordinate).title(restaurants[positionCard].name)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_selected)))
        mMap?.moveCamera(CameraUpdateFactory.newLatLng(coordinate))
        mMap?.setMinZoomPreference(17.00f)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun enableMyLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else if (mMap != null) {
            mMap?.isMyLocationEnabled = true
        }

    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Current lotation:\n" + p0, Toast.LENGTH_LONG).show()
    }

    override fun onMapClick(p0: LatLng?) {

    }


    override fun onMarkerClick(marker: Marker?): Boolean {
        val restaurant = restaurants.find { r -> r.name.equals(marker?.title) }
        if(restaurant != null) {
            myRecycler.scrollToPosition((myAdapter.getPositionBy(restaurant)))
            return true
        } else {
            return false
        }
    }

}
