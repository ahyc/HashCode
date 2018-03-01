public class Ride {
  private int[] starting;
  private int[] finishing;
  private int start;
  private int finish;

  public Ride() {
    starting = new int[2];
    finishing = new int[2];
    start = 0;
    finish = 0;
  }

  public Ride(int[] startL, int[] finishL, int startStep, int finishStep) {
    starting = startL;
    finishing = finishL;
    start = startStep;
    finish = finishStep;
  }

  public void setStart(int row, int col) {
    starting[0] = row;  starting[1] = col;
  }

  public void setFinish(int row, int col) {
    finishing[0] = row; finishing[1] = col;
  }

  public void setStart(int step) {
    start = step;
  }

  public void setFinish(int step) {
    finish = step;
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