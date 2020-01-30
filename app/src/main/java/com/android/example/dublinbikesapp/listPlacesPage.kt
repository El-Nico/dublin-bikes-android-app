package com.android.example.dublinbikesapp

//import packages
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

//FARUKH HALEEM 16648
class listPlacesPage() : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_places_page)

        //url for bike stations in dublin from jcdecaux
        val url =
            "https://api.jcdecaux.com/vls/v1/stations/?apiKey=58c3f7df449ee36b7a278dde2c381ba85f204d2b&contract=Dublin"

        //asynchronours inner task to get stations from api
        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>() {
        //get our stations in background
        override fun doInBackground(vararg url: String?): String {
            var text: String
            //try to open a http connection
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text =
                    connection.inputStream.use { it.reader().use { reader -> reader.readText() } }
            } finally {
                connection.disconnect()
            }
            //return our stations
            return text
        }

        //when stations have been obtained
        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            //call a function to process/parse result
            handleJson(result)
        }
    }

    //this function will process and parse the asynchronous result, stations
    private fun handleJson(jsonString: String?) {
        //get the textview from listPlacesPage, this is where station data will be displayed
        val textView: TextView = findViewById(R.id.apiData) as TextView
        //this is the button that displays the stations on textview when clicked
        val listButton: Button = findViewById(R.id.listStations) as Button

        //convert stations to JSONArray
        val jsonArray = JSONArray(jsonString)

        //loop through stations and put new line at the end of each station
        var x = 0
        var tempString = ""
        while (x < jsonArray.length()) {
            tempString += jsonArray.getJSONObject(x).toString() + "\n"
            x++
        }
        listButton.setOnClickListener {
            //set apiData textview on button click
            textView.text = tempString
        }
    }

    //launch the listPlacesPage activity, this function is called by the LIST PAGE Button onClick from activity_list_places_page.xml
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //launch the recyclerViewListPlaces activity, this function is called by the RECYCLER LIST Button onClick from activity_list_places_page.xml
    fun switchToListRecyclePage(view: View) {
        val intent = Intent(this, recyclerViewListPlaces::class.java)
        startActivity(intent)
    }

    //launch the MainActivity activity, this function is called by the HOME Button onClick from activity_list_places_page.xml
    fun switchToHomePage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //launch the mapPage activity, this function is called by the MAP Button onClick from activity_list_places_page.xml
    fun switchToMapPage(view: View) {
        val intent = Intent(this, mapPage::class.java)
        startActivity(intent)
    }
}


