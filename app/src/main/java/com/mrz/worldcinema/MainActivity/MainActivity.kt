package com.mrz.worldcinema.MainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.mrz.apikotlin.api.MovieGson
import com.mrz.worldcinema.R
import com.mrz.worldcinema.api.ApiRequest
import com.mrz.worldcinema.constants.Constants.Companion.BASE_URL
import com.mrz.worldcinema.constants.Constants.Companion.IMG_URL
import com.mrz.worldcinema.data.MoviesGson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getImg()
        getMovies()

    }

    private fun getImg() {
        val api = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .build()
                .create(ApiRequest::class.java)

        api.getData()
                .enqueue(object : Callback<MovieGson> {
                    override fun onFailure(call: Call<MovieGson>, t: Throwable) {
                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val response = api.getCover()
                                withContext(Dispatchers.Main) {
                                    val backgroundImage = response.body()?.backgroundImage
                                    val foregroundImage = response.body()?.foregroundImage
                                    Log.e("Main", response.body().toString())
                                    Glide.with(applicationContext).load(IMG_URL+backgroundImage.toString()).into(ivHeaderBg)
                                    Glide.with(applicationContext).load(IMG_URL+foregroundImage.toString()).into(ivHeaderFg)
                                }
                            }
                            catch (e: Exception){
                                Log.e("Main", "Error: ${e.message}")
                            }
                        }
                    }

                    override fun onResponse(call: Call<MovieGson>, response: Response<MovieGson>) {
                        GlobalScope.launch(Dispatchers.IO) {
                            try {
                                val response = api.getCover()
                                withContext(Dispatchers.Main) {
                                    val backgroundImage = response.body()?.backgroundImage
                                    val foregroundImage = response.body()?.foregroundImage
                                    Log.e("Main", response.body().toString())
                                    Glide.with(applicationContext).load(IMG_URL+backgroundImage.toString()).into(ivHeaderBg)
                                    Glide.with(applicationContext).load(IMG_URL+foregroundImage.toString()).into(ivHeaderFg)
                                }
                            }
                            catch (e: Exception){
                                Log.e("Main", "Error: ${e.message}")
                            }
                        }
                    }
                })

//        GlobalScope.launch(Dispatchers.IO) {
//            try {
//                val response = api.getCover()
//                withContext(Dispatchers.Main) {
//                    val backgroundImage = response.body()?.backgroundImage
//                    val foregroundImage = response.body()?.foregroundImage
//                    Log.e("Main", response.body().toString())
//                    Glide.with(applicationContext).load(IMG_URL+backgroundImage.toString()).into(ivHeaderBg)
//                    Glide.with(applicationContext).load(IMG_URL+foregroundImage.toString()).into(ivHeaderFg)
//                }
//            }
//            catch (e: Exception){
//                Log.e("Main", "Error: ${e.message}")
//            }
//        }
    }

    fun getMovies() {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiRequest::class.java)

        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = api.getMovies()
                withContext(Dispatchers.Main) {
                    Log.e("Main", response.body().toString())
                }
            }
            catch (e: Exception){
                Log.e("Main", "Error: ${e.message}")
            }
        }
    }



}