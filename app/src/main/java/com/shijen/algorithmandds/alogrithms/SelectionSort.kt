package com.shijen.algorithmandds.alogrithms

class SelectionSort:Sorting{
    override suspend fun sort(array: IntArray): IntArray {
        for(a in 0..array.size-1){
            var largeItemPos = 0
            for(k in 1..(array.size-1)-a){
               if(array.get(k)>array.get(largeItemPos)){
                   largeItemPos = k;
               }
            }
            if(largeItemPos != (array.size-1)-a){
                val temp = array.get((array.size-1)-a)
                array[(array.size-1) -a] = array[largeItemPos]
                array[largeItemPos] = temp
            }
        }
        return  array
    }
}