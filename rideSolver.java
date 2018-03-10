import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class rideSolver {

  public static void main(String args[]) {
    // The filename is passed as an argument to execute code:
    if(args.length == 1) {
      String fileName = "Input/"+args[0];
      int noVehicles, noRides, noSteps, R, C, bonus;
      List<Integer[]> rideList;
      List<Ride> rides = new ArrayList<Ride>();

      try {
        // Read the first line of the input file containing parameters:
        int[] input = Arrays.stream(Files.lines(Paths.get(fileName)).map(i -> i.split(" ")).findFirst().get())
                      .mapToInt(Integer::parseInt)
                      .toArray();
        R = input[0];  C = input[1];  noVehicles = input[2]; noRides = input[3]; bonus = input[4]; noSteps = input[5];

        // Read the input ride data from file and output as a list of integer arrays:
        rideList = Files.lines(Paths.get(fileName)).skip(1)
                    .map(line -> Arrays.stream(line.split(" "))
                                        .map(rideInfo -> Integer.parseInt(rideInfo)).toArray(Integer[]::new))
                    .collect(Collectors.toList());

        // Convert the list of integer arrays into a list of 'Ride' objects:
        int counter = 0, x = 0, y = 0;
        for(Integer[] ride: rideList) {
          x += ride[0];   y += ride[1];
          int[] startL = new int[] {ride[0], ride[1]};
          int[] finishL = new int[] {ride[2], ride[3]};
          int startStep = ride[4];    int finishStep = ride[5];
          rides.add(new Ride(startL, finishL, startStep, finishStep, counter));
          counter++;
        }

        // Initialise 'Car' array with a given number of 'Car' objects:
        Car[] cars = new Car[noVehicles];
    		for(int i = 0; i < cars.length; i++) {
    			cars[i] = new Car();
    		}

        // Calculate the average starting location, {x_avg, y_avg}, of all the rides:
        int[] average = new int[]{x/rides.size(), y/rides.size()};

        // Loop over the 'Car' objects until all the cars are assigned routes:
        int finishedCars = 0;
        while(finishedCars != noVehicles) {
          for(Car car: cars) {
            if(!car.getStatus()) {
              // Filter rides that are done or that the car cannot complete given its current step count:
              List<Ride> fitRides = rides.stream()
                                          .filter(ride -> !ride.getStatus() && ride.isCarFit(car))
                                          .collect(Collectors.toList());

              // If there are no potential rides, continue assigning rides to the other cars.
              // Else add the best potential ride to the car route:
              if(fitRides.isEmpty()) {
                car.setDone();
                finishedCars++;
              }
              else {
                fitRides.sort(Comparator.comparing((Ride ride) -> ride.getRideFitness(car, bonus, average))
                                        .reversed());
                car.addRide(fitRides.get(0));
              }
            }
          }

          // Sort the 'Car' array by ascending car steps:
          Arrays.sort(cars);
        }

        System.out.println();
        List<Ride> notDone = rides.stream().filter(ride -> !ride.getStatus()).collect(Collectors.toList());
        System.out.println("The number of unassigned rides: " +notDone.size());
        System.out.println();

        // Write solution onto an output file:
        printOutput(cars, fileName);
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("File name of input data set required. Exiting...");
    }
  }

  /**
    * Write output file for solution in given format:
    *   "M R0 R1 ... RM-1"
    * where: M – number of rides assigned to the vehicle (0 ≤ M ≤ N)
    *       Ri – ride numbers assigned to the vehicle, in the order in which the vehicle will perform them (0≤ Ri <N)
    *
    * @param  cars Array of 'Car' objects
    * @param  fileName the input file name
  */
  private static void printOutput(Car[] cars, String fileName) {
    String output = fileName.replace("in","out").replace("Input", "Output");

    if(cars.length > 0) {
      List<String> rideInfo = new ArrayList<String>();
      for(int carNo = 0; carNo < cars.length; carNo++) {
        rideInfo.add(cars[carNo].toString());
      }

      try {
        Files.write(Paths.get(output), rideInfo, Charset.forName("UTF-8"));
        System.out.println("File \""+output+"\" created.");
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println("There were no solutions to write!");
    }
  }

}
