package com.android.example.dublinbikesapp


//FARUKH HALEEM 16648
// the model for a full bike station from jcDecaux
data class fullStation(

    val number: Int,
    val contract_name: String,
    val name: String,
    val address: String,
    val position: Position,
    val banking: Boolean,
    val bonus: Boolean,
    val bike_stands: Int,
    val available_bike_stands: Int,
    val available_bikes: Int,
    val status: String,
    val last_update: Int
)

//the model for position property of a station
data class Position(

    val lat: Double,
    val lng: Double
)

//station data class
data class Station(
    val name: String,
    val address: String
)