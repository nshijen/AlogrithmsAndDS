package com.shijen.algorithmandds.alogrithms

enum class SortingAlgorithms(x:Sorting,name:String) {
    INSERTION_SORT(InsertionSort(),"Insertion Sort:"),
    BUBBLE_SORT(BubbleSort(),"Bubble Sort"),
    MERGE_SORT(MergeSort(),"Merge_Sort"),
    SELECTION_SORT(SelectionSort(),"Selection_Sort"),
    QUICK_SORT(QuickSort(),"Quick_Sort"),
    HEAP_SORT(HeapSort(),"Heap_Sort"),
    RADIX_SORT(RadixSort(),"Radix_Sort");
    val instance = x;
    val alogName = name

}