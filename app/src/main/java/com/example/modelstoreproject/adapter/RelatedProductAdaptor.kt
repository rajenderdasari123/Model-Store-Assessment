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


/**
 * Adapter to populate related product information list
 */
class RelatedProductAdaptor(private val listener: RelatedProductCLickLstener) :
    RecyclerView.Adapter<RelatedProductAdaptor.MyViewHolder>() {

    private var listData: List<RecyclerData>? = null

    /**
     * sets Adapter data.
     *
     * @param listData {@link List<RecyclerData>}
     */
    fun setRelatedProdctData(listData: List<RecyclerData>) {
        this.listData = listData
    }

    /**
     * MyView holder sets the details for its view
     */
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img_related_product = view.img_related_product
        fun bind(data: RecyclerData) {
            Glide.with(img_related_product).load(data.owner?.avatar_url)
                .apply(RequestOptions.centerCropTransform()).into(img_related_product)
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
            LayoutInflater.from(parent.context).inflate(R.layout.item_relatedproduct, parent, false)
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
        holder.img_related_product.setOnClickListener {
            listener.onRelatedProductItemClick(holder.img_related_product, position)
        }
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

