public class Branch {
    CustomerQueue CustomerQueue = new CustomerQueue();
    private int x;
    private int y;
    private boolean preparing = false;

    public Branch() {
    }

    public Branch(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }


    public boolean isPreparing() {
        return preparing;
    }

    public void prepareOrder() {
        this.preparing = true;
    }

    public int getDistance() {
        return this.x + this.y;
    }

    public void addCustomer(Customer c) {
        CustomerQueue.enqueue(c);
        preparing = true;
    }

    public void dequeueCustomer() {
        CustomerQueue.dequeue();
        preparing = false;
    }

    public boolean hasCustomer() {
        return CustomerQueue.containsCustomer();
    }

    //returns the number of customer currently in the branch
    public int CustomerNumber() {
        return CustomerQueue.getSize();
    }


    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}
