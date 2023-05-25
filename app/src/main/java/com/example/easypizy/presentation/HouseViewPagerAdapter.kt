package com.example.easypizy.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.easypizy.MyApp.Companion.applicationContext
import com.example.easypizy.R
import com.example.easypizy.data.data_source.local.entity.SmokeArea

class HouseViewPagerAdapter: ListAdapter<SmokeArea, HouseViewPagerAdapter.ItemViewHolder>(differ) {
    val acontext:Context = applicationContext()

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(smokeModel: SmokeArea) {
            val category = view.findViewById<TextView>(R.id.categoty)
            val location = view.findViewById<TextView>(R.id.loaction)
            val inout = view.findViewById<TextView>(R.id.inout)
            val cityname = view.findViewById<TextView>(R.id.cityname)
            val cardView = view.findViewById<CardView>(R.id.cardView)
            location.text = smokeModel.areaName
            category.text = smokeModel.category
            cityname.text = smokeModel.b
            inout.text = smokeModel.inout

            /* cardView.setOnClickListener {
                 View ->
                 val intent = Intent(acontext, InfomationActivity::class.java)
                 intent.putExtra("data", smokeModel)
                 Intent(acontext, InfomationActivity::class.java).apply {

                 }.run { acontext.startActivity(this.addFlags(FLAG_ACTIVITY_NEW_TASK)) }




             }*/


        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.item_house_detail_for_viewpager, parent, false))
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    companion object {
        val differ = object : DiffUtil.ItemCallback<SmokeArea>() {
            override fun areItemsTheSame(
                oldItem: SmokeArea,
                newItem: SmokeArea
            ): Boolean {
                return oldItem.areaName == newItem.areaName
            }

            override fun areContentsTheSame(
                oldItem: SmokeArea,
                newItem: SmokeArea
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}