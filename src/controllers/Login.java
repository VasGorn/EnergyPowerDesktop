package controllers;

import database.DatabaseHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Employee;
import model.Order;
import model.User;

import java.io.IOException;
import java.util.ArrayList;

public class Login {

    @FXML
    private Button btClose;

    @FXML
    private Button btnEnter;

    @FXML
    private Label labelWrong;

    @FXML
    private TextField tfUsername;

    @FXML
    private PasswordField tfPassword;


    @FXML
    public void closeLogin(){
        System.exit(0);
    }

    @FXML
    public void logIn(){
        String user = tfUsername.getText().trim();
        String password = tfPassword.getText().trim();

        //String truePassword = newListOfUsers.findPassword(user);

        if(!user.equals("") && !password.equals("")){
            logInAction(user, password);
        }
        else {
            labelWrong.setText("Error: empty some text fields!");
            labelWrong.setVisible(true);
        }

    }

    private void logInAction(String userTF, String passwordTF) {
        DatabaseHandler dbHandler = new DatabaseHandler();
        User.setUserName(userTF);
        User.setPassword(passwordTF);
        int result = dbHandler.getUser();


        if(result>0) {
            dbHandler.setServerDate();
            dbHandler.setNameForEmployee();
            dbHandler.setListOfPositionForUser();

            ArrayList<String> positionList = User.getPosition();
            boolean flagManager = false;
            boolean flagIngineer = false;
            boolean flagMaster = false;
            for(String s: positionList){
                if(s.equals("Менеджер")) flagManager = true;
                else if(s.equals("Инженер")) flagIngineer = true;
                else if(s.equals("Мастер")) flagMaster = true;
            }

            if(flagMaster){
                User.setMaster(true);

                dbHandler.setListOfWorkers();
                dbHandler.setListOfOrdersForMaster();
                dbHandler.setWorkTypeListToOrder(3);
                dbHandler.setListMonthQoutToOrderForMaster();
                dbHandler.setListMonthWorkTable();

            }

            if(flagManager){
                User.setManager(true);

                dbHandler.setListOfOrdersForManager();
                dbHandler.setWorkTypeListToOrder(1);
                //dbHandler.setListMonthQoutToOrderForMaster();
                //dbHandler.setListMonthQoutToOrderForManager();
            }

            if(flagIngineer){
                User.setEngineer(true);

                ArrayList<Employee> self = new ArrayList<>();
                self.add(User.getEmployee());

                User.setWorkers(self);

                dbHandler.setListOfOrdersForMaster();
                dbHandler.setWorkTypeListToOrder(3);

                dbHandler.setListMonthQoutToOrderForMaster();
                dbHandler.setListMonthWorkTable();
            }

            if(User.getUserName().equals("admin")){
                User.setAdmin(true);
            }


//            User.printNameEmployee();
//            User.printListOfPosition();
//            User.printWorkers();
//            User.printQouts();
//            User.printWorkTable();

            Stage stage = (Stage) btnEnter.getScene().getWindow();
            stage.close();

            Parent root;
            try {
                root = FXMLLoader.load(getClass().getResource("../fxml/MainStage.fxml"));
                Stage mainStage = new Stage();
                mainStage.setTitle("Menu");


                Scene scene = new Scene(root);
                scene.getStylesheets().add("css/MainStage.css");

                mainStage.setScene(scene);
                mainStage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            labelWrong.setText("Error: wrong username or password!");
            labelWrong.setVisible(true);
        }


    }
}
