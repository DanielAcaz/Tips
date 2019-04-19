package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.model.RestaurantItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.streams.toList

class RestaurantItemsMockService: RestaurantItemsService {

    var items: List<RestaurantItems>

    constructor(context: Context) {
        items = Collections.emptyList()
        this.context = context
    }

    override fun getRestaurantItems(): List<RestaurantItems> {
        val gson = Gson()
        val listType = object : TypeToken<List<RestaurantItems>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_items_template).bufferedReader().use { it.readText() }
        this.items = gson.fromJson<List<RestaurantItems>>(jsonString, listType)
        return items
    }

    override fun getRestaurantItemsById(id: Long): RestaurantItems {
        if (items.isNullOrEmpty()) getRestaurantItems()
        return items.let {
            it.stream().filter { id == id }.findFirst().orElseThrow { RestaurantItemNotFoundException() }
        }
    }

    override fun getRestaurantItemsByRestaurantId(restaurantId: Long): List<RestaurantItems> {
        if (items.isNullOrEmpty()) getRestaurantItems()
        return items.let {
            it.stream().filter { restaurantId == restaurantId }.toList()
        }
    }

    override val context: Context



}