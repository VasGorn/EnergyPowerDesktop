package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.*;

import java.util.ArrayList;
import java.util.Iterator;

public class WorkApproval {
    @FXML
    private ComboBox<Order> cbNameOrder;

    @FXML
    private ComboBox<String> cbPosition;

    @FXML
    private ComboBox<Employee> cbHead;

    @FXML
    private ComboBox<Integer> cbWorkHours;

    @FXML
    private ComboBox<Integer> cbOverHours;

    @FXML
    private ComboBox<Integer> cbDayMonth;

    @FXML
    private Label lbAddress;

    @FXML
    private Label lbDescription;

    @FXML
    private Label lbManager;


    @FXML
    private TextField tfMaxHours;

    @FXML
    private TextField tfCurrHours;

    @FXML
    private TextField tfLeftHours;

    @FXML
    private TextField tfWorker;

    @FXML
    private TextField tfWorkType;

    //-----------------------------------------------------------------------------------------------------------------

    @FXML
    private TableView wrightTable;

    @FXML
    private TableColumn<WR_Table, Employee> colW_Employee;

    @FXML
    private TableColumn<WR_Table, WorkType> colW_WorkType;

    @FXML
    private TableColumn<WR_Table, Integer> colW_NumDay, colW_WorkHours, colW_OverHours;

    //-----------------------------------------------------------------------------------------------------------------



    private ObservableList<WR_Table> observListForWrightTable = FXCollections.observableArrayList();
    private ObservableList<WorkTable> listForMonthWork = FXCollections.observableArrayList();
    private DatabaseHandler dbHandler = new DatabaseHandler();

    private int leftHours;

    @FXML
    private void initialize(){
        ArrayList<Order> listActiveOrder = dbHandler.getActiveOrders(User.getListOrdersManager(),User.getEmployeeID());

        for (Order o: listActiveOrder)
            cbNameOrder.getItems().add(o);

        //EVENT FOR CHANGING ORDER COMBOBOX!!!!
        cbNameOrder.setOnAction(event -> {
            leftHours = 0;
            observListForWrightTable.clear();


            lbAddress.setText(cbNameOrder.getValue().getAddress());
            lbDescription.setText(cbNameOrder.getValue().getDescription());
            lbManager.setText(cbNameOrder.getValue().getManagerName());

            tfMaxHours.setText(cbNameOrder.getValue().getStringMaxHours());

            cbHead.getItems().clear();
            cbDayMonth.getItems().clear();
            //------------------------------------------------------------------------------------------

        });

        cbPosition.getItems().add("Инженер");
        cbPosition.getItems().add("Мастер");

        //EVENT FOR CHANGING POSITION COMBOBOX!!!!
        cbPosition.setOnAction(event -> {
            if ( cbNameOrder.getValue() != null) {
                if (cbPosition.getValue().equals("Мастер")) {
                    ArrayList<Employee> listActiveEmployee = dbHandler.getEmployeeOnOrder(3, cbNameOrder.getValue().getId(), ServerDate.getNumMonth());
                    cbHead.getItems().clear();
                    for (Employee e : listActiveEmployee)
                        cbHead.getItems().add(e);
                }

                if (cbPosition.getValue().equals("Инженер")) {
                    ArrayList<Employee> listActiveEmployee = dbHandler.getEmployeeOnOrder(2, cbNameOrder.getValue().getId(), ServerDate.getNumMonth());
                    cbHead.getItems().clear();
                    for (Employee e : listActiveEmployee)
                        cbHead.getItems().add(e);
                }
                tfCurrHours.setText("");
                cbDayMonth.getItems().clear();

            }
        });

        //EVENT FOR CHANGING HEAD EMPLOYEE COMBOBOX!!!!
        cbHead.setOnAction(event -> {
            if (cbHead.getValue() != null && cbNameOrder.getValue() != null) {
                //get current hours in quotas
                int hoursInQuotas = dbHandler.getHoursInQuotas(cbHead.getValue().getID(),cbNameOrder.getValue().getId(),ServerDate.getNumMonth());
                tfCurrHours.setText(String.valueOf(hoursInQuotas));

                int position = 3;
                if (cbPosition.getValue().equals("Инженер")) {
                    position = 2;
                }

                //get sum of working hours
                int sumWorkHours = dbHandler.getSumWorkHoursOnOrders(cbNameOrder.getValue(),cbHead.getValue().getID(),position);

                leftHours = hoursInQuotas - sumWorkHours;

                tfLeftHours.setText(String.valueOf(leftHours));

                //get number of days for approval
                ArrayList<Integer> listOfNumDays = dbHandler.getListOfDaysForApproval(cbNameOrder.getValue(),cbHead.getValue().getID(),ServerDate.getNumMonth(),position);

                cbDayMonth.getItems().clear();

                for(Integer i: listOfNumDays)
                    cbDayMonth.getItems().add(i);


            }

        });

        //EVENT FOR CHANGING NUMBER OF MONTH COMBOBOX!!!!
        cbDayMonth.setOnAction(event -> {
            if (cbDayMonth.getValue() != null) {
                int position = 3;
                if (cbPosition.getValue().equals("Инженер")) {
                    position = 2;
                }

                ArrayList<WR_Table> listToApproval = dbHandler.getWorkTable(cbNameOrder.getValue().getId(),
                        cbHead.getValue().getID(),
                        ServerDate.getNumMonth(),
                        cbDayMonth.getValue(),
                        position);
                observListForWrightTable.clear();
                observListForWrightTable.setAll(listToApproval);

                tfWorker.setText("");
                tfWorkType.setText("");
            }
        });

        setIntegersOnComboBox(cbWorkHours,1,8,8);
        setIntegersOnComboBox(cbOverHours,0,12,0);

        //Table for writing
        colW_Employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colW_WorkType.setCellValueFactory(new PropertyValueFactory<>("workType"));
        colW_NumDay.setCellValueFactory(new PropertyValueFactory<>("numDay"));
        colW_WorkHours.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        colW_OverHours.setCellValueFactory(new PropertyValueFactory<>("overWorkHours"));

        wrightTable.setItems(observListForWrightTable);

    }

