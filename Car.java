import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Car implements Comparable<Car> {
	private int[] location;
	private int step;
	private boolean isDone;
	public List<Integer> ridesToDo;

	// Constructor for 'Car' objects:
	public Car(){
		location = new int[] {0,0};
		step = 0;
		isDone = false;
		ridesToDo = new ArrayList<Integer>();
	}

	/**
		* Add a ride to a car's route, updating its location and step count.
		*
		* @param  ride the ride which is added to the car's route
	*/
	public void addRide(Ride ride){
		ridesToDo.add(ride.getIndex());
		step += ride.getTotalRideSteps(this);
		location = ride.getFinishing();
		ride.setRideDone();
	}

	/**
    * Get the number of rides in the car's calculated route.
    *
    * @return  the size of the 'ridesToDo' list
  */
	public int getTotalRideNo() {
		return ridesToDo.size();
	}


	// Various getter and setter methods:
	public int getStep() {
		return step;
	}

	public int[] getLocation() {
		return location;
	}

	public void setDone() {
		isDone = true;
	}

	public boolean getStatus() {
		return isDone;
	}

	/**
		* Compare the step count of this car with another car.
		*
		* @param  other the car that's being compared to
		* @return the values {0, <0, >0} depending on the comparison result
	*/
	@Override
	public int compareTo(Car other) {
		return Integer.compare(step, other.getStep());
	}

	/**
		* Get the number of rides followed by all ride indices in the car's route as a String.
		*
		* @return string containing the ride number and the ride indices joined by whitespace
	*/
	@Override
	public String toString() {
		return ridesToDo.size() + " " + ridesToDo.stream()
													.map(rideNo -> String.valueOf(rideNo))
													.collect(Collectors.joining(" "));
	}
}
