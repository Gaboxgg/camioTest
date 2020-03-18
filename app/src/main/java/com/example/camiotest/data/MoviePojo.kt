package com.example.camiotest.data

import com.google.gson.annotations.SerializedName

data class MoviePojo (@SerializedName ("popularity") var popularity:String="",
                      @SerializedName("vote_count") var vote_count:String="",
                      @SerializedName("video") var video:String="",
                      @SerializedName("poster_path") var poster_path:String="",
                      @SerializedName  ("id") var id:String="",
                      @SerializedName  ("adult") var adult:String="",
                      @SerializedName  ("backdrop_path") var backdrop_path:String="",
                      @SerializedName  ("original_language") var original_language:String="",
                      @SerializedName  ("original_title") var original_title:String="",
                      @SerializedName  ("title") var title:String="",
                      @SerializedName  ("vote_average") var vote_average:String="",
                      @SerializedName  ("overview") var overview:String="",
                      @SerializedName  ("release_date") var release_date:String=""
//                      @SerializedName  ("objectID") var objectID:String=""
)