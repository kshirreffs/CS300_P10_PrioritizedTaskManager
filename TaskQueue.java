//////////////// FILE HEADER //////////////////////////
//
// Title:    P10 Prioritized Task Manager
// Course:   CS 300 Spring 2024
//
// Author:   Katelyn Shirreffs
// Email:    kshirreffs@wisc.edu
// Lecturer: Hobbes LeGault
//
//////////////////////// ASSISTANCE/HELP CITATIONS ////////////////////////////
//
// Persons:         NONE
// Online Sources:  JavaDocs - https://cs300-www.cs.wisc.edu/sp24/p10/doc/TaskQueue.html
                    // for most method heade4r commenting
//                  Hobbes lecture code - Lec1_Heap.java
                    // for help thinking through enqueue and percolate methods
//                  Zybooks - https://learn.zybooks.com/zybook/WISCCOMPSCI300Spring2024/chapter/13/section/
                    // thinking through heapsort algo
//
///////////////////////////////////////////////////////////////////////////////

import java.util.NoSuchElementException;

/**
 * Represents a priority queue for Task objects
 */
public class TaskQueue {

  // CITE: JavaDocs - for most method header commenting
  /**
   * oversized array that holds all of Tasks in the heap
   */
  private Task[] heapData;
  
  /**
   * the criteria used to determine how to prioritize Tasks in the queue
   */
  private CompareCriteria priorityCriteria;
  
  /**
   * the number of items in the TaskQueue
   */
  private int size;
  
  /**
   * Creates an empty TaskQueue with the given capacity and priority criteria.
   * 
   * @param capacity - the max number of Tasks this priority queue can hold
   * @param priorityCriteria - the criteria for the queue to use to determine a Task's priority
   * @throws IllegalArgumentException - with a descriptive message if the capacity is non-positive
   */
  public TaskQueue(int capacity, CompareCriteria priorityCriteria) {
    if (capacity <= 0) throw new IllegalArgumentException("Capacity must be positive");
    heapData = new Task[capacity];
    this.priorityCriteria = priorityCriteria;
  }
  
  /**
   * Gets the criteria use to prioritize tasks in this a TaskQueue.
   * 
   * @return - the prioritization criteria of this TaskQueue
   */
  public CompareCriteria getPriorityCriteria() {
    return priorityCriteria;
  }
  
  /**
   * Reports if a TaskQueue is empty.
   * 
   * @return true if this TaskQueue is empty, false otherwise
   */
  public boolean isEmpty() {
    return size == 0;
  }
  
  /**
   * Reports the size of a TaskQueue.
   * 
   * @return the number of Tasks in this TaskQueue
   */
  public int size() {
    return size;
  }
  
  /**
   * Gets the Task in a TaskQueue that has the highest priority WITHOUT removing it. 
   * The Task that has the highest priority may differ based on the current priority criteria.
   * 
   * @return the Task in this queue with the highest priority
   * @throws NoSuchElementException - with descriptive message if this TaskQueue is empty
   */
  public Task peekBest() {
    if (isEmpty()) throw new NoSuchElementException("Empty queue");
    return heapData[0];
  }
  
  // CITE: Hobbes lecture code - thinking through enqueue method
  /**
   * Adds the newTask to this priority queue.
   * 
   * @param newTask - the task to add to the queue
   * @throws IllegalArgumentException - with a descriptive message if the Task is already completed
   * @throws IllegalStateException - with a descriptive message if the priority queue is full
   */
  public void enqueue(Task newTask) {
    // check if Task is completed
    if (newTask.isCompleted()) throw new IllegalArgumentException("Task is already completed");
    
    // check is queue is full
    if (size == heapData.length) throw new IllegalStateException("Queue has reached capacity");
    
    // (1) add the new value to the last leaf (end of the array)
    heapData[size] = newTask;
    size++; // increase size
    
    // (2) percolate that new value up
    percolateUp(size-1);
  }
  
  // CITE: Hobbes lecture code - for help implementing percolate method
  /**
   * Fixes one heap violation by moving it up the heap.
   * 
   * @param index - the of the element where the violation may be
   */
  protected void percolateUp(int index) {
    // see if there's anywhere to percolate up to (i.e. if there's a parent)
    // if there is we can see if we need to percolate up
    if (parent(index) >= 0) {
      
      // (1) compare the value at index to its parent value
      int compare = heapData[index].compareTo(heapData[parent(index)], priorityCriteria);
      
      // (2) if value > parent value, swap AND continue percolating
      if (compare > 0) {
        swap(index, parent(index));
        percolateUp(parent(index));
      }
    }
  }
  
