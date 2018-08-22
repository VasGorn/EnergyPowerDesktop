package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import model.Employee;
import model.UserTable;

import java.util.ArrayList;

public class UserWindow {
    @FXML
    private ComboBox<Employee> cbAllEmployee;

    @FXML
    private CheckBox cbMaster;

    @FXML
    private CheckBox cbEngineer;

    @FXML
    private CheckBox cbManager;

    @FXML
    private TableView<UserTable> tvUserTable;

    @FXML
    private TableColumn<UserTable, Employee> colEmployee;

    @FXML
    private TableColumn<UserTable, String> colUser;

    @FXML
    private TableColumn<UserTable, CheckBox> colIsMaster;

    @FXML
    private TableColumn<UserTable, CheckBox> colIsEngineer;

    @FXML
    private TableColumn<UserTable, CheckBox> colIsManager;

    private ObservableList<UserTable> list = FXCollections.observableArrayList();
    private DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    private void initialize(){
        ArrayList<Employee> allEmployee;
        allEmployee = dbHandler.getAllEmployeeList();

        for(Employee e: allEmployee)
            cbAllEmployee.getItems().add(e);

        for(int i=0; i < 5;i++){
            CheckBox ch = new CheckBox();
            ch.setSelected(true);
            ch.setDisable(true);
            ch.setOpacity(1);
            UserTable newUT = new UserTable(allEmployee.get(i),i,"nick"+i);
            newUT.setCheckBoxIsManager(ch);
            list.add(newUT);
        }

        colEmployee.setCellValueFactory(new PropertyValueFactory<UserTable, Employee>("employee"));
        colUser.setCellValueFactory(new PropertyValueFactory<UserTable, String>("nameUser"));
        colIsMaster.setCellValueFactory(new PropertyValueFactory<UserTable, CheckBox>("checkBoxIsMaster"));
        colIsEngineer.setCellValueFactory(new PropertyValueFactory<UserTable, CheckBox>("checkBoxIsEngineer"));
        colIsManager.setCellValueFactory(new PropertyValueFactory<UserTable, CheckBox>("checkBoxIsManager"));


        tvUserTable.setItems(list);
    }

    @FXML
    private void cbMasterClicked(){
        if(cbMaster.isSelected()){
            cbEngineer.setSelected(false);
            cbManager.setSelected(false);
        }
    }

}
