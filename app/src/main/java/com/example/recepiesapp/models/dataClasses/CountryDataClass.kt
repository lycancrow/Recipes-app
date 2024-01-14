package com.example.recepiesapp.view.worldFoods

import com.google.gson.annotations.SerializedName

data class CountryDataClass(
    @SerializedName("name")
    val name: String,
    val flags: Flags,
    @SerializedName("demonym")
    val demonym: String
)

data class Flags(
    val svg: String,
    val png: String
)