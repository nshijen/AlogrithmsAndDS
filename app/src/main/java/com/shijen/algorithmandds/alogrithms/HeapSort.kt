package com.shijen.algorithmandds.alogrithms

class HeapSort:Sorting{
    override suspend fun sort(arrayList: ArrayList<Int>): ArrayList<Int> {
        /** we can also use this method [heapifyReverseOrder]*/
        val createHeap = createHeap(arrayList);
        deleteElements(createHeap)
        return createHeap
    }

    fun heapifyReverseOrder(list:ArrayList<Int>):ArrayList<Int>{
        val heap = ArrayList<Int>()
        var counter = list.size -1
        while(counter>=0){
            heap[counter]=list[counter]
            heapifingReversively(heap,counter)
            counter--
        }
        return heap
    }

    private fun deleteElements(createHeap: ArrayList<Int>) {
        var lastElement = createHeap.size-1
        while(lastElement > 0){
            deleteElement(createHeap,lastElement)
            lastElement--
            adjust(createHeap,lastElement)
        }
    }

    private fun adjust(heap: ArrayList<Int>, lastElement: Int) {
        var swaped = true
        var pointer = 0;
        while(hasChild(pointer,lastElement) && swaped){
            swaped = false
            if(has2ndChild(pointer,lastElement)){
                if(heap[2*pointer+1] >= heap[2*pointer+2]){
                    if(heap[pointer]<heap[2*pointer+1]){
                        swap(heap,pointer,2*pointer+1)
                        pointer = 2*pointer+1
                        swaped = true
                    }
                }else{
                    if(heap[pointer]<heap[2*pointer+2]){
                        swap(heap,pointer,2*pointer+2)
                        pointer = 2*pointer+2
                        swaped = true
                    }
                }
            }else{
                if(heap[pointer]<heap[2*pointer+1]){
                    swap(heap,pointer,2*pointer+1)
                    pointer = 2*pointer+1
                    swaped = true
                }
            }
        }
    }


    private fun heapifingReversively(heap: ArrayList<Int>, pointer: Int) {
        var swaped = true
        var tempPointer = pointer;
        var lastElement = heap.size-1
        while(hasChild(tempPointer,lastElement) && swaped){
            swaped = false
            if(has2ndChild(tempPointer,lastElement)){
                if(heap[2*tempPointer+1] >= heap[2*tempPointer+2]){
                    if(heap[tempPointer]<heap[2*tempPointer+1]){
                        swap(heap,tempPointer,2*tempPointer+1)
                        tempPointer = 2*tempPointer+1
                        swaped = true
                    }
                }else{
                    if(heap[tempPointer]<heap[2*tempPointer+2]){
                        swap(heap,tempPointer,2*tempPointer+2)
                        tempPointer = 2*tempPointer+2
                        swaped = true
                    }
                }
            }else{
                if(heap[tempPointer]<heap[2*tempPointer+1]){
                    swap(heap,tempPointer,2*tempPointer+1)
                    tempPointer = 2*tempPointer+1
                    swaped = true
                }
            }
        }
    }

    private fun has2ndChild(pointer: Int, lastElement: Int): Boolean {
        return 2*pointer+2<=lastElement
    }

    private fun hasChild(pointer: Int, lastElement: Int): Boolean {
        return 2*pointer+1<=lastElement
    }

    private fun deleteElement(createHeap: ArrayList<Int>, lastElement: Int) {
        swap(createHeap,lastElement,0)
    }

    private fun createHeap(array: ArrayList<Int>):ArrayList<Int> {
        val heap = ArrayList<Int>()
        var counter = 0
        while(counter<array.size){
            heap.add(array.get(counter))
            compareAndSwap(heap,counter)
            counter++
        }
        return heap
    }

    private fun compareAndSwap(array: ArrayList<Int>, counter: Int) {
        var pointer = counter
        while(hasParent(pointer)){
            var parentNode = (pointer - 1)/2
            if(array.get(parentNode)<array.get(pointer)){
                swap(array, parentNode, pointer)
            }
            pointer = parentNode
        }
    }

    private fun swap(array: ArrayList<Int>, ele1: Int, ele2: Int) {
        val temp = array.get(ele1)
        array[ele1] = array[ele2]
        array[ele2] = temp
    }

    private fun hasParent(counter: Int): Boolean {
        return counter != 0
    }
}