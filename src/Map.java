import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Map {
    public static List<List<String>> mapArray;
    private static int size;
    static int rectSize = 40; // The size of the squares for each grid of the map

    public Map(int size) {
        mapArray = new ArrayList<List<String>>(size);
        this.size = size;
    }

    public static int getSize() {
        int temp = size;
        return temp;
    }

    // Determine the size of the map based on the largest X and Y of given branch of each restaurant
    public static int getMapSize() {
        int size = 0;
        for (int i = 0; i < CrabFoodOperator.listOfRestaurant.size(); i++) {
            Restaurant tempRes = CrabFoodOperator.listOfRestaurant.get(i);
            for (int j = 0; j < tempRes.getBranchList().size(); j++) {
                Branch tempBranch = tempRes.getBranchList().get(j);
                if (tempBranch.getX() > size || tempBranch.getY() > size) {
                    size = Math.max(tempBranch.getX(), tempBranch.getY());
                }
            }
        }
        return size + 1;
    }

    // Return a gridpane of the Map
    public static GridPane getMapPane() {
        GridPane gridpane = new GridPane();

        for (int i = 0, branchRow = mapArray.size() - 1; i < mapArray.size(); i++, branchRow--) {
            for (int j = 0, branchCol = 0; j < mapArray.get(i).size(); j++, branchCol++) {

                String tempStr = mapArray.get(branchRow).get(branchCol);
                Text text = new Text(tempStr);
                text.setFont(new Font("Verdana", 25));
                Rectangle rect = new Rectangle(rectSize, rectSize);

                // Colors of the grid based on the Restaurant
                switch(tempStr){
                    case "B":
                        rect.setFill(Color.ORCHID);
                        break;
                    case "C":
                        rect.setFill(Color.SEAGREEN);
                        break;
                    case "P":
                        rect.setFill(Color.MEDIUMTURQUOISE);
                        break;
                    default:
                        rect.setFill(Color.SNOW);
                        break;
                }
                rect.setStroke(Color.BLACK);
                rect.setStrokeWidth(1);

                StackPane stack = new StackPane();
                stack.getChildren().addAll(rect, text);

                gridpane.add(stack, j, i);
            }
        }
        return gridpane;
    }

    // Generate the map
    public void generateMap() {
        for (int i = 0; i < size; i++) {
            // Filling the map with "0"s
            mapArray.add(new ArrayList<String>(Collections.nCopies(size, "0")));
        }
        searchForBranch();
        printMap();
    }

    // Add the Initial of Restaurant in the map at each branch location
    public void searchForBranch() {
        for (int i = 0; i < CrabFoodOperator.listOfRestaurant.size(); i++) {
            Restaurant tempRestaurant = CrabFoodOperator.listOfRestaurant.get(i);
            for (int j = 0; j < tempRestaurant.getBranchList().size(); j++) {
                Branch tempBranch = tempRestaurant.getBranchList().get(j);
                int x = tempBranch.getX();
                int y = tempBranch.getY();
                mapArray.get(y).set(x, Character.toString(tempRestaurant.getName().charAt(0)));
            }
        }
    }

    // Print the map in console
    public void printMap() {
        int branchRow = mapArray.size();
        int z = 0;
        int marker = 0;
        loop:
        for (int row = 0; row < (this.size * 2) + 1; row++) {
            for (int col = 0; col < (this.size * 2) + 1; col++) {
                if (row % 2 == 0) {
                    if (marker == this.size) {
                        branchRow -= 1;
                        marker = 0;
                    }
                    if (col % 2 == 0) {
                        System.out.print("+");
                    } else {
                        System.out.print("-");
                        marker++;
                    }
                } else {
                    if (col % 2 == 0) {
                        System.out.print("|");
                    } else if (branchRow == -1) {
                        break loop;
                    } else {
                        // System.out.print("(" + branchRow + "," + z + ")");
                        System.out.print(mapArray.get(branchRow).get(z));
                        z++;
                    }
                }
            }
            z = 0;
            System.out.println();
        }
    }
}
