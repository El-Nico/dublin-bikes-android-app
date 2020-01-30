package com.android.example.dublinbikesapp

//import packages
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_recycler_view_list_places.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//FARUKH HALEEM 16648
class mapPage : AppCompatActivity() {

    //initialize google maps
    lateinit var mapFragment: SupportMapFragment
    lateinit var googleMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map_page)

        //api url to get stations from jcDecaux
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=58c3f7df449ee36b7a278dde2c381ba85f204d2b&contract=Dublin"
        //get stations asynchronoursly
        AsyncTaskHandleJson().execute(url)
    }

    //inner class to get stations asynchronously
    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        //override the method to get stations in background
        override fun doInBackground(vararg url: String?): String {
            //open http connection and get stations as text
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text =
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            return text
        }

        // when the call is complete execute the handleJson method
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    //this function when handle the result of api call
    private fun handleJson(jsonString: String?) {
        //convert stations to jsonarray
        val jsonArray = JSONArray(jsonString)
        //this is the fragment view that will host the map
        mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        //get the map asynchronously
        mapFragment.getMapAsync(OnMapReadyCallback {
            googleMap = it
            //loop through the array of stations and extract each station object
            var y = 0
            while (y < jsonArray.length()) {
                //get the position object from the station object
                val jsonObject = jsonArray.getJSONObject(y).getJSONObject("position")
                //get the latitude and longitude from the position object and assign to location variable
                val location = LatLng(
                    jsonObject.getDouble("lat"),
                    jsonObject.getDouble("lng")
                )
                //add a marker based on the location variable, extract the station name and title to the marker
                googleMap.addMarker(
                    (MarkerOptions().position(location).title(
                        jsonArray.getJSONObject(
                            y
                        ).getString("name")
                    ))
                )
                //zoom appropriately into the map
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 15f))
                y++
            }
        })
    }

    //launch the listPlacesPage activity, this function is called by the LIST PAGE Button onClick from activity_map_page.xml
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //launch the recyclerViewListPlaces activity, this function is called by the RECYCLER LIST Button onClick from activity_map_page.xml
    fun switchToListRecyclePage(view: View) {
        val intent = Intent(this, recyclerViewListPlaces::class.java)
        startActivity(intent)
    }

    //launch the MainActivity activity, this function is called by the HOME Button onClick from activity_map_page.xml
    fun switchToHomePage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //launch the mapPage activity, this function is called by the MAP Button onClick from activity_map_page.xml
    fun switchToMapPage(view: View) {
        val intent = Intent(this, mapPage::class.java)
        startActivity(intent)
    }
}
