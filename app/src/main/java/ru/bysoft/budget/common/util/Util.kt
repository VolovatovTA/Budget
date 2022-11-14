package ru.bysoft.budget.common.util

import android.content.Context
import android.content.res.AssetManager
import com.google.gson.Gson

fun Context.getStringFromAsset(filePath: String) =
    this.assets.open(filePath).bufferedReader().use { it.readText() }

fun <T> T.toJson() = Gson().toJson(this)
inline fun <reified T> String.restore() = Gson().fromJson(this, T::class.java)

const val pointJson = ".json"