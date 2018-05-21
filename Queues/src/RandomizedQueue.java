import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

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
        queue = (Item []) new Object[0];
        start = 0;
        // queue.length
        end = 0;
        size = 0;
    }              // construct an empty randomized queue

    private void resize(int capacity){
        Item[] copy = (Item []) new Object[capacity];
        for(int i=start;i<end;i++){
            copy[i] = queue[i];
        }
        queue = copy;
    }

    public boolean isEmpty(){
        return size==0;
    }                // is the randomized queue empty?

    public int size(){
        return end - start;
    }                 // return the number of items on the randomized queue

    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("can not add null");
        }
        if (queue.length == 0){
            resize(1);
            queue[0] = item;
            size ++;
            end ++;
            return;
        }
        if (queue.length == end){
            resize(end*2);
            queue[end] = item;
            size++;
            end++;
            return;
        }
        queue[end] = item;
        size++;
        end++;
        return;

    }          // add the item

    public Item dequeue(){
        if (size == 0){
            throw new NoSuchElementException("can not remove empty queue");
        }
        int random = StdRandom.uniform(size) + start;
        Item item = queue[random];
        for (int i = random+1;i<end;i++){
            queue[i-1] = queue[i];
        }
        end--;
        size--;

        if (size <= queue.length/4){
            resize(size);
        }
        return item;

    }   // remove and return a random item
    public Item sample(){
        if (size == 0){
            throw new NoSuchElementException("can get sample from empty queue");
        }
        int random = StdRandom.uniform(size);
        return queue[start+random];
    }                 // return a random item (but do not remove it)
    @Override
    public Iterator<Item> iterator(){
        // i

        class Iter implements Iterator<Item>{
            private int current = 0;
            private int[] array = StdRandom.permutation(size,size);
            @Override
            public Item next() {
                int index = array[current];
                Item item = queue[start+index];
                current++;
                return item;
            }

            @Override
            public boolean hasNext() {
                return current < size;
            }

            @Override
            public void remove()  {
                throw new UnsupportedOperationException("no remove method");
            }
        }
        return new Iter();
    }      // return an independent iterator over items in random order

    public static void main(String[] args){
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        System.out.println(rq.isEmpty());
        System.out.println(rq.size());
        rq.enqueue(1);
        System.out.println(rq.isEmpty());
        rq.enqueue(2);
        rq.enqueue(3);

        System.out.println("---");
        for(int i:rq){
            System.out.println(i);
        }
        System.out.println("---");

        rq.dequeue();
        System.out.println("---");
        for(int i:rq){
            System.out.println(i);
        }
        System.out.println("---");

    }  // unit testing (optional)
}
