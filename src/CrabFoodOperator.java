import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class CrabFoodOperator {
    private static StringProperty process;
    static ArrayList<Restaurant> listOfRestaurant = new ArrayList<Restaurant>();
    private static ArrayList<Customer> listOfCustomer = new ArrayList<>();


    public CrabFoodOperator() {

        readRestaurant();
        readCustomer();
        checkCustomer();

        process = new SimpleStringProperty("");
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

    public static void readCustomer() {
        String str;
        boolean newCustomer = true;
        try {
            BufferedReader br = new BufferedReader(new FileReader("Customer.txt")); //restaurant list file
            while ((str = br.readLine()) != null) {
                if (str.equals("")) {
                    newCustomer = true;
                } else if (newCustomer) {
                    String[] tempArr;
                    tempArr = str.split(":");
                    int[] orderTime = new int[2];
                    orderTime[0] = toInt(tempArr[0]);
                    orderTime[1] = toInt(tempArr[1]);

                    listOfCustomer.add(new Customer(str, br.readLine(), br.readLine()));
                    newCustomer = false;
                    //newCustomer = true;
                }
            }

        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
    public static void checkCustomer(){
        System.out.println("Checking customers...");
        for(int i = 0 ;i< listOfCustomer.size();i++){
            Customer tempCus = listOfCustomer.get(i);
            System.out.println(tempCus.getOrderTime());
            System.out.println(tempCus.getRestaurantName());
            System.out.println(tempCus.getDishName());
        }
        System.out.println("Customer check completed!\n");

    }


    public static int toInt(String str) {
        return Integer.parseInt(str);

    }


}
