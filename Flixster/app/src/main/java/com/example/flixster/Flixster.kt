package com.example.flixster


import android.content.Intent
import android.net.Uri
import com.google.gson.annotations.SerializedName

class Flixster {
    @JvmField
    @SerializedName("original_title")
    var title: String? = null

    @SerializedName("poster_path")
    var bookImageUrl: String? = null

    @SerializedName("overview")
    var description: String? = null

}