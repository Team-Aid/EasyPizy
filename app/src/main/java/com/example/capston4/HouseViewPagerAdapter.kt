package com.example.capston4

import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.capston4.MyApp.Companion.applicationContext

class HouseViewPagerAdapter: ListAdapter<SmokeArea, HouseViewPagerAdapter.ItemViewHolder>(differ) {
    val acontext:Context = applicationContext()

    inner class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(smokeModel: SmokeArea) {
            val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
            val priceTextView = view.findViewById<TextView>(R.id.priceTextView)
            val cardView = view.findViewById<CardView>(R.id.cardView)
            titleTextView.text = smokeModel.areaName
            priceTextView.text = smokeModel.category

            cardView.setOnClickListener {
                View ->
                val intent = Intent(acontext, InfomationActivity::class.java)
                intent.putExtra("data", smokeModel)
                Intent(acontext, InfomationActivity::class.java).apply {

                }.run { acontext.startActivity(this.addFlags(FLAG_ACTIVITY_NEW_TASK)) }




            }


        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HouseViewPagerAdapter.ItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ItemViewHolder(inflater.inflate(R.layout.item_house_detail_for_viewpager, parent, false))
    }

    override fun onBindViewHolder(holder: HouseViewPagerAdapter.ItemViewHolder, position: Int) {
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