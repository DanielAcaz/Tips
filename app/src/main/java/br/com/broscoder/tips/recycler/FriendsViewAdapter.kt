package br.com.broscoder.tips.recycler

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import br.com.broscoder.tips.R
import br.com.broscoder.tips.model.Friend
import br.com.broscoder.tips.model.Order

class FriendsViewAdapter internal constructor(private val context: Context, private val data: Map<Friend, List<Order>>) : BaseExpandableListAdapter()  {

    override fun getGroup(groupPosition: Int): Any {
        return data.keys.sorted()[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {

        var convertView = convertView
        val friend = getGroup(groupPosition) as Friend
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.friend, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.friend_name)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = friend.name

        val billValueTextView = convertView!!.findViewById<TextView>(R.id.friend_bill_value)
        billValueTextView.setTypeface(null, Typeface.BOLD)
        billValueTextView.text = String.format("R$ %.2f", data[friend]?.filter { it.price >= 0 }?.sumByDouble { it.price })

        return convertView


    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data.get(data.keys.sorted()[groupPosition])!!.size
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        return this.data.get(this.data.keys.sorted()[groupPosition])!!.get(childPosition)
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(
            groupPosition: Int,
            childPosition: Int,
            isLastChild: Boolean,
            convertView: View?,
            parent: ViewGroup?
    ): View {

        var convertView = convertView
        val order = getChild(groupPosition, childPosition) as Order
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.menu_item, null)
        }
        val menuName = convertView!!.findViewById<TextView>(R.id.menu_item)
        menuName.text = order.name
        val menuPrice = convertView!!.findViewById<TextView>(R.id.price)
        if(order.price >= 0) {
            menuPrice.text = "R$ " + order.price.toString()
            menuPrice.visibility = View.VISIBLE
            menuName.setTextColor(Color.parseColor("#000000"))
        } else {
            menuPrice.visibility = View.INVISIBLE
            menuName.setTextColor(Color.parseColor("#2F51FF"))
        }
        return convertView



    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return data.keys.size
    }
}