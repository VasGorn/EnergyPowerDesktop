package model;

import java.util.ArrayList;

public class OrderQuot extends Order{
    private int[] sumMaxHourPerMounth = new int[12];

    private ArrayList<OrderQuot.Row> employeeList = new ArrayList<>(10);

    public OrderQuot(int id, String nameOrder, String description, String address, String managerName, int max_hours){
        super(id, nameOrder,description,address,managerName, max_hours);
    }

    public void setMaxHourOnMounth(int hours, int numberOfMonth){
        sumMaxHourPerMounth[numberOfMonth - 1] = hours;
    }

    public void addEmployee (Employee employee){
        OrderQuot.Row newEmployee = new OrderQuot.Row(employee);
        employeeList.add(newEmployee);
    }

    public void setHourPerMonthOnEmloyee(Employee employee, int hours, int numberMonth){
        int i;
        for(OrderQuot.Row r: employeeList){
            if(r.getEmployee().equals(employee)){
                r.setHourPerMonth(hours, numberMonth);
                return;
            }
        }

        this.addEmployee(employee);
        i = employeeList.size() - 1;
        employeeList.get(i).setHourPerMonth(hours, numberMonth);
    }


    private class Row{
        private  int[] hourPerMonth = new int[12];
        private Employee employee;

        private Row(Employee employee){
            this.employee = employee;
        }

        private void setHourPerMonth(int hours, int numberMonth){
            this.hourPerMonth[numberMonth - 1] = hours;
        }

        private Employee getEmployee(){
            return employee;
        }
    }
}
