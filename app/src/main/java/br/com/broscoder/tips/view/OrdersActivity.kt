package br.com.broscoder.tips.view

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.com.broscoder.tips.R

class OrdersActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)



    }

    fun addFriends(view: View) {
        intent = Intent(this, FriendsActivity::class.java)
        startActivity(intent)
    }

    fun doOrder(view: View) {
        intent = Intent(this, MenuActivity::class.java)
        startActivity(intent)
    }
}


