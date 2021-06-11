package dal;
import bll.Activity;
import bll.Person;
import bll.Season;
import util.PropertyManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private String driver ="";
    private String url = "";
    private String username = "";
    private String password = "";
    private static DatabaseManager instance;

    private Connection createConnection(){
        //Treiber Laden
        Connection connection = null;
        try {
            //Erzeugen der Connection
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return  connection;
    }
    public static DatabaseManager getInstance() {
        if (instance  == null){
            instance = new DatabaseManager();
        }
        return instance;
    }

    private DatabaseManager(){
        PropertyManager.getInstance().setFilename("db.properties");
        driver = PropertyManager.getInstance().readProperty("driver", "oracle.jdbc.OracleDriver");
        url = PropertyManager.getInstance().readProperty("url", "jdbc:oracle:thin:@tcif.htl-villach.at:1521/orcl");
        username = PropertyManager.getInstance().readProperty("username", "d3a11");
        password = PropertyManager.getInstance().readProperty("password", "d3a11");
    }

    public  List<Person> getAllPerson(){
        Statement stat;
        ResultSet resultSet;
        List<Person> personList = new ArrayList<>();
        String query = "SELECT * FROM Person";

        try ( Connection con = this.createConnection() ) {
            stat = con.createStatement();
            resultSet = stat.executeQuery(query);
            while (resultSet.next()){
                //index startet mit 1!!!!
                personList.add(new Person(resultSet.getInt(1),resultSet.getInt(2), resultSet.getString(3), resultSet.getString(4)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return personList;
    }

    public List<Activity> getAllActivities(){
        Statement stat;
        ResultSet resultSet;
        List<Activity> activityList = new ArrayList<>();
        String query = "SELECT * FROM Activity";

        try ( Connection con = this.createConnection() ) {
            stat = con.createStatement();
            resultSet = stat.executeQuery(query);
            while (resultSet.next()){
                //index startet mit 1!!!!
                activityList.add(new Activity(resultSet.getInt(1),resultSet.getInt(2), resultSet.getString(3)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return activityList;
    }

    public List<Season> getAllSeasons(){
        Statement stat;
        ResultSet resultSet;
        List<Season> seasonList = new ArrayList<>();
        String query = "SELECT * FROM Season";

        try ( Connection con = this.createConnection() ) {
            stat = con.createStatement();
            resultSet = stat.executeQuery(query);
            while (resultSet.next()){
                //index startet mit 1!!!!
                seasonList.add(new Season(resultSet.getInt(1), resultSet.getString(2)));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return seasonList;

    }

    public boolean updatePerson(Person p){
        boolean result = false;
        PreparedStatement preparedStatement;
            String stmt_update = "UPDATE Person SET firstname = ?, lastname = ? WHERE ID = ?";
        ResultSet resultSet;
        int numRows = 0;

        try ( Connection con = this.createConnection()){
            preparedStatement = con.prepareStatement(stmt_update);
            preparedStatement.setString(1, p.getFirstname());
            preparedStatement.setString(2, p.getLastname());
            preparedStatement.setInt(3, p.getId());

            numRows = preparedStatement.executeUpdate();
            if ( numRows == 1 ){
                result = true;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return result;
    }
}
