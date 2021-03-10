package com.mrz.worldcinema.api

import com.mrz.apikotlin.api.MovieGson
import com.mrz.apikotlin.api.Token
import com.mrz.worldcinema.data.MoviesGson
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiRequest {
    @Headers("Authorization: Bearer ktoya")
    @POST("auth/register")
    @FormUrlEncoded
    suspend fun signup(@Field("email") email: String,
                       @Field("password") password: String,
                       @Field("firstName") firstName: String,
                       @Field("lastName") lastName: String):Response<String>

    @POST("auth/login")
    @FormUrlEncoded
    suspend fun signin(@Field("email") email: String, @Field("password") password: String):Response<Token>

    @GET("movies/cover")
    fun getCover(): Response<MovieGson>

    @GET("/api/get")
    fun getData(): Call<MovieGson>

    @GET("movies")
    fun getMovies(): Response<MoviesGson>


}