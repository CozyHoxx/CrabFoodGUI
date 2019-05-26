import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Restaurant {
    static HashMap<String, Integer> dishOrderList = new HashMap<String, Integer>(); // A hashmap of orders and their amount for piechart
    ArrayList<Dish> dishList = new ArrayList<>();   // To store dishes the restaurant offer
    private ArrayList<Branch> branchList = new ArrayList<>();   // To store the branches of the restaurant
    private String name;    // Name of restaurant
    private boolean hasSortedBranch = false;    // Boolean for getBranchList() method

    public Restaurant() {
    }

    public Restaurant(String name) {
        this.name = name;
    }

    public void addBranch(int x, int y) {
        branchList.add(new Branch(x, y));   // Add to the branchList ArrayList
    }

    public void addDish(Dish dish) {
        dishList.add(dish);     // Add to list of dishes
        dishOrderList.put(dish.getDishName(), 0);    // Set dishes in the hashmap and give the value of 0
    }

    // Return the list coordinates of the branch in string
    // For checking purposes
    public String branchCoord() {
        String str = "{";
        for (int i = 0; i < this.branchList.size() - 1; i++) {
            str += this.branchList.get(i).toString() + ", ";
        }
        str += this.branchList.get(branchList.size() - 1).toString();
        return str + "}";

    }

    // Return the list of dishes of the restaurant
    // For checking purposes
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

    // Return branchlist in a sorted manner according to the distance to (0,0)
    public ArrayList<Branch> getBranchList() {
        if (!hasSortedBranch) {
            //  System.out.println(branchList);
            long startTime = System.currentTimeMillis();    // To check sorting time
            /*~~~~~~~~~sorting starts here~~~~~~~~~~*/
            Quicksort sorter = new Quicksort();
            sorter.sort(branchList);    // A sorter to sort the arraylist
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
            //  System.out.println("Sorting takes " + elapsedTime);
            hasSortedBranch = true;
        }
        return branchList;
    }

    // Return the dishlist in set for the piechart
    public Set getDishSet() {
        Set set = new HashSet();
        for (Dish dish : dishList)
            set.add(dish.getDishName());
        return set;
    }
}
