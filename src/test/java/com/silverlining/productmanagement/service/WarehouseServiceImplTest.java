package com.silverlining.productmanagement.service;

import com.silverlining.productmanagement.models.WarehouseLocation;
import org.junit.jupiter.api.Test;

import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;

class WarehouseServiceImplTest {

    public int findKthLargest(int[] nums, int k) {
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        for(int i = 0; i < k; i++){
            minHeap.offer(nums[i]);
        }
        System.out.println(minHeap);
        for(int i = k; i < nums.length; i++){
            System.out.println(minHeap);
            if(nums[i] > minHeap.peek()){
                minHeap.poll();
                minHeap.offer(nums[i]);
            }
        }

        return minHeap.peek();
    }
    @Test
    void testEnum(){
        System.out.println(WarehouseLocation.valueOf("MUM"));
        System.out.println(WarehouseLocation.HYDERABAD);
    }

}