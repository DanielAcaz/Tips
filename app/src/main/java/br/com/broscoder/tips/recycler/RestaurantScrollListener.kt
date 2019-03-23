package br.com.broscoder.tips.recycler

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import br.com.broscoder.tips.view.MapsActivity

class RestaurantScrollListener : RecyclerView.OnScrollListener() {

    lateinit var map: MapsActivity

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val layout = recyclerView.layoutManager as LinearLayoutManager
        map.positionCard = layout.findFirstCompletelyVisibleItemPosition()
        map.markMainRestaurant()
        Log.i("[RECYCLER LISTENER]", "PositionCard = ${layout.findFirstCompletelyVisibleItemPosition()}")
    }

    fun registerMap(map: MapsActivity) {
        this.map = map
    }
}