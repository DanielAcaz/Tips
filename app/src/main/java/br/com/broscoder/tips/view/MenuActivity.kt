package br.com.broscoder.tips.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import br.com.broscoder.tips.R
import br.com.broscoder.tips.enums.Scenario
import br.com.broscoder.tips.error.RestaurantItemNotFoundException
import br.com.broscoder.tips.factory.MenuFactory
import br.com.broscoder.tips.model.Menu
import br.com.broscoder.tips.recycler.MenuViewAdapter
import br.com.broscoder.tips.recycler.RestaurantScrollListener
import br.com.broscoder.tips.recycler.RestaurantViewAdapter
import br.com.broscoder.tips.service.MenuMockService
import br.com.broscoder.tips.service.TipsService
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    private lateinit var menus: List<Menu>
    private lateinit var myRecycler: RecyclerView
    private lateinit var myAdapter: MenuViewAdapter
    private lateinit var myLayout: LinearLayoutManager
    private lateinit var service: TipsService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val idRestaurant = intent.getLongExtra("restaurant_id", 0)

        service = MenuFactory(this).createServiceBy(Scenario.MOCK)
        menus = service.getByRestaurantId(idRestaurant).filterIsInstance<Menu>()
        myRecycler = findViewById(recycler_type_menu.id)
        myAdapter = MenuViewAdapter(this, menus) {
            val intent = Intent(this,RestaurantActivity::class.java)
            //intent.putExtra("menu", it)
            startActivity(intent)
        }
        myLayout = LinearLayoutManager(this)
        myRecycler.layoutManager = myLayout
        myRecycler.adapter = myAdapter
    }
}
