package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.model.Menu
import br.com.broscoder.tips.model.TipsModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.streams.toList

class MenuMockService(override val context: Context, private var menus: List<TipsModel> = Collections.emptyList()) : TipsService {

    override fun getAll(): List<TipsModel> {
        val gson = Gson()
        val listType = object : TypeToken<List<Menu>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_menu).bufferedReader().use { it.readText() }
        this.menus = gson.fromJson<List<Menu>>(jsonString, listType)
        return menus
    }

    override fun getById(id: Long): TipsModel {
        if (menus.isNullOrEmpty()) getAll()
        return menus.let {
            it.stream().filter { id == id }.findFirst().orElseThrow { RestaurantItemNotFoundException() }
        }
    }

    override fun getByRestaurantId(restaurantId: Long): List<TipsModel> {
        if (menus.isNullOrEmpty()) getAll()
        return (menus as List<Menu>).let {
            it.stream().filter { restaurantId == it.restaurantId }.toList()
        }
    }

    override fun getAll(offset: Int, limit: Int): List<TipsModel> {
        if (menus.isNullOrEmpty()) getAll()
        return menus.let {
            it.stream().skip(offset.toLong()).limit(limit.toLong()).toList()
        }
    }
}