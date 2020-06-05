package com.movies.network.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Genre {
    @SerializedName("name")
    @Expose
    String name;

    public String getName() {
        return name;
    }
}
