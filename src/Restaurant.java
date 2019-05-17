import java.util.ArrayList;

public class Restaurant {
    ArrayList<Dish> dishList = new ArrayList<>();
    private ArrayList<Branch> branchList = new ArrayList<>();
    private String name;
    private boolean hasSortedBranch = false;

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public void addBranch(int x, int y) {
        branchList.add(new Branch(x, y));
    }

    public void addDish(Dish dish) {
        dishList.add(dish);
    }


    public String branchCoord() {
        String str = "{";
        for (int i = 0; i < this.branchList.size() - 1; i++) {
            str += this.branchList.get(i).toString() + ", ";
        }
        str += this.branchList.get(branchList.size() - 1).toString();
        return str + "}";

    }

    public String resDish() {
        String str = "{";
        for (int i = 0; i < dishList.size() - 1; i++) {
            str += this.dishList.get(i).toString() + ", ";
        }
        str += this.dishList.get(this.dishList.size() - 1).toString();
        return str + "}";
    }

    public String getName() {
        return this.name;
    }


    public ArrayList<Branch> getBranchList() {

        if (!hasSortedBranch) {
            //  System.out.println(branchList);
            long startTime = System.currentTimeMillis();
            /*~~~~~~~~~sorting starts here~~~~~~~~~~*/
            Quicksort sorter = new Quicksort();
            sorter.sort(branchList);
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            //  System.out.println("Sorting takes " + elapsedTime);
            hasSortedBranch = true;
        }
        return branchList;
    }

}
