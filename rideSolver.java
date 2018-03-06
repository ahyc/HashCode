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
    if(args.length == 1) {
      String fileName = "Input/"+args[0];
        // System.out.println("The input data set is: '" + fileName +"'");

      int noVehicles, noRides, noSteps, R, C, bonus;
      List<Integer[]> rideList;
      List<Ride> rides = new ArrayList<Ride>();

      try {
        int[] input = Arrays.stream(Files.lines(Paths.get(fileName)).map(i -> i.split(" ")).findFirst().get())
                      .mapToInt(Integer::parseInt)
                      .toArray();
        R = input[0];  C = input[1];  noVehicles = input[2]; noRides = input[3]; bonus = input[4]; noSteps = input[5];
          // System.out.println("Vehicle No: " +noVehicles+ ", Ride No: "+noRides + ", Step no: " +noSteps);

        rideList = Files.lines(Paths.get(fileName)).skip(1)
                    .map(line -> Arrays.stream(line.split(" "))
                                        .map(rideInfo -> Integer.parseInt(rideInfo)).toArray(Integer[]::new))
                    .collect(Collectors.toList());

        // for(Integer[] ride: rideList) {
        //   for(int i = 0; i < ride.length; i++) {
        //     System.out.printf(ride[i] + " ");
        //   }
        //   System.out.println();
        // }

        int counter = 0;
        for(Integer[] ride: rideList) {
          int[] startL = new int[] {ride[0], ride[1]};
          int[] finishL = new int[] {ride[2], ride[3]};
          int startStep = ride[4];    int finishStep = ride[5];
          rides.add(new Ride(startL, finishL, startStep, finishStep, counter));
          counter++;
        }

        Car[] cars = new Car[noVehicles];
        for(int i = 0; i < cars.length; i++) {
          cars[i] = new Car();
        }

        for(Car car: cars) {
          boolean potentialRides = true;
          while(car.getStep() < noSteps && potentialRides) {
            List<Ride> fitRides = rides.stream()
                                        .filter(ride -> !ride.getStatus() && ride.isCarFit(car))
                                        .collect(Collectors.toList());

            if(fitRides.size() == 0) {
              potentialRides = false;
            }
            else {
              fitRides.sort(Comparator.comparing(Ride::getMinStep)
                                      .thenComparing(Comparator.comparing(Ride::getDistance))
                                      .reversed());

              car.addRide(fitRides.get(0));
            }
          }
        }

        // for(Car car: cars) {
        //   System.out.println(car.toString());
        // }
        // System.out.println(rides.stream().filter(ride -> !ride.getStatus()).collect(Collectors.toList()).size());

        printOutput(cars, fileName);
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
    else {
      System.out.println("File name of input data set required. Exiting...");
    }
  }

	//fitness is the bonus value (journey distance * bonus multiplier) - distance to pick-up point, or 0 if it won't finish on time
	// public static int fitnessTest(Car car, Ride ride, int rideBonus){
	// 	//stored so doesn't need to calculate twice:
	// 	int journeyDistance = distanceInt(ride.getStarting()[0],ride.getStarting()[1],ride.getFinishing()[0],ride.getFinishing()[1]);
	// 	int distanceToPickUpPoint = distanceInt(car.x, car.y, ride.getStarting()[0],ride.getStarting()[1]);
  //
	// 	if(ride.getFinish() >= (car.step + journeyDistance + distanceToPickUpPoint))
	// 			return journeyDistance*rideBonus - distanceToPickUpPoint;
	// 	else
	// 		 return 0;
	// }

	private static int distanceInt(int x1, int y1, int x2, int y2) {
		return Math.abs(x2-x1) + Math.abs(y2-y1);
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
