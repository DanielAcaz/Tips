package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.model.RestaurantItems
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        val restaurant = intent.getParcelableExtra<Restaurant>("restaurant")
        val item = intent.getParcelableExtra<RestaurantItems>("item")
        restaurant_name.text = restaurant.name
        lotation.text = item.lotation
        waitting_time.text = item.waitTime.toString()
        price.text = "R$ " + item.price
    }

    fun openTable(view: View) {
        intent = Intent(this, OrdersActivity::class.java)
        startActivity(intent)
    }
}
