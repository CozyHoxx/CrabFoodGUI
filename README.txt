CrabFood by team BrshBrsh of DS Tutorial Group 2

Application Description
--------------------------------------------
- This application 
  - Reads textfile containing restaurant and customer details
  - Simulate the delivering processes
  - Shows the position of the restaurant on a map
  - Shows the log file of the program
  - Shows the overall orders from restaurants
  - Shows the number of dish from each restaurant
  - Log into a text file(called Log.txt) the details of the customers and their orders
---------------------------------------------


Application Testing
--------------------------------------------
- The application reads from the TextFiles folder
  - Customer.txt       -> Customers details given in question
  - Restaurant.txt     -> Restaurant details given in question
  - CustomerTestFile   -> A new customer file containing (more) new customers
  - RestaurantTestFile -> A new restaurant file containing more branches or restaurant

- A way to test the application is to change what file the CrabFoodOperator
  will read from.
    - In the CrabFoodOperator class, change the name of the text file you would 
      wish to read from

There is a possibility to add more dishes to each restaurant,
provided that they are in format
--------------------------------------------


Format for each text file
--------------------------------------------
-> Customer.txt format
   [Time in hh:mm]
   [Name of Restaurant]
   [Name of Dish to order]
                            <-Empty line indicating new customer
   [Time in hh:mm]
   [Name of Restaurant]
   [Name of Dish to order]

-> Restaurant.txt format
   [Name of restaurant_1]
   x_1 y_1  <- coordinate of restaurant_1
   x_2 y_2
   ...    <- More coordinates up to user
   [Dish_1 name]
   [Dish_1 preparation time]
   [Dish_2 name]
   [Dish_2 preparation time]
   ...    <- More dishes up to user
          <- Empty line indicating end of a restaurant details
   [Name of restaurant_2]
   x_1 y_1
   ....
   [Name of Dish_1]
   [Preparation time of Dish_1]
   ...
----------------------------------------


