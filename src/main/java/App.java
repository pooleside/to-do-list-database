import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;
import java.util.ArrayList;


public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    //this method puts the home page out in the layout.vtl template
    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("categories", request.session().attribute("categories"));
      model.put("template", "templates/home.vtl");//We are putting the home.vtl template into the root "/" route
      return new ModelAndView(model, layout);  //returning to Spark
    }, new VelocityTemplateEngine());
//
//
    get("/categories", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      model.put("categories", Category.getAll());  //When you open home, if there are any tasks in the session it will display them
      model.put("template", "templates/categories.vtl");//We are putting the home.vtl template into the root "/" rout
      return new ModelAndView(model, layout);  //returning to Spark
    }, new VelocityTemplateEngine());

    get("categories/new", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/category-form.vtl");//We are putting the home.vtl template into the root "/" rout
      return new ModelAndView(model, layout);  //returning to Spark
    }, new VelocityTemplateEngine());

//     //We are now making a second page that displays results of home page(root page /)
    post("/categories", (request,response) -> {  //"tasks" is our route and new page
    HashMap<String, Object> model = new HashMap<String, Object>();//created a HashMap called model
    String name = request.queryParams("name"); // This brings in what was typed text box with the name "description" from home.vtl or root route
    Category newcategory = new Category(name);                   //Example line..<input id="description" name="description" class="form-control" type="text"/>
                                                        //When we take in "garden" in the description text box we put it in the String description. Then we need to create a place to store the description.  We create a new instance of the class called "Task". The name our instance is 'nt'
                                                    //anything coming in from a text input on a website is a string.
    model.put("template", "templates/success.vtl");//put in my HashMap a success.vtl template called "template"
    return new ModelAndView(model, layout);
  },new VelocityTemplateEngine());

  get("/categories/:id", (request,response) -> {
  HashMap<String, Object> model = new HashMap<String, Object>();
  Category category = Category.find(Integer.parseInt(request.params(":id"))); //This will be a string, turning into integer. Then using category-form.find method from instances and save it into a category object. Putting that category object into our model
  model.put("category", category);
  model.put("template", "templates/category-tasks-form.vtl");//category-tasks-form
  return new ModelAndView(model, layout);
  },new VelocityTemplateEngine());

  get("/categories/:id/tasks/new", (request,response) -> {
  HashMap<String, Object> model = new HashMap<String, Object>();
  Category category = Category.find(Integer.parseInt(request.params(":id"))); //This will be a string, turning into integer. Then using category-form.find method from instances and save it into a category object. Putting that category object into our model
  ArrayList<Task> tasks = category.gettasks();
  model.put("category", category);
  model.put("task", tasks);
  model.put("template", "templates/category.vtl");//categories.vtl
  return new ModelAndView(model, layout);
  },new VelocityTemplateEngine());

  get("/tasks", (request, response) -> {
    HashMap<String, Object> model = new HashMap<String, Object>();

    model.put("tasks", Task.all());  //When you open home, if there are any tasks in the session it will display them
    model.put("template", "templates/category.vtl");//We are putting the home.vtl template into the root "/" rout
    return new ModelAndView(model, layout);  //returning to Spark
  }, new VelocityTemplateEngine());

  post("/tasks", (request,response) -> {
  HashMap<String, Object> model = new HashMap<String, Object>();
  Category category = Category.find(Integer.parseInt(request.queryParams("categoryId")));
  // category category = category.find(Integer.parseInt(request.queryParams("categoryId"))); //This will be a string, turning into integer. Then using category-form.find method from instances and save it into a category object. Putting that category object into our model
  // ArrayList<task> tasks = category.gettasks();
  //
  // if(tasks == null) {
  //   tasks = new ArrayList<task>();
  //   request.session().attribute("tasks", tasks);
  // }
  //category category = new category("Honkey");
  String name = request.queryParams("name");
  Task newtask = new Task(name);
  category.addtask(newtask);


  model.put("category", category);
  model.put("task", newtask);
  model.put("template", "templates/category.vtl");
  return new ModelAndView(model, layout);
},new VelocityTemplateEngine());


  }//end of main method
}//end of class
