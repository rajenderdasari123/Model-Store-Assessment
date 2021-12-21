package com.example.modelstoreproject

import android.content.Context
import android.os.Build
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario
import androidx.test.platform.app.InstrumentationRegistry
import com.example.modelstoreproject.model.Owner
import com.example.modelstoreproject.model.RecyclerData
import com.example.modelstoreproject.model.RecyclerList
import com.example.modelstoreproject.utils.NetworkUtils
import kotlinx.android.synthetic.main.activity_model.*
import org.junit.After
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@Config(sdk = [Build.VERSION_CODES.O])
@RunWith(RobolectricTestRunner::class)
class ModelActivityTest {

    private lateinit var mContext: Context
    private var mModelActivity: ModelActivity? = null
    private var mModelActivityScenario: ActivityScenario<ModelActivity>? = null
    private var listData: List<RecyclerData>? = null

    @Mock
    private lateinit var mNetworkUtils: NetworkUtils

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mContext = InstrumentationRegistry.getInstrumentation().targetContext
        mModelActivityScenario = ActivityScenario.launch(ModelActivity::class.java)
        mModelActivityScenario?.moveToState(Lifecycle.State.RESUMED)
        mModelActivityScenario?.onActivity { activity -> mModelActivity = activity }
        Mockito.doReturn(true).`when`(mNetworkUtils).isNetworkAvailable(mContext)
    }

    @Test
    fun testInstance() {
        Assert.assertNotNull(mModelActivity);
    }

    @Test
    fun testItemCount() {
        mModelActivity?.btn_price?.performClick()
        assertEquals("basket: 1 Items(s)", mModelActivity?.txt_count?.text)
    }

    @Test
    fun testDestroy() {
        mModelActivityScenario?.moveToState(Lifecycle.State.DESTROYED)
        assertEquals(Lifecycle.State.DESTROYED, mModelActivity?.getLifecycle()?.getCurrentState())
    }

    @Test
    fun testVariantListData() {
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
        var recyclerList: RecyclerList = RecyclerList(listData!!)
        mModelActivity?.mModelViewModel?.getLiveDataObserver()?.postValue(recyclerList)
        mModelActivity?.getVariantProductAdaptor()?.setUpdateData(listData!!)
        assertEquals(2, mModelActivity?.getVariantProductAdaptor()?.itemCount)
    }

    @Test
    fun testRelatedProductData() {
        var owner: Owner = Owner("https://avatars.githubusercontent.com/u/45519360?v=4")
        val recyclerdata: RecyclerData = RecyclerData(
                "devtraining-needit-newyork",
                "This repository is used by the developer site training content, New York release. " +
                        "It is used for the Build the NeedIt App, Scripting in ServiceNow, Application Security," +
                        " Importing Data, Automating Application Logic, Flow Designer, REST Integrations," +
                        " Reporting and Analytics, Domain Separation, Mobile Applications, and Context-sensitive Help courses.",
                owner
        )
        listData = listOf(recyclerdata)
        var recyclerList: RecyclerList = RecyclerList(listData!!)
        mModelActivity?.mModelViewModel?.getLiveDataObserver()?.postValue(recyclerList)
        mModelActivity?.getRelatedProductAdaptor()?.setRelatedProdctData(listData!!)
        assertEquals(1, mModelActivity?.getRelatedProductAdaptor()?.itemCount)
    }

    @After
    fun tearDown() {
        mModelActivity = null
        mModelActivityScenario = null
    }
}