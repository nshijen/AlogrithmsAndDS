package com.shijen.algorithmandds.alogrithms

class BubbleSort : Sorting {
    override fun sort(array: IntArray): IntArray {
        var count = 1
        while (count < array.size) {
            for (a in 0..((array.size - 1) - count)) {
                if (array.get(a) > array.get(a + 1)) {
                    swap(array, a, a + 1)
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