package com.shijen.algorithmandds.alogrithms

class BubbleSort : Sorting {
    override  suspend fun sort(array: IntArray): IntArray {
        var count = 1
        var swaped = true
        while (count < array.size && swaped) {
            swaped = false
            for (a in 0..((array.size - 1) - count)) {
                if (array.get(a) > array.get(a + 1)) {
                    swap(array, a, a + 1)
                    swaped = true
                }
            }
            count++
        }
        return array
    }

    fun swap(array: IntArray, a: Int, b: Int) {
        val temp = array.get(a)
        array[a] = array[b]
        array[b] = temp
    }
}