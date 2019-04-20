package br.com.broscoder.tips.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import br.com.broscoder.tips.model.Menu

class MenuViewAdapter(private val context: Context, private val data: List<Menu>,
                      val listener: (Menu) -> Unit): RecyclerView.Adapter<RestaurantViewAdapter.RestaurantViewHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RestaurantViewAdapter.RestaurantViewHolder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getItemCount(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onBindViewHolder(p0: RestaurantViewAdapter.RestaurantViewHolder, p1: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}