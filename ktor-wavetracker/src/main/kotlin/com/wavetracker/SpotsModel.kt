package com.wavetracker

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonIgnoreProperties



@JsonIgnoreProperties(ignoreUnknown = true)
data class Fields(
    @JsonProperty("Geocode") val geocode: String,
    @JsonProperty("Photos") val photos: String,
    @JsonProperty("Peak Surf Season Begins") val peakSurfSeasonBegins: String,
    @JsonProperty("Destination State/Country") val destinationStateCountry: String,
    @JsonProperty("Peak Surf Season Ends") val peakSurfSeasonEnds: String,
    @JsonProperty("Difficulty Level") val difficultyLevel: Int,
    @JsonProperty("Destination") val destination: String,
    @JsonProperty("Surf Break") val surfBreak: String,
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


data class NewSpot(
    val geocode: String,
    val photos: String,
    val peakSurfSeasonBegins: String,
    val destinationStateCountry: String,
    val peakSurfSeasonEnds: String,
    val difficultyLevel: Int,
    val destination: String,
    val surfBreak: String,
    val address: String
)

