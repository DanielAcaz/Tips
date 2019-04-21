package br.com.broscoder.tips.model

import br.com.broscoder.tips.enums.MenuType
import com.google.gson.annotations.SerializedName

data class Menu(var id: Long,
                @SerializedName("id_restaurant") var restaurantId: Long,
                var type: MenuType,
                var name: String,
                var description: String,
                var price: Double) : TipsModel