public class SomeNode<K, V> {
    K time;
    V string;
    SomeNode<K, V> next;
    SomeNode<K, V> prev;

    public SomeNode() {
        this.time = null;
        this.string = null;
        this.next = null;
        this.prev = null;
    }

    public SomeNode(K time, V string) {
        this.time = time;
        this.string = string;
    }

}
