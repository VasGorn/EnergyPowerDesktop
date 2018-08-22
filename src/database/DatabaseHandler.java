package database;

import model.*;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseHandler extends Configs{
    private Connection dbConnection;


//    public DatabaseHandler(){
//    }

    private Connection getDbConnection() throws ClassNotFoundException, SQLException{
        String connectionString = "jdbc:mysql://" + dbHost + ":" + dbPort + "/" + dbName
                + "?useSSL=false&useUnicode=yes&characterEncoding=UTF-8&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
//+ "?useSSL=false"
        Class.forName("com.mysql.cj.jdbc.Driver");

        dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);

        return dbConnection;
    }

    public int getUser(){
        ResultSet resSet;
        int count = 0;

        String select = "SELECT * FROM " + Const.USER_TABLE + " WHERE " + Const.USER_NAME
                + "=? AND " + Const.USER_PASSWORD + "=?";



        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, User.getUserName());
            prSt.setString(2, User.getPassword());

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                User.setEmployeeID(resSet.getInt(Const.USER_EMPLOYEE_ID));
                count++;
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return count;
    }

    public void setNameForEmployee(){
        ResultSet resSet;

        String select = "SELECT * FROM " + Const.EMPLOYEE_TABLE + " WHERE " + Const.EMPLOYEE_ID
                + "=? ";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(User.getEmployeeID()));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                User.setNameEmployee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME));
                User.setEmployee(new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID)));
            }

            resSet.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setListOfPositionForUser(){
        ResultSet resSet;
        ArrayList<String> listPosіtion = new ArrayList<>();

        String select = "SELECT "
                + Const.POSITION_NAME
                + " FROM "
                + Const.POSITION_TABLE + ", "
                + Const.EMPLOYEE_HAS_POSITION_TABLE
                + " WHERE "
                + Const.EMPLOYEE_HAS_POSITION_EMPLOYEE_ID + "=?" + " AND "
                + Const.EMPLOYEE_HAS_POSITION_POSITION_ID + "=" + Const.POSITION_ID;

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(User.getEmployeeID()));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                listPosіtion.add(resSet.getString(1));
            }

            resSet.close();

            User.setPosition(listPosіtion);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setListOfWorkers(){
        ResultSet resSet;
        ArrayList<Employee> listWorkers = new ArrayList<>();

        String select = "SELECT " + Const.EMPLOYEE_ID + ", " + Const.EMPLOYEE_LASTNAME + ", " + Const.EMPLOYEE_FIRSTNAME + ", " + Const.EMPLOYEE_MIDDLENAME
                + " FROM " + Const.TEAM_TABLE + ", " + Const.EMPLOYEE_TABLE
                + " WHERE " + Const.TEAM_MASTER_ID + "=? " + "AND " + Const.TEAM_WORKER_ID + " = " + Const.EMPLOYEE_ID;

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(User.getEmployeeID()));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                listWorkers.add(new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME), resSet.getString(Const.EMPLOYEE_FIRSTNAME), resSet.getString(Const.EMPLOYEE_MIDDLENAME),resSet.getInt(Const.EMPLOYEE_ID)));
            }

            resSet.close();

            User.setWorkers(listWorkers);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setServerDate(){
        ResultSet resSet;

        String select = "SELECT NOW()";

        try (Statement prSt = getDbConnection().createStatement()) {

            resSet = prSt.executeQuery(select);
            java.sql.Date dbDate;

            while(resSet.next()) {
                dbDate = resSet.getDate(1);
                ServerDate.setServerDate(dbDate);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void setListOfOrdersForMaster(){
        ResultSet resSet;
        ArrayList<Order> listOrders = new ArrayList<>();

//        String select = "SELECT "
//                + Const.ORDER_ID + ", "
//                + Const.ORDER_NAME + ", "
//                + Const.ORDER_ADRESS + ", "
//                + Const.ORDER_DERSCRIPTION + ", "
//                + Const.ORDER_MANAGER_ID + ", "
//                + Const.ORDER_MAX_HOURS
//                + " FROM "
//                + Const.ORDER_TABLE + ", "
//                + Const.QOUTS_TABLE + ", "
//                + Const.HOUR_PER_MONTH_TABLE
//                + " WHERE "
//                + Const.QOUTS_EMPLOYEE_ID + "=?"
//                + " AND " + Const.QOUTS_ORDER_ID + "=" + Const.ORDER_ID
//                + " AND " + Const.HOUR_PER_MONTH_QUOT_ID + " = " + Const.QOUTS_ID
//                + " AND " + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + "=? "
//                + " AND " + Const.HOUR_PER_MONTH_HOURS + "> 0";
        String select = "SELECT "
                + Const.ORDER_ID + ", "
                + Const.ORDER_NAME + ", "
                + Const.ORDER_ADRESS + ", "
                + Const.ORDER_DERSCRIPTION + ", "
                + Const.ORDER_MANAGER_ID + ", "
                + Const.ORDER_MAX_HOURS + ", "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME
                + " FROM "
                + Const.ORDER_TABLE + ", "
                + Const.QOUTS_TABLE + ", "
                + Const.HOUR_PER_MONTH_TABLE + ", "
                + Const.EMPLOYEE_TABLE
                + " WHERE "
                + Const.QOUTS_EMPLOYEE_ID + "=?"
                + " AND " + Const.QOUTS_ORDER_ID + "=" + Const.ORDER_ID
                + " AND " + Const.HOUR_PER_MONTH_QUOT_ID + " = " + Const.QOUTS_ID
                + " AND " + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + "=? "
                + " AND " + Const.HOUR_PER_MONTH_HOURS + "> 0"
                + " AND " + Const.EMPLOYEE_ID + " = " + Const.ORDER_MANAGER_ID;

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(User.getEmployeeID()));
            prSt.setString(2, String.valueOf((ServerDate.getNumMonth())));


            resSet = prSt.executeQuery();

            while(resSet.next()) {
                listOrders.add(new Order(resSet.getInt(Const.ORDER_ID),
                        resSet.getString(Const.ORDER_NAME),
                        resSet.getString(Const.ORDER_ADRESS),
                        resSet.getString(Const.ORDER_DERSCRIPTION),
                        (resSet.getString(Const.EMPLOYEE_LASTNAME) + " " + resSet.getString(Const.EMPLOYEE_FIRSTNAME) + " " + resSet.getString(Const.EMPLOYEE_MIDDLENAME)),
                        resSet.getInt(Const.ORDER_MAX_HOURS)));
            }

            resSet.close();

            User.setListOrdersMaster(listOrders);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setListOfOrdersForManager(){
        ResultSet resSet;
        ArrayList<Order> listOrders = new ArrayList<>();

        String select = "SELECT "
                + Const.ORDER_ID + ", "
                + Const.ORDER_NAME + ", "
                + Const.ORDER_ADRESS + ", "
                + Const.ORDER_DERSCRIPTION + ", "
                + Const.ORDER_MANAGER_ID + ", "
                + Const.ORDER_MAX_HOURS
                + " FROM "
                + Const.ORDER_TABLE
                + " WHERE "
                + Const.ORDER_MANAGER_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(User.getEmployeeID()));


            resSet = prSt.executeQuery();

            while(resSet.next()) {
                listOrders.add(new Order(resSet.getInt(Const.ORDER_ID),
                        resSet.getString(Const.ORDER_NAME),
                        resSet.getString(Const.ORDER_ADRESS),
                        resSet.getString(Const.ORDER_DERSCRIPTION),
                        User.getNameEmployee(),
                        resSet.getInt(Const.ORDER_MAX_HOURS)));
            }

            resSet.close();

            User.setListOrdersManager(listOrders);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    //p = 3 - master
    //p = 1 - manager
    public void setWorkTypeListToOrder(int p){
        ResultSet resSet;
        ArrayList<Order> listOrders;
        if(p == 3) {
            listOrders = User.getListOrdersMaster();
        }else{
            listOrders = User.getListOrdersManager();
        }

        for(Order o: listOrders){

            ArrayList<WorkType> listWorkType = new ArrayList<>();
            String select = "SELECT "
                    + Const.WORK_TYPE_ID + ", "
                    + Const.WORK_TYPE_NAME
                    + " FROM "
                    + Const.WORK_TYPE_TABLE + ", "
                    + Const.ORDER_HAS_WORKTYPE_TABLE
                    + " WHERE "
                    + Const.ORDER_HAS_WORKTYPE_ORDER_ID + "=?"
                    + " AND " + Const.ORDER_HAS_WORKTYPE_TYPE_ID + "=" + Const.WORK_TYPE_ID;

            try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
                prSt.setString(1, String.valueOf(o.getId()));

                resSet = prSt.executeQuery();

                while(resSet.next()) {
                    listWorkType.add(new WorkType(resSet.getInt(Const.WORK_TYPE_ID ),resSet.getString(Const.WORK_TYPE_NAME )));
                }

                resSet.close();

                o.setWorkTypeList(listWorkType);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void setListMonthQoutToOrderForMaster(){
        ResultSet resSet;
        ArrayList<Order> listOrders = User.getListOrdersMaster();

        for(Order o: listOrders){

            ArrayList<int[]> listMonthQout = new ArrayList<>();
            String select = "SELECT "
                    + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + ", "
                    + Const.HOUR_PER_MONTH_HOURS
                    + " FROM "
                    + Const.HOUR_PER_MONTH_TABLE
                    + " WHERE "
                    + Const.HOUR_PER_MONTH_QUOT_ID + "= (SELECT "
                    + Const.QOUTS_ID
                    + " FROM "
                    + Const.QOUTS_TABLE
                    + " WHERE "
                    + Const.QOUTS_ORDER_ID + "=? AND "
                    + Const.QOUTS_EMPLOYEE_ID + "=?)"
                    + "ORDER BY " + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH;

            try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
                prSt.setString(1, String.valueOf(o.getId()));
                prSt.setString(2, String.valueOf(User.getEmployeeID()));

                resSet = prSt.executeQuery();

                while(resSet.next()) {
                    int[] array = {resSet.getInt(Const.HOUR_PER_MONTH_QUOT_NUM_MONTH), resSet.getInt(Const.HOUR_PER_MONTH_HOURS)};
                    listMonthQout.add(array);
                }

                resSet.close();

                o.setListMonthQout(listMonthQout);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void setListMonthWorkTable(){
        ResultSet resSet;
        ArrayList<Order> listOrders = User.getListOrdersMaster();

        for(Order o: listOrders){

            ArrayList<WR_Table> listForWorkTable = new ArrayList<>();
            String select = "SELECT "
                    + Const.EMPLOYEE_LASTNAME + ", "
                    + Const.EMPLOYEE_FIRSTNAME + ", "
                    + Const.EMPLOYEE_MIDDLENAME + ", "
                    + Const.EMPLOYEE_ID + ", "
                    + Const.WORK_TYPE_NAME + ", "
                    + Const.WORK_TYPE_ID + ", "
                    + Const.WORK_TIME_NUM_MONTH + ", "
                    + Const.WORK_TIME_NUM_DAY + ", "
                    + Const.WORK_TIME_WORK_TIME + ", "
                    + Const.WORK_TIME_OVER_TIME
                    + " FROM "
                    + Const.EMPLOYEE_TABLE + ", "
                    + Const.WORK_TYPE_TABLE + ", "
                    + Const.WORK_TIME_TABLE
                    + " WHERE "
                    + Const.EMPLOYEE_ID + " = " + Const.WORK_TIME_EMPLOYEE_ID + " AND "
                    + Const.WORK_TYPE_ID + " = " + Const.WORK_TIME_WORK_TYPE_ID + " AND "
                    + Const.WORK_TIME_ORDER_ID + " =?" + " AND "
                    + Const.WORK_TIME_NUM_MONTH + " =?";


            try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
                prSt.setString(1, String.valueOf(o.getId()));
                prSt.setString(2, String.valueOf(ServerDate.getNumMonth()));

                resSet = prSt.executeQuery();

                while(resSet.next()) {
                    WR_Table newRow = new WR_Table(new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME), resSet.getString(Const.EMPLOYEE_FIRSTNAME), resSet.getString(Const.EMPLOYEE_MIDDLENAME), resSet.getInt(Const.EMPLOYEE_ID)),
                            new WorkType(resSet.getInt(Const.WORK_TYPE_ID),resSet.getString(Const.WORK_TYPE_NAME)),
                            ServerDate.getNumMonth(),
                            resSet.getInt(Const.WORK_TIME_NUM_DAY),
                            resSet.getInt(Const.WORK_TIME_WORK_TIME),
                            resSet.getInt(Const.WORK_TIME_OVER_TIME));
                    listForWorkTable.add(newRow);
                }

                resSet.close();

                o.setListOfWorkTable(listForWorkTable);

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

    }

    public void addWorkTable(WR_Table newRecord, Order order){
        int orderID = order.getId();
        int employeeID = newRecord.getEmployee().getID();
        int workTypeID = newRecord.getWorkType().getId();
        int numMonth = newRecord.getNumMonth();
        int numDay = newRecord.getNumDay();
        int workTime = newRecord.getWorkHours();
        int overWork = newRecord.getOverWorkHours();

        String insert = "INSERT INTO " + Const.WORK_TIME_TABLE + " ( "
                + Const.WORK_TIME_ORDER_ID + ", "
                + Const.WORK_TIME_EMPLOYEE_ID + ", "
                + Const.WORK_TIME_WORK_TYPE_ID + ", "
                + Const.WORK_TIME_NUM_MONTH + ", "
                + Const.WORK_TIME_NUM_DAY + ", "
                + Const.WORK_TIME_WORK_TIME + ", "
                + Const.WORK_TIME_OVER_TIME + " ) "
                + "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(orderID));
            prSt.setString(2, String.valueOf(employeeID));
            prSt.setString(3, String.valueOf(workTypeID));
            prSt.setString(4, String.valueOf(numMonth));
            prSt.setString(5, String.valueOf(numDay));
            prSt.setString(6, String.valueOf(workTime));
            prSt.setString(7, String.valueOf(overWork));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateWorkTable(WR_Table newRecord, Order order){
        int orderID = order.getId();
        int employeeID = newRecord.getEmployee().getID();
        int workTypeID = newRecord.getWorkType().getId();
        int numMonth = newRecord.getNumMonth();
        int numDay = newRecord.getNumDay();
        int workTime = newRecord.getWorkHours();
        int overWork = newRecord.getOverWorkHours();

        String insert = "UPDATE " + Const.WORK_TIME_TABLE
                + " SET "
                + Const.WORK_TIME_WORK_TYPE_ID + "=?, "
                + Const.WORK_TIME_WORK_TIME + "=?, "
                + Const.WORK_TIME_OVER_TIME + "=? "
                + " WHERE "
                + Const.WORK_TIME_ORDER_ID + " =?" + " AND "
                + Const.WORK_TIME_EMPLOYEE_ID + " =?" + " AND "
                + Const.WORK_TIME_NUM_MONTH + " =?" + " AND "
                + Const.WORK_TIME_NUM_DAY + " =?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(workTypeID));
            prSt.setString(2, String.valueOf(workTime));
            prSt.setString(3, String.valueOf(overWork));
            prSt.setString(4, String.valueOf(orderID));
            prSt.setString(5, String.valueOf(employeeID));
            prSt.setString(6, String.valueOf(numMonth));
            prSt.setString(7, String.valueOf(numDay));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //p = 2 - engineer
    //p = 3 - master
    public int getSumWorkHoursOnOrders(Order order, int employeeID, int p){
        ResultSet resSet;
        int orderID = order.getId();
        int sum = 0;
        String select;

        if(p == 3) {
            select = "SELECT SUM("
                    + Const.WORK_TIME_WORK_TIME + ")+SUM("
                    + Const.WORK_TIME_OVER_TIME + ") "
                    + " FROM "
                    + Const.WORK_TIME_TABLE + ", "
                    + Const.TEAM_TABLE
                    + " WHERE "
                    + Const.TEAM_MASTER_ID + "=? AND "
                    + Const.WORK_TIME_ORDER_ID + "=? AND "
                    + Const.WORK_TIME_EMPLOYEE_ID + "=" + Const.TEAM_WORKER_ID;
        }else{
            select = "SELECT SUM("
                    + Const.WORK_TIME_WORK_TIME + ")+SUM("
                    + Const.WORK_TIME_OVER_TIME + ") "
                    + " FROM "
                    + Const.WORK_TIME_TABLE
                    + " WHERE "
                    + Const.WORK_TIME_EMPLOYEE_ID + "=? AND "
                    + Const.WORK_TIME_ORDER_ID + "=?";
        }

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(employeeID));
            prSt.setString(2,String.valueOf(orderID));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                sum = resSet.getInt(1);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return sum;
        }
    }

    public ArrayList<Employee> getAllEmployeeList(){
        ResultSet resSet;
        ArrayList<Employee> listEmployee = new ArrayList<>();

        String select = "SELECT "
                    + Const.EMPLOYEE_LASTNAME + ", "
                    + Const.EMPLOYEE_FIRSTNAME + ", "
                    + Const.EMPLOYEE_MIDDLENAME + ", "
                    + Const.EMPLOYEE_ID
                    + " FROM "
                    + Const.EMPLOYEE_TABLE;


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                            resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                            resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                            resSet.getInt(Const.EMPLOYEE_ID));
                listEmployee.add(employee);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return listEmployee;
        }
    }

    public int addEmployee(String lastName, String firstName, String middleName){
        int lastID = 0;
        ResultSet resSet;
        String insert = "INSERT INTO " + Const.EMPLOYEE_TABLE + " ("
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ") "
                + "VALUES (?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, String.valueOf(lastName));
            prSt.setString(2, String.valueOf(firstName));
            prSt.setString(3, String.valueOf(middleName));

            prSt.executeUpdate();
            resSet = prSt.getGeneratedKeys();

            if (resSet.next())
                lastID = resSet.getInt(1);

            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return lastID;
        }
    }

    public void deleteEmployee(int id){
        String delete = "DELETE FROM "
                + Const.EMPLOYEE_TABLE
                + " WHERE "
                + Const.EMPLOYEE_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setString(1, String.valueOf(id));


            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployee(Employee employee){
        String update = "UPDATE " + Const.EMPLOYEE_TABLE
                + " SET "
                + Const.EMPLOYEE_LASTNAME + "=?, "
                + Const.EMPLOYEE_FIRSTNAME + "=?, "
                + Const.EMPLOYEE_MIDDLENAME + "=? "
                + " WHERE "
                + Const.EMPLOYEE_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(update)) {
            prSt.setString(1, employee.getLastName());
            prSt.setString(2, employee.getFirstName());
            prSt.setString(3, employee.getMiddleName());
            prSt.setString(4, String.valueOf(employee.getID()));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Employee> getListOfMasters(){
        ResultSet resSet;
        ArrayList<Employee> listMasters = new ArrayList<>();

        String select = "SELECT "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ", "
                + Const.EMPLOYEE_ID
                + " FROM "
                + Const.EMPLOYEE_TABLE + ", "
                + Const.EMPLOYEE_HAS_POSITION_TABLE
                + " WHERE "
                + Const.EMPLOYEE_HAS_POSITION_POSITION_ID + "=?" + " AND "
                + Const.EMPLOYEE_HAS_POSITION_EMPLOYEE_ID + "=" + Const.EMPLOYEE_ID;


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(3));
            resSet = prSt.executeQuery();

            while(resSet.next()) {
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID));
                listMasters.add(employee);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return listMasters;
        }
    }

    public ArrayList<Employee> getListOfWorkers(Employee empl){
        ResultSet resSet;
        ArrayList<Employee> listWorkers = new ArrayList<>();

        String select = "SELECT "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ", "
                + Const.EMPLOYEE_ID
                + " FROM "
                + Const.EMPLOYEE_TABLE + ", "
                + Const.TEAM_TABLE
                + " WHERE "
                + Const.TEAM_MASTER_ID + "=?" + " AND "
                + Const.TEAM_WORKER_ID + "=" + Const.EMPLOYEE_ID;


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(empl.getID()));
            resSet = prSt.executeQuery();

            while(resSet.next()) {
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID));
                listWorkers.add(employee);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return listWorkers;
        }
    }

    public void addWorkerToMaster(Employee master, Employee worker){
        String insert = "INSERT INTO " + Const.TEAM_TABLE + " ("
                + Const.TEAM_MASTER_ID + ", "
                + Const.TEAM_WORKER_ID + ") "
                + "VALUES (?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(master.getID()));
            prSt.setString(2, String.valueOf(worker.getID()));

            prSt.executeUpdate();

            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkerFromMaster(Employee master, Employee worker){
        String delete = "DELETE FROM "
                + Const.TEAM_TABLE
                + " WHERE "
                + Const.TEAM_MASTER_ID + "=?" + " AND "
                + Const.TEAM_WORKER_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setString(1, String.valueOf(master.getID()));
            prSt.setString(2, String.valueOf(worker.getID()));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // p = 2 for engineer
    // p = 3 for master
    public ArrayList<Employee> getListOfEmployeesByPosition(int p){
        ResultSet resSet;
        ArrayList<Employee> listWorkers = new ArrayList<>();

        String select = "SELECT "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ", "
                + Const.EMPLOYEE_ID
                + " FROM "
                + Const.EMPLOYEE_TABLE + ", "
                + Const.EMPLOYEE_HAS_POSITION_TABLE
                + " WHERE "
                + Const.EMPLOYEE_HAS_POSITION_POSITION_ID + "=?" + " AND "
                + Const.EMPLOYEE_ID+ "=" + Const.EMPLOYEE_HAS_POSITION_EMPLOYEE_ID;


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(p));
            resSet = prSt.executeQuery();

            while(resSet.next()) {
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID));
                listWorkers.add(employee);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return listWorkers;
        }
    }

    // p = 2 for engineer
    // p = 3 for master
    public ArrayList<QuotTable> getTableQuot(Order order, int p){
        ResultSet resSet;
        ArrayList<QuotTable> listQuots = new ArrayList<>();

        String select = "SELECT "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ", "
                + Const.EMPLOYEE_ID + ", "
                + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + ", "
                + Const.HOUR_PER_MONTH_HOURS
                + " FROM "
                + Const.EMPLOYEE_TABLE + ", "
                + Const.QOUTS_TABLE + ", "
                + Const.HOUR_PER_MONTH_TABLE + ", "
                + Const.EMPLOYEE_HAS_POSITION_TABLE
                + " WHERE "
                + Const.HOUR_PER_MONTH_QUOT_ID + " = " + Const.QOUTS_ID + " AND "
                + Const.EMPLOYEE_ID + " = " + Const.QOUTS_EMPLOYEE_ID + " AND "
                + Const.EMPLOYEE_HAS_POSITION_EMPLOYEE_ID + " = " + Const.EMPLOYEE_ID + " AND "
                + Const.QOUTS_ORDER_ID + " = ? AND "
                + Const.EMPLOYEE_HAS_POSITION_POSITION_ID + " = ?";


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(order.getId()));
            prSt.setString(2, String.valueOf(p));

            resSet = prSt.executeQuery();
            boolean flag;
            while(resSet.next()) {
                flag = true;
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID));

                for(QuotTable qt: listQuots){
                    if(qt.getEmployee().equals(employee)){
                        qt.setHoursOnMonth(resSet.getInt(Const.HOUR_PER_MONTH_QUOT_NUM_MONTH),resSet.getInt(Const.HOUR_PER_MONTH_HOURS));
                        flag = false;
                        break;
                    }
                }

                if(flag){
                    QuotTable newQuot = new QuotTable (employee);
                    newQuot.setHoursOnMonth(resSet.getInt(Const.HOUR_PER_MONTH_QUOT_NUM_MONTH),resSet.getInt(Const.HOUR_PER_MONTH_HOURS));
                    listQuots.add(newQuot);
                }
            }

            resSet.close();


        } catch (SQLException e) {
                e.printStackTrace();
        } catch (ClassNotFoundException e) {
                e.printStackTrace();
        } finally {
            return listQuots;
        }
    }

    public void addQuot(int orderId, int employeeId, int numMonth, int hours){
        //--------------------------------------------------------------------------
        //add to Quot Table
        int quotID = 0;
        ResultSet resSet;
        String insert = "INSERT INTO " + Const.QOUTS_TABLE + " ("
                + Const.QOUTS_ORDER_ID + ", "
                + Const.QOUTS_EMPLOYEE_ID + ") "
                + "VALUES (?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, String.valueOf(orderId));
            prSt.setString(2, String.valueOf(employeeId));

            prSt.executeUpdate();
            resSet = prSt.getGeneratedKeys();

            if (resSet.next())
                quotID = resSet.getInt(1);


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        insertHoursPerMonth(numMonth, hours, quotID);

    }

    public void updateHourPerMonth(int orderId, int employeeId, int numMonth, int hours){
        String update = "UPDATE " + Const.HOUR_PER_MONTH_TABLE
                + " SET "
                + Const.HOUR_PER_MONTH_HOURS + "=? "
                + " WHERE "
                + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + "=? AND "
                + Const.HOUR_PER_MONTH_QUOT_ID + "= (SELECT "
                + Const.QOUTS_ID
                + " FROM "
                + Const.QOUTS_TABLE
                + " WHERE "
                + Const.QOUTS_ORDER_ID + "=? AND "
                + Const.QOUTS_EMPLOYEE_ID + "=?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(update)) {
            prSt.setString(1, String.valueOf(hours));
            prSt.setString(2, String.valueOf(numMonth));
            prSt.setString(3, String.valueOf(orderId));
            prSt.setString(4, String.valueOf(employeeId));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addHourPerMonth(int orderId, int employeeId, int numMonth, int hours){
        //--------------------------------------------------------------------------
        //search for quot id
        int quotID = 0;
        ResultSet resSet;

        String select = "SELECT "
                + Const.QOUTS_ID
                + " FROM "
                + Const.QOUTS_TABLE
                + " WHERE "
                + Const.QOUTS_ORDER_ID + "=? AND "
                + Const.QOUTS_EMPLOYEE_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(orderId));
            prSt.setString(2, String.valueOf(employeeId));
            resSet = prSt.executeQuery();

            while(resSet.next()) {
                quotID = resSet.getInt(Const.QOUTS_ID);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        insertHoursPerMonth(numMonth, hours, quotID);

    }

    private void insertHoursPerMonth(int numMonth, int hours, int quotID){
        //--------------------------------------------------------------------------
        //insert to HoursPerMonth
        if(quotID > 0) {
            String insert1 = "INSERT INTO " + Const.HOUR_PER_MONTH_TABLE + " ("
                    + Const.HOUR_PER_MONTH_QUOT_ID + ", "
                    + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + ", "
                    + Const.HOUR_PER_MONTH_HOURS + ") "
                    + "VALUES (?,?,?)";

            try (PreparedStatement prSt = getDbConnection().prepareStatement(insert1)) {
                prSt.setString(1, String.valueOf(quotID));
                prSt.setString(2, String.valueOf(numMonth));
                prSt.setString(3, String.valueOf(hours));

                prSt.executeUpdate();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public int addOrder(Order order, int managerID){
        int orderID = 0;
        ResultSet resSet;
        String insert = "INSERT INTO " + Const.ORDER_TABLE + " ("
                + Const.ORDER_NAME + ", "
                + Const.ORDER_MANAGER_ID + ", "
                + Const.ORDER_ADRESS + ", "
                + Const.ORDER_DERSCRIPTION + ", "
                + Const.ORDER_MAX_HOURS + ") "
                + "VALUES (?,?,?,?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, order.getNameOrder());
            prSt.setString(2, String.valueOf(managerID));
            prSt.setString(3, order.getAddress());
            prSt.setString(4, order.getDescription());
            prSt.setString(5, String.valueOf(order.getHoursMax()));

            prSt.executeUpdate();
            resSet = prSt.getGeneratedKeys();

            if (resSet.next())
                orderID = resSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return orderID;
        }
    }

    public void updateOrder(int orderID, String nameOrder, String description, String address, int maxHours){
        String update = "UPDATE " + Const.ORDER_TABLE
                + " SET "
                + Const.ORDER_NAME + "=?, "
                + Const.ORDER_DERSCRIPTION + "=?, "
                + Const.ORDER_ADRESS + "=?, "
                + Const.ORDER_MAX_HOURS + "=? "
                + " WHERE "
                + Const.ORDER_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(update)) {
            prSt.setString(1, nameOrder);
            prSt.setString(2, description);
            prSt.setString(3, address);
            prSt.setString(4, String.valueOf(maxHours));
            prSt.setString(5, String.valueOf(orderID));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderID){
        String delete = "DELETE FROM "
                + Const.ORDER_TABLE
                + " WHERE "
                + Const.ORDER_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setString(1, String.valueOf(orderID));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<WorkType> getAllWorkType(){
        ArrayList<WorkType> list = new ArrayList<>();
        ResultSet resSet;

        String select = "SELECT "
                + Const.WORK_TYPE_ID + ", "
                + Const.WORK_TYPE_NAME +
                " FROM "
                + Const.WORK_TYPE_TABLE;

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                WorkType newRow = new WorkType(resSet.getInt(Const.WORK_TYPE_ID),resSet.getString(Const.WORK_TYPE_NAME));
                list.add(newRow);
            }

            resSet.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    public void addWorkTypeToOrder(Order order, WorkType workType){
        String insert1 = "INSERT INTO " + Const.ORDER_HAS_WORKTYPE_TABLE + " ("
                + Const.ORDER_HAS_WORKTYPE_ORDER_ID + ", "
                + Const.ORDER_HAS_WORKTYPE_TYPE_ID + ") "
                + "VALUES (?,?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert1)) {
            prSt.setString(1, String.valueOf(order.getId()));
            prSt.setString(2, String.valueOf(workType.getId()));

            prSt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkTypeFromOrder(Order order, WorkType workType){
        String delete = "DELETE FROM "
                + Const.ORDER_HAS_WORKTYPE_TABLE
                + " WHERE "
                + Const.ORDER_HAS_WORKTYPE_ORDER_ID + "=? AND "
                + Const.ORDER_HAS_WORKTYPE_TYPE_ID + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setString(1, String.valueOf(order.getId()));
            prSt.setString(2, String.valueOf(workType.getId()));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Order> getActiveOrders(ArrayList<Order> orders, int managerID){
        ResultSet resSet;
        ArrayList<Order> listActOrders = new ArrayList<>();
        ArrayList<Integer> listOrderID = new ArrayList<>();

        String select = "SELECT DISTINCT "
                + Const.WORK_TIME_ORDER_ID
                + " FROM "
                + Const.WORK_TIME_TABLE
                + " WHERE "
                + Const.WORK_TIME_APPROVAL + "=?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(0));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                int orderID = resSet.getInt(Const.WORK_TIME_ORDER_ID);
                listOrderID.add(orderID);
            }

            resSet.close();

            for(Order o: orders){
                for(Integer i: listOrderID){
                    if(o.getId() == i.intValue()){
                        listActOrders.add(o);
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return listActOrders;
        }
    }

    //positionID = 3 - master
    //positionID = 2 - engineer
    //return list of employees who written in quotas
    public ArrayList<Employee> getEmployeeOnOrder(int positionID, int orderID, int numMonth){
        ResultSet resSet;
        ArrayList<Employee> listWorkers = new ArrayList<>();

        String select = "SELECT "
                + Const.EMPLOYEE_LASTNAME + ", "
                + Const.EMPLOYEE_FIRSTNAME + ", "
                + Const.EMPLOYEE_MIDDLENAME + ", "
                + Const.EMPLOYEE_ID
                + " FROM "
                + Const.EMPLOYEE_TABLE + ", "
                + Const.EMPLOYEE_HAS_POSITION_TABLE + ", "
                + Const.QOUTS_TABLE + ", "
                + Const.HOUR_PER_MONTH_TABLE
                + " WHERE "
                + Const.EMPLOYEE_HAS_POSITION_POSITION_ID + "=?" + " AND "
                + Const.EMPLOYEE_ID + "=" + Const.EMPLOYEE_HAS_POSITION_EMPLOYEE_ID + " AND "
                + Const.QOUTS_ORDER_ID + "=? AND "
                + Const.QOUTS_EMPLOYEE_ID + "=" + Const.EMPLOYEE_ID + " AND "
                + Const.HOUR_PER_MONTH_QUOT_ID + "=" + Const.QOUTS_ID + " AND "
                + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + "=?";


        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(positionID));
            prSt.setString(2, String.valueOf(orderID));
            prSt.setString(3, String.valueOf(numMonth));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                Employee employee = new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME),
                        resSet.getString(Const.EMPLOYEE_FIRSTNAME),
                        resSet.getString(Const.EMPLOYEE_MIDDLENAME),
                        resSet.getInt(Const.EMPLOYEE_ID));
                listWorkers.add(employee);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            return listWorkers;
        }
    }

    public int getHoursInQuotas(int employeeID, int orderID, int numMonth){
        ResultSet resSet;
        int hours = 0;

        String select = "SELECT "
                + Const.HOUR_PER_MONTH_HOURS
                + " FROM "
                + Const.HOUR_PER_MONTH_TABLE
                + " WHERE "
                + Const.HOUR_PER_MONTH_QUOT_NUM_MONTH + "=? AND "
                + Const.HOUR_PER_MONTH_QUOT_ID + "= (SELECT "
                + Const.QOUTS_ID
                + " FROM "
                + Const.QOUTS_TABLE
                + " WHERE "
                + Const.QOUTS_ORDER_ID + "=? AND "
                + Const.QOUTS_EMPLOYEE_ID + "=?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(numMonth));
            prSt.setString(2, String.valueOf(orderID));
            prSt.setString(3, String.valueOf(employeeID));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                hours = resSet.getInt(Const.HOUR_PER_MONTH_HOURS);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return hours;
        }
    }

    //p = 2 - engineer
    //p = 3 - master
    public ArrayList<Integer> getListOfDaysForApproval(Order order, int employeeID, int numMonth, int p){
        ResultSet resSet;
        int orderID = order.getId();
        ArrayList<Integer> list = new ArrayList<>();
        String select;

        if(p == 3) {
            select = "SELECT DISTINCT "
                    + Const.WORK_TIME_NUM_DAY
                    + " FROM "
                    + Const.WORK_TIME_TABLE + ", "
                    + Const.TEAM_TABLE
                    + " WHERE "
                    + Const.WORK_TIME_ORDER_ID + "=? AND "
                    + Const.TEAM_MASTER_ID + "=? AND "
                    + Const.WORK_TIME_EMPLOYEE_ID + "=" + Const.TEAM_WORKER_ID + " AND "
                    + Const.WORK_TIME_NUM_MONTH + "=? AND "
                    + Const.WORK_TIME_APPROVAL + "=0";
        }else{
            select = "SELECT "
                    + Const.WORK_TIME_NUM_DAY
                    + " FROM "
                    + Const.WORK_TIME_TABLE
                    + " WHERE "
                    + Const.WORK_TIME_ORDER_ID + "=? AND "
                    + Const.WORK_TIME_EMPLOYEE_ID + "=? AND "
                    + Const.WORK_TIME_NUM_MONTH + "=? AND "
                    + Const.WORK_TIME_APPROVAL + "=0";
        }

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(orderID));
            prSt.setString(2, String.valueOf(employeeID));
            prSt.setString(3, String.valueOf(numMonth));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                int numDay = resSet.getInt(1);
                list.add(numDay);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return list;
        }
    }

    //p = 2 - engineer
    //p = 3 - master
    public ArrayList<WR_Table> getWorkTable(int orderID, int employeeID, int numMonth, int numDay, int p){
        ResultSet resSet;

        ArrayList<WR_Table> listForWorkTable = new ArrayList<>();

        String select;

        if(p == 3) {
            select = "SELECT "
                    + Const.EMPLOYEE_LASTNAME + ", "
                    + Const.EMPLOYEE_FIRSTNAME + ", "
                    + Const.EMPLOYEE_MIDDLENAME + ", "
                    + Const.EMPLOYEE_ID + ", "
                    + Const.WORK_TYPE_NAME + ", "
                    + Const.WORK_TYPE_ID + ", "
                    + Const.WORK_TIME_NUM_MONTH + ", "
                    + Const.WORK_TIME_NUM_DAY + ", "
                    + Const.WORK_TIME_WORK_TIME + ", "
                    + Const.WORK_TIME_OVER_TIME
                    + " FROM "
                    + Const.EMPLOYEE_TABLE + ", "
                    + Const.WORK_TYPE_TABLE + ", "
                    + Const.WORK_TIME_TABLE + ", "
                    + Const.TEAM_TABLE
                    + " WHERE "
                    + Const.EMPLOYEE_ID + " = " + Const.WORK_TIME_EMPLOYEE_ID + " AND "
                    + Const.WORK_TYPE_ID + " = " + Const.WORK_TIME_WORK_TYPE_ID + " AND "
                    + Const.TEAM_WORKER_ID + " = " + Const.EMPLOYEE_ID + " AND "
                    + Const.TEAM_MASTER_ID + " =?" + " AND "
                    + Const.WORK_TIME_ORDER_ID + " =?" + " AND "
                    + Const.WORK_TIME_NUM_MONTH + " =?" + " AND "
                    + Const.WORK_TIME_NUM_DAY + " =?" + " AND "
                    + Const.WORK_TIME_APPROVAL + " =0";
        }else{
            select = "SELECT "
                    + Const.EMPLOYEE_LASTNAME + ", "
                    + Const.EMPLOYEE_FIRSTNAME + ", "
                    + Const.EMPLOYEE_MIDDLENAME + ", "
                    + Const.EMPLOYEE_ID + ", "
                    + Const.WORK_TYPE_NAME + ", "
                    + Const.WORK_TYPE_ID + ", "
                    + Const.WORK_TIME_NUM_MONTH + ", "
                    + Const.WORK_TIME_NUM_DAY + ", "
                    + Const.WORK_TIME_WORK_TIME + ", "
                    + Const.WORK_TIME_OVER_TIME
                    + " FROM "
                    + Const.EMPLOYEE_TABLE + ", "
                    + Const.WORK_TYPE_TABLE + ", "
                    + Const.WORK_TIME_TABLE
                    + " WHERE "
                    + Const.EMPLOYEE_ID + " = " + Const.WORK_TIME_EMPLOYEE_ID + " AND "
                    + Const.WORK_TYPE_ID + " = " + Const.WORK_TIME_WORK_TYPE_ID + " AND "
                    + Const.EMPLOYEE_ID + " =?" + " AND "
                    + Const.WORK_TIME_ORDER_ID + " =?" + " AND "
                    + Const.WORK_TIME_NUM_MONTH + " =?" + " AND "
                    + Const.WORK_TIME_NUM_DAY + " =?" + " AND "
                    + Const.WORK_TIME_APPROVAL + " =0";

        }

        try (PreparedStatement prSt = getDbConnection().prepareStatement(select)) {
            prSt.setString(1, String.valueOf(employeeID));
            prSt.setString(2, String.valueOf(orderID));
            prSt.setString(3, String.valueOf(numMonth));
            prSt.setString(4, String.valueOf(numDay));

            resSet = prSt.executeQuery();

            while(resSet.next()) {
                WR_Table newRow = new WR_Table(new Employee(resSet.getString(Const.EMPLOYEE_LASTNAME), resSet.getString(Const.EMPLOYEE_FIRSTNAME), resSet.getString(Const.EMPLOYEE_MIDDLENAME), resSet.getInt(Const.EMPLOYEE_ID)),
                        new WorkType(resSet.getInt(Const.WORK_TYPE_ID),resSet.getString(Const.WORK_TYPE_NAME)),
                        resSet.getInt(Const.WORK_TIME_NUM_MONTH),
                        resSet.getInt(Const.WORK_TIME_NUM_DAY),
                        resSet.getInt(Const.WORK_TIME_WORK_TIME),
                        resSet.getInt(Const.WORK_TIME_OVER_TIME));
                listForWorkTable.add(newRow);
            }

            resSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return listForWorkTable;
        }
    }

    public void updateWorkTableApprove(WR_Table newRecord, Order order){
        int orderID = order.getId();
        int employeeID = newRecord.getEmployee().getID();
        int numMonth = newRecord.getNumMonth();
        int numDay = newRecord.getNumDay();
        int workTime = newRecord.getWorkHours();
        int overWork = newRecord.getOverWorkHours();

        String insert = "UPDATE " + Const.WORK_TIME_TABLE
                + " SET "
                + Const.WORK_TIME_WORK_TIME + "=?, "
                + Const.WORK_TIME_OVER_TIME + "=?, "
                + Const.WORK_TIME_APPROVAL + "=1 "
                + " WHERE "
                + Const.WORK_TIME_ORDER_ID + " =?" + " AND "
                + Const.WORK_TIME_EMPLOYEE_ID + " =?" + " AND "
                + Const.WORK_TIME_NUM_MONTH + " =?" + " AND "
                + Const.WORK_TIME_NUM_DAY + " =?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(workTime));
            prSt.setString(2, String.valueOf(overWork));
            prSt.setString(3, String.valueOf(orderID));
            prSt.setString(4, String.valueOf(employeeID));
            prSt.setString(5, String.valueOf(numMonth));
            prSt.setString(6, String.valueOf(numDay));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int addWorkType(String nameWorkType){
        int workTypeID = 0;
        ResultSet resSet;
        String insert = "INSERT INTO " + Const.WORK_TYPE_TABLE + " ("
                + Const.WORK_TYPE_NAME + ") "
                + "VALUES (?)";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert, Statement.RETURN_GENERATED_KEYS)) {
            prSt.setString(1, nameWorkType);

            prSt.executeUpdate();
            resSet = prSt.getGeneratedKeys();

            if (resSet.next())
                workTypeID = resSet.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return workTypeID;
        }
    }

    public void updateWorkType(String nameType, WorkType wt){
        int id = wt.getId();
        String insert = "UPDATE " + Const.WORK_TYPE_TABLE
                + " SET "
                + Const.WORK_TYPE_NAME + "=1 "
                + " WHERE "
                + Const.WORK_TYPE_ID+ " =?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(insert)) {
            prSt.setString(1, String.valueOf(id));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteWorkType(WorkType wt){
        int id = wt.getId();
        String delete = "DELETE FROM "
                + Const.WORK_TYPE_TABLE
                + " WHERE "
                + Const.WORK_TYPE_ID+ " =?";

        try (PreparedStatement prSt = getDbConnection().prepareStatement(delete)) {
            prSt.setString(1, String.valueOf(id));

            prSt.executeUpdate();
            prSt.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
