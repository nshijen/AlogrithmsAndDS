package com.shijen.algorithmandds.alogrithms

class SelectionSort :Sorting{
    override suspend fun sort(array: IntArray): IntArray {
        for(a in 0..array.size-1){
            var largeItemPos = 0
            for(k in 1..array.size-a){
               if(array.get(k)>array.get(largeItemPos)){
                   largeItemPos = k;
               }
                if(k == array.size-a){
                    val temp = array.get(k)
                    array[k] = array[largeItemPos]
                    array[largeItemPos] = temp
                }
            }
        }
        return  array
    }
}