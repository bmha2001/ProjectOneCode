public class Node<KeyType, ValueType> {
  private KeyType key;
  private ValueType value;
  private Node<KeyType, ValueType> next;

  public Node(KeyType key, ValueType value) {
    this.key = key;
    this.value = value;
    next = null;
  }

  public KeyType getKey() {
    return key;
  }

  public ValueType getValue() {
    return value;
  }

  public void setNext(Node<KeyType, ValueType> next) {
    this.next = next;
  }

  public Node<KeyType, ValueType> getNext() {
    return next;
  }
}
