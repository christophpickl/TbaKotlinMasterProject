package com.github.christophpickl.tbakotlinmasterproject.app

import com.hypercubetools.ktor.moshi.moshi
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonDataException
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.squareup.moshi.rawType
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import mu.KotlinLogging
import mu.KotlinLogging.logger
import java.math.BigDecimal
import java.time.Instant
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

private val log = logger {}

fun Application.installMoshi() {
    log.debug { "Installing moshi." }
    install(ContentNegotiation) {
        moshi(moshiInstance)
    }
}

private val moshiInstance = Moshi.Builder()
    .add(UUIDAdapter)
    .add(BigDecimalAdapter)
    .add(ZonedDateTimeAdapter)
    .add(EpochMilliZonedDateTimeAdapter)
    .add(InstantAdapter)
    .add(BooleanAdapter)
    .add(StringMapAdapter)
    .add(MoshiArrayListJsonAdapter.FACTORY)
    .add(MoshiEnumJsonAdapter.FACTORY)
    .addLast(KotlinJsonAdapterFactory())
    .build()

object UUIDAdapter {
    @ToJson
    fun toJson(value: UUID): String = value.toString()
    
    @FromJson
    fun fromJson(value: String): UUID = UUID.fromString(value)
}

object BigDecimalAdapter {
    @ToJson
    fun toJson(value: BigDecimal): String = value.toPlainString()
    
    @FromJson
    fun fromJson(value: String): BigDecimal = BigDecimal(value)
}

object InstantAdapter {
    @ToJson
    fun toJson(value: Instant): String = value.toString()
    
    @FromJson
    fun fromJson(value: String): Instant = Instant.parse(value)
}

object ZonedDateTimeAdapter {
    @ToJson
    fun toJson(value: ZonedDateTime): String = value.toString()
    
    @FromJson
    fun fromJson(value: String): ZonedDateTime = ZonedDateTime.parse(value, DateTimeFormatter.ISO_DATE_TIME)
}

@JsonQualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class EpochMilli

object EpochMilliZonedDateTimeAdapter {
    @ToJson
    fun toJson(@EpochMilli value: ZonedDateTime): Long = value.toInstant().toEpochMilli()
    
    @FromJson
    @EpochMilli
    fun fromJson(value: Long): ZonedDateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(value), ZoneOffset.UTC)
}

object BooleanAdapter {
    @ToJson
    fun toJson(value: Boolean): Boolean = value
    
    @FromJson
    fun fromJson(reader: JsonReader): Boolean {
        return when (val peek = reader.peek()) {
            JsonReader.Token.STRING -> reader.nextString().toBoolean()
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            else -> throw JsonDataException("Expected Boolean but was ${peek.name}")
        }
    }
}

object StringMapAdapter {
    
    @ToJson
    fun toJson(writer: JsonWriter, value: Map<String, String>) {
        writer.beginObject()
        value.forEach { (k, v) ->
            writer.name(k)
            writer.value(v)
        }
        writer.endObject()
    }
    
    @FromJson
    fun fromJson(reader: JsonReader): Map<String, String> {
        val map = mutableMapOf<String, String>()
        reader.beginObject()
        while (reader.hasNext()) {
            val name = reader.nextName()
            val value = when (val peek = reader.peek()) {
                JsonReader.Token.STRING,
                JsonReader.Token.NUMBER -> reader.nextString()
                JsonReader.Token.BOOLEAN -> reader.nextBoolean().toString()
                JsonReader.Token.NULL -> reader.nextNull<String>()
                else -> throw JsonDataException("Expected String but was ${peek.name}")
            }
            value?.let { map[name] = it }
        }
        reader.endObject()
        return map
    }
}

class MoshiEnumJsonAdapter<T : Enum<T>> private constructor(private val enumValues: Array<T>) : JsonAdapter<T>() {
    override fun fromJson(reader: JsonReader): T? {
        val value = reader.readJsonValue()
        return value?.let { enumValue ->
            enumValues.first { it.name.uppercase() == enumValue.toString().uppercase() }
        }
    }
    
    override fun toJson(writer: JsonWriter, value: T?) {
        writer.jsonValue(value?.toString())
    }
    
    companion object {
        val FACTORY = Factory { type, annotations, _ ->
            when {
                annotations.isNotEmpty() -> null
                type is Class<*> && type.isEnum -> {
                    @Suppress("UNCHECKED_CAST")
                    val values = (type.enumConstants as Array<out Enum<*>>)
                    MoshiEnumJsonAdapter(values)
                }
                else -> {
                    null
                }
            }
        }
    }
}


abstract class MoshiArrayListJsonAdapter<C : MutableCollection<T>?, T> private constructor(
    private val elementAdapter: JsonAdapter<T>
) : JsonAdapter<C>() {
    abstract fun newCollection(): C
    
    override fun fromJson(reader: JsonReader): C {
        val result = newCollection()
        reader.beginArray()
        while (reader.hasNext()) {
            result?.add(elementAdapter.fromJson(reader)!!)
        }
        reader.endArray()
        return result
    }
    
    override fun toJson(writer: JsonWriter, value: C?) {
        writer.beginArray()
        for (element in value!!) {
            elementAdapter.toJson(writer, element)
        }
        writer.endArray()
    }
    
    override fun toString(): String = "$elementAdapter.collection()"
    
    companion object {
        val FACTORY = Factory { type, annotations, moshi ->
            val rawType = Types.getRawType(type)
            
            return@Factory when {
                annotations.isNotEmpty() -> null
                rawType == List::class.java || rawType == ArrayList::class.java -> {
                    val elementType = Types.collectionElementType(type, MutableCollection::class.java)
                    newArrayListAdapter(moshi, elementType.rawType).nullSafe()
                }
                else -> null
            }
        }
        
        private fun <T> newArrayListAdapter(moshi: Moshi, clazz: Class<T>): JsonAdapter<MutableCollection<T>> {
            val elementAdapter: JsonAdapter<T> = moshi.adapter(clazz)
            
            return object :
                MoshiArrayListJsonAdapter<MutableCollection<T>, T>(elementAdapter) {
                override fun newCollection(): MutableCollection<T> = ArrayList()
            }
        }
    }
}
