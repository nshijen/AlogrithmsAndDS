package com.shijen.algorithmandds.alogrithms

class MergeSort : Sorting {
    override suspend fun sort(array: IntArray): IntArray {
        val mergeSort = mergeSort(array, 0, array.size - 1)
        return mergeSort
    }

    fun mergeSort(intArray: IntArray, startPos: Int, endPos: Int): IntArray {
        if (endPos - startPos == 0) {
            return intArray;
        } else if (endPos - startPos == 1) {
            if (intArray.get(endPos) >= intArray.get(startPos)) {
                return intArray
            } else {
                val temp = intArray.get(startPos)
                intArray[startPos] = intArray.get(endPos)
                intArray[endPos] = temp
            }
        } else {
            val median = (endPos + startPos) / 2
            mergeSort(intArray, startPos, median)
            mergeSort(intArray, median + 1, endPos)
            merge(intArray, startPos, median, endPos)
        }
        return intArray
    }

    private fun merge(array: IntArray, start: Int, median: Int, endPos: Int) {
        var firstFinished = false;
        var secondFinished = false;
        var firstArrPos = start
        var secondArrPos = median + 1
        val tempArray = IntArray(endPos - start + 1)
        var count = 0;
        while (count < tempArray.size) {
            if (firstArrPos > median) {
                firstFinished = true
            }
            if (secondArrPos > endPos) {
                secondFinished = true
            }
            if (!firstFinished && !secondFinished) {
                if (array[firstArrPos] > array[secondArrPos]) {
                    tempArray[count] = array[secondArrPos]
                    secondArrPos++
                } else {
                    tempArray[count] = array[firstArrPos]
                    firstArrPos++
                }
            } else if (firstFinished) {
                tempArray[count] = array[secondArrPos]
                secondArrPos++
            } else {
                tempArray[count] = array[firstArrPos]
                firstArrPos++
            }
            count++
        }
        for (a in tempArray.withIndex()) {
            array[(start + a.index)] = a.value
        }
    }
}