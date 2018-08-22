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

public class WorkTime {
    @FXML
    private ComboBox<Order> cbNameOrder;

    @FXML
    private ComboBox<Employee> cbTeam;

    @FXML
    private ComboBox<WorkType> cbTypeWork;

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
    private Label lbDate;

    @FXML
    private TextField tfMaxHours;

    @FXML
    private TextField tfCurrHours;

    @FXML
    private TextField tfLeftHours;


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

    @FXML
    private TableView workTable;

    @FXML
    private TableColumn<WorkTable, Employee> colEmloyee;

    @FXML
    private TableColumn<WorkTable, WorkTable.columnDay>  colDay1,  colDay2,  colDay3,  colDay4,  colDay5,
                                                         colDay6,  colDay7,  colDay8,  colDay9, colDay10,
                                                        colDay11, colDay12, colDay13, colDay14, colDay15,
                                                        colDay16, colDay17, colDay18, colDay19, colDay20,
                                                        colDay21, colDay22, colDay23, colDay24, colDay25,
                                                        colDay26, colDay27, colDay28, colDay29, colDay30,
                                                        colDay31;



    private ArrayList<Order> newListOrders = User.getListOrdersMaster();
    private ArrayList<Employee> newListWorkers = User.getWorkersList();
    private ObservableList<WR_Table> observListForWrightTable = FXCollections.observableArrayList();
    private ObservableList<WorkTable> listForMonthWork = FXCollections.observableArrayList();
    private DatabaseHandler dbHandler = new DatabaseHandler();

    private int leftHours;

