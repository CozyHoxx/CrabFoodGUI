import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class CrabFoodOperator {

    static ArrayList<Restaurant> listOfRestaurant = new ArrayList<Restaurant>();
    static SomeList<Integer, String> listOfProcesses = new SomeList<>();
    static SomeList<Integer, String> copy_listOfProcesses = new SomeList<>();
    private static StringProperty process;
    private static ArrayList<Customer> listOfCustomer = new ArrayList<>();
    private static Logger logger = new Logger();

    public CrabFoodOperator() {

        logger.startLog();
        readRestaurant();
        readCustomer();
        Map map = new Map(Map.getMapSize());
        map.generateMap();
        System.out.println();
        startDelivery();
        listOfProcesses.printList();
        copy_listOfProcesses.copy(listOfProcesses);
        //System.out.println();
        //copy_listOfProcesses.printList();
        //checkRestaurant();
        //checkCustomer();

        process = new SimpleStringProperty("");
    }

    public static void startDelivery() {

        int customerNumber = 1;
        for (int i = 0; i < listOfCustomer.size(); i++) {
            Customer tempCustomer = listOfCustomer.get(i);
            int orderTime = tempCustomer.getOrderTimeInt();
            String dish = tempCustomer.getDishName();
            String restaurant = tempCustomer.getRestaurantName();

            String tempString = " Customer " + customerNumber +
                    " wants to order " + dish +
                    " from " + restaurant;
            // System.out.println(orderTime + tempString);
            listOfProcesses.add(orderTime, tempString);


            int[] coord = branchIdentifier(restaurant, tempCustomer);
            String branch = "(" + coord[0] + "," + coord[1] + ")";

            String tempString2 = " Branch of " + restaurant +
                    " at " + branch + " take the order";
            //  System.out.println(orderTime + tempString2);
            listOfProcesses.add(orderTime, tempString2);


            int dishPrepTime = getDishPrepTime(restaurant, dish);
            int completedTime = orderTime + dishPrepTime;

            String tempString3 = " " + restaurant +
                    " at branch " + branch +
                    " has finished the order and delivers the order to customer " + customerNumber;
            //  System.out.println(completedTime + tempString3);
            listOfProcesses.add(completedTime, tempString3);
            tempCustomer.setFinishedCookingTime(completedTime);

            int deliveredTime = completedTime + coord[2];
            String tempString4 = " The food has been delivered to customer " + customerNumber;
            //  System.out.println(deliveredTime + tempString4);
            listOfProcesses.add(deliveredTime, tempString4);
            tempCustomer.setDeliveryTime(coord[2]);

            // ADD THIS LATER
            // this will log to textfile
            // logger.log(tempCustomer, customerNumber);

            logger.log(tempCustomer,customerNumber);

            customerNumber++;

        }
    }

    public static int getDishPrepTime(String restaurantName, String dishName) {
        Restaurant tempRes = new Restaurant();
        int prepareTime = 0;
        for (int i = 0; i < listOfRestaurant.size(); i++) {
            if (listOfRestaurant.get(i).getName().equalsIgnoreCase(restaurantName)) {
                tempRes = listOfRestaurant.get(i);
            }
        }
        for (int j = 0; j < tempRes.dishList.size(); j++) {
            if (tempRes.dishList.get(j).getDishName().equalsIgnoreCase(dishName)) {
                prepareTime = tempRes.dishList.get(j).prepareTime();
            }

        }
        return prepareTime;

    }

    public static int[] branchIdentifier(String s, Customer c) {
        int[] coord = new int[3];
        Restaurant r = new Restaurant();
        for (int i = 0; i < listOfRestaurant.size(); i++) {
            if (listOfRestaurant.get(i).getName().equalsIgnoreCase(s)) {
                r = listOfRestaurant.get(i);
                break;
            }
        }

        Branch b = new Branch();
        int minCustomer = 0;
        for (int i = 0; i < r.getBranchList().size(); i++) {
            b = r.getBranchList().get(i);
            if (b.CustomerNumber() <= minCustomer) {
                b = r.getBranchList().get(i);
                b.addCustomer(c);
                break;
            } else {
                minCustomer++;
                i = 0;
            }
        }

        coord[0] = b.getX();
        coord[1] = b.getY();
        coord[2] = coord[0] + coord[1];


        return coord;

    }

    public static void appendToProcess(String stringToAppend) {
        process.set(process.concat("\n" + stringToAppend).get());
    }

    public static StringProperty getProcess() {

        return process;
    }

    public static boolean isNum(String str) {
        try {
            int temp = Integer.parseInt(str);
            return true;

        } catch (Exception e) {
            //  e.printStackTrace();
            return false;
        }
    }

    public static void readCustomer() {
        String str;
        boolean newCustomer = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Customer.txt")); //restaurant list file
            while ((str = br.readLine()) != null) {
                if (str.equals("")) {
                    newCustomer = true;
                } else if (newCustomer) {


                    listOfCustomer.add(new Customer(str, br.readLine(), br.readLine()));
                    newCustomer = false;
                    //newCustomer = true;
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public static void checkCustomer() {
        System.out.println("Checking customers...");
        for (int i = 0; i < listOfCustomer.size(); i++) {
            Customer tempCus = listOfCustomer.get(i);
            System.out.println(tempCus.getOrderTimeStr());
            System.out.println(tempCus.getRestaurantName());
            System.out.println(tempCus.getDishName());
        }
        System.out.println("Customer check completed!\n");

    }

    public static int toInt(String str) {
        return Integer.parseInt(str);

    }

    public void readRestaurant() {
        try {
            boolean readingRes = true;
            String delimiters = "\\s+|,\\s*|\\.\\s*";
            String[] temp;
            String str;
            int restaurantIndex = 0;

            try {
                BufferedReader br = new BufferedReader(new FileReader("Input.txt")); //restaurant list file
                while ((str = br.readLine()) != null) {
                    //  System.out.println(str);
                    if (readingRes) {
                        listOfRestaurant.add(new Restaurant(str));
                        readingRes = false;
                    } else if (str.equals("")) {
                        //reset to reading a new restaurant
                        restaurantIndex++;
                        readingRes = true;
                    } else {
                        if (!isNum(String.valueOf(str.charAt(0))) && !readingRes) {
                            listOfRestaurant.get(restaurantIndex).addDish(new Dish(str, toInt(br.readLine())));
                        } else {
                            temp = str.split(delimiters);
                            listOfRestaurant.get(restaurantIndex).addBranch(toInt(temp[0]), toInt(temp[1]));
                        }
                    }
                }
            } catch (Exception e) {
                //e.printStackTrace();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkRestaurant() {
        for (int i = 0; i < listOfRestaurant.size(); i++) {
            Restaurant tempRes = listOfRestaurant.get(i);
            System.out.println(tempRes.getName());
            System.out.println(tempRes.getBranchList());
        }
    }


}
