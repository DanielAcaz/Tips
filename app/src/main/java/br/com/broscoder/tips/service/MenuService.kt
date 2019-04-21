package br.com.broscoder.tips.service

import br.com.broscoder.tips.enums.MenuType
import br.com.broscoder.tips.model.Menu

interface MenuService : TipsService {

    fun getMenuTypesByRestaurantId(id: Long) : List<MenuType>
    fun getMenuByGroupId(groupId: Long): List<Menu>
    fun getMenuAcitveByRestaurantId(restaurantId: Long): List<Menu>
    fun getMenuAcitveById(id: Long): List<Menu>
    fun getMappedMenuActiveByRestaurantId(restaurantId: Long) : Map<MenuType, List<Menu>>


}