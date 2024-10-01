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
// Online Sources:  JavaDocs - https://cs300-www.cs.wisc.edu/sp24/p10/doc/TaskQueueTester.html
                    // for most class and method header commenting
//
///////////////////////////////////////////////////////////////////////////////

// CITE: JavaDocs - for most class and method header commenting

import java.util.NoSuchElementException;

/**
 * A suite of tester methods to check the correctness of various methods 
 * for the Prioritized Task Manager assignment.
 */
public class TaskQueueTester {

  /**
   * Tests the correctness of a Task compareTo() method implementation 
   * when the criteria parameter is TIME.
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToTime() {
    Task small = new Task("Chemistry", "do chem hw", 30, PriorityLevel.MEDIUM);
    Task big = new Task("Final paper", "write an outline", 90, PriorityLevel.URGENT);
    Task big2 = new Task("Other paper", "write an outline", 90, PriorityLevel.HIGH);
    
    // when our task should be smaller than the other (less time)
    // should return number less than 0
    if (small.compareTo(big, CompareCriteria.TIME) >= 0) return false;
    
    // when our task should be bigger than the other (more time)
    // should return number more than 0
    if (big.compareTo(small, CompareCriteria.TIME) <= 0) return false;
    
    // when our task should be equal to the other (same time)
    // should return 0
    if (big.compareTo(big2, CompareCriteria.TIME) != 0) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a Task compareTo() method implementation 
   * when the criteria parameter is TITLE.
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToTitle() {
    Task small = new Task("Chemistry", "do chem hw", 30, PriorityLevel.MEDIUM);
    Task big = new Task("Final paper", "write an outline", 90, PriorityLevel.URGENT);
    Task big2 = new Task("Final paper", "draft it", 200, PriorityLevel.HIGH);
    Task bigLowerCase = new Task("a", "write an outline", 90, PriorityLevel.URGENT);
    Task smallLowerCase = new Task("z", "draft it", 200, PriorityLevel.HIGH);
    Task upper = new Task("A", "draft it", 200, PriorityLevel.HIGH);
    Task lower = new Task("a", "draft it", 200, PriorityLevel.HIGH);
    
    // TEST 1: upper case
    
    // when our task should be smaller than the other (greater lexicographically)
    // should return number less than 0
    if (big.compareTo(small, CompareCriteria.TITLE) >= 0) return false;
    
    // when our task should be bigger than the other (smaller lexicographically)
    // should return number more than 0
    if (small.compareTo(big, CompareCriteria.TITLE) <= 0) return false;
    
    // when our task should be equal to the other (same lexicographically)
    // should return 0
    if (big.compareTo(big2, CompareCriteria.TITLE) != 0) return false;
    
    // TEST 2: lower case
    
    // when our task should be bigger than the other (smaller lexicographically)
    // should return number more than 0
    if (bigLowerCase.compareTo(smallLowerCase, CompareCriteria.TITLE) <= 0) return false;
    
    // when our task should be smaller than the other (greater lexicographically)
    // should return number less than 0
    if (smallLowerCase.compareTo(bigLowerCase, CompareCriteria.TITLE) >= 0) return false;
    
    // TEST 3: upper vs lower case
    
    // when our task should be bigger than the other (smaller lexicographically)
    // should return number more than 0
    if (upper.compareTo(lower, CompareCriteria.TITLE) <= 0) return false;
    
    // when our task should be smaller than the other (greater lexicographically)
    // should return number less than 0
    if (lower.compareTo(upper, CompareCriteria.TITLE) >= 0) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a Task compareTo() method implementation 
   * when the criteria parameter is LEVEL.
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testCompareToLevel() {
    Task small = new Task("Chemistry", "do chem hw", 30, PriorityLevel.MEDIUM);
    Task big = new Task("Final paper", "write an outline", 90, PriorityLevel.HIGH);
    Task big2 = new Task("Other paper", "write", 90, PriorityLevel.HIGH);
    Task biggest = new Task("Mandarin", "hw", 200, PriorityLevel.URGENT);
    
    // when our task should be smaller than the other (smaller priority)
    // should return number less than 0
    if (small.compareTo(big, CompareCriteria.LEVEL) >= 0) return false;
    
    // when our task should be bigger than the other (greater priority)
    // should return number more than 0
    if (big.compareTo(small, CompareCriteria.LEVEL) <= 0) return false;
    
    // when our task should be bigger than the other (greater priority)
    // should return number more than 0
    if (biggest.compareTo(big, CompareCriteria.LEVEL) <= 0) return false;
    
    // when our task should be equal to the other (same lexicographically)
    // should return 0
    if (big.compareTo(big2, CompareCriteria.LEVEL) != 0) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a TaskQueue enqueue() method implementation 
   * including exceptions and edge cases (if applicable).
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testEnqueue() {
    
    // TEST 1: enqueue one value
    TaskQueue tq = new TaskQueue(5, CompareCriteria.TIME);
    Task t = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    tq.enqueue(t); // enqueue the task
    
    // check heapData contents
    Task[] tqData = tq.getHeapData();
    if (!tqData[0].equals(t)) return false; // make sure t was enqueued properly
    
    // check size variable
    if (tq.size() != 1) return false;
    
    // TEST 2: enqueue multiple values (accordingly tests percolateUp())
    Task t2 = new Task("Chemistry", "do chem hw", 70, PriorityLevel.MEDIUM); // [t, t2]
    Task t3 = new Task("Chemistry", "do chem hw", 50, PriorityLevel.MEDIUM); // [t, t2, t3]
    Task t4 = new Task("Chemistry", "do chem hw", 90, PriorityLevel.MEDIUM); // [t4, t, t3, t2]
    tq.enqueue(t2);
    tq.enqueue(t3);
    tq.enqueue(t4);
    
    // check heapData contents (with expected order)
    // expected = [t4, t, t3, t2] (organized by time)
    tqData = tq.getHeapData();
    if (!tqData[0].equals(t4)) return false;
    if (!tqData[1].equals(t)) return false;
    if (!tqData[2].equals(t3)) return false;
    if (!tqData[3].equals(t2)) return false;
    
    // check size variable
    if (tq.size() != 4) return false;
    
    // TEST 3: adding a completed task (should throw error)
    Task completed = new Task("Chemistry", "do chem hw", 90, PriorityLevel.MEDIUM);
    completed.markCompleted();
    
    try {
      tq.enqueue(completed);
      return false; // no exception was thrown when it should have been
    } catch (IllegalArgumentException e) {
      // make sure we caught the right kind of error, if so we can continue with our other tests
      // nothing happens
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    // TEST 4: adding a task to a full queue (should throw error)
    Task t5 = new Task("Chemistry", "do chem hw", 40, PriorityLevel.MEDIUM);
    Task t6 = new Task("Chemistry", "do chem hw", 100, PriorityLevel.MEDIUM);
    tq.enqueue(t5); // should now be a full queue
    
    try {
      tq.enqueue(t6);
      return false; // no exception was thrown when it should have been
    } catch (IllegalStateException e) {
      // do nothing
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    // TESTS Part 2 (CompareCriteria = LEVEL)
    
    // TEST 1: enqueue multiple values (accordingly tests percolateUp())
    TaskQueue levelQueue = new TaskQueue(20, CompareCriteria.LEVEL);
    Task medium = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task optional = new Task("Chemistry", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task high = new Task("Chemistry", "do chem hw", 50, PriorityLevel.HIGH);
    Task urgent = new Task("Chemistry", "do chem hw", 90, PriorityLevel.URGENT);
    Task low = new Task("Chemistry", "do chem hw", 90, PriorityLevel.LOW);
    levelQueue.enqueue(medium); // [medium]
    levelQueue.enqueue(optional); // [medium, optional]
    levelQueue.enqueue(high); // [high, optional, medium]
    levelQueue.enqueue(urgent); // [urgent, high, medium, optional]
    levelQueue.enqueue(low); // [urgent, high, medium, optional, low]
    
    // check heapData contents (with expected order)
    // expected = [urgent, high, medium, optional, low] (organized by level)
    Task[] levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(urgent)) return false;
    if (!levelData[1].equals(high)) return false;
    if (!levelData[2].equals(medium)) return false;
    if (!levelData[3].equals(optional)) return false;
    if (!levelData[4].equals(low)) return false;
    
    // check size variable
    if (tq.size() != 5) return false;
    
    // TEST 2: enqueue more values (with duplicate levels)
    Task optional2 = new Task("Chemistry", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task high2 = new Task("Chemistry", "do chem hw", 50, PriorityLevel.HIGH);
    Task urgent2 = new Task("Chemistry", "do chem hw", 90, PriorityLevel.URGENT);
    levelQueue.enqueue(optional2); // [urgent, high, medium, optional, low, opt2]
    levelQueue.enqueue(high2); // [urgent, high, high2, optional, low, opt2, medium]
    levelQueue.enqueue(urgent2); // [urgent, urgent2, high2, high, low, opt2, medium, optional]
    
    // check heapData contents (with expected order)
    // expected = [urgent, urgent2, high2, high, low, opt2, medium, optional] (organized by level)
    levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(urgent)) return false;
    if (!levelData[1].equals(urgent2)) return false;
    if (!levelData[2].equals(high2)) return false;
    if (!levelData[3].equals(high)) return false;
    if (!levelData[4].equals(low)) return false;
    if (!levelData[5].equals(optional2)) return false;
    if (!levelData[6].equals(medium)) return false;
    if (!levelData[7].equals(optional)) return false;
    
    // TESTS Part 3 (CompareCriteria = TITLE)
    
    // TEST 1: enqueue multiple values (accordingly tests percolateUp())
    TaskQueue titleQueue = new TaskQueue(20, CompareCriteria.TITLE);
    Task a = new Task("a", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task B = new Task("B", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task c = new Task("c", "do chem hw", 50, PriorityLevel.HIGH);
    Task D = new Task("D", "do chem hw", 90, PriorityLevel.URGENT);
    Task e = new Task("e", "do chem hw", 90, PriorityLevel.LOW);
    titleQueue.enqueue(a); // [a]
    titleQueue.enqueue(B); // [B, a]
    titleQueue.enqueue(c); // [B, a, c]
    titleQueue.enqueue(D); // [B, D, c, a]
    titleQueue.enqueue(e); // [B, D, c, a, e]
    
    // check heapData contents (with expected order)
    // expected = [B, D, c, a, e] (organized by title)
    Task[] titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(B)) return false;
    if (!titleData[1].equals(D)) return false;
    if (!titleData[2].equals(c)) return false;
    if (!titleData[3].equals(a)) return false;
    if (!titleData[4].equals(e)) return false;
    
    // TEST 2: enqueue more values (with some duplicates)
    Task z = new Task("z", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task B2 = new Task("B", "do chem hw", 50, PriorityLevel.HIGH);
    Task H = new Task("H", "do chem hw", 90, PriorityLevel.URGENT);
    titleQueue.enqueue(z); // [B, D, c, a, e, z]
    titleQueue.enqueue(B2); // [B, D, B2, a, e, z, c]
    titleQueue.enqueue(H); // [B, D, B2, H, e, z, c, a]
    
    // check heapData contents (with expected order)
    // expected = [B, D, B2, H, e, z, c, a] (organized by title)
    titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(B)) return false;
    if (!titleData[1].equals(D)) return false;
    if (!titleData[2].equals(B2)) return false;
    if (!titleData[3].equals(H)) return false;
    if (!titleData[4].equals(e)) return false;
    if (!titleData[5].equals(z)) return false;
    if (!titleData[6].equals(c)) return false;
    if (!titleData[7].equals(a)) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a TaskQueue dequeue() method implementation 
   * including exceptions and edge cases (if applicable).
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testDequeue() {
    
    // set up queue
    TaskQueue tq = new TaskQueue(5, CompareCriteria.TIME);
    Task t = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task t2 = new Task("Chemistry", "do chem hw", 70, PriorityLevel.MEDIUM);
    Task t3 = new Task("Chemistry", "do chem hw", 50, PriorityLevel.MEDIUM);
    Task t4 = new Task("Chemistry", "do chem hw", 90, PriorityLevel.MEDIUM);
    tq.enqueue(t);
    tq.enqueue(t2);
    tq.enqueue(t3);
    tq.enqueue(t4);
    // current queue: [t4, t, t3, t2]
    
    // TEST 1: dequeue one value
    tq.dequeue(); // should dequeue t4
    
    // check heapData contents
    // expected = [t, t2, t3] (organized by time)
    Task[] tqData = tq.getHeapData();
    if (!tqData[0].equals(t)) return false;
    if (!tqData[1].equals(t2)) return false;
    if (!tqData[2].equals(t3)) return false;
    
    // check size variable
    if (tq.size() != 3) return false;
    
    // TEST 2: dequeue all values (accordingly tests percolateDown())
    
    tq.dequeue(); // should dequeue t2
    // expected = [t2, t3]
    tqData = tq.getHeapData();
    if (!tqData[0].equals(t2)) return false;
    if (!tqData[1].equals(t3)) return false;
    
    tq.dequeue(); // should dequeue t2
    // expected = [t3]
    tqData = tq.getHeapData();
    if (!tqData[0].equals(t3)) return false;
    
    tq.dequeue(); // should dequeue t3
    // expected = empty array
    if (!tq.isEmpty()) return false;
    
    // TEST 3: dequeue from empty queue (should throw error)
    try {
      tq.dequeue();
      return false; // no exception was thrown when it should have been
    } catch (NoSuchElementException e) {
      // do nothing
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    // TESTS Part 2 (CompareCriteria = LEVEL)
    
    // set up queue
    TaskQueue levelQueue = new TaskQueue(20, CompareCriteria.LEVEL);
    Task medium = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task optional = new Task("Chemistry", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task high = new Task("Chemistry", "do chem hw", 50, PriorityLevel.HIGH);
    Task urgent = new Task("Chemistry", "do chem hw", 90, PriorityLevel.URGENT);
    Task low = new Task("Chemistry", "do chem hw", 90, PriorityLevel.LOW);
    levelQueue.enqueue(medium); // [medium]
    levelQueue.enqueue(optional); // [medium, optional]
    levelQueue.enqueue(high); // [high, optional, medium]
    levelQueue.enqueue(urgent); // [urgent, high, medium, optional]
    levelQueue.enqueue(low); // [urgent, high, medium, optional, low]
    
    // TEST 1: dequeue one value
    levelQueue.dequeue(); // should dequeue urgent
    
    // check heapData contents
    // expected = [high, low, medium, optional] (organized by level)
    Task[] levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(high)) return false;
    if (!levelData[1].equals(low)) return false;
    if (!levelData[2].equals(medium)) return false;
    if (!levelData[3].equals(optional)) return false;
    
    // check size variable
    if (levelQueue.size() != 4) return false;
    
    // TEST 2: dequeue all values (accordingly tests percolateDown())
    
    levelQueue.dequeue(); // should dequeue high
    // expected = [medium, low, opt]
    levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(medium)) return false;
    if (!levelData[1].equals(low)) return false;
    if (!levelData[2].equals(optional)) return false;
    
    levelQueue.dequeue(); // should dequeue medium
    // expected = [low, optional]
    levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(low)) return false;
    if (!levelData[1].equals(optional)) return false;
    
    levelQueue.dequeue(); // should dequeue low
    // expected = [optional]
    levelData = levelQueue.getHeapData();
    if (!levelData[0].equals(optional)) return false;
    
    levelQueue.dequeue(); // should dequeue optional
    // expected = empty
    if (!levelQueue.isEmpty()) return false;
    
    // TESTS Part 3 (CompareCriteria = TITLE)
    
    // set up queue
    TaskQueue titleQueue = new TaskQueue(20, CompareCriteria.TITLE);
    Task a = new Task("a", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task B = new Task("B", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task c = new Task("c", "do chem hw", 50, PriorityLevel.HIGH);
    Task D = new Task("D", "do chem hw", 90, PriorityLevel.URGENT);
    Task e = new Task("e", "do chem hw", 90, PriorityLevel.LOW);
    titleQueue.enqueue(a); // [a]
    titleQueue.enqueue(B); // [B, a]
    titleQueue.enqueue(c); // [B, a, c]
    titleQueue.enqueue(D); // [B, D, c, a]
    titleQueue.enqueue(e); // [B, D, c, a, e]
    
    // TEST 1: dequeue one value
    titleQueue.dequeue(); // should dequeue B
    
    // check heapData contents
    // expected = [D, a, c, e] (organized by title)
    Task[] titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(D)) return false;
    if (!titleData[1].equals(a)) return false;
    if (!titleData[2].equals(c)) return false;
    if (!titleData[3].equals(e)) return false;
    
    // TEST 2: dequeue all values (accordingly tests percolateDown())
    
    titleQueue.dequeue(); // should dequeue D
    // expected = [a, e, c]
    titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(a)) return false;
    if (!titleData[1].equals(e)) return false;
    if (!titleData[2].equals(c)) return false;
    
    titleQueue.dequeue(); // should dequeue a
    // expected = [c, e]
    titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(c)) return false;
    if (!titleData[1].equals(e)) return false;
    
    titleQueue.dequeue(); // should dequeue c
    // expected = [e]
    titleData = titleQueue.getHeapData();
    if (!titleData[0].equals(e)) return false;
    
    titleQueue.dequeue(); // should dequeue optional
    // expected = empty
    if (!titleQueue.isEmpty()) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a TaskQueue peek() method implementation 
   * including exceptions and edge cases (if applicable).
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testPeek() {
    
    // set up queue
    TaskQueue tq = new TaskQueue(5, CompareCriteria.TIME);
    Task t = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task t2 = new Task("Chemistry", "do chem hw", 70, PriorityLevel.MEDIUM);
    Task t3 = new Task("Chemistry", "do chem hw", 50, PriorityLevel.MEDIUM);
    Task t4 = new Task("Chemistry", "do chem hw", 90, PriorityLevel.MEDIUM);
    tq.enqueue(t);
    tq.enqueue(t2);
    tq.enqueue(t3);
    tq.enqueue(t4);
    
    // TEST 1: peek at value
    Task best = tq.peekBest();
    
    // make sure we returned the right value (best is t4)
    if (!best.equals(t4)) return false;
    
    // TEST 2: peeking at an empty queue, should throw an error
    // empty our queue
    tq.dequeue();
    tq.dequeue();
    tq.dequeue();
    tq.dequeue();
    
    try {
      tq.peekBest();
      return false; // no exception was thrown when it should have been
    } catch (NoSuchElementException e) {
      // do nothing
    } catch (Exception e) { // any other type of exception is not good, it's a broken implementation
      e.printStackTrace();
      return false;
    }
    
    // TESTS Part 2 (CompareCriteria = TITLE)
    
    TaskQueue titleQueue = new TaskQueue(20, CompareCriteria.TITLE);
    Task a = new Task("a", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task B = new Task("B", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task c = new Task("c", "do chem hw", 50, PriorityLevel.HIGH);
    Task D = new Task("D", "do chem hw", 90, PriorityLevel.URGENT);
    Task e = new Task("e", "do chem hw", 90, PriorityLevel.LOW);
    titleQueue.enqueue(a); // [a]
    titleQueue.enqueue(B); // [B, a]
    titleQueue.enqueue(c); // [B, a, c]
    titleQueue.enqueue(D); // [B, D, c, a]
    titleQueue.enqueue(e); // [B, D, c, a, e]
    
    // TEST 1: peek at value, make sure data remains unchanged
    Task bestTitle = titleQueue.peekBest();
    
    // make sure we returned the right value (best is B)
    if (!bestTitle.equals(B)) return false;
    
    // TESTS Part 3 (CompareCriteria = LEVEL)
    
    TaskQueue levelQueue = new TaskQueue(20, CompareCriteria.LEVEL);
    Task medium = new Task("Chemistry", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task optional = new Task("Chemistry", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task high = new Task("Chemistry", "do chem hw", 50, PriorityLevel.HIGH);
    Task urgent = new Task("Chemistry", "do chem hw", 90, PriorityLevel.URGENT);
    Task low = new Task("Chemistry", "do chem hw", 90, PriorityLevel.LOW);
    levelQueue.enqueue(medium); // [medium]
    levelQueue.enqueue(optional); // [medium, optional]
    levelQueue.enqueue(high); // [high, optional, medium]
    levelQueue.enqueue(urgent); // [urgent, high, medium, optional]
    levelQueue.enqueue(low); // [urgent, high, medium, optional, low]
    
    // TEST 1: peek at value, make sure data remains unchanged
    Task bestLevel = levelQueue.peekBest();
    
    // make sure we returned the right value (best is B)
    if (!bestLevel.equals(urgent)) return false;
    
    return true;
  }
  
  /**
   * Tests the correctness of a TaskQueue reprioritize() method implementation 
   * including exceptions and edge cases (if applicable).
   * 
   * @return true if all the implementation passes all test cases, false otherwise
   */
  public static boolean testReprioritize() {
    // set up queue
    TaskQueue tq = new TaskQueue(5, CompareCriteria.TIME);
    Task t = new Task("t", "do chem hw", 80, PriorityLevel.OPTIONAL);
    Task t2 = new Task("t2", "do chem hw", 70, PriorityLevel.MEDIUM); // [t, t2]
    Task t3 = new Task("t3", "do chem hw", 50, PriorityLevel.HIGH); // [t, t2, t3]
    Task t4 = new Task("t4", "do chem hw", 90, PriorityLevel.URGENT); // [t4, t, t3, t2]
    tq.enqueue(t);
    tq.enqueue(t2);
    tq.enqueue(t3);
    tq.enqueue(t4);
    
    // TEST 1: Moving from time to level
    // originally prioritized by TIME, but we will switch to LEVEL ordering
    
    tq.reprioritize(CompareCriteria.LEVEL);
    Task[] tqData = tq.getHeapData();
    // check if we have a valid heap
    if (!validHeap(tqData, 4, CompareCriteria.LEVEL)) return false;
    
    // size should be the same
    if (tq.size() != 4) return false;
    
    // TEST 2: Moving from title to level
    TaskQueue titleQueue = new TaskQueue(20, CompareCriteria.TITLE);
    Task a = new Task("a", "do chem hw", 80, PriorityLevel.MEDIUM);
    Task B = new Task("B", "do chem hw", 70, PriorityLevel.OPTIONAL);
    Task c = new Task("c", "do chem hw", 50, PriorityLevel.HIGH);
    Task D = new Task("D", "do chem hw", 90, PriorityLevel.URGENT);
    Task e = new Task("e", "do chem hw", 90, PriorityLevel.LOW);
    titleQueue.enqueue(a); // [a]
    titleQueue.enqueue(B); // [B, a]
    titleQueue.enqueue(c); // [B, a, c]
    titleQueue.enqueue(D); // [B, D, c, a]
    titleQueue.enqueue(e); // [B, D, c, a, e]
    
    titleQueue.reprioritize(CompareCriteria.LEVEL);
    Task[] levelData = titleQueue.getHeapData();
    // check if we have a valid heap
    if (!validHeap(levelData, 5, CompareCriteria.LEVEL)) return false;
    
    
    return true;
  }
  
