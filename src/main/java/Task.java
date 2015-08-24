import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.sql2o.*;



public class Task {
  //private static ArrayList<Task> instances = new ArrayList<Task>();
//these are the properties (buckets) where the class stores data
  private String description;
  private int id;

  //This is the constructor.  It creates a new category instance.
  public Task (String descriptionp) {
    description = descriptionp;  //Filling the properties with parameter value
    //instances.add(this); //adding itself to a list.  When we are working inside an object we use keycategory 'this' to reference that object
    //mId = instances.size(); //instances is the list.  Size is how many items are in the list.  Example: If there are 5 instances it will have any ID of 5
                            //Using the count as the ID at the time of creation.  You add it and then say "how big is it?"
  }
      //Method section
      //Method that gets the string from the properties.
      public String getDescription() {
        return description; // do not put = because just returning a variable that is
                        //already filled by the constructor.  This is why we
                        //have a constructor.  We have created our getDescription
                        //method.
      }
      public int getId() {
        return id;
      }

      @Override
      public boolean equals(Object otherTask){
        if (!(otherTask instanceof Task)) {
          return false;
        } else {
          Task newTask = (Task) otherTask;
          return this.getDescription().equals(newTask.getDescription()) &&
                      this.getId() == newTask.getId();
        }
      }
      public static List<Task> all() {
          String sql = "SELECT id, description FROM tasks;";
          try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Task.class);
          }
        }

        public void save() {
          try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO tasks(description) VALUES (:description)";
            this.id = (int) con.createQuery(sql, true)
            .addParameter("description", description)
            .executeUpdate()
            .getKey();
          }
        }
        public static Task find(int id) {
          try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM Tasks where id=:id";
            Task task = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Task.class);
            return task;
          }

        }
}
