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

/**
 * Adapter to populate variant product information list
 */
class VariantProductAdaptor(private val listener: VariantProductClickListener) :
        RecyclerView.Adapter<VariantProductAdaptor.MyViewHolder>() {
    private var selectedItemPosition: Int = 0
    private var listData: List<RecyclerData>? = null

    /**
     * sets Adapter data.
     *
     * @param listData {@link List<RecyclerData>}
     */
    fun setUpdateData(listData: List<RecyclerData>) {
        this.listData = listData
    }

    /**
     * MyView holder sets the details for its view
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView = view.image
        val ViewShadow = view.view_shadow
        fun bind(data: RecyclerData) {
            Glide.with(imageView).load(data.owner?.avatar_url)
                    .apply(RequestOptions.centerCropTransform()).into(imageView)
        }
    }

    /**
     * This method calls onCreateViewHolder(ViewGroup, int) to create a new RecyclerView.ViewHolder and initializes some private fields to be used by RecyclerView.
     *
     * @param parent   {@link ViewGroup}
     * @param viewType int of View type.
     * @return {@link MyViewHolder}.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                LayoutInflater.from(parent.context).inflate(R.layout.recycler_item, parent, false)
        return MyViewHolder(view)
    }

    /**
     * This method internally calls onBindViewHolder(ViewHolder, int) to update the RecyclerView.ViewHolder contents with the item at the given position and also sets up some private fields to be used by RecyclerView.
     *
     * @param holder   {@link MyViewHolder}
     * @param position int
     */
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

    /**
     * This method Returns the total number of items in the data set held by the adapter.
     *
     * @return int.
     */
    override fun getItemCount(): Int {
        if (listData == null) return 0
        else return listData?.size!!
    }
}