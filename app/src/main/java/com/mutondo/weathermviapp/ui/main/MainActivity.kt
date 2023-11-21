package com.mutondo.weathermviapp.ui.main

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.google.android.material.navigation.NavigationView
import com.mutondo.weathermviapp.LocationService
import com.mutondo.weathermviapp.R
import com.mutondo.weathermviapp.databinding.ActivityMainBinding
import com.mutondo.weathermviapp.databinding.DialogFavoriteLocationBinding
import com.mutondo.weathermviapp.domain.models.FavoriteLocation
import com.mutondo.weathermviapp.ui.favorite.FavoriteFragment
import com.mutondo.weathermviapp.ui.favorite.FavoriteLocationIntent
import com.mutondo.weathermviapp.ui.favorite.FavoriteLocationViewModel
import com.mutondo.weathermviapp.ui.weather.WeatherFragment
import com.mutondo.weathermviapp.ui.weather.WeatherViewModel
import com.mutondo.weathermviapp.utils.Constants.LOCATION_REQUEST_CODE
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var favoriteViewModel: FavoriteLocationViewModel

    @Inject
    lateinit var weatherViewModel: WeatherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)
        setUpNavigationDrawer()

        if (savedInstanceState == null) {
            val weatherFragment = WeatherFragment()

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container, weatherFragment, WeatherFragment.TAG)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationService()
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, LocationService::class.java))
    }

    private fun setUpNavigationDrawer() {
        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, binding.appBarMain.toolbar,
            R.string.open_navigation_drawer, R.string.close_navigation_drawer
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
        binding.navView.bringToFront()
    }

    private fun showFragment(fragment: Fragment, fragmentTag: String) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container, fragment, fragmentTag)
            addToBackStack(null)
        }
    }

    private fun startLocationService() {

        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            startService(Intent(this, LocationService::class.java))
        } else {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("Location permission is required to get current location!")
                    .setTitle("Permission required")
                builder.setPositiveButton("OK") { dialog, id ->
                    ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
                }
                val dialog = builder.create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if(requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
            } else {
                // Permission denied - make request again
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_REQUEST_CODE)
            }
        }
    }

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.nav_weather -> {
                var weatherFragment = supportFragmentManager.findFragmentByTag(WeatherFragment.TAG)
                if(weatherFragment == null) {
                    weatherFragment = WeatherFragment()
                }
                showFragment(weatherFragment, WeatherFragment.TAG)
            }

            R.id.nav_favorites -> {
                var favoriteFragment = supportFragmentManager.findFragmentByTag(FavoriteFragment.TAG)
                if(favoriteFragment == null) {
                    favoriteFragment = FavoriteFragment()
                }
                showFragment(favoriteFragment, FavoriteFragment.TAG)
            }

//            R.id.nav_fav_loc_map -> {
//                val mapFragment = MapFragment()
//                showFragment(mapFragment, MapFragment.TAG)
//            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when(item.itemId) {
            R.id.action_favorite -> {
                saveLocation()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun saveLocation() {
        val binding = DialogFavoriteLocationBinding.inflate(layoutInflater)

        val latitude = "Latitude: ${weatherViewModel.latitude}"
        val longitude = "Longitude: ${weatherViewModel.longitude}"

        binding.locationLatitude.text = latitude
        binding.locationLongitude.text = longitude

        AlertDialog.Builder(this)
            .setView(binding.root)
            .setTitle("Location")
            .setPositiveButton("Save") { dialog, id ->
                val favorite = FavoriteLocation(
                    binding.locationName.text.toString(),
                    weatherViewModel.latitude.toDouble(),
                    weatherViewModel.longitude.toDouble(),
                )

                favoriteViewModel.setMviIntent(FavoriteLocationIntent.SaveFavoriteIntent(favorite))

                dialog.cancel()
            }
            .setNegativeButton("Cancel") { dialog, id ->
                dialog.cancel()
            }
            .show()
    }
}