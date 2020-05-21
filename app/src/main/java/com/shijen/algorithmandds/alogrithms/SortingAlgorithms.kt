package com.shijen.algorithmandds.alogrithms

enum class SortingAlgorithms(x:Sorting,name:String) {
    INSERTION_SORT(InsertionSort(),"Insertion Sort:"),
    BUBBLE_SORT(BubbleSort(),"Bubble Sort");
    val instance = x;
    val alogName = name

}