package com.example.camiotest.data

import com.google.gson.annotations.SerializedName

data class MoviesPojo (@SerializedName("results") var movies:List<MoviePojo>)