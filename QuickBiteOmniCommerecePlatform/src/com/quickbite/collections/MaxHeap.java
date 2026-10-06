package com.quickbite.collections;


import java.util.ArrayList;
import java.util.List;

import com.quickbite.entity.Product;

public class MaxHeap {
    private List<ProductEntry> heap = new ArrayList<>();

    public static class ProductEntry {
        public Product product;
        public int salesCount;

        public ProductEntry(Product product, int salesCount) {
            this.product = product;
            this.salesCount = salesCount;
        }
    }

    public void insert(Product product, int salesCount) {
        heap.add(new ProductEntry(product, salesCount));
        siftUp(heap.size() - 1);
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index).salesCount > heap.get(parentIndex).salesCount) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    public ProductEntry extractMax() {
        if (heap.isEmpty()) return null;
        ProductEntry max = heap.get(0);
        ProductEntry last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return max;
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (index < size) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int largest = index;

            if (left < size && heap.get(left).salesCount > heap.get(largest).salesCount) largest = left;
            if (right < size && heap.get(right).salesCount > heap.get(largest).salesCount) largest = right;

            if (largest != index) {
                swap(index, largest);
                index = largest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        ProductEntry temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}






