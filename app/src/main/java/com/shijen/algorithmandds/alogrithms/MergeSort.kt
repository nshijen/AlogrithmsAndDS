package com.shijen.algorithmandds.alogrithms

class MergeSort : Sorting {

    override suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        val mergeSort = mergeSort(arrayList, 0, arrayList.size - 1)
        return mergeSort
    }

    fun mergeSort(arrayList: ArrayList<Int>, startPos: Int, endPos: Int): ArrayList<Int> {
        if (endPos - startPos == 0) {
            return arrayList;
        } else if (endPos - startPos == 1) {
            if (arrayList.get(endPos) >= arrayList.get(startPos)) {
                return arrayList
            } else {
                val temp = arrayList.get(startPos)
                arrayList[startPos] = arrayList.get(endPos)
                arrayList[endPos] = temp
            }
        } else {
            val median = (endPos + startPos) / 2
            mergeSort(arrayList, startPos, median)
            mergeSort(arrayList, median + 1, endPos)
            merge(arrayList, startPos, median, endPos)
        }
        return arrayList
    }

    private fun merge(list: ArrayList<Int>, start: Int, median: Int, endPos: Int) {
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
                if (list[firstArrPos] > list[secondArrPos]) {
                    tempArray[count] = list[secondArrPos]
                    secondArrPos++
                } else {
                    tempArray[count] = list[firstArrPos]
                    firstArrPos++
                }
            } else if (firstFinished) {
                tempArray[count] = list[secondArrPos]
                secondArrPos++
            } else {
                tempArray[count] = list[firstArrPos]
                firstArrPos++
            }
            count++
        }
        for (a in tempArray.withIndex()) {
            list[(start + a.index)] = a.value
        }
    }
}