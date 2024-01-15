// model/YourResponseModel.kt

package com.example.wavetrackercompose.model

import com.google.gson.annotations.SerializedName

data class ResponseModel (
    val records : List<Record>
)

data class Record(
    val id : String,
    val fields : Field
)

data class Field(
    val Destination : String,
    @field:SerializedName("Destination State/Country")
    val destinationStateCountry: String,
    val Address : String,
    val Photos : List<Photos>,
    @field:SerializedName("Difficulty Level")
    val difficultyLevel: Int,
    @field:SerializedName("Peak Surf Season Begins")
    val peakSurfSeasonBegins: String,
    @field:SerializedName("Peak Surf Season Ends")
    val peakSurfSeasonEnds: String,
    @field:SerializedName("Surf Break")
    val surfBreak: List<String>,
)

data class Photos(
    val id : String,
    val url : String,
    val thumbnails : Thumbnail
)

data class Thumbnail(
    val small : Small,
    val large : Large,
    val full : Full
)

data class Small(
    val url: String
)

data class Large(
    val url: String
)

data class Full(
    val url: String
)