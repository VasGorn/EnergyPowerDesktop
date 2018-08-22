package interfaces;

import model.Employee;

public interface OrderTable {

    void add(Employee employee);

    void update(Employee employee);

    void delete(Employee employee);
}
