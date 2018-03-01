import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class rideSolver {
  public static void main(String args[]) {
    if(args.length == 1) {
      String fileName = "Input/"+args[0];
        // System.out.println("The input data set is: '" + fileName +"'");

      int noVehicles, noRides, noSteps, R, C, bonus;
      Integer[][] grid;

      try {
        int[] input = Arrays.stream(Files.lines(Paths.get(fileName)).map(i -> i.split(" ")).findFirst().get())
                      .mapToInt(Integer::parseInt)
                      .toArray();
         R = input[0];  C = input[1];  noVehicles = input[2]; noRides = input[3]; bonus = input[4]; noSteps = input[5];
          // System.out.println("Vehicle No: " +noVehicles+ ", Ride No: "+noRides + ", Step no: " +noSteps);

          grid = Files.lines(Paths.get(fileName)).skip(1)
                          .map(line -> Arrays.stream(line.split(" "))
                                              .map(ingred -> Integer.parseInt(ingred)).toArray(Integer[]::new))
                          .toArray(Integer[][]::new);

          // System.out.println("The input grid is: ");
          // System.out.println(Arrays.deepToString(grid)
          //                           .replace("], ", "]\n")
          //                           .replace("[[", "[")
          //                           .replace("]]", "]"));
          //
          // System.out.println("The input headers are: " +Arrays.toString(input));

          intersection[][] intersections = new intersection[R][C];
          for(int i = 0; i < intersections.length; i++) {
            for(int j = 0; j < intersections[0].length; j++) {
              intersections[i][j] = new intersection();
            }
          }


          for(int row = 0; row < grid.length; row++) {
            int[] startL = new int[] {grid[row][0], grid[row][1]};
            int[] finishL = new int[] {grid[row][2], grid[row][3]};
            int startStep = grid[row][4];   int finishStep = grid[row][5];

            intersections[startL[0]][startL[1]].addStartingRide(startL, finishL, startStep, finishStep, row);
            intersections[finishL[0]][finishL[1]].addFinishingRide(startL, finishL, startStep, finishStep, row);
            // System.out.println(startL[0] + " " + startL[1] + " " + startStep);
            // System.out.println(finishL[0] + " " + finishL[1] + " " + finishStep);
          }

          printNoOfStartingRides(intersections);
          printNoOfFinishingRides(intersections);

          Ride ride = new Ride(new int[]{0,0}, new int[]{1,3}, 2, 9);

          intersections[0][0].removeStartingRide(ride);

          System.out.println(intersections[0][0].getNoOfStarting());

      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("File name of input data set required. Exiting...");
    }
  }

  public static void printNoOfStartingRides(intersection[][] intersections) {
    for(int i = 0; i < intersections.length; i++) {
      for(int j = 0; j < intersections[0].length; j++) {
        System.out.printf((intersections[i][j]).getNoOfStarting() +" ");
      }
      System.out.println();
    }
    System.out.println();
  }

  public static void printNoOfFinishingRides(intersection[][] intersections) {
    for(int i = 0; i < intersections.length; i++) {
      for(int j = 0; j < intersections[0].length; j++) {
        System.out.printf((intersections[i][j]).getNoOfFinishing() +" ");
      }
      System.out.println();
    }
    System.out.println();
  }

}