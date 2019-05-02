package vn.edu.vnuk.tasks.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import vn.edu.vnuk.tasks.jdbc.ConnectionFactory;
import vn.edu.vnuk.tasks.model.Task;

public class TaskDao {
	private Connection connection;

    public TaskDao(){
        this.connection = new ConnectionFactory().getConnection();
    }

    public TaskDao(Connection connection){
        this.connection = connection;
    }


    //  CREATE
    public void create(Task task) throws SQLException{

        String sqlQuery = "insert into tasks (description, is_complete, date_of_completion) "
                        +	"values (?, ?, ?)";

        PreparedStatement statement;

        try {
                statement = connection.prepareStatement(sqlQuery);

                //	Replacing "?" through values
                statement.setString(1, task.getDescription());
                statement.setBoolean(2, task.getIsComplete());
                statement.setDate(3, new Date(task.getDateOfCompletion().getTimeInMillis()));

                // 	Executing statement
                statement.execute();

                System.out.println("New record in DB !");

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
                System.out.println("Done !");
        }

    }
    
    
    //  READ (List of Contacts)
    @SuppressWarnings("finally")
    public List<Task> read() throws SQLException {

        String sqlQuery = "select * from tasks";
        PreparedStatement statement;
        List<Task> tasks = new ArrayList<Task>();

        try {

            statement = connection.prepareStatement(sqlQuery);

            // 	Executing statement
            ResultSet results = statement.executeQuery();

            while(results.next()){

            	Task task = new Task();
            	task.setId(results.getLong("id"));
            	task.setDescription(results.getString("description"));
            	task.setIsComplete(results.getBoolean("is_complete"));


                Calendar date = Calendar.getInstance();
                date.setTime(results.getDate("date_of_completion"));
                task.setDateOfCompletion(date);

                tasks.add(task);

            }

            results.close();
            statement.close();


        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
                return tasks;
        }


    }


    //  READ (Single Contact)
    @SuppressWarnings("finally")
    public Task read(Long id) throws SQLException{

        String sqlQuery = "select * from tasks where id=?";

        PreparedStatement statement;
        Task task = new Task();

        try {
            statement = connection.prepareStatement(sqlQuery);

            //	Replacing "?" through values
            statement.setLong(1, id);

            // 	Executing statement
            ResultSet results = statement.executeQuery();

            if(results.next()){

            	task.setId(results.getLong("id"));
            	task.setDescription(results.getString("description"));
            	task.setIsComplete(results.getBoolean("is_complete"));


                Calendar date = Calendar.getInstance();
                date.setTime(results.getDate("date_of_completion"));
                task.setDateOfCompletion(date);
            }

            statement.close();

        } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } finally {
                return task;
        }

    }


    //  UPDATE
    public void update(Task task) throws SQLException {
        String sqlQuery = "update tasks set description=?, is_complete=?," 
                            + "date_of_completion=? where id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, task.getDescription());
            statement.setBoolean(2, task.getIsComplete());
            statement.setDate(3, new Date(task.getDateOfCompletion().getTimeInMillis()));
            statement.setLong(4, task.getId());
            statement.execute();
            statement.close();
            
            System.out.println("Task successfully modified.");
        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    
    //  DELETE
    public void delete(Task task) throws SQLException {
        String sqlQuery = "delete from tasks where id=?";

        try {
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, task.getId());
            statement.execute();
            statement.close();
            
            System.out.println("Task successfully deleted.");

        } 

        catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}