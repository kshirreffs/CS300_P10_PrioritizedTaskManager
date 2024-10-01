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
// Online Sources:  NONE
//
///////////////////////////////////////////////////////////////////////////////

/**
 * Instantiable class for Task objects. These Tasks can be given different priority levels
 * and compared based on different criteria.
 *
 * @author Michelle & Katelyn Shirreffs
 */
public class Task {

  /** the title of this Task*/
  private String title;
  
  /** the description of this Task*/
  private String description;
  
  /** the priority of this Task*/
  private PriorityLevel priorityLevel;
  
  /** the amount of estimated time to complete this Task*/
  private int time;
  
  /** denotes whether or not this task has been completed*/
  private boolean isCompleted;

  /**
   * Creates a new Task object using the given information. By default, newly
   * created Tasks are NOT completed.
   * @param title the title of the Task
   * @param description the description of the Task
   * @param time the estimated time to complete the Task
   * @param priorityLevel the priority level of the Task
   */
  public Task(String title, String description, int time, PriorityLevel priorityLevel) {
    this.title = title;
    this.description = description;
    this.time = time;
    this.priorityLevel = priorityLevel;
  }
  
  /**
   * Gets a Task's title.
   * @return this Task's title
   */
  public String getTitle() {return this.title;}
  
  /**
   * Gets a Task's description.
   * @return this Task's description
   */
  public String getDescription() {return this.description;}
  
  /**
   * Gets a Task's estimated completion time.
   * @return this Task's estimated completion time
   */
  public int getTime() {return this.time;}
  
  /**
   * Reports if a Task has been completed.
   * @return true if this Task is completed, false otherwise 
   */
  public boolean isCompleted() {return this.isCompleted;}
  
  /**
   * Marks this Task as completed.
   */
  public void markCompleted() {this.isCompleted = true;}
  
  
  /**
   * Returns a String representation of a Task.
   * @return this Task as a String
   */
  @Override
  public String toString() {
    return title + ": " + description + "(" + this.priorityLevel + "), ETA " 
        + this.time + " minutes";
  }
 
  /**
   * Determines if another object is equal to a Task.
   * @param other the object to check is equal to this Task
   * @return true if other is a Task and has the same title, time, description and priority level,
   * false otherwise
   */
  @Override
  public boolean equals(Object other) {
    if(other instanceof Task) {
      Task t = (Task) other;
      return this.title.equals(t.title) && this.description.equals(t.description)
          && this.priorityLevel == t.priorityLevel && this.time == t.time;
    }
    
    return false;
  }
  
  /**
   * Compares the other Task to this Task based on the given PrioritizationMode. 
   * @param other the Task to compare to this
   * @param criteria the criteria use to determine how to compare them
   * @return &lt; 0 if this is smaller than other, &gt; 0 of this is larger than other, and 0 if
   * they are equal
   *
   * @author Katelyn Shirreffs
   */
  public int compareTo(Task other, CompareCriteria criteria) {
    
    // TITLE criteria
    if (criteria == CompareCriteria.TITLE) {
      return -1 * this.title.compareTo(other.getTitle()); 
      // multiplying by -1 will return REVERSE lexicographical order
      // zero value remains unchanged
    } 
    
    // LEVEL criteria
    else if (criteria == CompareCriteria.LEVEL) {
      return this.priorityLevel.compareTo(other.priorityLevel);
      // uses enum's automatic compareTo() method (based on ordinal values)
    } 
    
    // TIME criteria
    else {
      if (this.time > other.time) return 1;
      else if (this.time < other.time) return -1;
      else return 0; // they must be equal
    }
  }
}
