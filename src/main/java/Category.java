import java.util.Arrays;
import java.util.List;
import org.sql2o.*;


public class Category {
//these are the properties (buckets) where the class stores data
  private String name;
  private int id;

  //This is the constructor.  It creates a new category instance.
  public Category (String name) {
    this.name = name;  //Filling the properties with parameter value
    ;
  }
      //Method section
      //Method that gets the string from the properties.
      public String getName() {
        return name; // do not put = because just returning a variable that is
                        //already filled by the constructor.  This is why we
                        //have a constructor.  We have created our getDescription
                        //method.
      }

      public int getId() {
        return id;
      }

      public static List<Category> all(){
        String sql = "SELECT id, name FROM Categories";
        try(Connection con = DB.sql2o.open()) {
          return con.createQuery(sql).executeAndFetch(Category.class);
        }
      }
      @Override
      public boolean equals(Object otherCategory){
        if (!(otherCategory instanceof Category)) {
          return false;
        } else {
          Category newCategory = (Category) otherCategory;
          return this.getName().equals(newCategory.getName());
        }
    }
      public void save() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "INSERT INTO Categories(name) VALUES (:name)";
          this.id = (int) con.createQuery(sql, true)
          .addParameter("name", this.name)
          .executeUpdate()
          .getKey();
        }
      }
      public static Category find(int id) {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM Categories where id=:id";
          Category Category = con.createQuery(sql)
            .addParameter("id", id)
            .executeAndFetchFirst(Category.class);
          return Category;
        }
      }
      public List<Task> getTasks() {
        try(Connection con = DB.sql2o.open()) {
          String sql = "SELECT * FROM Tasks where categoryId=:id";
          return con.createQuery(sql)
          .addParameter("id", id)
          .executeAndFetch(Task.class);
  }
}

}
