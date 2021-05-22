package com.example.testcompose.network

import com.example.testcompose.utils.BuildType
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.BODY
import okhttp3.logging.HttpLoggingInterceptor.Level.NONE
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*

private const val SSL = "SSL"

val networkModule = module {

    single { createOkHttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideGson() }
    single { provideApi(retrofit = get()) }
}

fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = initLoggingInterceptor()
    val trustAllCerts = initTrustManager()
    val sslSocketFactory: SSLSocketFactory = initSslSocketFactory(trustAllCerts)
    val okHttpClientBuilder = initOkHttpBuilder(
        sslSocketFactory,
        trustAllCerts,
        httpLoggingInterceptor
    )

    return okHttpClientBuilder.build()
}

private fun initLoggingInterceptor(): HttpLoggingInterceptor {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = if (BuildType.isDebug()) BODY else NONE
    return httpLoggingInterceptor
}

private fun initTrustManager(): Array<TrustManager> = arrayOf(
    object : X509TrustManager {
        override fun checkClientTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) {
        }

        override fun checkServerTrusted(
            chain: Array<out X509Certificate>?,
            authType: String?
        ) {
        }

        override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
    }
)

private fun initSslSocketFactory(trustAllCerts: Array<TrustManager>): SSLSocketFactory {
    val sslContext = SSLContext.getInstance(SSL)
    sslContext.init(null, trustAllCerts, SecureRandom())
    return sslContext.socketFactory
}

private fun initOkHttpBuilder(
    sslSocketFactory: SSLSocketFactory,
    trustAllCerts: Array<TrustManager>,
    httpLoggingInterceptor: HttpLoggingInterceptor,
): OkHttpClient.Builder {

    return OkHttpClient
        .Builder()
        .sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .writeTimeout(60L, TimeUnit.SECONDS)
        .addInterceptor(httpLoggingInterceptor)
}

fun provideGson(): Gson = GsonBuilder().create()

fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
    return Retrofit.Builder()
        .baseUrl(" https://aitec.one/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)


