package br.com.broscoder.tips.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Restaurant

class RestaurantViewAdapter(private val context: Context, private val data: List<Restaurant>): RecyclerView.Adapter<RestaurantViewAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): RestaurantViewHolder {
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.activity_grid_restaurants, viewGroup, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: RestaurantViewHolder, position: Int) {
        viewHolder.restaurantName.text = data[position].name
    }




    class RestaurantViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val restaurantName = view.findViewById<TextView>(R.id.restaurant_name)

    }
}