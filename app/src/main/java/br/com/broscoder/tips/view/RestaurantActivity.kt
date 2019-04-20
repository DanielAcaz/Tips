package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.model.Items
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_restaurant.*

class RestaurantActivity : AppCompatActivity() {

    lateinit var restaurant: Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        this.restaurant = intent.getParcelableExtra<Restaurant>("restaurant")
        val item = intent.getParcelableExtra<Items>("item")
        restaurant_name.text = restaurant.name
        getRestaurantImage(restaurant)
        lotation.text = item.lotation
        waitting_time.text = inMiniute(item.waitTime).toString() + " min."
        price.text = "R$ " + item.price
    }

    private fun getRestaurantImage(restaurant: Restaurant) {
        if (isNotNullAndNotEmpty(restaurant)) {
            Picasso.get().load(restaurant.image).into(restaurant_image)
        }
    }

    private fun isNotNullAndNotEmpty(restaurant: Restaurant) =
            !restaurant.image.isNullOrEmpty()

    fun openTable(view: View) {
        intent = Intent(this, OrdersActivity::class.java)
        startActivity(intent)
    }

    private fun inMiniute(miliseconds: Long) : Long {
        return (miliseconds/1000)/60
    }

    fun openMenu(view: View) {
        intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("restaurant_id", this.restaurant.id)
        startActivity(intent)
    }
}
