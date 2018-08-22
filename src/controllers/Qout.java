package controllers;

import database.DatabaseHandler;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.util.ArrayList;

public class Qout {
    @FXML
    private ComboBox<Order> cbNameOrder;

    @FXML
    private Button btnWrite;

    @FXML
    private Button btnListOfOrders;

    @FXML
    private TextField tfManager;
    @FXML
    private TextField tfMaxHours;
    @FXML
    private TextField tfDescription;
    @FXML
    private TextField tfAddress;

    @FXML
    private ComboBox<String> cbPosition;
    @FXML
    private ComboBox<Employee> cbWorkers;
    @FXML
    private ComboBox<String> cbMonth;

    @FXML
    private TextField tfHours;

    //--------------------------------------------------------------------------
    //table
    @FXML
    private TreeTableView<QuotTable> ttvQouts;

    @FXML
    private TreeTableColumn<QuotTable, String> colEmployee;

    @FXML
    private TreeTableColumn<QuotTable, String>  colJanuary,    colFebruary,
                                                colMarch,      colApril,      colMay,
                                                colJune,       colJuly,       colAugust,
                                                colSeptember,  colOctober,    colNovember,
                                                colDecember;


    private ArrayList<Order> newListOrders = User.getListOrdersManager();
    private ArrayList<QuotTable> listForQuotTableEngineer;
    private ArrayList<QuotTable> listForQuotTableMaster;
    private DatabaseHandler dbHandler = new DatabaseHandler();

    private TreeItem<QuotTable> rootMaster;
    private TreeItem<QuotTable> rootEngineer;
    private TreeItem<QuotTable> root;

    @FXML
    private void initialize(){

        for(Order o: newListOrders)
            cbNameOrder.getItems().add(o);

        cbPosition.getItems().add("Инженер");
        cbPosition.getItems().add("Мастер");

        cbPosition.setOnAction(event -> {
            ArrayList<Employee> listOfEmployee;

            if(cbPosition.getValue().equals("Инженер")){
                listOfEmployee = dbHandler.getListOfEmployeesByPosition(2);
            }else{
                listOfEmployee = dbHandler.getListOfEmployeesByPosition(3);
            }

            cbWorkers.getItems().clear();

            for(Employee e: listOfEmployee)
                cbWorkers.getItems().add(e);

        });

        setItemsToComboBoxMonth();

        QuotTable qRootMaster = new QuotTable(new Employee("Мастера","","",0));
        QuotTable qRootEngineer = new QuotTable(new Employee("Инженера","","",1));
        QuotTable qRoot = new QuotTable(new Employee("","","",2));

        rootMaster = new TreeItem<>(qRootMaster);
        rootEngineer = new TreeItem<>(qRootEngineer);
        root = new TreeItem<>(qRoot);

        rootMaster.setExpanded(true);
        rootEngineer.setExpanded(true);

        setPropertyToColumns();


        ttvQouts.setRoot(root);
        ttvQouts.setShowRoot(false);

        root.getChildren().add(rootMaster);
        root.getChildren().add(rootEngineer);

        cbNameOrder.setOnAction(event -> {
            tfManager.setText(User.getNameEmployee());
            tfMaxHours.setText(cbNameOrder.getValue().getStringMaxHours());
            tfDescription.setText(cbNameOrder.getValue().getDescription());
            tfAddress.setText(cbNameOrder.getValue().getAddress());

            //listForQuotTableEngineer.clear();
            //listForQuotTableMaster.clear();

            listForQuotTableEngineer = dbHandler.getTableQuot(cbNameOrder.getValue(),2);
            listForQuotTableMaster = dbHandler.getTableQuot(cbNameOrder.getValue(),3);

            rootMaster.getChildren().clear();
            rootEngineer.getChildren().clear();

            for(QuotTable q: listForQuotTableMaster)
                rootMaster.getChildren().add(new TreeItem<>(q));

            for(QuotTable q: listForQuotTableEngineer)
                rootEngineer.getChildren().add(new TreeItem<>(q));

            ttvQouts.refresh();

        });



    }

    public void btnWriteClicked(){
        String number = tfHours.getText();

        int numMonth = getNumMonth();

        try{
            int hours = Integer.parseInt(number);
            writeToQoutTable(numMonth, hours);

        }
        catch (NumberFormatException e){
            e.printStackTrace();
        }


    }

    public void btnListOrdersClicked(){
        Parent root;
        try{
            root = FXMLLoader.load(getClass().getResource("../fxml/ListOrders.fxml"));
            Stage workTimeStage = new Stage();
            workTimeStage.setTitle("ListOfOrders");
            workTimeStage.setScene(new Scene(root));
            //workTimeStage.initModality(Modality.APPLICATION_MODAL);
            //workTimeStage.initOwner(btnListOfOrders.getScene().getWindow());
            workTimeStage.show();
        }
        catch (IOException a){
            a.printStackTrace();
        }
    }

