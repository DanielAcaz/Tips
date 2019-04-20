package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.model.TipsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.streams.toList

class RestaurantMockService : TipsService {

    private lateinit var restaurants: List<Restaurant>

    constructor(context: Context) {
        this.context = context
    }

    override fun getAll():List<Restaurant> {
        val gson = Gson()
        val listType = object : TypeToken<List<Restaurant>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_template).bufferedReader().use { it.readText() }
        this.restaurants = gson.fromJson<List<Restaurant>>(jsonString, listType)
        return restaurants
    }

    override fun getAll(offset: Int, limit: Int): List<Restaurant> {
        if (restaurants.isNullOrEmpty()) getAll()
        return restaurants.let {
            it.stream().skip(offset.toLong()).limit(limit.toLong()).toList()
        }
    }

    override fun getById(id: Long): Restaurant {
        if (restaurants.isNullOrEmpty()) getAll()
        return restaurants.let {
            it.stream().filter { id == id }.findFirst().orElseThrow { RestaurantItemNotFoundException() }
        }
    }

    override fun getByRestaurantId(restaurantId: Long): List<TipsModel> {
        if (restaurants.isNullOrEmpty()) getAll()
        return Arrays.asList(getById(restaurantId))
    }

    override var context: Context

}