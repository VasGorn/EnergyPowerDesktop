package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Order;
import model.User;
import model.WR_Table;
import model.WorkType;

import java.util.ArrayList;
import java.util.Iterator;

public class ListOfOrders {
    @FXML
    private Button btnAddOrder;

    @FXML
    private TextField tfNameOrder;

    @FXML
    private TextField tfMaxHours;

    @FXML
    private TextField tfDescription;

    @FXML
    private TextField tfAddress;

    @FXML
    private ListView lvOrders;

    @FXML
    private ListView lvAllWorkType;

    @FXML
    private ListView lvOrderWorkType;

    //private ArrayList<Order> newListOrders =
    private ObservableList<Order> observListForWrightTable = FXCollections.observableArrayList(User.getListOrdersManager());
    private ObservableList<WorkType> observListAllWorkType;
    private ObservableList<WorkType> observListWorkTypeOrder= FXCollections.observableArrayList();
    private DatabaseHandler dbHandler = new DatabaseHandler();

    @FXML
    private void initialize() {
        lvOrders.setItems(observListForWrightTable);

        ArrayList<WorkType> allList = dbHandler.getAllWorkType();
        observListAllWorkType = FXCollections.observableArrayList(allList);
        lvAllWorkType.setItems(observListAllWorkType);

        lvOrderWorkType.setItems(observListWorkTypeOrder);
    }

    public void btnAddOrderClicked(){

        try{
            String nameOrder = tfNameOrder.getText();
            String description = tfDescription.getText();
            String address = tfAddress.getText();

            int maxHours = Integer.parseInt(tfMaxHours.getText());

            int managerID = User.getEmployeeID();
            String nameManager = User.getNameEmployee();

            Order newOrder = new Order(1, nameOrder, address, description,nameManager,maxHours);

            boolean flag = true;

            for(Order o: observListForWrightTable){
                if(newOrder.equals(o))
                    flag = false;
            }

            if(flag){
                int orderID = dbHandler.addOrder(newOrder, managerID);

                if(orderID > 0){
                    newOrder.setId(orderID);
                    observListForWrightTable.add(newOrder);
                    User.getListOrdersManager().add(newOrder);
                }

            }

        }catch (NumberFormatException ne){
            ne.printStackTrace();
        }

    }

    public void btnEditClicked(){
        Object selected = lvOrders.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            try{
                String nameOrder = tfNameOrder.getText();
                String description = tfDescription.getText();
                String address = tfAddress.getText();

                int maxHours = Integer.parseInt(tfMaxHours.getText());

                ((Order) selected).setNameOrder(nameOrder);
                ((Order) selected).setDescription(description);
                ((Order) selected).setAddress(address);
                ((Order) selected).setHoursMax(maxHours);

                int orderID =  ((Order) selected).getId();

                dbHandler.updateOrder(orderID, nameOrder, description, address, maxHours);

            }catch (NumberFormatException ne){
                ne.printStackTrace();
            }
        }
    }

    public void btnDeleteClicked(){
        Object selected = lvOrders.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            tfNameOrder.clear();
            tfDescription.clear();
            tfAddress.clear();
            tfMaxHours.clear();

            int orderID =  ((Order) selected).getId();

            dbHandler.deleteOrder(orderID);

            observListForWrightTable.remove(selected);
            User.getListOrdersManager().remove(selected);
        }
    }

    public void listLeftBtnClicked(){
        Object selected = lvOrders.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            String nameOrder = ((Order) selected).getNameOrder();
            String description = ((Order) selected).getDescription();
            String address = ((Order) selected).getAddress();
            String maxHours = ((Order) selected).getStringMaxHours();

            tfNameOrder.setText(nameOrder);
            tfDescription.setText(description);
            tfAddress.setText(address);
            tfMaxHours.setText(maxHours);

            observListWorkTypeOrder.setAll( ((Order) selected).getWorkTypeList());
        }
    }

    public void btnAddWorkType(){
        Object selWorkType = lvAllWorkType.getSelectionModel().getSelectedItem();
        Object selOrder = lvOrders.getSelectionModel().getSelectedItem();

        if((selWorkType!=null) && (selOrder!=null)){
            boolean flag = true;
            for(WorkType wt: observListWorkTypeOrder){
                if(wt.equals(selWorkType))
                    flag = false;
            }

            if(flag) {
                ((Order) selOrder).getWorkTypeList().add((WorkType) selWorkType);
                observListWorkTypeOrder.add((WorkType) selWorkType);
                dbHandler.addWorkTypeToOrder(((Order) selOrder),((WorkType)selWorkType));
            }
        }
    }

    public void btnDeleteWorkType(){
        Object selWorkType = lvOrderWorkType.getSelectionModel().getSelectedItem();

        Object selOrder = lvOrders.getSelectionModel().getSelectedItem();
        if(!(selWorkType==null)){

            Iterator<WorkType> it = observListWorkTypeOrder.iterator();
            while(it.hasNext()){
                WorkType sel = it.next();
                if( sel == selWorkType){
                    it.remove();
                    ((Order) selOrder).getWorkTypeList().remove(selWorkType);
                    dbHandler.deleteWorkTypeFromOrder(((Order) selOrder),((WorkType)selWorkType));
                }
            }
        }
    }

}
