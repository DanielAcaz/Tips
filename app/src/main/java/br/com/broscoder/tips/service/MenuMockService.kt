package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.R
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.model.Menu
import br.com.broscoder.tips.model.RestaurantItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.streams.toList

class MenuMockService(override val context: Context, private var menus: List<Menu> = Collections.emptyList()) : MenuService {

    override fun getMenus(): List<Menu> {
        val gson = Gson()
        val listType = object : TypeToken<List<RestaurantItems>>() { }.type
        val jsonString = context.getResources().openRawResource(R.raw.restaurants_menu).bufferedReader().use { it.readText() }
        this.menus = gson.fromJson<List<Menu>>(jsonString, listType)
        return menus
    }

    override fun getMenuById(id: Long): Menu {
        if (menus.isNullOrEmpty()) getMenus()
        return menus.let {
            it.stream().filter { id == id }.findFirst().orElseThrow { RestaurantItemNotFoundException() }
        }
    }

    override fun getMenusByRestaurantId(restaurantId: Long): List<Menu> {
        if (menus.isNullOrEmpty()) getMenus()
        return menus.let {
            it.stream().filter { restaurantId == it.restaurantId }.toList()
        }
    }


}