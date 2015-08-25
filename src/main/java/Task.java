import java.util.List;
import org.sql2o.*;



public class Task {
//these are the properties (buckets) where the class stores data
  private String description;
  private int id;
  private int categoryId;

  //This is the constructor.  It creates a new category instance.
  public Task (String description, int categoryId) {
    this.description = description;
    this.categoryId = categoryId;
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

      public int getCategoryId() {
        return categoryId;
      }

      @Override
      public boolean equals(Object otherTask){
        if (!(otherTask instanceof Task)) {
          return false;
        } else {
          Task newTask = (Task) otherTask;
          //System.out.println(this.getCategoryId());
          //System.out.println(newTask.getCategoryId());
          return this.getDescription().equals(newTask.getDescription()) &&
                      this.getId() == newTask.getId() &&
                      this.getCategoryId() == newTask.getCategoryId();
        }
      }
      public static List<Task> all() {
          String sql = "SELECT id, description, categoryId FROM tasks;";
          try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Task.class);
          }
        }

        public void save() {
          try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO tasks (description, categoryId) VALUES (:description, :categoryId)";
            this.id = (int)con.createQuery(sql, true)
            .addParameter("description", this.description)
            .addParameter("categoryId", this.categoryId)
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
 //
 //        public void update(String description) {
 //          try(Connection con = DB.sql2o.open()) {
 //            String sql = "UPDATE tasks SET description = :description) WHERE id = :id";
 //            con.createQuery(sql)
 //            .addParameter("description", description)
 //            .addParameter("id", id)
 //            .executeUpdate();
 //   }
 // }
 //
 //      public void delete() {
 //        try(Connection con = DB.sql2o.open()) {
 //          String sql = "DELETE FROM tasks WHERE id = :id;";
 //          con.createQuery(sql)
 //          .addParameter("id", id)
 //          .executeUpdate();
 //   }
 // }
}
