package com.shijen.algorithmandds.alogrithms

class InsertionSort : Sorting {
    override suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        if (arrayList.size <= 1) {
            return arrayList
        }
        for (a in (1..(arrayList.size - 1))) {
            for (x in a - 1 downTo 0) {
                if (arrayList.get(a) >= arrayList.get(x)) {
                    val insertionPosition = x + 1
                    arrayList.add(insertionPosition,arrayList.get(a))
                    arrayList.removeAt(a)
                    break;
                } else {
                    if (x == 0) {
                        val insertionPosition = x
                        arrayList.add(insertionPosition,arrayList.get(a))
                        arrayList.removeAt(a)
                        break;
                    }
                }
            }
        }
        return arrayList
    }
}