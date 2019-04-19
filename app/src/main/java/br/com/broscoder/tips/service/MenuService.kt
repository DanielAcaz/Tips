package br.com.broscoder.tips.service

import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Menu
import br.com.broscoder.tips.model.RestaurantItems
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

interface MenuService: TipsService {

    fun getMenus():List<Menu>
    fun getMenuById(id: Long) : Menu
    fun getMenusByRestaurantId(restaurantId: Long): List<Menu>

}