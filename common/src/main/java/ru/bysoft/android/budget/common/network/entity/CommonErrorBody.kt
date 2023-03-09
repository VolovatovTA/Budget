package ru.bysoft.android.budget.common.network.entity

import com.google.gson.annotations.SerializedName

data class CommonErrorBody(
    @SerializedName("slug")
    val slug: String
)