    @FXML
    private void initialize(){
        leftHours = 0;

//        for(Order o: User.getListOrdersForMaster()){
//            o.setSumWorkHours(dbHandler.getSumWorkHoursOnOrders(o,User.getEmployeeID()));
//        }

        //getNumOfMonth(User.getDateOnServer())
        for(Order o: newListOrders)
            cbNameOrder.getItems().add(o);

        for(Employee e: newListWorkers)
            cbTeam.getItems().add(e);

        //EVENT FOR CHANGING COMBOBOX!!!!
        cbNameOrder.setOnAction(event -> {
            leftHours = 0;
            observListForWrightTable.clear();

            Order currOrder = cbNameOrder.getValue();

            int workHours = 0;
            if(User.isEngineer()) {
                workHours = dbHandler.getSumWorkHoursOnOrders(currOrder, User.getEmployeeID(), 2);
            }else{
                workHours = dbHandler.getSumWorkHoursOnOrders(currOrder, User.getEmployeeID(), 3);
            }

            currOrder.setSumWorkHours(workHours);

            lbAddress.setText(cbNameOrder.getValue().getAddress());
            lbDescription.setText(cbNameOrder.getValue().getDescription());
            lbManager.setText(cbNameOrder.getValue().getManagerName());
            //------------------------------------------------------------------------------------------
            int currHour = cbNameOrder.getValue().getHoursByMonth(ServerDate.getNumMonth());
            int sumWorkHours = cbNameOrder.getValue().getSumWorkHours();
            leftHours = currHour - sumWorkHours;
            tfMaxHours.setText(cbNameOrder.getValue().getStringMaxHours());

            String strCurrHour = String.valueOf(currHour);
            tfCurrHours.setText(strCurrHour);

            //tfLeftHours.clear();
            tfLeftHours.setText(String.valueOf(leftHours));
            //------------------------------------------------------------------------------------------

            //add work type to combobox
            cbTypeWork.getItems().clear();
            ArrayList<WorkType> workTypeList = cbNameOrder.getValue().getWorkTypeList();
            for(WorkType w: workTypeList)
                cbTypeWork.getItems().add(w);

            //work table from DataBase
            listForMonthWork.clear();
            ArrayList<WR_Table> listWorkTable = cbNameOrder.getValue().getListForWorkTable();
            Iterator<WR_Table> it = listWorkTable.iterator();
            while(it.hasNext()){
                writeToWorkTable(it.next());
            }

        });

        for(int i = 1; i <= 8; i++)
            cbWorkHours.getItems().add(i);

        cbWorkHours.setValue(8);

        for(int i = 0; i <= 12; i++)
            cbOverHours.getItems().add(i);

        cbOverHours.setValue(0);

        for (int i = 1; i <= ServerDate.getMaxDaysInMonth(); i++)
            cbDayMonth.getItems().add(i);

        cbDayMonth.setValue(ServerDate.getDayOfMonth());

        //Table for writing
        colW_Employee.setCellValueFactory(new PropertyValueFactory<>("employee"));
        colW_WorkType.setCellValueFactory(new PropertyValueFactory<>("workType"));
        colW_NumDay.setCellValueFactory(new PropertyValueFactory<>("numDay"));
        colW_WorkHours.setCellValueFactory(new PropertyValueFactory<>("workHours"));
        colW_OverHours.setCellValueFactory(new PropertyValueFactory<>("overWorkHours"));

        wrightTable.setItems(observListForWrightTable);

        lbDate.setText(ServerDate.getNameOfMonth() + ", " + ServerDate.getNumberOfYear() + " г.");

        //TABLE
        colEmloyee.setCellValueFactory(new PropertyValueFactory<>("employee"));

         colDay1.setCellValueFactory(new PropertyValueFactory<>("day1"));   colDay2.setCellValueFactory(new PropertyValueFactory<>("day2"));
         colDay3.setCellValueFactory(new PropertyValueFactory<>("day3"));   colDay4.setCellValueFactory(new PropertyValueFactory<>("day4"));
         colDay5.setCellValueFactory(new PropertyValueFactory<>("day5"));   colDay6.setCellValueFactory(new PropertyValueFactory<>("day6"));
         colDay7.setCellValueFactory(new PropertyValueFactory<>("day7"));   colDay8.setCellValueFactory(new PropertyValueFactory<>("day8"));
         colDay9.setCellValueFactory(new PropertyValueFactory<>("day9"));  colDay10.setCellValueFactory(new PropertyValueFactory<>("day10"));
        colDay11.setCellValueFactory(new PropertyValueFactory<>("day11")); colDay12.setCellValueFactory(new PropertyValueFactory<>("day12"));
        colDay13.setCellValueFactory(new PropertyValueFactory<>("day13")); colDay14.setCellValueFactory(new PropertyValueFactory<>("day14"));
        colDay15.setCellValueFactory(new PropertyValueFactory<>("day15")); colDay16.setCellValueFactory(new PropertyValueFactory<>("day16"));
        colDay17.setCellValueFactory(new PropertyValueFactory<>("day17")); colDay18.setCellValueFactory(new PropertyValueFactory<>("day18"));
        colDay19.setCellValueFactory(new PropertyValueFactory<>("day19")); colDay20.setCellValueFactory(new PropertyValueFactory<>("day20"));
        colDay21.setCellValueFactory(new PropertyValueFactory<>("day21")); colDay22.setCellValueFactory(new PropertyValueFactory<>("day22"));
        colDay23.setCellValueFactory(new PropertyValueFactory<>("day23")); colDay24.setCellValueFactory(new PropertyValueFactory<>("day24"));
        colDay25.setCellValueFactory(new PropertyValueFactory<>("day25")); colDay26.setCellValueFactory(new PropertyValueFactory<>("day26"));
        colDay27.setCellValueFactory(new PropertyValueFactory<>("day27")); colDay28.setCellValueFactory(new PropertyValueFactory<>("day28"));
        colDay29.setCellValueFactory(new PropertyValueFactory<>("day29")); colDay30.setCellValueFactory(new PropertyValueFactory<>("day30"));
        colDay31.setCellValueFactory(new PropertyValueFactory<>("day31"));

        workTable.setItems(listForMonthWork);
    }

    @FXML
    private void addButtonClicked(){
        int sumToAdd = cbWorkHours.getValue() + cbOverHours.getValue();
        int temp;

        for (Object o : observListForWrightTable) {
            if (((WR_Table) o).getEmployee().equals(cbTeam.getValue())) {
                sumToAdd = sumToAdd - ((WR_Table) o).getWorkHours() - ((WR_Table) o).getOverWorkHours();
                temp = leftHours - sumToAdd;

                if(temp >= 0) {
                    leftHours = temp;
                    tfLeftHours.setText(String.valueOf(leftHours));

                    ((WR_Table) o).setWorkType(cbTypeWork.getValue());
                    ((WR_Table) o).setNumDay(cbDayMonth.getValue());
                    ((WR_Table) o).setWorkHours(cbWorkHours.getValue());
                    ((WR_Table) o).setOverWorkHours(cbOverHours.getValue());

                    wrightTable.refresh();
                    return;
                }
            }
        }

        temp = leftHours - sumToAdd;
        if(temp >= 0) {
            leftHours = temp;
            tfLeftHours.setText(String.valueOf(leftHours));
            cbNameOrder.getValue().addToSumWorkHours(sumToAdd);
            WR_Table newRow = new WR_Table(cbTeam.getValue(), cbTypeWork.getValue(),
                    ServerDate.getNumMonth(), cbDayMonth.getValue(),
                    cbWorkHours.getValue(), cbOverHours.getValue());
            observListForWrightTable.add(newRow);
        }
    }

