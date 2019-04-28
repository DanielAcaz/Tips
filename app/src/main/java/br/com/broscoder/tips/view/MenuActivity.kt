package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.ExpandableListView
import android.widget.Toast
import br.com.broscoder.tips.R
import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.factory.MenuFactory
import br.com.broscoder.tips.model.Friend
import br.com.broscoder.tips.model.Menu
import br.com.broscoder.tips.model.Order
import br.com.broscoder.tips.model.Restaurant
import br.com.broscoder.tips.recycler.MenuViewAdapter
import br.com.broscoder.tips.service.MenuService
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var menus: List<Menu>
    private lateinit var myAdapter: MenuViewAdapter
    private lateinit var service: MenuService
    private lateinit var restaurant: Restaurant
    private var toOrder: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        restaurant = intent.getParcelableExtra<Restaurant>("restaurant")
        val idRestaurant = restaurant.id
        toOrder = intent.getBooleanExtra("toOrder", false)
        val toFriendOrder = intent.getParcelableExtra<Friend>("friend")

        service = MenuFactory(this).createServiceBy(Scenario.MOCK)
        menus = service.getMenuAcitveByRestaurantId(idRestaurant)
        val mappedMenu = service.getMappedMenuActiveByRestaurantId(idRestaurant)
        myAdapter = MenuViewAdapter(this, mappedMenu)
        val expandableListView = findViewById(R.id.expandableListView) as ExpandableListView
        expandableListView.setAdapter(myAdapter)
        expandableListView!!.setOnChildClickListener { parent, v, groupPosition, childPosition, id ->
            if(toOrder){
                val intent = Intent(this, OrdersActivity::class.java)
                intent.putExtra("orderName", mappedMenu.get(mappedMenu.keys.sorted()[groupPosition])!![childPosition].name)
                intent.putExtra("orderPrice", mappedMenu.get(mappedMenu.keys.sorted()[groupPosition])!![childPosition].price)
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
                finish()
            } else if (toFriendOrder != null) {
                val intent = Intent(this, FriendsActivity::class.java)
                intent.putExtra("orderName", mappedMenu.get(mappedMenu.keys.sorted()[groupPosition])!![childPosition].name)
                intent.putExtra("orderPrice", mappedMenu.get(mappedMenu.keys.sorted()[groupPosition])!![childPosition].price)
                intent.putExtra("friend", toFriendOrder)
                intent.putExtra("restaurant", restaurant)
                startActivity(intent)
                finish()
            }
            false
        }
    }
}
