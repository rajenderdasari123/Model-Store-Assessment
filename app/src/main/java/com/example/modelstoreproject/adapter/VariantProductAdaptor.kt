package com.example.modelstoreproject.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.modelstoreproject.R
import com.example.modelstoreproject.model.RecyclerData
import kotlinx.android.synthetic.main.recycler_item.view.*

class VariantProductAdaptor(private val listener: VariantProductClickListener) :
        RecyclerView.Adapter<VariantProductAdaptor.MyViewHolder>() {
    private var selectedItemPosition: Int = 0
    private var listData: List<RecyclerData>? = null

    fun setUpdateData(listData: List<RecyclerData>) {
        this.listData = listData
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.image
        val ViewShadow = view.view_shadow
        fun bind(data: RecyclerData) {
            Glide.with(imageView).load(data.owner?.avatar_url)
                    .apply(RequestOptions.centerCropTransform()).into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData?.get(position)!!)
        holder.imageView.setOnClickListener {
            selectedItemPosition = position
            listener.onVariantProductItemClick(holder.imageView, position)
            notifyDataSetChanged()
        }
        if(selectedItemPosition == position)
            holder.ViewShadow.setBackgroundColor(Color.parseColor("#36ba02"))
        else
            holder.ViewShadow.setBackgroundColor(Color.parseColor("#ffffff"))
    }

    override fun getItemCount(): Int {
        if (listData == null) return 0
        else return listData?.size!!
    }
}