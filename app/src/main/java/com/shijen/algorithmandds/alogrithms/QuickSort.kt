package com.shijen.algorithmandds.alogrithms

class QuickSort:Sorting {
    override suspend fun sort(array: IntArray): IntArray {
        quickSort(array,0,array.size-1);
        return array
    }

    private fun quickSort(array: IntArray, start: Int, end: Int) {
        val pivot = start;
        var low = start+1;
        var high = end
        if(low == end){
            if(array[high] < array[pivot]){
                val temp = array.get(pivot)
                array[pivot] = array.get(high)
                array[high] = temp
            }
            return
        }
        while (low<high){
            while(low<=end && array.get(low)<array.get(pivot)){
                low++
            }
            while(high>=start+1 && array.get(high)>array.get(pivot)){
                high--
            }
            if(high>low){
                val temp = array.get(low)
                array[low] = array.get(high)
                array[high] = temp
             }
        }
        if(high>pivot){
            val temp = array.get(high)
            array[high] = array.get(pivot)
            array[pivot] = temp
        }
        if(((high-1)-start)>=1)
            quickSort(array,start,high-1)

        if(end-(high+1)>=1)
        quickSort(array,high+1,end)
    }
}