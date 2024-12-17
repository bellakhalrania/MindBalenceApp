package iset.example.mindbalenceapp

import com.example.login_signuppi.UserApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



    object RetrofitInstance {
        private const val BASE_URL = "http://192.168.0.190:3000"


        val retrofit: Retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val userApi: UserApi by lazy {
            retrofit.create(UserApi::class.java)
        }


    }
