package model;

import javafx.scene.control.CheckBox;

public class UserTable {
    private Employee employee;
    private int userID;
    private String nameUser;
    private CheckBox checkBoxIsMaster;
    private CheckBox checkBoxIsEngineer;
    private CheckBox checkBoxIsManager;

    public UserTable(Employee employee, int userID, String nameUser) {
        this.employee = employee;
        this.userID = userID;
        this.nameUser = nameUser;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getNameUser() {
        return nameUser;
    }

    public void setNameUser(String nameUser) {
        this.nameUser = nameUser;
    }

    public CheckBox getCheckBoxIsMaster() {
        return checkBoxIsMaster;
    }

    public void setCheckBoxIsMaster(CheckBox checkBoxIsMaster) {
        this.checkBoxIsMaster = checkBoxIsMaster;
    }

    public CheckBox getCheckBoxIsEngineer() {
        return checkBoxIsEngineer;
    }

    public void setCheckBoxIsEngineer(CheckBox checkBoxIsEngineer) {
        this.checkBoxIsEngineer = checkBoxIsEngineer;
    }

    public CheckBox getCheckBoxIsManager() {
        return checkBoxIsManager;
    }

    public void setCheckBoxIsManager(CheckBox checkBoxIsManager) {
        this.checkBoxIsManager = checkBoxIsManager;
    }
}
