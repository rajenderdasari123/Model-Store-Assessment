package com.example.modelstoreproject

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.modelstoreproject.adapter.VariantProductClickListener
import com.example.modelstoreproject.adapter.RelatedProductAdaptor
import com.example.modelstoreproject.adapter.RelatedProductCLickLstener
import com.example.modelstoreproject.adapter.VariantProductAdaptor
import com.example.modelstoreproject.model.RecyclerData
import com.example.modelstoreproject.model.RecyclerList
import kotlinx.android.synthetic.main.activity_model.*
import kotlinx.android.synthetic.main.recycler_item.*

class ModelActivity : AppCompatActivity(), VariantProductClickListener, RelatedProductCLickLstener {
    private lateinit var mModelViewModel: ModelViewModel
    private lateinit var variantProductAdaptor: VariantProductAdaptor
    private lateinit var rlatedProductAdaptor: RelatedProductAdaptor
    private var listData: List<RecyclerData>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)
        initVarianProduct()
        initViewModel()
        initRelatedProduct()
    }

    private fun initViewModel() {
        mModelViewModel = ViewModelProvider(this).get(ModelViewModel::class.java)
        mModelViewModel.getLiveDataObserver().observe(this, object : Observer<RecyclerList> {
            override fun onChanged(t: RecyclerList?) {
                if (t !== null) {
                    listData = t.items
                    Glide.with(img_product).load(listData?.get(0)!!.owner?.avatar_url)
                        .apply(RequestOptions.centerCropTransform()).into(img_product)
                    variantProductAdaptor.setUpdateData(t.items)
                    variantProductAdaptor.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@ModelActivity,
                        "Error in getting the data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        mModelViewModel.makeApicall()

        mModelViewModel.getRelatedProductData().observe(this, object : Observer<RecyclerList> {
            override fun onChanged(t: RecyclerList?) {
                if (t !== null) {
                    listData = t.items
                    rlatedProductAdaptor.setRelatedProdctData(t.items)
                    rlatedProductAdaptor.notifyDataSetChanged()
                } else {
                    Toast.makeText(
                        this@ModelActivity,
                        "Error in getting the data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
        mModelViewModel.relatedProductApi()
    }

    private fun initRelatedProduct() {
        val reatedList = findViewById<RecyclerView>(R.id.relatedProductList)
        reatedList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rlatedProductAdaptor = RelatedProductAdaptor(this)
        reatedList.adapter = rlatedProductAdaptor
    }

    private fun initVarianProduct() {
        val variantList = findViewById<RecyclerView>(R.id.variantList)
        variantList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        variantProductAdaptor = VariantProductAdaptor(this)
        variantList.adapter = variantProductAdaptor
    }

    override fun onVariantProductItemClick(view: View, position: Int) {
        when (view.id) {
            R.id.image -> {
                Toast.makeText(this@ModelActivity, "item Clicked  " + position, Toast.LENGTH_SHORT)
                    .show()
                item_name.setText(listData?.get(position)!!.name)
                Glide.with(img_product).load(listData?.get(position)!!.owner?.avatar_url)
                    .apply(RequestOptions.centerCropTransform()).into(img_product)
            }
        }
    }

    override fun onRelatedProductItemClick(view: View, positon: Int) {
        when (view.id) {
            R.id.img_related_product -> {

            }
        }
    }
}