package com.mikhaylenko.weatherdemo.data

import okhttp3.Interceptor
import okhttp3.Response

private const val API_KEY_QUERY_PARAMETER = "key"

class WeatherApiKeyInterceptor(
    private val apiKey: String
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val urlWithApiKey = request.url
            .newBuilder()
            .setQueryParameter(API_KEY_QUERY_PARAMETER, apiKey)
            .build()

        return chain.proceed(
            request.newBuilder()
                .url(urlWithApiKey)
                .build()
        )
    }
}
