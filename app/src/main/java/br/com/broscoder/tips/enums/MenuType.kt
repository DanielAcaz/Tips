package br.com.broscoder.tips.enums

enum class MenuType(var title:String) {

    MAIN_COURSE("Main Course"),
    APPETIZER("Appertizer"),
    OTHERS("Others"),
    VEGGIE_COURSE("Veggie Course"),
    SALAD("Salad"),
    DRINKS("Drinks"),
    DESSERT("Dessert");

    override fun toString(): String{
        return title
    }


}