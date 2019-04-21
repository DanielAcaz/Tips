package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Friend
import br.com.broscoder.tips.model.Order
import br.com.broscoder.tips.model.Restaurant
import kotlinx.android.synthetic.main.activity_friend.*

class FriendActivity : AppCompatActivity() {

    lateinit var restaurant: Restaurant

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friend)

        restaurant = intent.getParcelableExtra("restaurant")
    }

    fun saveFriend(view: View) {
        val friend = Friend(friend_name.text.toString())
        val orderList = mutableListOf(Order("+ new Order", -1.0))
        FriendsActivity.myFrendsAndOrders.put(friend, orderList)
        intent = Intent(this, FriendsActivity::class.java)
        intent.putExtra("friend", friend)
        intent.putExtra("restaurant", restaurant)
        startActivity(intent)
        finish()
    }
}
