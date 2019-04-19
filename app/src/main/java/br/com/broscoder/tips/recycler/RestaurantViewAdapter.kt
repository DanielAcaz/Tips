package br.com.broscoder.tips.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Restaurant
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_grid_restaurants.view.*

class RestaurantViewAdapter(private val context: Context, private val data: List<Restaurant>,
                            val listener: (Restaurant) -> Unit): RecyclerView.Adapter<RestaurantViewAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): RestaurantViewHolder {
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.activity_grid_restaurants, viewGroup, false)
        return RestaurantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: RestaurantViewHolder, position: Int) {
        val restaurant = data[position]
        viewHolder?.let {
            it.binView(restaurant, listener)
        }
    }

    fun getPositionBy(restaurant: Restaurant): Int {
        for(x in 0..data.size) {
            if(data[x].name.equals(restaurant.name)) {
                return x
            }
        }
        return -1
    }
    class RestaurantViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun binView(restaurant: Restaurant,
                    listener: (Restaurant) -> Unit) = with(itemView) {

            restaurant_name.text = restaurant.name
            if (isNotNullAndNotEmpty(restaurant)) {
                Picasso.get().load(restaurant.image).into(restaurant_image)
            }

            setOnClickListener {listener(restaurant)}
        }

        private fun isNotNullAndNotEmpty(restaurant: Restaurant) =
                !restaurant.image.isNullOrEmpty()
    }
}