package com.android.example.dublinbikesapp

//import needed packages
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*

//FARUKH HALEEM 16648
//this adapter will bind the station list to recycler view
class StationsAdapter(val context: Context, val stations: MutableList<Station>) :
    RecyclerView.Adapter<StationsAdapter.MyViewHolder>() {

    //create the view holder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return MyViewHolder(view)
    }

    // the number of items on the list
    override fun getItemCount(): Int {
        return stations.size
    }

    //when the view is ready call the set data function
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val station = stations[position]
        holder.setData(station, position)
    }

    //prepare the view holder
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentStation: Station? = null
        var currentPosition: Int = 0


        // when a station is clicked on the list, display the name in a toast
        init {
            itemView.setOnClickListener {
                Toast.makeText(context, currentStation!!.name + " Clicked !", Toast.LENGTH_LONG)
                    .show()
            }
        }

        //this function will be called to set the text on the list when the view is ready
        fun setData(station: Station, pos: Int) {
            itemView.txvName.text = station!!.name
            itemView.txvAddress.text = station!!.address

            this.currentStation = station
            this.currentPosition = pos
        }
    }
}