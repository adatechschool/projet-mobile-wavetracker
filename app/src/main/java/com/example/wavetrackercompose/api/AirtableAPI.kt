// api/AirtableAPI.kt

package com.example.wavetrackercompose.api

import com.example.wavetrackercompose.model.SpotDetails
import com.example.wavetrackercompose.model.SpotList
import com.example.wavetrackercompose.model.Spots
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface AirtableAPI {
//    @GET("v0/{baseId}/{tableName}")
//    suspend fun getRecords(
//        @Path("baseId") baseId: String,
//        @Path("tableName") tableName: String,
//        @Header("Authorization") authorization: String
//    ): Response<ResponseModel>

    @GET("/spots")
    suspend fun getSpots(): Response<SpotList>

    @GET("/spots/{id}")
    suspend fun getSpotDetails(@Path("id") spotId: String): Response<SpotDetails>
}