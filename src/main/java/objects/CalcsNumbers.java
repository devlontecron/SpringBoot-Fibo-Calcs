package objects;

/**
*
* @author Devin Durham
*/
public class CalcsNumbers {
   

  private int Ocalcs;
  private int Oloops;
  private int Osleep;
  
  public CalcsNumbers() {
  }

   public int getCalcs() {
       return Ocalcs;
   }

   public void setCalcs(int calcs) {
       this.Ocalcs = calcs;
   }

   public int getLoops() {
       return Oloops;
   }

   public void setLoops(int loops) {
       this.Oloops = loops;
   }

   public int getSleep() {
       return Osleep;
   }

   public void setSleep(int sleep) {
       this.Osleep = sleep;
   }
   
}