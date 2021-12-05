package com.example.modelstoreproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.modelstoreproject.R
import com.example.modelstoreproject.model.RecyclerData
import kotlinx.android.synthetic.main.item_relatedproduct.view.*
import kotlinx.android.synthetic.main.recycler_item.view.*

class RelatedProductAdaptor(private val listener: RelatedProductCLickLstener) :
    RecyclerView.Adapter<RelatedProductAdaptor.MyViewHolder>() {

    private var listData: List<RecyclerData>? = null

    fun setRelatedProdctData(listData: List<RecyclerData>) {
        this.listData = listData
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_related_product = view.img_related_product
        fun bind(data: RecyclerData) {
            Glide.with(img_related_product).load(data.owner?.avatar_url)
                .apply(RequestOptions.centerCropTransform()).into(img_related_product)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_relatedproduct, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(listData?.get(position)!!)
        holder.img_related_product.setOnClickListener {
            listener.onRelatedProductItemClick(holder.img_related_product, position)
        }
    }

    override fun getItemCount(): Int {
        if (listData == null) return 0
        else return listData?.size!!
    }
}

