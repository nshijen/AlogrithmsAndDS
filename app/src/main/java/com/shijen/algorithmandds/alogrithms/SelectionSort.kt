package com.shijen.algorithmandds.alogrithms

class SelectionSort:Sorting{
    override suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        for(a in 0..arrayList.size-1){
            var largeItemPos = 0
            for(k in 1..(arrayList.size-1)-a){
               if(arrayList.get(k)>arrayList.get(largeItemPos)){
                   largeItemPos = k;
               }
            }
            if(largeItemPos != (arrayList.size-1)-a){
                val temp = arrayList.get((arrayList.size-1)-a)
                arrayList[(arrayList.size-1) -a] = arrayList[largeItemPos]
                arrayList[largeItemPos] = temp
            }
        }
        return  arrayList
    }
}