    @FXML
    private void deleteButtonClicked(){
        ObservableList allList;
        Object selected;
        allList = wrightTable.getItems();
        selected = wrightTable.getSelectionModel().getSelectedItem();

        Iterator<WR_Table> it = allList.iterator();
        while(it.hasNext()){
            WR_Table sel = it.next();
            if( sel == selected){
                leftHours = leftHours + sel.getWorkHours() + sel.getOverWorkHours();
                tfLeftHours.setText(String.valueOf(leftHours));
                it.remove();
            }
        }
    }

    @FXML
    private void WriteButtonClicked(){
        ObservableList listToWright;
        listToWright = wrightTable.getItems();
        Order currOrder = cbNameOrder.getValue();
        ArrayList<WR_Table> listInOrder = currOrder.getListForWorkTable();
        boolean flag = false;

        Iterator<WR_Table> it = listToWright.iterator();
        while(it.hasNext()){
            WR_Table newElement = it.next();
            writeToWorkTable(newElement);

            for(WR_Table w: listInOrder){
                if(w.equals(newElement)){
                    w.setWorkType(newElement.getWorkType());
                    w.setWorkHours(newElement.getWorkHours());
                    w.setOverWorkHours(newElement.getOverWorkHours());
                    flag = true;
                    dbHandler.updateWorkTable(newElement,currOrder);
                }
            }

            if(!flag){
                listInOrder.add(newElement);
                dbHandler.addWorkTable(newElement,currOrder);
            }
        }

        listToWright.clear();
    }

    private void writeToWorkTable(WR_Table t){

        for(Object o: listForMonthWork){
            if(((WorkTable)o).getEmployee().equals(t.getEmployee())){
                ((WorkTable) o).setHoursWorkType(t.getNumDay(),t.getWorkHours(),t.getOverWorkHours(),t.getWorkType());
                workTable.refresh();
                return;
            }
        }

        WorkTable newRow = new WorkTable(t.getEmployee());
        newRow.setHoursWorkType(t.getNumDay(),t.getWorkHours(),t.getOverWorkHours(),t.getWorkType());
        listForMonthWork.add(newRow);
    }


//    private ArrayList<WorkTable> getListForTable(){
//        ArrayList<WorkTable> newList = new ArrayList<>();
//        newList.add(new WorkTable(new Employee("bob",1)));
//        newList.add(new WorkTable(new Employee("rachel",2)));
//        newList.add(new WorkTable(new Employee("kael",2)));
//        newList.add(new WorkTable(new Employee("nerd",2)));
//        newList.add(new WorkTable(new Employee("lacky",3)));
//
//        newList.get(0).getDay1().setWorkHours(1); newList.get(0).getDay2().setWorkHours(2);
//        newList.get(0).getDay3().setWorkHours(3); newList.get(0).getDay4().setWorkHours(4);
//        newList.get(0).getDay5().setWorkHours(5); newList.get(0).getDay6().setWorkHours(6);
//        newList.get(0).getDay7().setWorkHours(7); newList.get(0).getDay8().setWorkHours(8);
//        newList.get(0).getDay9().setWorkHours(9); newList.get(0).getDay10().setWorkHours(10);
//        newList.get(0).getDay11().setWorkHours(11); newList.get(0).getDay12().setWorkHours(12);
//        newList.get(0).getDay13().setWorkHours(13); newList.get(0).getDay14().setWorkHours(14);
//        newList.get(0).getDay15().setWorkHours(15); newList.get(0).getDay16().setWorkHours(16);
//        newList.get(0).getDay17().setWorkHours(17); newList.get(0).getDay18().setWorkHours(18);
//        newList.get(0).getDay19().setWorkHours(19); newList.get(0).getDay20().setWorkHours(20);
//        newList.get(0).getDay21().setWorkHours(21); newList.get(0).getDay22().setWorkHours(22);
//        newList.get(0).getDay23().setWorkHours(23); newList.get(0).getDay24().setWorkHours(24);
//        newList.get(0).getDay25().setWorkHours(25); newList.get(0).getDay26().setWorkHours(26);
//        newList.get(0).getDay27().setWorkHours(27); newList.get(0).getDay28().setWorkHours(28);
//        newList.get(0).getDay29().setWorkHours(29); newList.get(0).getDay30().setWorkHours(30);
//        newList.get(0).getDay31().setWorkHours(31);
//
//        return newList;
//
//    }

}