    private void setIntegersOnComboBox(ComboBox<Integer> cb, int iStart, int iStop, int setValue){
        for(int i = iStart; i <= iStop; i++)
            cb.getItems().add(i);
        cb.setValue(setValue);

    }


    public void tvClicked(){
        Object selected = wrightTable.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            Employee employee = ((WR_Table) selected).getEmployee();
            WorkType workType = ((WR_Table) selected).getWorkType();

            tfWorker.setText(employee.toString());
            tfWorkType.setText(workType.toString());

            cbWorkHours.setValue(((WR_Table) selected).getWorkHours());
            cbOverHours.setValue(((WR_Table) selected).getOverWorkHours());
        }
    }

    public void btnReplaceClicked(){
        Object selected = wrightTable.getSelectionModel().getSelectedItem();
        if(!(selected==null)){

            Iterator<WR_Table> it = observListForWrightTable.iterator();
            while(it.hasNext()){
                WR_Table sel = it.next();
                if( sel == selected){
                    sel.setWorkHours(cbWorkHours.getValue());
                    sel.setOverWorkHours(cbOverHours.getValue());
                }
            }

            wrightTable.refresh();
        }
    }

    public void btnApproveClicked(){
        Object selected = wrightTable.getSelectionModel().getSelectedItem();
        if(!(selected==null)){

            Iterator<WR_Table> it = observListForWrightTable.iterator();
            while(it.hasNext()){
                WR_Table sel = it.next();
                if( sel == selected){
                    dbHandler.updateWorkTableApprove(sel,cbNameOrder.getValue());
                    it.remove();

                    tfWorker.setText("");
                    tfWorkType.setText("");
                }
            }

            wrightTable.refresh();
        }
    }

    public void btnApproveAllClicked(){
        if(observListForWrightTable.get(0) != null){

            Iterator<WR_Table> it = observListForWrightTable.iterator();
            while(it.hasNext()){
                WR_Table sel = it.next();
                dbHandler.updateWorkTableApprove(sel,cbNameOrder.getValue());
                it.remove();

                tfWorker.setText("");
                tfWorkType.setText("");
            }

            wrightTable.refresh();
        }
    }
}
