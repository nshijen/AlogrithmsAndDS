package com.shijen.algorithmandds

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity() {
    var sortingAlog: SortingAlgorithms
    var startTime: Long = 0L
    var endTime: Long = 0L

    init {
        sortingAlog = SortingAlgorithms.INSERTION_SORT
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onStart() {
        super.onStart()
        sorting_buttons.setOnCheckedChangeListener { group, id ->
            when (id) {
                insertion_sort.id -> sortingAlog = SortingAlgorithms.INSERTION_SORT
                bubble_sort.id -> sortingAlog = SortingAlgorithms.BUBBLE_SORT
                merge_sort.id -> sortingAlog = SortingAlgorithms.MERGE_SORT
                selection_sort.id -> sortingAlog = SortingAlgorithms.SELECTION_SORT
                quick_sort.id -> sortingAlog = SortingAlgorithms.QUICK_SORT
                heap_sort.id -> sortingAlog = SortingAlgorithms.HEAP_SORT
            }
            tv_alog_name.setText(sortingAlog.alogName)
        }
        tv_alog_name.setText(sortingAlog.alogName)
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
            /*intArrayOf(6, 2, 9, -1, 3, 6, 24, 23, 4)*/
            /*intArrayOf(9,8,7,6,5,4,3,2,1,0,-1,-2)*/
        updateTheInputString(intArray)
        sortTheArray(intArray)
    }

    private suspend fun updateTheInputString(intArray: IntArray) {
        withContext(Dispatchers.Main) {
            if (intArray.size > 20) {
                tv_input_variables.setText("Array containing 100000 numbers:")
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
        withContext(Dispatchers.Main) {
            progress_layout.visibility = View.GONE
            if (intArray.size > 20) {
                tv_out_put.setText("Sorted 100000 integers")
            } else {
                tv_out_put.setText(intArray.contentToString())
            }
            tv_time_taken.setText("Time taken" + (l))
            println("Output Elements: first 10 ${intArray.take(10)}")
            println("Output Elements: last 10 ${intArray.takeLast(10)}")
        }
    }
}
