public class Ride {
  private int[] starting;
  private int[] finishing;
  private int start;
  private int finish;
  private boolean isDone;
  private int index;

  public Ride(int[] startL, int[] finishL, int startStep, int finishStep, int rowNo) {
    starting = startL;
    finishing = finishL;
    start = startStep;
    finish = finishStep;
    isDone = false;
    index = rowNo;
  }

  public void getIndex() {
    return index;
  }

  public void setRideDone() {
    isDone = true;
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