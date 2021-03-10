package com.mrz.worldcinema.MainActivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.gson.GsonBuilder
import com.mrz.worldcinema.R
import com.mrz.worldcinema.api.ApiRequest
import com.mrz.worldcinema.constants.Constants.Companion.BASE_URL
import com.mrz.worldcinema.constants.Constants.Companion.IMG_URL
import com.squareup.picasso.Picasso
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.CallAdapter
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getImg()
        getMovies()

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


    private fun getImg() {
        val api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(ApiRequest::class.java)

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
}