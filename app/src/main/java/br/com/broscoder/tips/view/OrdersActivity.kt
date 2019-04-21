package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Order
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.recycler.OrderViewAdapter
import kotlinx.android.synthetic.main.activity_orders.*
import kotlin.math.log

class OrdersActivity : AppCompatActivity() {

    companion object {
        private var orders: MutableList<Order> = ArrayList()
    }
    private lateinit var myRecycler: RecyclerView
    private lateinit var myAdapter: OrderViewAdapter
    private lateinit var restaurant: Restaurant


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        restaurant = intent.getParcelableExtra("restaurant")

        restaurant_name.text = restaurant.name

        val order = Order (
                intent.getStringExtra("orderName").orEmpty(),
                intent.getDoubleExtra("orderPrice", 0.0)
        )

        if (order.isValid()) {
            OrdersActivity.orders.add(order)
        }

        myRecycler = findViewById(recycler_order.id)
        myAdapter = OrderViewAdapter(this, orders)
        val myLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecycler.layoutManager = myLayout
        myRecycler.adapter = myAdapter
    }

    fun addFriends(view: View) {
        intent = Intent(this, FriendsActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
    }

    fun doOrder(view: View) {
        intent = Intent(this, MenuActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        intent.putExtra("toOrder", true)
        startActivity(intent)
    }
}


