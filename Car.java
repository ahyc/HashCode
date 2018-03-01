import java.util.ArrayList;

public class Car{

	public int x;
	public int y;
	public int step;
	public ArrayList<Integer> ridesToDo;

	public Car(){
		ridesToDo = new ArrayList<Ride>();
	}

	public int distance(int x1, int y1, int x2, int y2){
		return Math.abs(x2-x1) + Math.abs(y2-y1);
	}

	public void addRide(Ride ride){
		ridesToDo.add(ride.getIndex());
		//update time, position
		//assume only pick rides that can be finished in time, so it'll get there before or at starting time so you don't have to calculate that
		step += ride.getStart() + distance(ride.getStarting()[0],ride.getStarting()[1],ride.getFinishing()[0],ride.getFinishing()[1]);
		x = ride.getFinishing()[0];
		y = ride.getFinishing()[1];
	}

	private ArrayList<Integer> getRides() {
		return ridesToDo;
	}

	private int getTotalRideNo() {
		return ridesToDo.size();
	}
}
