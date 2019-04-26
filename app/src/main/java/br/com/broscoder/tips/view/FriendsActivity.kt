package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ExpandableListView
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Friend
import br.com.broscoder.tips.model.Order
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.recycler.FriendsViewAdapter
import java.util.*
import kotlin.collections.HashMap

class FriendsActivity : AppCompatActivity() {

    companion object {
        var myFrendsAndOrders : MutableMap<Friend, MutableList<Order>> = HashMap()

        fun friendsOrders() : List<Order> {
            var friendsOrders : MutableList<Order> = Collections.emptyList()
            myFrendsAndOrders.let {
                it.forEach { friend, orders ->
                   friendsOrders.addAll(orders)
                }
                return friendsOrders
            }
        }

    }
    private lateinit var myAdapter: FriendsViewAdapter
    private lateinit var restaurant: Restaurant
    private val CHILD_FIRST_POSITION = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        this.restaurant = intent.getParcelableExtra<Restaurant>("restaurant")
        val friend = intent.getParcelableExtra<Friend>("friend")

        if(friend != null) {
            val mapFriend = myFrendsAndOrders.get(friend)
            val order = Order(
                    intent.getStringExtra("orderName").orEmpty(),
                    intent.getDoubleExtra("orderPrice", 0.0)
            )
            if(order.isValid()) {
                mapFriend?.add(order)
            }
        }

        myAdapter = FriendsViewAdapter(this, myFrendsAndOrders)
        val expandableListView = findViewById(R.id.expandableListView) as ExpandableListView
        expandableListView.setAdapter(myAdapter)

        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, id, childPosition ->
            if(childPosition == CHILD_FIRST_POSITION) {

                intent = Intent(this, MenuActivity::class.java)
                intent.putExtra("friend", myFrendsAndOrders.keys.sorted()[groupPosition])
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
                finish()
            }
            false
        }
    }

    fun addFriends(view: View) {
        intent = Intent(this, FriendActivity::class.java)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
        finish()
    }
}
