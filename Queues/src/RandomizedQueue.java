import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item [] queue;
    private int start;
    private int end;
    private int size;
    /*
    * 用数组实现
    * @author: Rick
    */
    public RandomizedQueue(){
        queue = (Item []) new Object[1];
        start = 0;
        end = 0;
    }              // construct an empty randomized queue
    public boolean isEmpty(){
        return queue[start] == null;
    }                // is the randomized queue empty?
    public int size(){
        return end - start;
    }                 // return the number of items on the randomized queue
    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("can not add null");
        }

    }          // add the item
    public Item dequeue(){
        if (size == 0){
            throw new NoSuchElementException("can not remove empty queue");
        }
    }                // remove and return a random item
    public Item sample(){

    }                 // return a random item (but do not remove it)
    public Iterator<Item> iterator(){
        // i

    }      // return an independent iterator over items in random order
    public static void main(String[] args){

    }  // unit testing (optional)
}
