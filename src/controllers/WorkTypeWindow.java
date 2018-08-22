package controllers;

import database.DatabaseHandler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.WorkType;

import java.util.ArrayList;
import java.util.Iterator;

public class WorkTypeWindow {
    @FXML
    private TextField tfWorkType;

    @FXML
    private ListView lvWorkType;

    private ObservableList<WorkType> observListAllWorkType;
    private DatabaseHandler dbHandler = new DatabaseHandler();

    public void initialize(){
        ArrayList<WorkType> allList = dbHandler.getAllWorkType();
        observListAllWorkType = FXCollections.observableArrayList(allList);
        lvWorkType.setItems(observListAllWorkType);
    }

    public void btnAddWorkTypeClicked(){
        String nameWorkType = tfWorkType.getText();
        if(!nameWorkType.equals("")){
            int id = dbHandler.addWorkType(nameWorkType);
            if(id > 0){
                WorkType newWorkType = new WorkType(id, nameWorkType);
                observListAllWorkType.add(newWorkType);
            }
        }
    }

    public void btnEditWorkTypeClicked(){
        Object selected = lvWorkType.getSelectionModel().getSelectedItem();
        String nameWorkType = tfWorkType.getText();
        if(selected!=null && !nameWorkType.equals("")){
            WorkType wt = ((WorkType) selected);
            wt.setTypeName(nameWorkType);
            dbHandler.updateWorkType(nameWorkType, wt);

            lvWorkType.refresh();
        }
    }

    public void btnDeleteTypeClicked(){
        Object selWorkType = lvWorkType.getSelectionModel().getSelectedItem();

        if(!(selWorkType==null)){

            Iterator<WorkType> it = observListAllWorkType.iterator();
            while(it.hasNext()){
                WorkType sel = it.next();
                if( sel == selWorkType){
                    it.remove();
                    dbHandler.deleteWorkType(sel);
                    tfWorkType.setText("");
                }
            }
        }
    }

    public void lvLeftClicked(){
        Object selected = lvWorkType.getSelectionModel().getSelectedItem();
        if(!(selected==null)){
            WorkType wt = ((WorkType) selected);

            tfWorkType.setText(wt.getTypeName());
        }
    }

}
