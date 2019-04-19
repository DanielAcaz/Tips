package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.model.Restaurant

interface RestaurantService: TipsService {

    fun getRestaurants() : List<Restaurant>
    fun getRestaurants(offset: Int, limit: Int) : List<Restaurant>
    fun getRestaurantById(id: Int) : Restaurant

}