package database;

public class Const {
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "users_id";
    public static final String USER_EMPLOYEE_ID = "employee_employee_id";
    public static final String USER_NAME = "user_name";
    public static final String USER_PASSWORD = "password";

    public static final String EMPLOYEE_TABLE = "employee";
    public static final String EMPLOYEE_ID = "employee_id";
    public static final String EMPLOYEE_LASTNAME = "lastname";
    public static final String EMPLOYEE_FIRSTNAME = "firstname";
    public static final String EMPLOYEE_MIDDLENAME = "middlename";

    public static final String POSITION_TABLE = "position_table";
    public static final String POSITION_ID = "position_id";
    public static final String POSITION_NAME = "position_name";

    public static final String EMPLOYEE_HAS_POSITION_TABLE = "employee_has_position";
    public static final String EMPLOYEE_HAS_POSITION_EMPLOYEE_ID = "employee_employee_id";
    public static final String EMPLOYEE_HAS_POSITION_POSITION_ID = "position_position_id";

    public static final String TEAM_TABLE = "team";
    public static final String TEAM_WORKER_ID = "worker_id";
    public static final String TEAM_MASTER_ID = "master_id";

    public static final String ORDER_TABLE = "orders";
    public static final String ORDER_ID = "order_id";
    public static final String ORDER_NAME = "name_order";
    public static final String ORDER_MANAGER_ID = "employee_manager_id";
    public static final String ORDER_ADRESS = "adress";
    public static final String ORDER_DERSCRIPTION = "description";
    public static final String ORDER_MAX_HOURS = "max_hour";

    public static final String QOUTS_TABLE = "qouts";
    public static final String QOUTS_ID = "qout_id";
    public static final String QOUTS_ORDER_ID = "order_order_id";
    public static final String QOUTS_EMPLOYEE_ID = "employee_on_order_id";

    public static final String HOUR_PER_MONTH_TABLE = "hour_per_month";
    public static final String HOUR_PER_MONTH_ID = "id";
    public static final String HOUR_PER_MONTH_QUOT_ID = "qouts_qout_id";
    public static final String HOUR_PER_MONTH_QUOT_NUM_MONTH = "num_month";
    public static final String HOUR_PER_MONTH_HOURS = "hours";

    public static final String WORK_TYPE_TABLE = "work_type";
    public static final String WORK_TYPE_ID = "type_id";
    public static final String WORK_TYPE_NAME = "type_name";

    public static final String ORDER_HAS_WORKTYPE_TABLE = "order_has_work_type";
    public static final String ORDER_HAS_WORKTYPE_ORDER_ID = "order_order_id";
    public static final String ORDER_HAS_WORKTYPE_TYPE_ID = "work_type_type_id";

    public static final String WORK_TIME_TABLE = "work_time";
    public static final String WORK_TIME_ID = "id";
    public static final String WORK_TIME_ORDER_ID = "order_order_id";
    public static final String WORK_TIME_EMPLOYEE_ID = "employee_employee_id";
    public static final String WORK_TIME_WORK_TYPE_ID = "work_type_type_id";
    public static final String WORK_TIME_NUM_MONTH = "num_month";
    public static final String WORK_TIME_NUM_DAY = "num_day";
    public static final String WORK_TIME_WORK_TIME = "work_time";
    public static final String WORK_TIME_OVER_TIME = "over_time";
    public static final String WORK_TIME_APPROVAL = "work_approval";

}
