package br.com.broscoder.tips.service

import android.content.Context
import br.com.broscoder.tips.model.TipsModel

interface TipsService {

    val context: Context

    fun getAll(): List<TipsModel>
    fun getById(id: Long) : TipsModel
    fun getByRestaurantId(restaurantId: Long): List<TipsModel>
    fun getAll(offset: Int, limit: Int) : List<TipsModel>
}