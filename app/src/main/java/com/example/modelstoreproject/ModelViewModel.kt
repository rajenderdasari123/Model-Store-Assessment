package com.example.modelstoreproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.modelstoreproject.di.RetroServiceInterface
import com.example.modelstoreproject.model.RecyclerList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class ModelViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var variantdatalist: MutableLiveData<MutableList<String>>

    @Inject
    lateinit var mService: RetroServiceInterface
    private lateinit var liveDataList: MutableLiveData<RecyclerList>
    private lateinit var relatedProductList: MutableLiveData<RecyclerList>


    init {
        (application as MyApplication).getRetroComponent().inject(this)
        variantdatalist = MutableLiveData<MutableList<String>>()
        relatedProductList = MutableLiveData()
        liveDataList = MutableLiveData()
    }

    fun getLiveDataObserver(): MutableLiveData<RecyclerList> {
        return liveDataList
    }

    fun getVariantData(): MutableLiveData<MutableList<String>> {
        return variantdatalist
    }

    fun getRelatedProductData(): MutableLiveData<RecyclerList> {
        return relatedProductList
    }

    fun makeApicall() {
        viewModelScope.launch (Dispatchers.IO) {
            val call: Call<RecyclerList?> = mService.getDataFromAPI("atl")
            call?.enqueue(object : Callback<RecyclerList?> {
                override fun onResponse(
                    call: Call<RecyclerList?>,
                    response: Response<RecyclerList?>
                ) {
                    if (response.isSuccessful) {
                        liveDataList.postValue(response.body())
                    } else {
                        liveDataList.postValue(null)
                    }
                }

                override fun onFailure(call: Call<RecyclerList?>, t: Throwable) {
                    liveDataList.postValue(null)
                }
            })
        }
    }

    fun relatedProductApi() {
        viewModelScope.launch (Dispatchers.IO) {
            val call: Call<RecyclerList?> = mService.getDataFromAPI("atl")
            call?.enqueue(object : Callback<RecyclerList?> {
                override fun onResponse(
                    call: Call<RecyclerList?>,
                    response: Response<RecyclerList?>
                ) {
                    if (response.isSuccessful) {
                        relatedProductList.postValue(response.body())
                    } else {
                        relatedProductList.postValue(null)
                    }
                }

                override fun onFailure(call: Call<RecyclerList?>, t: Throwable) {
                    relatedProductList.postValue(null)
                }
            })
        }
    }
}

