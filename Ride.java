public class Ride {
  private int index;
  private int[] starting;
  private int[] finishing;
  private int distance;
  private int start;
  private int finish;
  private boolean isDone;

  /**
		* Constructor to 'Ride' object initialising various varibles depending on input params.
		*
		* @param  startL int array of starting location of ride
    * @param  finishL int array of finishing location of ride
    * @param  startStep earliest step in which the ride can start from
    * @param  finishStep latest step in which the ride must be finished before
    * @param  rowNo ride index from the original list of rides
	*/
  public Ride(int[] startL, int[] finishL, int startStep, int finishStep, int rowNo) {
    starting = startL;
    finishing = finishL;
    start = startStep;
    finish = finishStep;
    index = rowNo;
    distance = calculateSteps(startL, finishL);
    isDone = false;
  }

  /**
    * Calculate distance between two locations.
    *
    * @param  startL int array of starting location
    * @param  finishL int array of finishing location
    * @return scalar value of the distance between the two locations
  */
  private int calculateSteps(int[] startL, int[] finishL) {
    return Math.abs(finishL[1] - startL[1]) + Math.abs(finishL[0] - startL[0]);
  }

  /**
    * Checks whether a specific car is able to finish the ride in time and whether if
    * it's able to start before a minimum step count.
    *
    * @param  car car object being tested for the ride
    * @return boolean indicating whether the car can potentially take up the ride
  */
  public boolean isCarFit(Car car) {
    int totalToStart = getStepsToStart(car);
    if((getTotalRideSteps(car) < finish) && (totalToStart <= getMinStep()))
      return true;
    else{
      return false;
    }
  }

  /**
    * Calculate a fitness score for the ride based on the given constraints.
    *
    * @param  car car object
    * @param  bonus the given bonus if ride starts on time or earlier
    * @param  avgStartL the average starting location of the original ride list
    * @return score indicating the fitness of the ride for a specific car
  */
  public int getRideFitness(Car car, int bonus, int[] avgStartL) {
    int distanceFromAvg = calculateSteps(finishing, avgStartL);
    int distanceToStart = calculateSteps(car.getLocation(), starting);
    int score = distance - distanceToStart - distanceFromAvg;
    if(carArrivesEarly(car)) {
      int waitingTime = (getStepsToStart(car) < start)? start - getStepsToStart(car): 0;
      score = score + bonus - waitingTime;
    }
    return score;
  }

  /**
    * Checks whether a specific car arrives early or on-time to ride.
    *
    * @param  car car object being tested for the ride
    * @return boolean indicating whether the car arrives early or on-time
  */
  private boolean carArrivesEarly(Car car) {
    return (getStepsToStart(car) <= start)? true: false;
  }

  /**
    * Calculates the latest step count in which a car can take the ride.
    *
    * @return int indicating the latest step count a ride can be taken-up on
  */
  private int getMinStep() {
    return (finish-1) - distance;
  }

  /**
    * Calculates the arrival step count of car if it takes-up the ride.
    *
    * @param  car car object with its location and step count
    * @return int value of the step count at ride starting location
  */
  private int getStepsToStart(Car car) {
    return calculateSteps(car.getLocation(), starting) + car.getStep();
  }

  /**
    * Calculates the finishing step count of car if it takes-up the ride.
    *
    * @param  car car object with its location and step count
    * @return int value of the step count at ride finishing location
  */
  public int getTotalRideSteps(Car car) {
    int stepToStart = getStepsToStart(car);
    int waitingTime = (stepToStart < start)? start - stepToStart: 0;
    return calculateSteps(car.getLocation(), starting) + distance + waitingTime;
  }

  // Getter and setter methods:
  public int getIndex() {
    return index;
  }

  public boolean getStatus() {
    return isDone;
  }

  public void setRideDone() {
    isDone = true;
  }

  public int[] getFinishing() {
    return finishing;
  }

}