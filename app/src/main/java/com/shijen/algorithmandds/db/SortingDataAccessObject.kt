package com.shijen.algorithmandds.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms

@Dao
interface SortingDataAccessObject {
    @Insert
    fun insertSortingData(sortingSpeed:SortingSpeed)

    @Query("SELECT AVG(timeTaken) from SortingSpeed where alog like:sortingAlgorithm ")
    fun getAvgTimeOf(sortingAlgorithm: String):Double

    @Query("SELECT count(id) from SortingSpeed")
    fun getCount():Int
}