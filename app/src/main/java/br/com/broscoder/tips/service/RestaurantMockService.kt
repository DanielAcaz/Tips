package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Restaurant
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RestaurantMockService : RestaurantService {

    private lateinit var restaurants: List<Restaurant>

    constructor(context: Context) {
        this.context = context
    }

    override fun getRestaurants():List<Restaurant> {
        val gson = Gson()
        val listType = object : TypeToken<List<Restaurant>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_template).bufferedReader().use { it.readText() }
        this.restaurants = gson.fromJson<List<Restaurant>>(jsonString, listType)
        return restaurants
    }

    override fun getRestaurants(offset: Int, limit: Int): List<Restaurant> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getRestaurantById(id: Int): Restaurant {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override var context: Context

}