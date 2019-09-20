package com.example.forecast_mvvm.data

import com.example.forecast_mvvm.data.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "c72c141b838237625d6a0f0d8867f59f"

interface WeatherDataInterface {

// http://api.weatherstack.com/current?access_key=c72c141b838237625d6a0f0d8867f59f&query=New%20York

    @GET("current")
    fun getCurrentWeather(
        @Query("query") query: String
    ): Deferred<CurrentWeatherResponse>

    companion object {
        operator fun invoke(): WeatherDataInterface {
            val interceptor = Interceptor { chain ->

                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()


                return@Interceptor chain.proceed(request)
            }


            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build()


            return Retrofit.Builder().client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
                .create(WeatherDataInterface::class.java)
        }
    }


}