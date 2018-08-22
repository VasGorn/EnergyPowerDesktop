package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.User;

import java.io.IOException;

public class MainStage {
    @FXML
    private Button btnWorkTimeStage;

    @FXML
    private Button btnWorkApproval;

    @FXML
    private Button btnQuot;

    @FXML
    private Button btnEmployeeList;

    @FXML
    private Button btnTeam;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnWorkType;


    @FXML
    private void initialize(){
        if(User.isMaster()){
            btnWorkTimeStage.setDisable(false);
        }

        if(User.isManager()){
            btnWorkApproval.setDisable(false);
            btnQuot.setDisable(false);
        }

        if(User.isEngineer()){
            btnWorkTimeStage.setDisable(false);
        }

        if(User.isAdmin()){
            btnEmployeeList.setDisable(false);
            btnTeam.setDisable(false);
            btnUsers.setDisable(false);
            btnWorkType.setDisable(false);
        }
    }


    @FXML
    public void btnWorkTimeStageClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/WorkTime.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("WriteWorkTime");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    @FXML
    public void btnEmployeeListClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/EmployeeList.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("EmployeeList");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    @FXML
    public void btnTeamClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/Team.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("Team");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    @FXML
    public void btnQuotClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/Quot.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("Quotas");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    public void btnWorkApprovalClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/WorkApproval.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("WorkApproval");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    public void btnWorkTypeClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/WorkType.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("WorkType");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();

        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    public void btnUsersClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/Users.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("Users");
            workTimeStage.setScene(new Scene(root));
            workTimeStage.initModality(Modality.APPLICATION_MODAL);
            workTimeStage.initOwner(btnWorkTimeStage.getScene().getWindow());
            workTimeStage.showAndWait();
        }
        catch (IOException a){
            a.printStackTrace();
        }
    }
}
