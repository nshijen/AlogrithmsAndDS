package com.shijen.algorithmandds.alogrithms

class BubbleSort : Sorting {
    override  suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        var count = 1
        var swaped = true
        while (count < arrayList.size && swaped) {
            swaped = false
            for (a in 0..((arrayList.size - 1) - count)) {
                if (arrayList.get(a) > arrayList.get(a + 1)) {
                    swap(arrayList, a, a + 1)
                    swaped = true
                }
            }
            count++
        }
        return arrayList
    }

    fun swap(arrayList: ArrayList<Int>, a: Int, b: Int) {
        val temp = arrayList.get(a)
        arrayList[a] = arrayList[b]
        arrayList[b] = temp
    }
}