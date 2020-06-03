package com.shijen.algorithmandds

import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.room.Room
import com.facebook.stetho.Stetho
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
import com.shijen.algorithmandds.db.SortingDatabase
import com.shijen.algorithmandds.db.SortingSpeed
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.histogram_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {
    var sortingAlog: SortingAlgorithms
    var startTime: Long = 0L
    var endTime: Long = 0L
    lateinit var db: SortingDatabase
    var popup: PopupMenu? = null
    val graphData:HashMap<String,Double>  = HashMap()

    init {
        sortingAlog = SortingAlgorithms.INSERTION_SORT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)
        setContentView(R.layout.activity_main)
        initDb()
        setUpGraph()
    }

    private fun setUpGraph() {
        CoroutineScope(Dispatchers.IO).launch{
            for(item in SortingAlgorithms.values()){
                val avgTime = getAvgTime(item.alogName)
                graphData.put(item.alogName,avgTime)
                println("HASHMAP $graphData")
            }
            updateView()
        }
    }

    suspend fun updateView() {

        for(item in graphData){
            val inflate = layoutInflater.inflate(R.layout.histogram_item, null)
            inflate.tv_histo_time_taken.text = item.key.toString()
            inflate.v_histogram.layoutParams = ConstraintLayout.LayoutParams(convertDpToPixel(30.toFloat(),this).toInt(),(item.value * convertDpToPixel(15.toFloat(),this)).toInt())
            withContext(Dispatchers.Main){
                (ll_graphLayout).addView(inflate)
            }
        }
    }

    private fun initDb() {
        db = Room.databaseBuilder(this, SortingDatabase::class.java, "SortingData").build()
    }

    override fun onStart() {
        super.onStart()
        tv_alog_name.setText(sortingAlog.alogName)
        tv_alog_name.setOnClickListener(View.OnClickListener {
            showPopup(it)
        })
    }

    fun showPopup(v: View?) {
        v?.let {
            popup = PopupMenu(this, v)
        }
        menuInflater.inflate(R.menu.sorting_menu, popup?.getMenu())
        popup?.show()
        popup?.setOnMenuItemClickListener(this@MainActivity)
    }

    fun sort(view: View) {
        progress_layout.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.Default).launch {
            getResourceIntArray()
        }
    }

    suspend fun getResourceIntArray() {
        val intArray =
            resources.getIntArray(R.array.dummy_100000_array)
        /*intArrayOf(6, 2, 9, 34, 3, 6, -1, -11, -132, -4, 24, 23, 4)*/
        /*intArrayOf(9,8,7,6,5,4,3,2,1,0,-1,-2)*/
        updateTheInputString(intArray)
        sortTheArray(intArray)
    }

    private suspend fun updateTheInputString(intArray: IntArray) {
        withContext(Dispatchers.Main) {
            if (intArray.size > 20) {
                tv_input_variables.setText("Sorting an array containing 1 lakh random numbers:")
            } else {
                tv_input_variables.setText(intArray.contentToString())
            }
        }
    }

    private suspend fun sortTheArray(intArray: IntArray) {
        startTime = System.currentTimeMillis();
        val sort = sortingAlog.instance.sort(intArray)
        endTime = System.currentTimeMillis()
        updateTheOutputTimeAndResult(sort, endTime - startTime)
    }

    private suspend fun updateTheOutputTimeAndResult(intArray: IntArray, l: Long) {
        val inSeconds = l.toDouble() / 1000
        insertIntoDb(inSeconds, sortingAlog.alogName)
        val avgTime = getAvgTime(sortingAlog.alogName)
        updateAverageTime(avgTime)
        getNumOfRecord()
        withContext(Dispatchers.Main) {
            progress_layout.visibility = View.GONE
            if (intArray.size > 20) {
                tv_out_put.setText("Sorted 1 lakh random integers")
            } else {
                tv_out_put.setText(intArray.contentToString())
            }
            tv_time_taken.text = "Time taken: $inSeconds seconds"
            println("Output Elements: first 10 ${intArray.take(10)}")
            println("Output Elements: last 10 ${intArray.takeLast(10)}")
        }
    }

    suspend fun insertIntoDb(double: Double, algoName: String) {
        db.getDao().insertSortingData(SortingSpeed(double, algoName))
    }

    suspend fun getAvgTime(algoName: String):Double {
        return db.getDao().getAvgTimeOf(algoName)
    }

    suspend fun getNumOfRecord() {
        val avgTimeOf = db.getDao().getCount()
        withContext(Dispatchers.Main) {
            println("Count: $avgTimeOf")
        }
    }

    suspend fun updateAverageTime(avgTime: Double) {
        withContext(Dispatchers.Main) {
            tv_avg_time_taken.setText("Average time: $avgTime seconds")
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.insertion_sort -> sortingAlog = SortingAlgorithms.INSERTION_SORT
            R.id.bubble_sort -> sortingAlog = SortingAlgorithms.BUBBLE_SORT
            R.id.merge_sort -> sortingAlog = SortingAlgorithms.MERGE_SORT
            R.id.selection_sort -> sortingAlog = SortingAlgorithms.SELECTION_SORT
            R.id.quick_sort -> sortingAlog = SortingAlgorithms.QUICK_SORT
            R.id.heap_sort -> sortingAlog = SortingAlgorithms.HEAP_SORT
            R.id.radix_sort -> sortingAlog = SortingAlgorithms.RADIX_SORT
        }
        tv_alog_name.setText(sortingAlog.alogName)
        return true
    }

    fun convertPixelsToDp(px: Float, context: Context): Float {
        return px / (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        return dp * (context.getResources()
            .getDisplayMetrics().densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
