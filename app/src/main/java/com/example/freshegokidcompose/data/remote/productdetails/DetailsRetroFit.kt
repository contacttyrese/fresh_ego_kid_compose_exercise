package com.example.freshegokidcompose.data.remote.productdetails

import com.example.freshegokidcompose.data.remote.BaseService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

object DetailsRetroFit {
    private fun createRetroFit(): Retrofit {
        val client = OkHttpClient.Builder()
//            val interceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
//            client.addInterceptor(interceptor)
        return Retrofit.Builder()
            .baseUrl(BaseService.API_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client.build())
            .build()
    }

    fun createDetailsService(): DetailsService {
        return createRetroFit().create(DetailsService::class.java)
    }
}