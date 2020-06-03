package com.shijen.algorithmandds.db;

import androidx.room.Database;
import androidx.room.RoomDatabase

@Database(entities = arrayOf(SortingSpeed::class), version = 1)
abstract class SortingDatabase:RoomDatabase() {
    abstract fun getDao():SortingDataAccessObject
}
