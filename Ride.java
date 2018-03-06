public class Ride {
  private int index;
  private int[] starting;
  private int[] finishing;
  private int distance;
  private int start;
  private int finish;
  private boolean isDone;

  public Ride(int[] startL, int[] finishL, int startStep, int finishStep, int rowNo) {
    starting = startL;
    finishing = finishL;
    start = startStep;
    finish = finishStep;
    index = rowNo;
    distance = calculateDistance(startL, finishL);
    isDone = false;
  }

  private int calculateDistance(int[] startL, int[] finishL) {
    return Math.abs(finishL[1] - startL[1]) + Math.abs(finishL[0] - startL[0]);
  }

  public boolean isCarFit(Car car) {
    if((calculateDistance(car.getLocation(), starting) + distance + car.getStep()) < finish)
      return true;
    else
      return false;
  }

  public int getDistance() {
    return distance;
  }

  public int getMinStep() {
    return (finish - 1) - distance;
  }

  public int getIndex() {
    return index;
  }

  public boolean getStatus() {
    return isDone;
  }

  public void setRideDone() {
    isDone = true;
  }

  public int[] getStarting() {
    return starting;
  }

  public int[] getFinishing() {
    return finishing;
  }

  public int getStart() {
    return start;
  }

  public int getFinish() {
    return finish;
  }

}