package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Order
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.recycler.OrderViewAdapter
import kotlinx.android.synthetic.main.activity_orders.*
import kotlin.streams.toList

class OrdersActivity : AppCompatActivity() {

    companion object {
        private var myOrders: MutableList<Order> = ArrayList()
    }
    private lateinit var myRecycler: RecyclerView
    private lateinit var myAdapter: OrderViewAdapter
    private lateinit var restaurant: Restaurant
    private lateinit var orders: MutableList<Order>
    private var friendsOrders = FriendsActivity.friendsOrders()

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
            OrdersActivity.myOrders.add(order)
        }
    }

    override fun onResume() {
        super.onResume()
        friendsOrders = FriendsActivity.friendsOrders()

        orders = ArrayList()
        orders.addAll(myOrders)
        orders.addAll(friendsOrders)

        myRecycler = findViewById(recycler_order.id)
        myAdapter = OrderViewAdapter(this, orders)
        val myLayout = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        myRecycler.layoutManager = myLayout
        myRecycler.adapter = myAdapter

        enable_my.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                orders.let {
                    it.addAll(myOrders)
                    val positions = myOrders.stream().mapToInt {order ->
                        it.indexOf(order) }
                    positions.forEach{position -> myAdapter.notifyItemInserted(position)}
                }
            } else {
                orders.let {
                    val positions = myOrders.stream().mapToInt {order ->
                        it.indexOf(order)
                    }.toList()
                    it.removeAll(myOrders)
                    myAdapter.notifyDataSetChanged()
                    //positions.forEach{position -> myAdapter.notifyItemRemoved(position)}
                }
            }
            updateBillValue()
        }

        enable_friends.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked) {
                orders.let {
                    it.addAll(friendsOrders)
                    val positions = friendsOrders.stream().mapToInt {order ->
                        it.indexOf(order) }
                    positions.forEach{position -> myAdapter.notifyItemInserted(position)}
                }
            } else {
                orders.let {
                    val positions = friendsOrders.stream().mapToInt {order ->
                        it.indexOf(order)
                    }.toList()
                    it.removeAll(friendsOrders)
                    myAdapter.notifyDataSetChanged()
                    //positions.forEach{position -> myAdapter.notifyItemRemoved(position)}
                }
            }
            updateBillValue()
        }

        enable_tips.setOnCheckedChangeListener { buttonView, isChecked ->
            updateBillValue()
        }

        updateBillValue()
    }

    override fun onBackPressed() {
        finishAffinity()
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

    fun updateBillValue(){
        var bill = orders.sumByDouble { it.price }
        if(enable_tips.isChecked) {
            bill *= 1.1
        }
        total_bill.text = String.format("R$ %.2f", bill)
    }
}


