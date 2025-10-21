package com.example.summitapp.model.remote

import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StringToDoubleAdapter : JsonDeserializer<Double> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Double {
        return try {
            json.asDouble
        } catch (e: Exception) {
            json.asString.toDoubleOrNull() ?: 0.0
        }
    }
}