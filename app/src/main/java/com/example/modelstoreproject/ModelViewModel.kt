package com.example.modelstoreproject

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.modelstoreproject.di.RetroServiceInterface
import com.example.modelstoreproject.model.RecyclerList
import com.example.modelstoreproject.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

/**
 * The View Model for {@link ModelActivity}
 */
class ModelViewModel(application: Application) : AndroidViewModel(application) {
    private var mApplication: Application
    private lateinit var variantdatalist: MutableLiveData<MutableList<String>>
    private var mErrorMutableLiveData: MutableLiveData<Throwable>? = null
    private var mNetworkErrorMutableLiveData: MutableLiveData<Boolean>? = null
    private lateinit var networkUtils: NetworkUtils

    @Inject
    lateinit var mService: RetroServiceInterface
    private lateinit var liveDataList: MutableLiveData<RecyclerList>
    private lateinit var relatedProductList: MutableLiveData<RecyclerList>


    init {
        this.mApplication = application
        (application as MyApplication).getRetroComponent().inject(this)
        variantdatalist = MutableLiveData<MutableList<String>>()
        relatedProductList = MutableLiveData()
        mErrorMutableLiveData = MutableLiveData()
        mNetworkErrorMutableLiveData = MutableLiveData()
        liveDataList = MutableLiveData()
        networkUtils = NetworkUtils()
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

    fun getErrorMutableLiveData(): MutableLiveData<Throwable>? {
        return mErrorMutableLiveData
    }

    fun getNetworkErrorMutableLiveData(): MutableLiveData<Boolean>? {
        return mNetworkErrorMutableLiveData
    }

    /**
     * api call for getting variant product data
     */
    fun makeApicall() {
        if (networkUtils.isNetworkAvailable(mApplication)) {
            mNetworkErrorMutableLiveData?.postValue(false);
            viewModelScope.launch(Dispatchers.IO) {
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
                        mErrorMutableLiveData?.postValue(t)
                    }
                })
            }
        } else {
            mNetworkErrorMutableLiveData?.postValue(true);
        }
    }

    /**
     * api call for getting related product data
     */
    fun relatedProductApi() {
       // if (NetworkUtils.isNetworkAvailable()) {
            viewModelScope.launch(Dispatchers.IO) {
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
                        mErrorMutableLiveData?.postValue(t)
                    }
                })
            }
        /*} else {
            mNetworkErrorMutableLiveData?.postValue(true);
        }*/
    }

}

