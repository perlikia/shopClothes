package com.example.dadata

import com.example.dadata.model.Address
import com.example.dadata.model.Point
import com.example.dadata.model.SuggestionsUnion
import kotlinx.serialization.encodeToString
import com.squareup.okhttp.MediaType
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.RequestBody
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonBuilder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder

class DadataHandler private constructor(){
    companion object {

        private val client = OkHttpClient()

        private var API_KEY: String? = null

        fun setKey(key: String){
            API_KEY = "Token $key"
        }

        fun geolocateAddress(lat: Double, lon: Double) : Address?{
            val path = "http://suggestions.dadata.ru/suggestions/api/4_1/rs/geolocate/address"

            val json = Json{
                ignoreUnknownKeys = true
            }

            val URL = prepareRequest(
                path,
                RequestBody.create(
                    MediaType.parse("application/json"),
                    json.encodeToString(
                        Point(lat, lon)
                    )
                )
            )

            client.newCall(
                URL
            ).execute().let {
                return json.decodeFromString<SuggestionsUnion>(it.body().string()).suggestions.firstOrNull()?.data
            }
        }

        private fun prepareRequest(url: String, body: RequestBody) : Request {
            return Request.
            Builder().
            url(url).
            header("Authorization", API_KEY).
            post(body).
            build()
        }
    }
}