  // HELPER METHODS
  
  /**
   * Checks if we have a valid max heap (using our compareTo method)
   * 
   * @param data with Tasks to check
   * @param size how many items in data have non-null values
   * @return true if is a valid max heap, false otherwise
   */
  private static boolean validHeap(Task[] data, int size, CompareCriteria criteria) {
    for (int i = 0; i < size; i++) {
      // see if we have a left child to check
      if (leftChild(i) < size) {
        if (data[i].compareTo(data[leftChild(i)], criteria) < 0) { // parent is smaller than child
          return false;
        }
      }
      // see if we have a right child to check
      if (rightChild(i) < size) {
        if (data[i].compareTo(data[rightChild(i)], criteria) < 0) { // parent is smaller than child
          return false;
        }
      }
    }
    return true;
  }
  
  /**
   * finds the left child of a given index value
   * 
   * @param i index of parent
   * @return index of left child
   */
  private static int leftChild(int i) { return 2*i+1; }
  
  /**
   * finds the right child of a given index value
   * 
   * @param i index of parent
   * @return index of right child
   */
  private static int rightChild(int i) { return 2*i+2; }
  
  public static void main(String[] args) {
    System.out.println("testCompareToTime(): " + testCompareToTime());
    System.out.println("testCompareToTitle(): " + testCompareToTitle());
    System.out.println("testCompareToLevel() " + testCompareToLevel());
    System.out.println("testEnqueue(): " + testEnqueue());
    System.out.println("testDequeue(): " + testDequeue());
    System.out.println("testPeek(): " + testPeek());
    System.out.println("testReprioritize(): " + testReprioritize());

  }

}
