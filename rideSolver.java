import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class rideSolver {

  //fitness is the bonus value (journey distance * bonus multiplier) - distance to pick-up point, or 0 if it won't finish on time
	public static int fitnessTest(Car car, Ride ride, int rideBonus){
    //stored so doesn't need to calculate twice:
	  int journeyDistance = distanceInt(ride.getStarting()[0],ride.getStarting()[1],ride.getFinishing()[0],ride.getFinishing()[1]);
    int distanceToPickUpPoint = distanceInt(car.x, car.y, ride.getStarting()[0],ride.getStarting()[1]);

    if(ride.getFinish() >= (car.step + journeyDistance + distanceToPickUpPoint))
        return journeyDistance*rideBonus - distanceToPickUpPoint;
    else
       return 0;
	}

	private static int distanceInt(int x1, int y1, int x2, int y2) {
		return Math.abs(x2-x1) + Math.abs(y2-y1);
	}

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

          Car[] cars = new Car[noVehicles];
	Ride[] rides = new Ride[noRides];//TODO
          for(Car car: cars) {
            car = new Car();
    	  int currentFittestRideNo=0;
    	  int currentFittestRideNosFitness=0;
    	  while(car.step>noSteps){//while there's still time for the car to pick up rides
	    	  for(int i=0; i<rides.length; i++){//finds fittest ride 
	    		  if(rides[i].getStart()>car.step && currentFittestRideNosFitness < fitnessTest(car,rides[i],rideBonus)){
	    			  currentFittestRideNo = i;
	    			  currentFittestRideNosFitness = fitnessTest(car,rides[i],rideBonus);
	    		  }
	    	  }
	    	  car.addRide(rides[currentFittestRideNo]);//adds ride to car's schedule, also updates car's time & location
    	  }
      }
          }


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

  private static void printOutput(Car[] cars, String fileName) {
    String output = fileName.replace("in","out").replace("Input", "Output");

    if(cars.length > 0) {
      for(int i = 0; i < cars.length; i ++) {
        try {
          String rides = cars[i].getTotalRideNo() + " ";
          for(Integer ride: cars[i].getRides()) {
            rides += ride +" ";
          }
          Files.write(Paths.get(output), (rides + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
          System.out.println("File \""+output+"\" created.");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      System.out.println("There were no solutions to write!");
    }

  }

}
