package ru.ilsave.testtask.networking

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    companion object {
        private val retrofit by lazy {
            val logging = HttpLoggingInterceptor()

            logging.setLevel(HttpLoggingInterceptor.Level.BODY)    // задаем уровень (бади потому что хотим видеть основу ответа)
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

            val base_url = "https://ilsave.onlyoffice.eu/"

            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(base_url)
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(TestApi::class.java)
        }
    }
}