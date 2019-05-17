import java.util.ArrayList;

public class Customer {
    private String orderTime;
    private String restaurant;
    private String dish;
    private ArrayList<String> orderList = new ArrayList<>();
    private int deliveryTime;
    private int finishedCookingTime;

    public Customer() {
    }

    public Customer(String time, String restaurant, String dish) {
        this.orderTime = time;
        this.restaurant = restaurant;
        this.dish = dish;
        addDish(dish);
    }

    public String getRestaurantName() {
        return this.restaurant;
    }

    public String getDishName() {
        return this.dish;
    }


    public String getOrderTime() {
       // String hour = Main.pad(2, '0', Integer.toString(orderTime[0]) + "");
       // String minute = Main.pad(2, '0', Integer.toString(orderTime[1]) + "");
       // return hour + ":" + minute;
        return this.orderTime;
    }

    public void addDish(String dish) {
        orderList.add(dish);
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public int getFinishedCookingTime() {
        return finishedCookingTime;
    }

    public void setFinishedCookingTime(int finishedCookingTime) {
        this.finishedCookingTime = finishedCookingTime;
    }

    public String toString() {
        return this.getOrderTime() + "\t: " + this.dish + " from " + this.restaurant;
    }
}
