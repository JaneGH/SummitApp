package com.example.summitapp.model.remote
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type

class StringToIntAdapter : JsonDeserializer<Int> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): Int {
        return try {
            json.asInt
        } catch (e: Exception) {
            json.asString.toIntOrNull() ?: 0
        }
    }
}
