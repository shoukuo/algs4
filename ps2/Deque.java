import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    private int N;
    private Node<Item> first;
    private Node<Item> last;
    
    // helper link Node class
    private static class Node<Item>
    {
        private Item item;
        private Node<Item> next;
        private Node<Item> prev;
    }
    
    public Deque()
    {
        N = 0;
        first = null;
        last = null;
    }
    
    public boolean isEmpty()
    {
        return N == 0;
    }
    
    public int size()
    {
        return N;
    }
    
    public void addFirst(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException();
        
        Node<Item> oldfirst = first;
        first = new Node<Item>();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty())    last = first;
        else              oldfirst.prev = first;
        N += 1;
    }
    
    public void addLast(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException(); 
        
        Node<Item> oldlast = last;
        last = new Node<Item>();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty())  first = last;
        else            oldlast.next = last;
        N += 1;
    }
    
    public Item removeFirst()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        Node<Item> oldfirst = first;
        first = first.next;
        N -= 1;
        if (isEmpty())  last = null;
        else            first.prev = null;
        return oldfirst.item;
    }
    
    public Item removeLast()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        Node<Item> oldlast = last;
        last = last.prev;
        N -= 1;
        if (isEmpty())  first = null;
        else            last.next = null;
        return oldlast.item;
    }
    
    public Iterator<Item> iterator()
    {
        return new DequeIterator<Item>(first);
    }
    
    private class DequeIterator<Item> implements Iterator<Item> {
        private Node<Item> current;
        public DequeIterator(Node<Item> first)
        {
            current = first;
        }
        public boolean hasNext() { return current != null; }
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        public Item next() 
        {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next;
            return item;
        }
        
    }
    
    public static void main(String[] args)
    {
        
    }
}
