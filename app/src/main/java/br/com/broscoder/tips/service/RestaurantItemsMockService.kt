package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.model.Items
import br.com.broscoder.tips.model.TipsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.streams.toList

class RestaurantItemsMockService: TipsService {

    var items: List<Items>

    constructor(context: Context) {
        items = Collections.emptyList()
        this.context = context
    }

    override fun getAll(): List<TipsModel> {
        val gson = Gson()
        val listType = object : TypeToken<List<Items>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_items_template).bufferedReader().use { it.readText() }
        this.items = gson.fromJson<List<Items>>(jsonString, listType)
        return items
    }

    override fun getById(id: Long): TipsModel {
        if (items.isNullOrEmpty()) getAll()
        return items.let {
            it.stream().filter { id == id }.findFirst().orElseThrow { RestaurantItemNotFoundException() }
        }
    }

    override fun getByRestaurantId(restaurantId: Long): List<TipsModel> {
        if (items.isNullOrEmpty()) getAll()
        return items.let {
            it.stream().filter { restaurantId == it.restaurantId }.toList()
        }
    }

    override fun getAll(offset: Int, limit: Int): List<TipsModel> {
        if (items.isNullOrEmpty()) getAll()
        return items.let {
            it.stream().skip(offset.toLong()).limit(limit.toLong()).toList()
        }
    }

    override val context: Context



}