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

      int noVehicles, noRides, noSteps;
      String[][] grid;

      try {
        int[] input = Arrays.stream(Files.lines(Paths.get(fileName)).map(i -> i.split(" ")).findFirst().get())
                      .mapToInt(Integer::parseInt)
                      .toArray();
        noVehicles = input[2]; noRides = input[3]; noSteps = input[5];
          // System.out.println("Vehicle No: " +noVehicles+ ", Ride No: "+noRides + ", Step no: " +noSteps);

        grid = Files.lines(Paths.get(fileName)).skip(1)
                          .map(line -> Arrays.stream(line.split(""))
                                              .map(ingred -> ingred).toArray(String[]::new))
                          .toArray(String[][]::new);

          // System.out.println("The input is: ");
          // Arrays.stream(grid)
          //       .map(a -> String.join(" ", a))
          //       .forEach(System.out::println);
          //
          // System.out.println("The input headers are: " +Arrays.toString(input));
          // printIntGrid(fileName);

      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("File name of input data set required. Exiting...");
    }
  }
}