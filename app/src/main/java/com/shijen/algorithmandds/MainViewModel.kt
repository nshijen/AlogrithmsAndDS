package com.shijen.algorithmandds

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.room.Room
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
import com.shijen.algorithmandds.db.SortingDatabase
import com.shijen.algorithmandds.db.SortingSpeed
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var startTime: Long = 0L
    private var endTime: Long = 0L
    private val graphMap: HashMap<String, Double> = HashMap()
    private val applicationContext: Context = application.applicationContext
    val graphLiveData: MutableLiveData<HashMap<String, Double>> = MutableLiveData()
    val progressLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val sortingAlogrithmLiveData: MutableLiveData<SortingAlgorithms> = MutableLiveData()
    val inputVariablesLiveData: MutableLiveData<String> = MutableLiveData()
    val outputLiveData: MutableLiveData<String> = MutableLiveData()
    val timeTakenLiveData: MutableLiveData<Pair<Double?, Double>> = MutableLiveData()

    private lateinit var db: SortingDatabase

    fun sort() {
        CoroutineScope(Dispatchers.Default).launch {
            progressLiveData.postValue(true)
            val resourceIntArray = getResourceIntArray()
            updateTheInputString(resourceIntArray)
            sortTheArray(resourceIntArray)
        }
    }

    fun initDb() {
        db = Room.databaseBuilder(applicationContext, SortingDatabase::class.java, "SortingData")
            .build()
    }

    fun getResourceIntArray(): ArrayList<Int> {

        return applicationContext.resources.getIntArray(R.array.dummy_100000_array).toCollection(ArrayList<Int>())
        /*arrayListOf(6, 2, 9, 34, 3, 6, -1, -11, -132, -4, 24, 23, 4)*/


        /*intArrayOf(9,8,7,6,5,4,3,2,1,0,-1,-2)*/
    }

    private fun updateTheInputString(arrayList: ArrayList<Int>) {
        if (arrayList.size > 20) {
            inputVariablesLiveData.postValue("Sorting an array containing 1 lakh random numbers:")
        } else {
            inputVariablesLiveData.postValue(arrayList.toString())
        }
    }

    private suspend fun sortTheArray(arrayList: ArrayList<Int>) {
        startTime = System.currentTimeMillis();
        val sortedList = sortingAlogrithmLiveData.value?.instance?.sort(arrayList)
        endTime = System.currentTimeMillis()
        updateTheOutputTimeAndResult(sortedList, endTime - startTime)
    }

    private fun updateTheOutputTimeAndResult(arrayList: ArrayList<Int>?, timeTaken: Long) {
        val timeTakenSeconds = timeTaken.toDouble() / 1000
        var avgTime: Double? = null
        sortingAlogrithmLiveData.value?.let {
            insertIntoDb(timeTakenSeconds, it.alogName)
            avgTime = getAvgTime(it.alogName)
            timeTakenLiveData.postValue(Pair(avgTime, timeTakenSeconds))
        }
        progressLiveData.postValue(false)
        arrayList?.let {
            if (it.size > 20) {
                outputLiveData.postValue("Sorted 1 lakh random integers")
            } else {
                outputLiveData.postValue(it.toString())
            }
            println("Output Elements: first 10 ${it.take(10)}")
            println("Output Elements: last 10 ${it.takeLast(10)}")
        }
        setUpGraph()
    }

    fun setUpGraph() {
        graphMap.clear()
        for (item in SortingAlgorithms.values()) {
            val avgTime = getAvgTime(item.alogName)
            graphMap[item.alogName] = avgTime
            println("HASHMAP ${graphMap}")
        }
        graphLiveData.postValue(graphMap)
    }

    private fun insertIntoDb(double: Double, algoName: String) {
        db.getDao().insertSortingData(SortingSpeed(double, algoName))
    }

    private fun getAvgTime(algoName: String): Double {
        return db.getDao().getAvgTimeOf(algoName)
    }

    fun setSortingAlgo(sortingAlgorithm: SortingAlgorithms) {
        sortingAlogrithmLiveData.value = sortingAlgorithm
    }
}