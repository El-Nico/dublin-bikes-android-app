package com.android.example.dublinbikesapp

//import needed packages
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_recycler_view_list_places.*
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//FARUKH HALEEM 16648
class recyclerViewListPlaces : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view_list_places)

        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager


        //api stations in dublin url
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=58c3f7df449ee36b7a278dde2c381ba85f204d2b&contract=Dublin"
        //get stations asynchronoursly
        AsyncTaskHandleJson().execute(url)
    }

    //this class will get the bike stations asynchronously
    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        //get the stations in background
        override fun doInBackground(vararg url: String?): String {
            //open a http connection to get stations
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

        //call the handleJson function when the stations have been obtain, this function will process, parse the result
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }

    //the function that will arrange the result
    private fun handleJson(jsonString: String?) {
        //convert stations to json array
        val jsonArray = JSONArray(jsonString)
        //make an array list of station data type
        val list = ArrayList<Station>()

        //loop through stations and add to the station list
        var x = 0
        while (x < jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(x)
            list.add(
                Station(
                    jsonObject.getString("name"),
                    jsonObject.getString("address")
                )
            )
            x++
        }

        //feed the list to adapter so it can bind to view
        val adapter = StationsAdapter(this, list.toMutableList())
        recyclerView.adapter = adapter
    }

    //launch the listPlacesPage activity, this function is called by the LIST PAGE Button onClick from activity_recycler_view_list_places.xml
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //launch the recyclerViewListPlaces activity, this function is called by the RECYCLER LIST Button onClick from activity_recycler_view_list_places.xml
    fun switchToListRecyclePage(view: View) {
        val intent = Intent(this, recyclerViewListPlaces::class.java)
        startActivity(intent)
    }

    //launch the MainActivity activity, this function is called by the HOME Button onClick from activity_recycler_view_list_places.xml
    fun switchToHomePage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //launch the mapPage activity, this function is called by the MAP Button onClick from activity_recycler_view_list_places.xml
    fun switchToMapPage(view: View) {
        val intent = Intent(this, mapPage::class.java)
        startActivity(intent)
    }

}
