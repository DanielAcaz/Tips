package br.com.broscoder.tips.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Order
import kotlinx.android.synthetic.main.order_item.view.*

class OrderViewAdapter (private val context: Context, private val data: List<Order>) : RecyclerView.Adapter<OrderViewAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): OrderViewHolder {
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.order_item, viewGroup, false)
        return OrderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: OrderViewHolder, position: Int) {
        val order = data[position]
        viewHolder.let { it.binView(order) }
    }

    class OrderViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun binView(order: Order) = with(itemView) {
            order_name.text = order.name
            order_price.text = "R$ " + order.price.toString()
        }
    }

}