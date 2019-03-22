package br.com.broscoder.tips.view

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
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.adapter.RestaurantViewAdapter
import br.com.broscoder.tips.model.Restaurant
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback, GoogleMap.OnMapClickListener {

    private lateinit var mMap: GoogleMap

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

        val restaurants = ArrayList<Restaurant>();
        restaurants.add(Restaurant("Confraria Jardim", R.drawable.themartian))
        restaurants.add(Restaurant("Rancho das Figueiras", R.drawable.thewildrobot))
        restaurants.add(Restaurant("The Burguer Map", R.drawable.mariasemples))
        restaurants.add(Restaurant("Europa's Bar", R.drawable.themartian))
        restaurants.add(Restaurant("Rösti Bar e Batataria", R.drawable.hediedwith))
        restaurants.add(Restaurant("Garoupa Frutos do Mar", R.drawable.thevigitarian))
        restaurants.add(Restaurant("Fritz Cervejaria Artezanal", R.drawable.thewildrobot))
        restaurants.add(Restaurant("Toca do Tamanduá", R.drawable.mariasemples))
        restaurants.add(Restaurant("Baby Beef Jardim", R.drawable.themartian))
        restaurants.add(Restaurant("Fonte Leone", R.drawable.hediedwith))
        restaurants.add(Restaurant("Rosa's churrascaria", R.drawable.thevigitarian))
        restaurants.add(Restaurant("Cruzeiro's Bar", R.drawable.thewildrobot))
        restaurants.add(Restaurant("Tex Mex Madrecita", R.drawable.mariasemples))
        restaurants.add(Restaurant("Si Señor", R.drawable.themartian))
        restaurants.add(Restaurant("All In Burguer", R.drawable.hediedwith))
        restaurants.add(Restaurant("New Yorker Burger", R.drawable.thevigitarian))

        val myRecycler = findViewById<RecyclerView>(recycler_restaurants.id)
        val myAdapter = RestaurantViewAdapter(this, restaurants)
        myRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        myRecycler.adapter = myAdapter

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
        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMapClickListener(this)

        enableMyLocation()
        // Add a marker in res and move the camera
        val res1 = LatLng(-23.647322, -46.5400726)
        mMap.addMarker(MarkerOptions().position(res1).title("Marker in res"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(res1))
        

        val res2 = LatLng(-23.6488817, -46.5400823)
        mMap.addMarker(MarkerOptions().position(res2).title("Marker in res"))
        
        

        val res3 = LatLng(-23.6501265, -46.5395923)
        mMap.addMarker(MarkerOptions().position(res3).title("Marker in res"))
        
        

        val res4 = LatLng(-23.6502741, -46.5394789)
        mMap.addMarker(MarkerOptions().position(res4).title("Marker in res"))
        
        

        val res5 = LatLng(-23.6505611, -46.5390032)
        mMap.addMarker(MarkerOptions().position(res5).title("Marker in res"))
        
        

        val res6 = LatLng(-23.6493992, -46.5401684)
        mMap.addMarker(MarkerOptions().position(res6).title("Marker in res"))
        
        

        val res7 = LatLng(-23.6520983, -46.5382491)
        mMap.addMarker(MarkerOptions().position(res7).title("Marker in res"))
        

        val res8 = LatLng(-23.6532007, -46.5374512)
        mMap.addMarker(MarkerOptions().position(res8).title("Marker in res"))
        

        val res9 = LatLng(-23.653856, -46.5373311)
        mMap.addMarker(MarkerOptions().position(res9).title("Marker in res"))
        

        val res10 = LatLng(-23.6539032, -46.5397724)
        mMap.addMarker(MarkerOptions().position(res10).title("Marker in res"))
        

        val res11 = LatLng(-23.6542284, -46.5367881)
        mMap.addMarker(MarkerOptions().position(res11).title("Marker in res"))
        

        val res12 = LatLng(-23.6590383, -46.5362942)
        mMap.addMarker(MarkerOptions().position(res12).title("Marker in res"))
        

        val res13 = LatLng(-23.6605835, -46.5381558)
        mMap.addMarker(MarkerOptions().position(res13).title("Marker in res"))
        
        
        mMap.setMinZoomPreference(15.00f)
        
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun enableMyLocation() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
        } else if (mMap != null) {
            mMap.isMyLocationEnabled = true
        }

    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(this, "Current location:\n" + p0, Toast.LENGTH_LONG).show()
    }

    override fun onMapClick(p0: LatLng?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
