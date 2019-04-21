package br.com.broscoder.tips.model

data class Order(var name: String, var price: Double) : TipsModel {
    fun isValid() : Boolean {
        return !(name.isNullOrEmpty() && price <= 0)
    }
}