package base;

import java.util.Comparator;

class MergeSort<T, C extends Comparator<T>> {
    public void mergeSort(T[] array, int start, int end, C comparator)
    {
        // base case
        if (start < end)
        {
            // find the middle point
            int middle = (start + end) / 2;

            mergeSort(array, start, middle, comparator); // sort first half
            mergeSort(array, middle + 1, end, comparator);  // sort second half

            // merge the sorted halves
            merge(array, start, middle, end, comparator);
        }
    }

    // merges two subarrays of array[].
    public void merge(T[] array, int start, int middle, int end, C comparator)
    {
        T[] leftArray  = (T[]) new Object[middle - start + 1];
        T[] rightArray = (T[]) new Object[end - middle];

        // fill in left array
        for (int i = 0; i < leftArray.length; ++i)
            leftArray[i] = array[start + i];

        // fill in right array
        for (int i = 0; i < rightArray.length; ++i)
            rightArray[i] = array[middle + 1 + i];

        /* Merge the temp arrays */

        // initial indexes of first and second subarrays
        int leftIndex = 0, rightIndex = 0;

        // the index we will start at when adding the subarrays back into the main array
        int currentIndex = start;

        // compare each index of the subarrays adding the lowest value to the currentIndex
        while (leftIndex < leftArray.length && rightIndex < rightArray.length)
        {
            if (comparator.compare(leftArray[leftIndex],rightArray[rightIndex]) <= 0)
            {
                array[currentIndex] = leftArray[leftIndex];
                leftIndex++;
            }
            else
            {
                array[currentIndex] = rightArray[rightIndex];
                rightIndex++;
            }
            currentIndex++;
        }

        // copy remaining elements of leftArray[] if any
        while (leftIndex < leftArray.length) array[currentIndex++] = leftArray[leftIndex++];

        // copy remaining elements of rightArray[] if any
        while (rightIndex < rightArray.length) array[currentIndex++] = rightArray[rightIndex++];
    }
}