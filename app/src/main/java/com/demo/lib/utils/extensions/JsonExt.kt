package com.demo.lib.utils.extensions

import com.demo.lib.utils.ContextAccessor
import com.demo.lib.utils.DiModuleUtil
import com.demo.lib.utils.RemoteEntryPoint
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.IOException
import java.lang.reflect.Type
import java.util.*

inline fun <reified T : Any?> T.toJson(): String? {

    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)

    val adapter = hiltRemoteEntryPoint
        .moshi()
        .adapter(T::class.java)

    return adapter.toJson(this)
}


inline fun <reified T> String.toJsonObject(): T? {

    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)

    val adapter = hiltRemoteEntryPoint
        .moshi()
        .adapter<T>(T::class.java)

    return adapter.fromJson(this)
}


@Throws(IOException::class)
inline fun <reified T> String.toJsonList(): List<T> {

    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)


    if (this.isEmpty()) return Collections.emptyList()


    val moshi: Moshi = hiltRemoteEntryPoint.moshi()


    val type: Type = Types.newParameterizedType(MutableList::class.java, T::class.java)


    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)


    whenNotNull(adapter.fromJson(this)) {
        return it
    }

    return Collections.emptyList()
}


inline fun <reified T> List<T>.toJson(): String? {


    val hiltRemoteEntryPoint =
        DiModuleUtil.accessEntryPoint<RemoteEntryPoint>(ContextAccessor.context)


    val moshi: Moshi = hiltRemoteEntryPoint.moshi()


    val type: Type = Types.newParameterizedType(MutableList::class.java, T::class.java)


    val adapter: JsonAdapter<List<T>> = moshi.adapter(type)


    return adapter.toJson(this)
}