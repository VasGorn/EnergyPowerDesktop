package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Employee;

import java.util.ArrayList;
import java.util.Iterator;

public class Team {
    private DatabaseHandler dbHandler;
    private ObservableList<Employee> obsListAllEmployee;
    private ObservableList<Employee> obsListTeamEmployee;

    @FXML
    private ComboBox<Employee> cbMasters;

    @FXML
    private TableView tableAllEmployee;

    @FXML
    private TableColumn<Employee, String> colAllNameEmployee;

    @FXML
    private TableColumn<Employee, Integer> colAllEmployeeID;

    //------------------------------------------------------------------

    @FXML
    private TableView tableTeamEmployee;

    @FXML
    private TableColumn<Employee, String> colTeamNameEmployee;

    @FXML
    private TableColumn<Employee, Integer> colTeamEmployeeID;

    @FXML
    private void initialize(){
        dbHandler = new DatabaseHandler();
        //ComboBox - masters
        ArrayList<Employee> listMasters = dbHandler.getListOfMasters();
        for(Employee e: listMasters)
            cbMasters.getItems().add(e);

        cbMasters.setOnAction(event -> {
            ArrayList<Employee> listWorkers = dbHandler.getListOfWorkers(cbMasters.getValue());
            obsListTeamEmployee = FXCollections.observableArrayList(listWorkers);
            tableTeamEmployee.setItems(obsListTeamEmployee);
        });
        //----------------------------------------------------------------------------------------

        ArrayList<Employee> employeesList;
        employeesList = dbHandler.getAllEmployeeList();
        obsListAllEmployee = FXCollections.observableArrayList(employeesList);

        //property for "All" columns
        colAllNameEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAllEmployeeID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //assign list to "All" table
        tableAllEmployee.setItems(obsListAllEmployee);


        //----------------------------------------------------------------------------------------
        //property for "team" columns
        colTeamNameEmployee.setCellValueFactory(new PropertyValueFactory<>("name"));
        colTeamEmployeeID.setCellValueFactory(new PropertyValueFactory<>("ID"));

        //assign list to "team" table
        //tableTeamEmployee.setItems(obsListTeamEmployee);
    }

    public void btnAddClicked(){
        Object selected = tableAllEmployee.getSelectionModel().getSelectedItem();
        ObservableList<Employee> obsListTeam = tableTeamEmployee.getItems();
        if(selected != null){
            for(Employee e: obsListTeam) {
                if (e.equals(selected))
                    return;
            }
        }
        dbHandler.addWorkerToMaster(cbMasters.getValue(),(Employee)selected);
        obsListTeamEmployee.add((Employee)selected);
    }

    public void btnDeleteClicked(){
        Object selected = tableTeamEmployee.getSelectionModel().getSelectedItem();
        ObservableList<Employee> obsListTeam = tableTeamEmployee.getItems();
        if(selected != null){
            Iterator<Employee> it = obsListTeam.iterator();
            while(it.hasNext()){
                Employee sel = it.next();
                if( sel.equals(selected)){
                    dbHandler.deleteWorkerFromMaster(cbMasters.getValue(),(Employee)selected);
                    it.remove();
                }
            }
        }
    }

}
