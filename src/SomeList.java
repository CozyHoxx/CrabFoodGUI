public class SomeList<K extends Comparable<K>, V> {
    SomeNode<K, V> head;
    SomeNode<K, V> tail;
    int size = 0;

    public SomeList() {
        this.head = null;
        this.tail = null;
    }

    public void add(K time, V string) {
        if (size == 0) {
            SomeNode<K, V> newNode = new SomeNode<>(time, string);
            this.head = newNode;
            if (tail == null) {
                tail = head;
            }
            size++;
        } else {
            SomeNode current = head;
            for (int i = 0; i < size; i++) {
                if (current.next != null) {
                    if ((time.compareTo((K) current.next.time) > 0) || (time.compareTo((K) current.next.time) == 0)) {
                        current = current.next;
                    }
                }
            }
            SomeNode<K, V> temp = current.next;
            current.next = new SomeNode<K, V>(time, string);
            current.next.next = temp;
            size++;
        }

    }

    public void add(int index, K time, V string) {
        if (index == size)
            addLast(time, string);
        else if (index >= size) {
            addLast(time, string);
        } else {
            SomeNode temp = head;
            for (int i = 0; i < index; i++) {
                temp = temp.next;
            }
            SomeNode<K, V> insert = temp.next;
            temp.next = new SomeNode<K, V>(time, string);
            temp.next.next = insert;
            size++;
        }
    }

    public void addLast(K time, V string) {

        if (tail == null) {
            head = tail = new SomeNode<K, V>(time, string);
        } else {
            tail.next = new SomeNode<K, V>(time, string);
            tail = tail.next;
        }
        size++;
    }

    public K removeFirst() {
        if (size == 0) return null;
        else {
            SomeNode<K, V> temp = head;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return temp.time;
        }
    }

    public String removeFirst_Str() {
        if (size == 0) return null;
        else {
            SomeNode<K, V> temp = head;
            head = head.next;
            size--;
            if (head == null) {
                tail = null;
            }
            return getTimeInStr(temp);
        }
    }

    public String removeFirstNode() {
        if (size == 0) return null;
        else {
            SomeNode<K, V> temp = head;
            V tempString = getString(0);
            head = head.next;
            size--;
            return getTimeInStr(temp)+ ""+ tempString;
        }
    }




    public K removeLast() {
        if (size == 0) return null;
        else if (size == 1) {
            SomeNode<K, V> temp = head;
            head = tail = null;
            size = 0;
            return temp.time;
        } else {
            SomeNode<K, V> current = head;
            for (int i = 1; i < size - 1; i++) {
                current = current.next;
            }
            SomeNode<K, V> temp = tail;
            tail = current;
            tail.next = null;
            size--;
            return temp.time;
        }
    }

    public K remove(int index) {
        if (index < 0 || index >= size) return null;
        else if (index == 0) return removeFirst();
        else if (index == (size - 1)) return removeLast();
        else {
            SomeNode<K, V> previous = head;
            for (int i = 1; i < index; i++) {
                previous = previous.next;
            }
            SomeNode<K, V> current = previous.next;
            previous.next = current.next;
            size--;
            return current.time;
        }
    }

    public int indexOf(K time) {
        int index = 0;
        for (SomeNode temp = head; temp != null; temp = temp.next) {
            if (time == (temp.time)) {
                return index;
            }
            index++;

        }
        return -1;
    }

    public K getTime(int index) {
        if (index < 0 || index >= size) return null;
        else if (index == 0) return head.time;
        else {
            SomeNode<K, V> temp = head;
            for (int i = 1; i <= index; i++) {
                temp = temp.next;
            }
            return temp.time;
        }
    }

    public String getTime_Str(int index) {
        String str;
        String minuteStr;
        String hourStr;

        if (index < 0 || index >= size) return null;
        else if (index == 0) {
            return getTimeInStr(head);
        } else if (index == size - 1) {
            return getTimeInStr(tail);
        } else {
            SomeNode<K, V> temp = head;
            for (int i = 1; i <= index; i++) {
                temp = temp.next;
            }
            return getTimeInStr(temp);
        }


    }

    public  V getString(int index) {
        if (index < 0 || index >= size) return null;
        else if (index == 0){
            return head.string;

        }
        else {
            SomeNode<K, V> temp = head;
            for (int i = 1; i <= index; i++) {
                temp = temp.next;
            }
            return temp.string;
        }
    }

    public K set(int index, K time, V string) {
        K temp = remove(index);
        add(index, time, string);
        return temp;
    }

    public void printList() {
        //System.out.println("The list contains ");
        SomeNode<K, V> current = head;
        for (int i = 0; i < size; i++) {
            int minuteInt = Integer.parseInt(current.time.toString()) % 60;
            int hourInt = Integer.parseInt(current.time.toString()) / 60;

            String minuteStr = Main.pad(2, '0', Integer.toString(minuteInt));
            String hourStr = Main.pad(2, '0', Integer.toString(hourInt));

            System.out.print(hourStr + ":" + minuteStr);

            System.out.println(current.string);
            current = current.next;
        }
    }

    public int getSize() {
        return this.size;
    }

    //RETURN HH:MM
    public String getTimeInStr(SomeNode<K, V> node) {
        String str="";
        String minuteStr;
        String hourStr;
        int minuteInt = Integer.parseInt(node.time.toString()) % 60;
        int hourInt = Integer.parseInt(node.time.toString()) / 60;
        minuteStr = Main.pad(2, '0', Integer.toString(minuteInt));
        hourStr = Main.pad(2, '0', Integer.toString(hourInt));
        return str += hourStr + ":" + minuteStr;

    }







    public void copy(SomeList<K,V> list){
        for (int i = 0 ; i < list.size ;i++){
            this.add(list.getTime(i),list.getString(i));
            //CHECKING PURPOPSES
            //System.out.println(list.getTime(i));
            //System.out.println(list.getString(i));
        }
    }


}