    private void writeToQoutTable(int numMonth, int hours){
        int orderId = cbNameOrder.getValue().getId();
        int employeeId = cbWorkers.getValue().getID();

        if(cbPosition.getValue().equals("Мастер") && (numMonth > 0) && (hours > 0)){

            if(cbWorkers.getValue() != null){
                Employee employee = cbWorkers.getValue();

                boolean flag = true;

                for(QuotTable qt: listForQuotTableMaster){
                    if(qt.getEmployee().equals(employee)){

                        int hoursBefore = qt.getHoursOnMonth(numMonth);

                        if(hoursBefore > 0){
                            dbHandler.updateHourPerMonth(orderId, employeeId, numMonth, hours);
                        }else{
                            dbHandler.addHourPerMonth(orderId, employeeId, numMonth, hours);
                        }

                        qt.setHoursOnMonth(numMonth, hours);
                        flag = false;
                    }
                }

                if(flag){
                    QuotTable newQt = new QuotTable(employee);
                    newQt.setHoursOnMonth(numMonth,hours);

                    dbHandler.addQuot(orderId, employeeId, numMonth, hours);

                    listForQuotTableMaster.add(newQt);
                }

                rootMaster.getChildren().clear();

                for(QuotTable q: listForQuotTableMaster)
                    rootMaster.getChildren().add(new TreeItem<>(q));

                ttvQouts.refresh();

            }
        }

        if(cbPosition.getValue().equals("Инженер") && (numMonth > 0) && (hours > 0)){

            if(cbWorkers.getValue() != null){
                Employee employee = cbWorkers.getValue();

                boolean flag = true;

                for(QuotTable qt: listForQuotTableEngineer){
                    if(qt.getEmployee().equals(employee)){

                        int hoursBefore = qt.getHoursOnMonth(numMonth);

                        if(hoursBefore > 0){
                            dbHandler.updateHourPerMonth(orderId, employeeId, numMonth, hours);
                        }else{
                            dbHandler.addHourPerMonth(orderId, employeeId, numMonth, hours);
                        }

                        qt.setHoursOnMonth(numMonth, hours);
                        flag = false;
                    }
                }

                if(flag){
                    QuotTable newQt = new QuotTable(employee);
                    newQt.setHoursOnMonth(numMonth,hours);

                    dbHandler.addQuot(orderId, employeeId, numMonth, hours);

                    listForQuotTableEngineer.add(newQt);
                }

                rootEngineer.getChildren().clear();

                for(QuotTable q: listForQuotTableEngineer)
                    rootEngineer.getChildren().add(new TreeItem<>(q));

                ttvQouts.refresh();

            }
        }
    }

    private int getNumMonth(){
        String sMonth = cbMonth.getValue();
        if(sMonth.equals("Январь"))
            return 1;
        else if(sMonth.equals("Февраль"))
            return 2;
        else if(sMonth.equals("Март"))
            return 3;
        else if(sMonth.equals("Апрель"))
            return 4;
        else if(sMonth.equals("Май"))
            return 5;
        else if(sMonth.equals("Июнь"))
            return 6;
        else if(sMonth.equals("Июль"))
            return 7;
        else if(sMonth.equals("Август"))
            return 8;
        else if(sMonth.equals("Сентябрь"))
            return 9;
        else if(sMonth.equals("Окрябрь"))
            return 10;
        else if(sMonth.equals("Ноябрь"))
            return 11;
        else if(sMonth.equals("Декабрь"))
            return 12;
        else
            return 0;
    }


    private void setItemsToComboBoxMonth(){
        cbMonth.getItems().add("Январь");
        cbMonth.getItems().add("Февраль");
        cbMonth.getItems().add("Март");
        cbMonth.getItems().add("Апрель");
        cbMonth.getItems().add("Май");
        cbMonth.getItems().add("Июнь");
        cbMonth.getItems().add("Июль");
        cbMonth.getItems().add("Август");
        cbMonth.getItems().add("Сентябрь");
        cbMonth.getItems().add("Окрябрь");
        cbMonth.getItems().add("Ноябрь");
        cbMonth.getItems().add("Декабрь");
    }

    private void setPropertyToColumns(){
        colEmployee.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getNameEmployee())
        );

        colJanuary.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getJanuary().getStringHours())
        );
        colFebruary.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getFebruary().getStringHours())
        );
        colMarch.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getMarch().getStringHours())
        );
        colApril.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getApril().getStringHours())
        );
        colMay.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getMay().getStringHours())
        );
        colJune.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getJune().getStringHours())
        );
        colJuly.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getJuly().getStringHours())
        );
        colAugust.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getAugust().getStringHours())
        );
        colSeptember.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getSeptember().getStringHours())
        );
        colOctober.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getOctober().getStringHours())
        );
        colNovember.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getNovember().getStringHours())
        );
        colDecember.setCellValueFactory(
                (TreeTableColumn.CellDataFeatures<QuotTable, String> param) ->
                        new ReadOnlyStringWrapper(param.getValue().getValue().getDecember().getStringHours())
        );
    }

    private ArrayList<QuotTable> getTestTable(){
        ArrayList<QuotTable> newList = new ArrayList<>();
        Employee em = new Employee("sdf","asd","sdf",1);
        QuotTable newRow = new QuotTable(em);
        newRow.setHoursOnMonth(1,11);
        newRow.setHoursOnMonth(2,12);
        newRow.setHoursOnMonth(3,13);
        newRow.setHoursOnMonth(4,14);
        newRow.setHoursOnMonth(5,15);
        newRow.setHoursOnMonth(6,16);
        newRow.setHoursOnMonth(7,17);
        newRow.setHoursOnMonth(8,18);
        newRow.setHoursOnMonth(9,19);
        newRow.setHoursOnMonth(10,20);
        newRow.setHoursOnMonth(11,21);
        newRow.setHoursOnMonth(12,22);
        newList.add(newRow);
        return newList;
    }
}
