// model/YourResponseModel.kt

package com.example.wavetrackercompose.model

data class ResponseModel (
    val records : List<Record>
)

data class Record(
    val id : String,
    val fields : Field
)

data class Field(
    val Destination : String,
    val Address : String,
    val Photos : List<Photos>
)

data class Photos(
    val id : String,
    val url : String,
    val thumbnails : Thumbnail
)

data class Thumbnail(
    val small : Small
)

data class Small(
    val url: String
)