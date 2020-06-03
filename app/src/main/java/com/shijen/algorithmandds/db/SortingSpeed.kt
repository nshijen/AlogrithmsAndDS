package com.shijen.algorithmandds.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shijen.algorithmandds.alogrithms.SortingAlgorithms
@Entity
data class SortingSpeed(@ColumnInfo var timeTaken:Double, @ColumnInfo var alog:String){
    @PrimaryKey(autoGenerate = true) var id:Int = 0
}