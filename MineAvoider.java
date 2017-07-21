package com.company;
import static java.lang.Boolean.*;
import java.util.*;
public class Main {
    int row_max;
    int column_max;
    public int[][] direction;
    int[][] mine_field;
    Hashtable distance_path = new Hashtable();

    public void initialize() {
        direction = new int[][]{
                {0, 1},
                {1, 1},
                {1, 0}};
        // this direction array has the difference that should be added to rows and column address to get the new position with respect to current position in a matrix.
        //add the directions that are allowed
        /*right                  row+0,column+1
          diagonal_right_down    row+1,column+1
          down                   row+1,column+0
          diagonal_left_down     row+1,column-1
          ...

        */
        mine_field = new int[][]{
                {0, 1, 0, 0,1},
                {0, 0, 0, 0,1},
                {0, 1, 0, 0,0},
                {0, 0, 0, 0,0}
        };//  mark mines with 1 and non-mines with 0
        //mine matrix can have any dissimilar rows and columns.
        row_max = mine_field.length - 1;
        column_max = mine_field[0].length - 1;
    }
    public static void main(String[] args) {
        Main player = new Main();
        player.initialize();
        player.path_finder(0, 0, "[0][0] ", 0);// start finding the path from 0,0
        player.showShortestPath();
    }
    void path_finder(int current_row, int current_column, String old_path, int distance_covered) // this function will find all possible paths from source to destination
    {
        int total_directions = direction[0].length;
        if ((current_row <= row_max) && (current_column <= column_max))  //check if current row and column is out of bounds
            for (int direction_count = 0; direction_count <= total_directions; direction_count++) // traverse through all permitted directions
            {
                int row_inc = direction[direction_count][0];
                int column_inc = direction[direction_count][1];
                if (hasNoMine(current_row + row_inc, current_column + column_inc))//check if there is a mine in the direction we wish to go
                {
                    String path_taken = old_path + String.format("[%1$s][%2$s] ", current_row + row_inc, current_column + column_inc);//append the new mine free address to the travelled route
                    path_finder(current_row + row_inc, current_column + column_inc, path_taken, distance_covered + 1);//recursively find all paths from the new address address
                }
                if (isDestination(current_row, current_column)) //checks if the current position is the destination.
                {
                    // possible_paths_distance.add(new path_info(old_path,distance_covered));
                    //possible_paths.add(old_path);// add the path the list of known paths
                    distance_path.put(distance_covered, old_path);// store distance as key and its path as value
                }
            }
    }
    boolean isDestination(int row, int column) //checks if the current position is the destination.
    {
        if ((row == row_max) && (column == column_max)) {
            return TRUE;
        } else return FALSE;
    }

    boolean hasNoMine(int row, int column)  //checks if current position out of boundary or current position has mine
    {
        boolean isNotPresent = TRUE;
        if ((row <= row_max) && (column <= column_max) && (row >= 0) && (column >= 0)) //check for out of boundary
        {
            if (mine_field[row][column] == 1) // checks if mine not present
            {
                isNotPresent = FALSE;
            } else {
                isNotPresent = TRUE;
            }
        }
        return isNotPresent;
    }
  void   showShortestPath() // finds and displays the shortest path out of all known pat
  {
      try {
          Set<Integer> distances = distance_path.keySet(); // retrieves all set of various possible distances
          int min_distance = Collections.min(distances);//
          System.out.print("minimum distance:" + min_distance + " path:" + distance_path.get(min_distance));
      }
      catch (java.util.NoSuchElementException e)
      {
          System.out.println("There was no path found");
      }
    }
}
