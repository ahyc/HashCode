import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class Car {
	private int[] location;
	private int step;
	public List<Integer> ridesToDo;

	public Car(){
		location = new int[] {0,0};
		step = 0;
		ridesToDo = new ArrayList<Integer>();
	}

	//update time, position
	//assume only pick rides that can be finished in time, so it'll get there before or at starting time so you don't have to calculate that
	public void addRide(Ride ride){
		ridesToDo.add(ride.getIndex());
		step += ride.getDistance() + ride.getStart();
		location = ride.getFinishing();
		ride.setRideDone();
	}

	public int getTotalRideNo() {
		return ridesToDo.size();
	}

	public int getStep() {
		return step;
	}

	public int[] getLocation() {
		return location;
	}

	public String toString() {
		return ridesToDo.size() + " " + ridesToDo.stream()
																							.map(rideNo -> String.valueOf(rideNo))
																							.collect(Collectors.joining(" "));
	}
}
