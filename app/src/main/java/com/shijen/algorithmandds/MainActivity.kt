package com.shijen.algorithmandds

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.shijen.algorithmandds.alogrithms.Sorting
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    var sortingAlog: SortingAlgorithms = SortingAlgorithms.INSERTION_SORT
    lateinit var intArray: IntArray
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sorting_buttons.setOnCheckedChangeListener { group, id->
            when(id){
                insertion_sort.id-> sortingAlog = SortingAlgorithms.INSERTION_SORT
                bubble_sort.id-> sortingAlog = SortingAlgorithms.BUBBLE_SORT
            }
            tv_alog_name.setText(sortingAlog.alogName)
        }
         //resources.getIntArray(R.array.dummy_100000_array)
        intArray = resources.getIntArray(R.array.dummy_100000_array)//intArrayOf(6,2,9,-1,3,6,24,23,4)
        tv_input_variables.setText(intArray.contentToString())
    }
    fun sort(view: View){

        val startTime = System.currentTimeMillis();
        val sort = sortingAlog.instance.sort(intArray)
        val endTime = System.currentTimeMillis();
        tv_out_put.setText(sort.contentToString())
        tv_time_taken.setText("Time taken"+(endTime-startTime))
    }
}
