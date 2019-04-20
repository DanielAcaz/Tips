package br.com.broscoder.tips.recycler

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Menu
import com.alespero.expandablecardview.ExpandableCardView
import kotlinx.android.synthetic.main.activity_grid_menu.view.*

class MenuViewAdapter(private val context: Context, private val data: List<Menu>,
                      val listener: (Menu) -> Unit): RecyclerView.Adapter<MenuViewAdapter.MenuViewHolder>() {


    override fun onCreateViewHolder(viewGroup: ViewGroup, index: Int): MenuViewAdapter.MenuViewHolder {
//        val card : ExpandableCardView = viewGroup.card_view_menu
//        card.setTitle(data[index].type)
        val mInflater = LayoutInflater.from(context)
        val view = mInflater.inflate(R.layout.activity_grid_menu, viewGroup, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(viewHolder: MenuViewAdapter.MenuViewHolder, position: Int) {
        val menu = data[position]
        viewHolder?.let {
            it.binView(menu, listener)
        }
    }

    class MenuViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun binView(menu: Menu,
                    listener: (Menu) -> Unit) = with(itemView) {

            card_view_menu.setTitle("TESTE")
            setOnClickListener { listener(menu) }
        }
    }
}