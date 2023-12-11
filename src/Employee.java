public class Employee
{
    private static Employee instance = null;


    public String EmployeeFName;
    public String EmployeeMName;
    public String EmployeeLName;
    private String EmployeeEmailAddress;
    private String EmployeePassword;

    private String Employee_id;

    private Employee (String Employee_id, String EmployeeEmailAddress, String EmployeePassword)
    {
        this.Employee_id = Employee_id;
        this.EmployeeEmailAddress = EmployeeEmailAddress;
        this.EmployeePassword = EmployeePassword;
    }
    public static Employee getInstance(String Employee_id, String EmployeeEmailAddress, String EmployeePassword)
    {
        if (instance == null)
        {
            instance = new Employee(Employee_id, EmployeeEmailAddress, EmployeePassword);

        }
        else if (!instance.getEmployee_id().equals(Employee_id) || !instance.getEmployeeEmailAddress().equals(EmployeeEmailAddress) || !instance.getEmployeePassword().equals(EmployeePassword))
        {
            instance = new Employee(Employee_id, EmployeeEmailAddress, EmployeePassword);
        }
        return instance;
    }

    public String getEmployeeEmailAddress()
    {
        return EmployeeEmailAddress;
    }
    public String getEmployeePassword()
    {
        return EmployeePassword;
    }

    public String getEmployee_id()
    {
        return Employee_id;
    }

    public void setEmployee_id(String Employee_id)
    {
        this.Employee_id = Employee_id;
    }
    public void setEmployeeAddress(String EmployeeEmailAddress)
    {
        this.EmployeeEmailAddress = EmployeeEmailAddress;
    }

    public void setEmployeePassword(String EmployeePassword)
    {
        this.EmployeePassword = EmployeePassword;
    }
    public String EmployeeAddress;
    public String EmployeePhoneNumber;
    public String EmployeeSSN;
}
