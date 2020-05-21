package com.shijen.algorithmandds.alogrithms

class InsertionSort : Sorting {
    private fun shiftAndInsert(
        array: IntArray,
        insertionItemPosition: Int,
        insertionPosition: Int
    ) {
        val insertionItem = array.get(insertionItemPosition)
        if (insertionPosition == insertionItemPosition) {
            return
        }
        shiftElements(array, insertionPosition, insertionItemPosition)
        array[insertionPosition] = insertionItem
    }

    private fun shiftElements(
        array: IntArray,
        insertionPosition: Int,
        insertionItemPostion: Int
    ) {
        for (a in insertionItemPostion downTo insertionPosition) {
            if (a - 1 != -1) {
                array[a] = array.get(a - 1)
            }
        }
    }

    override fun sort(array: IntArray): IntArray {
        if (array.size <= 1) {
            return array
        }
        for (a in (1..(array.size - 1))) {
            //Log.d("Shijen","Outerloop index:"+a+" element:"+array.get(a))
            for (x in a - 1 downTo 0) {
                //Log.d("Shijen","Innerloop index:"+x+" element:"+array.get(x))
                if (array.get(a) >= array.get(x)) {
                    val insertionPosition = x + 1
                    shiftAndInsert(array, a, insertionPosition)
                    break;
                } else {
                    if (x == 0) {
                        val insertionPosition = x
                        shiftAndInsert(array, a, insertionPosition)
                        break;
                    }
                }
            }
        }
        return array
    }
}