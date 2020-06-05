package com.shijen.algorithmandds.alogrithms

class QuickSort:Sorting {
    override suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        quickSort(arrayList,0,arrayList.size-1);
        return arrayList
    }

    private fun quickSort(arrayList: ArrayList<Int>, start: Int, end: Int) {
        val pivot = start;
        var low = start+1;
        var high = end
        if(low == end){
            if(arrayList[high] < arrayList[pivot]){
                val temp = arrayList.get(pivot)
                arrayList[pivot] = arrayList.get(high)
                arrayList[high] = temp
            }
            return
        }
        while (low<high){
            while(low<=end && arrayList.get(low)<arrayList.get(pivot)){
                low++
            }
            while(high>=start+1 && arrayList.get(high)>arrayList.get(pivot)){
                high--
            }
            if(high>low){
                val temp = arrayList.get(low)
                arrayList[low] = arrayList.get(high)
                arrayList[high] = temp
             }
        }
        if(high>pivot){
            val temp = arrayList.get(high)
            arrayList[high] = arrayList.get(pivot)
            arrayList[pivot] = temp
        }
        if(((high-1)-start)>=1)
            quickSort(arrayList,start,high-1)

        if(end-(high+1)>=1)
        quickSort(arrayList,high+1,end)
    }
}