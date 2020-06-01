package com.shijen.algorithmandds.alogrithms

class RadixSort : Sorting {
    override suspend fun sort(array: IntArray): IntArray {
        var finalArrayList: List<Int> = array.toList()
        val bucketSort =
            bucketSort(finalArrayList, lengthOfLargestNum(finalArrayList as ArrayList<Int>))
        return bucketSort.toIntArray()
    }

    fun getUnitValues(num: Int, postion: Int): Int {
        val powered = Math.pow(10.toDouble(), postion.toDouble())
        var i = (num / powered).toInt() % 10
        if (i < 0) {
            i = -i;
        }
        return i
    }

    fun bucketSort(inputArray: List<Int>, count: Int): ArrayList<Int> {
        var tempArrayList: ArrayList<Int> = ArrayList();
        tempArrayList.addAll(inputArray)
        for (position in 0..count) {
            val arrayList: ArrayList<ArrayList<Int>> = ArrayList();
            val finalArray = ArrayList<Int>();
            for (b in 0..9) {
                arrayList.add(ArrayList())
            }
            for (a in tempArrayList) {
                val unitValues = getUnitValues(a, position)
                arrayList.get(unitValues).add(a)
            }
            for (p in arrayList) {
                if (p.size > 0) {
                    finalArray.addAll(p)
                }
            }
            tempArrayList.clear()
            tempArrayList.addAll(finalArray);
            finalArray.clear()
            arrayList.clear()
        }
        tempArrayList = separatePosNeg(tempArrayList)
        return tempArrayList
    }

    fun separatePosNeg(tempArrayList: ArrayList<Int>): ArrayList<Int> {
        val arrayNeg = ArrayList<Int>()
        val arrayPos = ArrayList<Int>()
        for (item in tempArrayList) {
            if (item < 0) {
                arrayNeg.add(item)
            } else {
                arrayPos.add(item)
            }
        }
        tempArrayList.clear()
        tempArrayList.addAll(arrayNeg.reversed());
        tempArrayList.addAll(arrayPos);
        return tempArrayList
    }

    fun lengthOfLargestNum(tempArrayList: ArrayList<Int>): Int {
        var x: Int = 0;
        for (temp in tempArrayList) {
            if (temp > Math.abs(x))
                x = temp
        }
        return x.toString().length
    }
}