  // CITE: Hobbes lecture code - pseudocode for dequeue
  /**
   * Gets and removes the Task that has the highest priority. 
   * The Task that has the highest priority may differ based on the current priority criteria.
   * 
   * @return the Task in this queue with the highest priority
   * @throws NoSuchElementException - with descriptive message if this TaskQueue is empty
   */
  public Task dequeue() {
    // check if queue is empty
    if (isEmpty()) throw new NoSuchElementException("Empty queue");
    
    // (1) SAVE the root of the heap
    Task root = heapData[0];
    
    // (2) move end of array into index 0
    heapData[0] = heapData[size-1];
    
    // "remove" that last item after moving it
    Task[] updated = new Task[heapData.length];
    for (int i = 0; i < size - 1; i++) { // skips the last value
      updated[i] = heapData[i];
    }
    heapData = updated;
    
    size--; // adjust size
    
    // (3) percolate DOWN
    percolateDown(0);
    
    // (4) return the old root
    return root;
  }
  
  /**
   * Fixes one heap violation by moving it down the heap.
   * 
   * @param index - the of the element where the violation may be
   */
  protected void percolateDown(int index) {
    // see if there's anywhere to percolate down to (i.e. if there's a child)
    // if there is we can see if we need to percolate down
    if (leftChild(index) < size && rightChild(index) < size) {
      
      // (1) compare the value at index to its child value
      Task leftChild = heapData[leftChild(index)];
      Task rightChild = heapData[rightChild(index)];
      int leftCompare = heapData[index].compareTo(leftChild, priorityCriteria);
      int rightCompare = heapData[index].compareTo(rightChild, priorityCriteria);
      
      // (2) if value < left child and < right child value, see which child is the biggest
      if (leftCompare < 0 && rightCompare < 0) {
        int childComp = leftChild.compareTo(rightChild, priorityCriteria);
        // if left child is greater than or equal to right child, swap with left child and percolate
        if (childComp >= 0) {
          swap(index, leftChild(index));
          percolateDown(leftChild(index));
        } 
        // otherwise swap with right child and continue to percolate
        else {
          swap(index, rightChild(index));
          percolateDown(rightChild(index));
        }
      } 
      // (2b) if our value is only less than the left child
      else if (leftCompare < 0) {
        swap(index, leftChild(index));
        percolateDown(leftChild(index));
      } 
      // (2c) if our value is only less than the right child
      else if (rightCompare < 0) {
        swap(index, rightChild(index));
        percolateDown(rightChild(index));
      }
    }
    // (3) check if there's only a left child to evaluate
    else if (leftChild(index) < size) {
      Task leftChild = heapData[leftChild(index)];
      int leftCompare = heapData[index].compareTo(leftChild, priorityCriteria);
      // then check if value > left child value, swap AND continue percolating
      if (leftCompare < 0) {
        swap(index, leftChild(index));
        percolateDown(leftChild(index));
      }
    }
  }
  
  // CITE: Zybooks - thinking through heap sorting
  /**
   * Changes the priority criteria of this priority queue and fixes it 
   * so that is is a proper priority queue based on the new criteria.
   * 
   * @param priorityCriteria - the (new) criteria that should be used to prioritize Tasks in queue
   */
  public void reprioritize(CompareCriteria priorityCriteria) {
    this.priorityCriteria = priorityCriteria;
    // Rebuild the heap according to the new priority criteria
    for (int i = size / 2 - 1; i >= 0; i--) {
        percolateDown(i);
    }
  }
  
  /**
   * Creates and returns a deep copy of the heap's array of data.
   * 
   * @return the deep copy of the array holding the heap's data
   */
  public Task[] getHeapData() {
    Task[] copy = new Task[heapData.length];
    for (int i = 0; i < size; i++) {
      copy[i] = heapData[i];
    }
    return heapData;
  }
  
  // HELPER METHODS
  
  /**
   * finds the left child of a given index value
   * 
   * @param i index of parent
   * @return index of left child
   */
  private int leftChild(int i) { return 2*i+1; }
  
  /**
   * finds the right child of a given index value
   * 
   * @param i index of parent
   * @return index of right child
   */
  private int rightChild(int i) { return 2*i+2; }
  
  /**
   * finds the parent of a given index value
   * 
   * @param i index of child
   * @return index of parent
   */
  private int parent(int i) { return (i-1)/2; }
  
  /**
   * swaps 2 Task items in heapData
   * 
   * @param i index of Task to move to index j
   * @param j index of Task to move to index i
   */
  private void swap(int i, int j) {
    Task temp = heapData[i];
    heapData[i] = heapData[j];
    heapData[j] = temp;
  }
  
}
