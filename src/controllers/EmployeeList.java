package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;
import model.WR_Table;
import model.WorkTable;

import java.util.ArrayList;
import java.util.Iterator;

public class EmployeeList {
    private DatabaseHandler dbHandler;
    private ObservableList<Employee> obsListEmployee;

    @FXML
    private TextField tfLastName, tfFirstName, tfMiddleName;

    @FXML
    private Button btnAdd, btnEdit, btnDelete;

    @FXML
    private TableView tableEmployee;

    @FXML
    private TableColumn<Employee, String> colNameEmployee;

    @FXML
    private TableColumn<Employee, Integer> colEmployeeID;

    @FXML
    private void initialize(){
        dbHandler = new DatabaseHandler();
        btnEdit.setDisable(true);

        ArrayList<Employee> employeesList;
        employeesList = dbHandler.getAllEmployeeList();
        obsListEmployee = FXCollections.observableArrayList(employeesList);

        //property for columns
        colNameEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmployeeID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //assign list to table
        tableEmployee.setItems(obsListEmployee);


    }

    @FXML
    private void btnAddClicked(){
        String lastName = tfLastName.getText().trim();
        String firstName = tfFirstName.getText().trim();
        String middleName = tfMiddleName.getText().trim();

        if(lastName.equals("") || firstName.equals("") || middleName.equals("")){
            return;
        }
        else{
            int newID = dbHandler.addEmployee(lastName, firstName, middleName);
            Employee newEmployee = new Employee(lastName, firstName, middleName, newID);
            obsListEmployee.add(newEmployee);
        }

    }

    @FXML
    private void btnDeleteClicked(){
        ObservableList allList;
        Object selected = tableEmployee.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            dbHandler.deleteEmployee(((Employee) selected).getID());
        }

        allList = tableEmployee.getItems();

        Iterator<Employee> it = allList.iterator();
        while(it.hasNext()){
            Employee em = it.next();
            if( em == selected){
                it.remove();
            }
        }
    }

    @FXML
    private void btnEditClicked(){
        String lastName = tfLastName.getText().trim();
        String firstName = tfFirstName.getText().trim();
        String middleName = tfMiddleName.getText().trim();
        Object selected = tableEmployee.getSelectionModel().getSelectedItem();

        if(!(selected==null)){
            ((Employee) selected).setLastName(lastName);
            ((Employee) selected).setFirstName(firstName);
            ((Employee) selected).setMiddleName(middleName);
            dbHandler.updateEmployee((Employee) selected);
            tableEmployee.refresh();
        }
    }

    @FXML
    private void btnRightTableClicked(){
        Object selected = tableEmployee.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            String lastName = ((Employee) selected).getLastName();
            String firstName = ((Employee) selected).getFirstName();
            String middleName = ((Employee) selected).getMiddleName();

            tfLastName.setText(lastName);
            tfFirstName.setText(firstName);
            tfMiddleName.setText(middleName);

            btnEdit.setDisable(false);
        }
    }

    @FXML
    private void btnLeftTableClicked(){
        btnEdit.setDisable(true);
    }
}
