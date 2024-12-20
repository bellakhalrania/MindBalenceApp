package iset.example.mindbalenceapp.ui

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClientTask {

        private const val BASE_URL = "http://192.168.1.107:8888/api/tasks" // Adresse localhost pour l'émulateur Android

        val instance: TaskApi by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(TaskApi::class.java)
        }
    }