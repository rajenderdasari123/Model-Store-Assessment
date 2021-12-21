package com.example.modelstoreproject.adapter

import android.content.Context
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.example.modelstoreproject.ModelActivity
import com.example.modelstoreproject.model.Owner
import com.example.modelstoreproject.model.RecyclerData
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Config(sdk = [Build.VERSION_CODES.O])
@RunWith(RobolectricTestRunner::class)
class VariantProductAdaptorTest {
    private var mModelActivity: ModelActivity? = null
    private lateinit var mContext: Context
    private var mModelActivityScenario: ActivityScenario<ModelActivity>? = null
    private lateinit var mVariantProductAdaptor: VariantProductAdaptor
    private var listData: List<RecyclerData>? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mContext = InstrumentationRegistry.getInstrumentation().targetContext
        mModelActivityScenario = ActivityScenario.launch(ModelActivity::class.java)
        mModelActivityScenario?.moveToState(Lifecycle.State.RESUMED)
        mModelActivityScenario?.onActivity { activity -> mModelActivity = activity }
        mVariantProductAdaptor = mModelActivity?.getVariantProductAdaptor()!!
    }

    @Test
    fun testAdapterCount(){
        getVariantListData()
        mVariantProductAdaptor.setUpdateData(listData!!)
        mVariantProductAdaptor.notifyDataSetChanged()
        Assert.assertEquals(2, mVariantProductAdaptor.itemCount)
    }

    fun getVariantListData(){
        var owner: Owner = Owner("https://avatars.githubusercontent.com/u/45519360?v=4")
        val recyclerdata: RecyclerData = RecyclerData(
            "devtraining-needit-newyork",
            "This repository is used by the developer site training content, New York release. " +
                    "It is used for the Build the NeedIt App, Scripting in ServiceNow, Application Security," +
                    " Importing Data, Automating Application Logic, Flow Designer, REST Integrations," +
                    " Reporting and Analytics, Domain Separation, Mobile Applications, and Context-sensitive Help courses.",
            owner
        )

        var owner2: Owner = Owner("https://avatars.githubusercontent.com/u/29196356?v=4")
        val recyclerdata2: RecyclerData = RecyclerData(
            "devtraining-createnotes-newyork",
            "Repository for the Service Portal Creating Custom Widgets module, New York release.",
            owner2
        )
        listData = listOf(recyclerdata, recyclerdata2)
    }

}