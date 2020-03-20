package com.example.camiotest.interfaces

import com.example.camiotest.data.MoviesPojo
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("popular?api_key=0bda8a0480d0c29189aa581a42c8000d")
    fun getPopularMovies(): Call<MoviesPojo>

    @GET("upcoming?api_key=0bda8a0480d0c29189aa581a42c8000d")
    fun getUpcomingMovies(): Call<MoviesPojo>

    @GET("top_rated?api_key=0bda8a0480d0c29189aa581a42c8000d")
    fun getTopRatedMovies(): Call<MoviesPojo>
}