package br.com.broscoder.tips.service

import br.com.broscoder.tips.model.RestaurantItems

interface RestaurantItemsService: TipsService {

    fun getRestaurantItems() : List<RestaurantItems>
    fun getRestaurantItemsById(id: Long) : RestaurantItems
    fun getRestaurantItemsByRestaurantId(restaurantId: Long) : List<RestaurantItems>

}