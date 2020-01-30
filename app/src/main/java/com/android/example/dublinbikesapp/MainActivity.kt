package com.android.example.dublinbikesapp

//import packages
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

//FARUKH HALEEM 16648
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    //launch the listPlacesPage activity, this function is called by the LIST PAGE Button onClick from activity_main.xml
    fun switchToListPage(view: View) {
        val intent = Intent(this, listPlacesPage::class.java)
        startActivity(intent)
    }

    //launch the recyclerViewListPlaces activity, this function is called by the RECYCLER LIST Button onClick from activity_main.xml
    fun switchToListRecyclePage(view: View) {
        val intent = Intent(this, recyclerViewListPlaces::class.java)
        startActivity(intent)
    }

    //launch the MainActivity activity, this function is called by the HOME Button onClick from activity_main.xml
    fun switchToHomePage(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    //launch the mapPage activity, this function is called by the MAP Button onClick from activity_main.xml
    fun switchToMapPage(view: View) {
        val intent = Intent(this, mapPage::class.java)
        startActivity(intent)
    }
}
