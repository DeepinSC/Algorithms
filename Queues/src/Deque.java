import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    // 应该是必须用链表了,双向吧
    private class ListNode<Item> {
        private ListNode<Item> pre=null;
        private ListNode<Item> next=null;
        private Item item;
        private ListNode(Item item){
            this.item = item;
        }
    }


    // private ListNode<Item> queue;
    private ListNode<Item> start;
    private ListNode<Item> end;

    private int size;


    public Deque(){
        // queue = (Item[]) new Object[1];
        start = new ListNode<>(null);
        end = start;
        // current = start;
        size = 0;

    } // construct an empty deque

    public boolean isEmpty(){
        return start.item == null && start == end ;
    }               // is the deque empty?
    public int size(){
        return size;
    }                    // return the number of items on the deque
    public void addFirst(Item item) {
        if (item == null){
            throw new IllegalArgumentException("can not add null");
        }
        if (start.item==null){
            start = new ListNode<>(item);
            end = start;
            size++;
            return;
        }
        ListNode<Item> now = start;
        start = new ListNode<>(item);
        now.pre = start;
        start.next = now;
        size++;
        return;
    }      // add the item to the front
    public void addLast(Item item) {
        if (item == null){
            throw new IllegalArgumentException("can not add null");
        }
        if (end.item==null){
            start = new ListNode<>(item);
            end = start;
            size++;
            return;
        }
        ListNode<Item> now = end;
        end = new ListNode<>(item);
        now.next = end;
        end.pre = now;
        size++;
        return;
    }           // add the item to the end
    public Item removeFirst() {
        if (size == 0){
            throw new NoSuchElementException("can not remove empty queue");
        }
        if (size == 1){
            Item item = start.item;
            start = new ListNode<>(null);
            end = start;
            size--;
            return item;
        }
        Item item = start.item;
        start = start.next;
        start.pre = null;
        size--;
        return item;
    }              // remove and return the item from the front
    public Item removeLast()  {
        if (size == 0){
            throw new NoSuchElementException("can not remove empty queue");
        }
        if (size == 1){
            Item item = start.item;
            start = new ListNode<>(null);
            end = start;
            size--;
            return item;
        }
        Item item = end.item;
        end = end.pre;
        end.next = null;
        size--;
        return item;
    }               // remove and return the item from the end

    @Override
    public Iterator<Item> iterator() {
        class Iter implements Iterator<Item>{
            private ListNode<Item> current = start;
            @Override
            public Item next(){
                /*if (current.next != null){
                    current = current.next;
                    Item item = current.item;
                    return item;
                }
                current = start;
                throw new NoSuchElementException("no next ele");*/
                if (current==null || current.item == null){
                    throw new NoSuchElementException("no next ele");
                }
                Item item = current.item;
                current = current.next;
                return item;
            }
            @Override
            public boolean hasNext(){
                return current != null && current.item != null;
            }
            @Override
            public void remove() {
                throw new UnsupportedOperationException("no remove method");
            }
        }
        return new Iter();
    }       // return an iterator over items in order from front to end
    public static void main(String[] args) {
        Deque<Integer> deque = new Deque<>();
        System.out.println(deque.isEmpty());
        deque.addLast(1);
        System.out.println(deque.isEmpty());
        deque.removeFirst();
        // Iterator<Integer> iter = deque.iterator();
        // iter.next();
        System.out.println(deque.size());
        System.out.println("---");
        for(int i : deque){
            System.out.println(i);
        }
        System.out.println("---");
        //System.out.println(deque.removeLast());
        //System.out.println(deque.removeFirst());
        System.out.println(deque.isEmpty());
    }  // unit testing (optional)
}
