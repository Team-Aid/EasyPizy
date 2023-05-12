package com.example.capston4

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView



    class HouseListAdapter: ListAdapter<SmokeArea, HouseListAdapter.ItemViewHolder>(differ) {

        inner class ItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
            fun bind(smokeModel: SmokeArea){
                val titleTextView = view.findViewById<TextView>(R.id.titleTextView)
                val priceTextView = view.findViewById<TextView>(R.id.priceTextView)

                titleTextView.text = smokeModel.areaName
                priceTextView.text = smokeModel.category

            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): HouseListAdapter.ItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return ItemViewHolder(inflater.inflate(R.layout.item_house, parent, false))
        }

        override fun onBindViewHolder(holder: HouseListAdapter.ItemViewHolder, position: Int) {
            holder.bind(currentList[position])
        }
        private fun dpToPx(context: Context, dp: Int) : Int{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), context.resources.displayMetrics).toInt()
        }
        companion object{
            val differ = object: DiffUtil.ItemCallback<SmokeArea>(){
                override fun areItemsTheSame(oldItem: SmokeArea, newItem: SmokeArea): Boolean {
                    return oldItem.areaName == newItem.areaName
                }

                override fun areContentsTheSame(oldItem: SmokeArea, newItem: SmokeArea): Boolean {
                    return oldItem == newItem
                }
            }
        }
    }
