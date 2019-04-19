package br.com.broscoder.tips.model

data class Menu(var id: Long,
                var restaurantId: Long,
                var type: String,
                var name: String,
                var description: String,
                var price: Double) {
}