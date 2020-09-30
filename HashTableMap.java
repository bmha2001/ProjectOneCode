import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashTableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

  private LinkedList<Node<KeyType, ValueType>>[] values;

  private int size = 0;
  private int capacity;

  @SuppressWarnings("unchecked")
  public HashTableMap(int capacity) {
    values = new LinkedList[capacity];
    this.capacity = capacity;
  }

  @SuppressWarnings("unchecked")
  public HashTableMap() {
    values = new LinkedList[10];
    this.capacity = 10;
  }

  /**
   * Returns true if passed key-value pair was successfully inserted into the hash table, returns
   * false if key already exists
   * 
   * @param key   the key associated with the value
   * @param value the value associated with the key
   * 
   * @return true if key-value pair was successfully placed into the table, false if key already
   *         existed
   */
  @Override
  public boolean put(KeyType key, ValueType value) {

    int index = Math.abs(key.hashCode()) % capacity;
    Node<KeyType, ValueType> addMe = new Node<KeyType, ValueType>(key, value);
    
    if(values[index] != null ) {
      Node<KeyType, ValueType> temp = values[index].get(0);
      while (temp != null) {
        if (temp.getKey().equals(key))
          return false;
        temp = temp.getNext();
      }
    }


    if (values[index] == null) {
      LinkedList<Node<KeyType, ValueType>> add = new LinkedList<>();
      values[index] = add;
      add.add(addMe);
    } else {

      values[index].add(addMe);
      values[index].get(values[index].size() - 2).setNext(addMe);
    }

    size++;

    double loadFactor = (double) size / capacity;
    if (loadFactor * 100 >= 80) {
      rehash(); // private helper method
    }

    return true;
  }

  /**
   * Helper method to rehash hash table when load factor >= 80% Called in the put(Key) method.
   */
  @SuppressWarnings("unchecked")
  private void rehash() {
    int newCapacity = 2 * capacity;
    LinkedList<Node<KeyType, ValueType>>[] temp = new LinkedList[newCapacity];
    size = 0;

    // traverses hash table
    for (int i = 0; i < capacity; i++) {
      // if linked list is found, must iterate through linked list
      if (values[i] != null) {
        Node<KeyType, ValueType> head = values[i].getFirst();
        // linked list traversal
        while (head != null) {
          int index = Math.abs(head.getKey().hashCode()) % newCapacity;
          Node<KeyType, ValueType> reset;
          if (temp[index] == null) {
            LinkedList<Node<KeyType, ValueType>> add = new LinkedList<>();
            temp[index] = add;
            add.add(head);
            reset = head;
            head = head.getNext();
            reset.setNext(null);
          } else {
            temp[index].add(head);
            temp[index].get(temp[index].size() - 2).setNext(head);
            head = head.getNext();
          }
          size++;
         

        }
      }

    }
    capacity = newCapacity;
    values = temp;

  }

  /**
   * Returns the value associated with a passed key
   * 
   * @param key the key to the value that is to be returned
   * @throws NoSuchElementException if the key-value pair does not exist
   * @return the value associated with the key
   */
  @Override
  public ValueType get(KeyType key) throws NoSuchElementException {
    if (!containsKey(key)) {
      throw new NoSuchElementException("Error");
    }
    ValueType value = null;

    for (int i = 0; i < capacity; i++) {
      if (values[i] != null) {
        Node<KeyType, ValueType> head = values[i].getFirst();
        while (head != null) {
          if (head.getKey().equals(key)) {
            value = head.getValue();
            break;
          }
          head = head.getNext();
        }
      }
    }

    return value;
  }

  /**
   * Returns the number of elements in the hash table
   * 
   * @return size of hash table
   */
  @Override
  public int size() {
    return size;
  }

  /**
   * Determines if passed key is in the current hash table
   * 
   * @param key the key to be checked if it already exists in the table
   * @return true if the table contains the key, false otherwise
   */
  @Override
  public boolean containsKey(KeyType key) {
    for (int i = 0; i < capacity; i++) {
      if (values[i] != null) {
        Node<KeyType, ValueType> temp = values[i].getFirst();
        while (temp != null) {
          if (temp.getKey().equals(key)) {
            return true;
          }
          temp = temp.getNext();
        }
      }
    }

    return false;
  }

  /**
   * Removes key-value pair found at specified key from the hash table
   *
   * @param key the key that is the key-value pair to be removed
   * @return null if the key does not exists, otherwise returns the value associated with the key
   *         that is being removed
   */
  @Override
  public ValueType remove(KeyType key) {
    if (!containsKey(key)) {
      return null;
    }

    ValueType value = null;
    for (int i = 0; i < capacity; i++) {
      if (values[i] != null) {
        Node<KeyType, ValueType> head = values[i].getFirst();
        Node<KeyType, ValueType> temp = null;
        while (head != null) {
          if (head.getKey().equals(key)) {
            value = head.getValue();
            if (temp != null) {
              temp.setNext(head.getNext());
            } else {
              values[i].set(0, head.getNext());
            }
            values[i].remove(head);
            break;
          }
          temp = head;
          head = head.getNext();
        }
      }
    }

    size--;
    return value;
  }

  /**
   * Clears Hash Table of all key-value pairs
   */
  @Override
  public void clear() {
    for (int i = 0; i < capacity; i++) {
      if (values[i] != null)
        values[i] = null;
    }

    size = 0;
  }

  /*
   * Method to aid testing the rehashing ability
   * 
   * @return the capacity of table
   */
  public int getCapacity() {
    return capacity;
  }
  


}


