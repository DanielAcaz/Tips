package br.com.broscoder.tips.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Restaurant(var name: String, var latitude:Double, var longitude:Double) {

}