package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.broscoder.tips.R

class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)
    }

    fun openTable(view: View) {
        intent = Intent(this, OrdersActivity::class.java)
        startActivity(intent)
    }
}
