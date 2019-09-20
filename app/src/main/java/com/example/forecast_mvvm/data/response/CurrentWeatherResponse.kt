package com.example.forecast_mvvm.data.response


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherForecast: CurrentWeatherForecast,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
)