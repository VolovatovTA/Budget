package ru.bysoft.android.budget.common.network.entity

import com.google.gson.annotations.SerializedName
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class CommonErrorBody(
    @SerializedName("slug")
    val slug: String
)



object EmptyAnswer : Call<Unit> {
    override fun clone(): Call<Unit> = this

    override fun execute(): Response<Unit> = Response.success(Unit)

    override fun enqueue(callback: Callback<Unit>) {}

    override fun isExecuted(): Boolean = true

    override fun cancel() {}

    override fun isCanceled(): Boolean = false

    override fun request(): Request = Request.Builder().build()

    override fun timeout(): Timeout = Timeout()

}