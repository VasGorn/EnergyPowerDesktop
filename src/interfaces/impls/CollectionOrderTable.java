package interfaces.impls;

import interfaces.OrderTable;
import model.Employee;
import model.Order;

import java.util.ArrayList;

public class CollectionOrderTable implements OrderTable {
    private Order order;
    private int numMonth;
    private ArrayList<CollectionOrderTable.Row> employeeList = new ArrayList<>(10);

    public CollectionOrderTable(Order order, int numMonth){
        this.order = order;
        this.numMonth = numMonth;
    }

    @Override
    public void add(Employee employee){
        for(CollectionOrderTable.Row r: employeeList){
            if(r.getEmployee().equals(employee)){
                return;
            }
        }

        CollectionOrderTable.Row newRowEmployee = new CollectionOrderTable.Row(employee);
        employeeList.add(newRowEmployee);
    }

    @Override
    public void update(Employee employee){

    }

    @Override
    public void delete(Employee employee){

    }

    public void fillTestData(){
//        Employee enm1 = new Employee("John Anderson","1");
//        Employee enm2 = new Employee("Alivia Clark","2");
//        Employee enm3 = new Employee("Bob Gar","3");


    }

    private class Row{
        private Employee employee;
        //Matrix 31x3, [0;30] - day of month, [0;2] - work time, over time, type of work
        private int[][] tableWorkTime = new int[31][3];

        private Row(Employee employee){
            this.employee = employee;
        }

        private void setWorkTimeType(int numOfDay, int workTime, int overWorkTime, int typeWork){
            tableWorkTime[numOfDay][0] = workTime;
            tableWorkTime[numOfDay][1] = overWorkTime;
            tableWorkTime[numOfDay][2] = typeWork;
        }

        private Employee getEmployee(){
            return this.employee;
        }
    }

}
