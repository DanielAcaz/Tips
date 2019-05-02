package br.com.broscoder.tips.recycler

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import br.com.broscoder.tips.R
import br.com.broscoder.tips.enums.MenuType
import br.com.broscoder.tips.model.Menu

class MenuViewAdapter internal constructor(private val context: Context, private val data: Map<MenuType, List<Menu>>) : BaseExpandableListAdapter() {

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
        val type = getGroup(groupPosition) as MenuType
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.menu_type, null)
        }
        val listTitleTextView = convertView!!.findViewById<TextView>(R.id.menu_type_name)
        listTitleTextView.setTypeface(null, Typeface.BOLD)
        listTitleTextView.text = type.toString()
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
        val menu = getChild(groupPosition, childPosition) as Menu
        if (convertView == null) {
            val layoutInflater = this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            convertView = layoutInflater.inflate(R.layout.menu_item, null)
        }
        val menuName = convertView!!.findViewById<TextView>(R.id.menu_item)
        menuName.text = menu.name
        val menuPrice = convertView!!.findViewById<TextView>(R.id.price)
        menuPrice.text = "R$ " + menu.price.toString()
        val menuDescription = convertView!!.findViewById<TextView>(R.id.item_description)
        menuDescription.text = menu.description
        return convertView



    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return data.keys.size
    }
}