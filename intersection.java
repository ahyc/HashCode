import java.util.ArrayList;
import java.util.List;

public class intersection {
  private List<Ride> starting;
  private List<Ride> finishing;

  public intersection() {
    starting = new ArrayList<Ride>();
    finishing = new ArrayList<Ride>();
  }

  public int getNoOfStarting() {
    return starting.size();
  }

  public int getNoOfFinishing() {
    return finishing.size();
  }

  public void removeStartingRide(Ride ride) {
    starting.remove(ride);
  }

  public void addStartingRide(int[] startL, int[] finishL, int startStep, int finishStep, int index) {
    Ride ride = new Ride(startL, finishL, startStep, finishStep, index);
    starting.add(ride);
  }

  public void addFinishingRide(int[] startL, int[] finishL, int startStep, int finishStep, int index) {
    Ride ride = new Ride(startL, finishL, startStep, finishStep, index);
    finishing.add(ride);
  }
}