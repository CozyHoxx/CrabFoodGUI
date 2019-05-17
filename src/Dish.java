public class Dish {
    private String dishName;
    private int dishPrepTime;

    public Dish() {
    }

    public Dish(String name, int dishPrepTime) {
        this.dishName = name;
        this.dishPrepTime = dishPrepTime;
    }


    public int prepareTime() {
        return this.dishPrepTime;
    }

    public String getDishName() {
        return this.dishName;
    }

    @Override
    public String toString() {
        return this.dishName + "(" + this.dishPrepTime + "sec)";
    }
}