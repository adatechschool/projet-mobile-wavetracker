// network/RetrofitClient.kt

package com.example.wavetrackercompose.network

import android.util.Log
import com.example.wavetrackercompose.api.AirtableAPI
import com.example.wavetrackercompose.model.ResponseModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotsApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.airtable.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val airtableAPI: AirtableAPI = retrofit.create(AirtableAPI::class.java)


    suspend fun getSpots(): ResponseModel {
        val response = airtableAPI.getSpots(
            "appP8WO3YPoHlF1HN",
            "tbl2iE0NQ9ACxA8Gn",
            "Bearer patV1oTfwYPIfNlhX.6059a2190fb9f34ae9580a810c850a783049e592bc2748caa8d88cc161cc0fec"
        )

        if (response.isSuccessful) {
            Log.v("spotsAPI","chanmé")
            Log.v("spotsAPI", response.body().toString())
            // Assurez-vous que ResponseModel a une propriété contenant la liste des spots
        } else {
            // Gérer les erreurs ici (par exemple, log ou lancer une exception)
            Log.v("spotsAPI","error")
        }
        return response.body()?: ResponseModel(emptyList())
    }
}