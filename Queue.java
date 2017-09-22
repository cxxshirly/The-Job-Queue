
public class Queue implements QueueInterface {
  private Node lastNode;
  
  public Queue() {
    lastNode = null;   
  }  // end default constructor
  
  // queue operations:
  public boolean isEmpty() {
	return (lastNode == null); 
  }  // end isEmpty

  //remove all the elements in the linked list
  public void dequeueAll() {
	lastNode = null;
  }  // end dequeueAll

  //insert a newItem to the lastnode of the linked list
  public void enqueue(Object newItem) {
    // insert the new node
    if (isEmpty()) {
      // insertion into empty queue
        Node newNode = new Node(newItem);
	lastNode = newNode;
	lastNode.setNext(newNode);
    }
    else {
      // insertion into nonempty queue
      Node newNode = new Node(newItem,lastNode.getNext()); 
      lastNode.setNext(newNode);
      lastNode = newNode;
    }  // end if
  }  // end enqueue

  public Object dequeue() throws QueueException {
    if (!isEmpty()) {
      // queue is not empty; remove front
      Object temp = lastNode.getNext().getItem();
      lastNode.getNext().setItem(null);
      if(lastNode.getNext() == lastNode ){
	 dequeueAll();
      }
      else{
      	lastNode.setNext(lastNode.getNext().getNext());   
      }
      return temp;
    }  //end if
    else {
      throw new QueueException("QueueException on dequeue:"
                             + "queue empty");
    }  // end if
  }  // end dequeue

  public Object front() throws QueueException {
    if (!isEmpty()) {
      Node firstNode = lastNode.getNext();
       return firstNode.getItem();
    }
    else {
      throw new QueueException("QueueException on front:"
                             + "queue empty");
    }
  }

  public Object clone() throws CloneNotSupportedException
  {
	boolean copied = false;
        Queue copy = new Queue();
        Node curr = lastNode, prev = null;
        while ( (! copied) && (lastNode != null) )
        {
                Node temp = new Node(curr.getItem());
                if (prev == null)
                        copy.lastNode = temp;
                else
                        prev.setNext(temp);
                prev = temp;
                curr = curr.getNext();
		copied = (lastNode == curr);
        }
	prev.setNext(copy.lastNode);
        return copy;
  }
} // end Queue
