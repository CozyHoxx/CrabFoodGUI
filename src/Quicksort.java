import java.util.ArrayList;

public class Quicksort {
    private ArrayList<Branch> branch;
    private int size;

    public void sort(ArrayList<Branch> values) {
        // check for empty or null array
        if (values == null || values.size() == 0) {
            return;
        }
        this.branch = values;
        size = values.size();
        quicksort(0, size - 1);
    }

    private void quicksort(int low, int high) {
        int i = low, j = high;
        // Get the pivot element from the middle of the list
        Branch pivot = branch.get(low + (high - low) / 2);

        // Divide into two lists
        while (i <= j) {
            // If the current value from the left list is smaller than the pivot
            // element then get the next element from the left list
            while (branch.get(i).getDistance() < pivot.getDistance()) {
                i++;
            }
            // If the current value from the right list is larger than the pivot
            // element then get the next element from the right list
            while (branch.get(j).getDistance() > pivot.getDistance()) {
                j--;
            }

            // If we have found a value in the left list which is larger than
            // the pivot element and if we have found a value in the right list
            // which is smaller than the pivot element then we exchange the
            // values.
            // As we are done we can increase i and j
            if (i <= j) {
                exchange(i, j);
                i++;
                j--;
            }
        }
        // Recursion
        if (low < j)
            quicksort(low, j);
        if (i < high)
            quicksort(i, high);
    }

    private void exchange(int i, int j) {
        Branch temp = branch.get(i);
        branch.set(i, branch.get(j));
        branch.set(j, temp);
    }
}