package com.wavetracker

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

data class Photo(
    val id: String,
    val width: Int,
    val height: Int,
    val url: String,
    val filename: String,
    val size: Int,
    val type: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(
    val small: Thumbnail,
    val large: Thumbnail,
    val full: Thumbnail
)

data class Thumbnail(
    val url: String,
    val width: Int,
    val height: Int
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class Fields(
    @JsonProperty("Geocode") val geocode: String,
    @JsonProperty("Magic Seaweed Link") val magicSeaweedLink: String,
    @JsonProperty("Photos") val photos: List<Photo>,
    @JsonProperty("Peak Surf Season Begins") val peakSurfSeasonBegins: String,
    @JsonProperty("Destination State/Country") val destinationStateCountry: String,
    @JsonProperty("Peak Surf Season Ends") val peakSurfSeasonEnds: String,
    @JsonProperty("Difficulty Level") val difficultyLevel: Int,
    @JsonProperty("Destination") val destination: String,
    @JsonProperty("Surf Break") val surfBreak: List<String>,
    @JsonProperty("Address") val address: String
)

data class Record(
    val id: String,
    @JsonProperty("createdTime") val createdTime: String,
    val fields: Fields
)

data class SpotData(
    val records: List<Record>
)

data class SpotsObject(
    val spotList: List<SpotList>
)

