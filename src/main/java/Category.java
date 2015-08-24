import java.util.ArrayList;


public class Category {
  private static ArrayList<Category> instances = new ArrayList<Category>();
//these are the properties (buckets) where the class stores data
  private String mName;
  private int mId;
  private ArrayList<Task> mtasks;

  //This is the constructor.  It creates a new category instance.
  public Category (String name) {
    mName = name;  //Filling the properties with parameter value
    instances.add(this); //adding itself to a list.  When we are working inside an object we use keycategory 'this' to reference that object
    mId = instances.size(); //instances is the list.  Size is how many items are in the list.  Example: If there are 5 instances it will have any ID of 5
    mtasks = new ArrayList<Task>();                        //Using the count as the ID at the time of creation.  You add it and then say "how big is it?"
  }
      //Method section
      //Method that gets the string from the properties.
      public String getName() {
        return mName; // do not put = because just returning a variable that is
                        //already filled by the constructor.  This is why we
                        //have a constructor.  We have created our getDescription
                        //method.
      }

      public int getId() {
        return mId;
      }

      public static ArrayList<Category> getAll() {
        return instances;
      }

      public  ArrayList<Task> gettasks() {
        return mtasks;
      }

      public void addtask(Task d) {
        mtasks.add(d);
      }

      public static Category find(int id) {          //the try-catch work flow.  Why do we use this?
        try{                                     //a way to catch user input mistakes and avoid error screen
          return instances.get(id -1);           //We tell java to 'try' to run a block of code and then to
        } catch (IndexOutOfBoundsException e) {  //catch certain exceptions and run some other code instead
          return null;                           //we use -1 because ArrayLists use the 0 index.  We will get
        }                                        //an IndexOutOfBoundsException and return null (nothing)
      }                                          //When we catch an exception we are creating an Excepion object
                                                //If you wanted to look at them(the mistakes the application in catching) use System.out.println(e.getStackTrace) in place of return null
      public static void clear () {
        instances.clear();
      }

}
