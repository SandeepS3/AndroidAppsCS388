package com.codepath.articlesearch;
import android.support.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
class Movie:java.io.Serializable {
    @SerializedName("title")
    var title = "";

    @SerializedName("overview")
    var overview = "";


    @SerializedName("poster_path")
    var poster_path = "";

    @SerializedName("id")
    var id = 0;

    @SerializedName("release_date")
    var release_date = "";

    @SerializedName("vote_average")
    var vote_average = 0.0;

    @SerializedName("vote_count")
    var vote_count = 0;


    constructor(t:String, ov:String, url:String, id:Int, rd:String, va:Double, vc:Int) {
        this.title = t;
        this.overview = ov;
        this.poster_path = url;
        this.id = id;
        this.release_date = rd;
        this.vote_average = va;
        this.vote_count = vc;
    }
}