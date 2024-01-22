// network/SpotsApi.kt

package com.example.wavetrackercompose.network

import android.util.Log
import com.example.wavetrackercompose.api.AirtableAPI
import com.example.wavetrackercompose.model.SpotDetails
import com.example.wavetrackercompose.model.SpotList
import com.example.wavetrackercompose.model.Spots
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object SpotsApi {
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.5.46:8080")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val airtableAPI: AirtableAPI = retrofit.create(AirtableAPI::class.java)


    suspend fun getSpots(): SpotList {
        val response = airtableAPI.getSpots()

        if (response.isSuccessful) {
            Log.v("spotsAPI", "chanmé")
            Log.v("spotsAPI", response.body().toString())

            return response.body() ?: SpotList(emptyList())
        } else {
            // Gérer les erreurs ici (par exemple, log ou lancer une exception)
            Log.v("spotsAPI", "error")
        }
        return SpotList(emptyList())
    }


    suspend fun getSpotDetails(spotId: String): SpotDetails? {  // Modifiez le type de retour ici
        val response = airtableAPI.getSpotDetails(spotId)

        return if (response.isSuccessful) {
            Log.v("spotsAPI", "Successful response")
            Log.v("spotsAPI", response.body().toString())

            response.body()
        } else {
            // Gérer les erreurs ici (par exemple, log ou lancer une exception)
            Log.v("spotsAPI", "Error response: ${response.code()}")

            null // Retournez une instance de SpotDetails vide en cas d'échec de la requête
        }

    }

}