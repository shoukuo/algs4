import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] a;
    private int N;
    
    public RandomizedQueue()
    {
        a = (Item[]) new Object[1];
        N = 0;
    }
    
    public boolean isEmpty()
    {
        return N == 0;
    }
    
    public int size()
    {
        return N;
    }
    
    private void resize(int max)
    {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }
    public void enqueue(Item item)
    {
        if (item == null) throw new java.lang.NullPointerException();
        
        if (N == a.length) resize(2*a.length);
        a[N++] = item;
    }
    
    public Item dequeue()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        int pos = StdRandom.uniform(0, N);
        Item tmp = a[pos];
        a[pos] = a[N-1];
        a[N-1] = null;
        N -= 1;
        if (N > 0 && N == a.length/4) resize(a.length/4);
        return tmp;
    }
    
    public Item sample()
    {
        if (isEmpty()) throw new java.util.NoSuchElementException();
        
        int pos = StdRandom.uniform(0, N);
        return a[pos];
    }
    
    public Iterator<Item> iterator()
    {
        return new RdqueIterator();
    }
    
    private class RdqueIterator implements Iterator<Item> {
        private int[] shuffle;
        private int current;
        
        public RdqueIterator()
        {
            shuffle = new int[N];
            
            for (int i = 0; i < N; i++)
                shuffle[i] = i;
            
            for (int i = 1; i < N; i++)
            {
                int p = StdRandom.uniform(0, i+1);
                exch(shuffle, p, i);
            }
            current = 0;
        }
        private void exch(int[] src, int i, int j)
        {
            int tmp = src[i];
            src[i] = src[j];
            src[j] = tmp;
        }
        public boolean hasNext()
        {
            return  current < N;
        }
        
        public void remove() { throw new java.lang.UnsupportedOperationException(); }
        
        public Item next()
        {
            if (!hasNext()) throw new java.util.NoSuchElementException();
            Item tmp = a[shuffle[current]];
            current += 1;
            return tmp;
            
        }
    }
    
    public static void main(String[] args)
    {
        
    }
}
