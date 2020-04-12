package kz.yeltayev.aqms.api

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherApiServiceModule {

    private var retrofit: Retrofit

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
/*
* buildConfigField("String", "WEATHER_BIT_URL", "\"https://api.weatherbit.io/v2.0/forecast/daily?\"")
            buildConfigField("String", "WEATHER_BIT_API_KEY", "\"a3507d96676d41dabde682c792dc489a\"")
            *
* */
        retrofit = Retrofit.Builder()
            .baseUrl("https://api.weatherbit.io/v2.0/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    fun getWeatherService(): WeatherService {
        return retrofit.create(WeatherService::class.java)
    }

}