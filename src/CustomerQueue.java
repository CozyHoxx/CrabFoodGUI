public class CustomerQueue<E> {
    private java.util.LinkedList<E> Qlist = new java.util.LinkedList<>();

    public void enqueue(E c) {
        Qlist.add(c);
    }

    public E dequeue() {
        return Qlist.removeFirst();
    }

    public int getSize() {
        return Qlist.size();
    }

    public boolean containsCustomer() {
        return Qlist.size() == 0;
    }


    public String toString() {
        return "Customer Q: " + Qlist.toString();
    }

}