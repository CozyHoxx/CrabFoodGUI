/*
 * The node Class for SomeList class
 * This node will store time and the string for the processes
 *
 * */
public class SomeNode<K, V> {
    K time;
    V string;
    SomeNode<K, V> next;

    // Constructor with empty parameters
    public SomeNode() {
        this.time = null;
        this.string = null;
        this.next = null;
    }

    // Constructor with parameters
    public SomeNode(K time, V string) {
        this.time = time;
        this.string = string;
    }

}
