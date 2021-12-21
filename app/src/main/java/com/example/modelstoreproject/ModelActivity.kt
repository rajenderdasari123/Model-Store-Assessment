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
import com.example.modelstoreproject.adapter.RelatedProductAdaptor
import com.example.modelstoreproject.adapter.RelatedProductCLickLstener
import com.example.modelstoreproject.adapter.VariantProductAdaptor
import com.example.modelstoreproject.adapter.VariantProductClickListener
import com.example.modelstoreproject.model.RecyclerData
import com.example.modelstoreproject.model.RecyclerList
import kotlinx.android.synthetic.main.activity_model.*
import kotlinx.android.synthetic.main.recycler_item.*

class ModelActivity : AppCompatActivity(), VariantProductClickListener, RelatedProductCLickLstener {
     lateinit var mModelViewModel: ModelViewModel
    private lateinit var variantProductAdaptor: VariantProductAdaptor
    private lateinit var rlatedProductAdaptor: RelatedProductAdaptor
    private var listData: List<RecyclerData>? = null
    var count = 0

    /**
     * Called when the activity is first created. This is where you should do all of your normal
     * static set up: create views, bind data to lists, etc. This method also provides you with a
     * Bundle containing the activity's previously frozen state, if there was one.
     *
     * @param savedInstanceState-{@link Bundle}.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)
        initVarianProduct()
        initObservers()
        initRelatedProduct()

        btn_price.setOnClickListener {
            count++
            txt_count.setBackgroundResource(R.drawable.bg_count_green);
            txt_count.text = "basket: $count Items(s)"
        }
    }

    /**
     * This method initializes the Observers.
     */
    private fun initObservers() {
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
        mModelViewModel.getErrorMutableLiveData()?.observe(this, this::handleErrors);
        mModelViewModel.getNetworkErrorMutableLiveData()?.observe(this, this::networkError);
    }

    /**
     * This method handle NetworkError Call.
     *
     * @param aBoolean {@link Boolean}.
     */
    private fun networkError(aBoolean:Boolean) {
        if (aBoolean) {
            tv_no_network.setVisibility(View.VISIBLE);
            img_product.setVisibility(View.GONE);
        } else {
            tv_no_network.setVisibility(View.GONE);
            img_product.setVisibility(View.VISIBLE);
        }
    }

    /**
     * This method handle Api Call Errors.
     *
     * @param throwable {@link Throwable}.
     */
    private fun handleErrors(throwable:Throwable) {
        Toast.makeText(this, throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
    }

    /**
     * setting related product adapter to recycler view
     */
    private fun initRelatedProduct() {
        val reatedList = findViewById<RecyclerView>(R.id.relatedProductList)
        reatedList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rlatedProductAdaptor = RelatedProductAdaptor(this)
        reatedList.adapter = rlatedProductAdaptor
    }

    /**
     * setting variant product adapter to recycler view
     */
    private fun initVarianProduct() {
        val variantList = findViewById<RecyclerView>(R.id.variantList)
        variantList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        variantProductAdaptor = VariantProductAdaptor(this)
        variantList.adapter = variantProductAdaptor
    }

    /**
     * click even for variant product list
     */
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

    /**
     * click even for reated product list
     */
    override fun onRelatedProductItemClick(view: View, positon: Int) {
        when (view.id) {
            R.id.img_related_product -> {

            }
        }
    }

    fun getVariantProductAdaptor(): VariantProductAdaptor? {
        return variantProductAdaptor
    }

    fun getRelatedProductAdaptor(): RelatedProductAdaptor? {
        return rlatedProductAdaptor
    }
}