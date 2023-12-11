import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class CreateAccountForm extends JDialog
{
    private JLabel CreateAccountTextLogo;
    private JLabel WeisTextLogo;
    private JTextField TextFieldFName;
    private JTextField TextFieldMName;
    private JTextField TextFieldLName;
    private JTextField TextFieldCreateAccountEmailAddress;
    private JTextField TextFieldAddress;
    private JTextField TextFieldPhoneNumber;
    private JPasswordField PasswordFieldEmployeePassword;
    private JPasswordField PasswordFieldSSN;
    private JButton CreateAccountButton;
    private JButton GoToLoginPageButton;
    private JPanel EmployeeCreateAccountPanel;
    private JTextField employeeIDTextField;

    public CreateAccountForm (JFrame parent)
    {
        super(parent);
        setTitle("Weis Markets - Create Employee Account");
        setContentPane(EmployeeCreateAccountPanel);
        setMinimumSize(new Dimension(650,800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Create Account
        CreateAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                //Variables
                String Employee_id = employeeIDTextField.getText();
                String EmployeeEmailAddress = TextFieldCreateAccountEmailAddress.getText();
                String EmployeePassword = String.valueOf(PasswordFieldEmployeePassword.getPassword());
                String EmployeeFName = TextFieldFName.getText();
                String EmployeeMName = TextFieldMName.getText();
                String EmployeeLName = TextFieldLName.getText();
                String EmployeeAddress = TextFieldAddress.getText();
                String EmployeePhoneNumber = TextFieldPhoneNumber.getText();
                String EmployeeSSN = String.valueOf(PasswordFieldSSN.getPassword());

                weisNewEmployee = createNewWeisEmployee(Employee_id, EmployeeFName, EmployeeMName, EmployeeLName, EmployeeEmailAddress, EmployeeAddress, EmployeePhoneNumber, EmployeeSSN, EmployeePassword);

                //Boolean Variables used to check if SSN and ID are Already Registered with us
                boolean CheckEmpSSN = false;
                boolean CheckEmpID = false;

                //Check if Employee ID and Employee SSN Already Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM EMPLOYEE WHERE Employee_id = ? OR Ssn = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Employee_id);
                    preparedStatement.setString(2, EmployeeSSN);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the EMPLOYEE Table check if the Employee_id and SSN are the same as the entered in SSN or Employee_id
                    while (resultSet.next())
                    {
                        if(resultSet.getString("Employee_id").equals(Employee_id))
                        {
                            CheckEmpID = true;
                        }
                        if(resultSet.getString("Ssn").equals(EmployeeSSN))
                        {
                            CheckEmpSSN = true;
                        }
                    }

                } catch (SQLException ex)
                {
                    ex.printStackTrace();
                }


                //Massive If Else Statement that Checks Numerous Constraints
                if (weisNewEmployee != null)
                {
                    dispose();
                    CreateAccountSuccessful createAccountSuccessful = new CreateAccountSuccessful(null);
                }
                else if(CheckEmpID)
                {
                        JOptionPane.showMessageDialog(CreateAccountForm.this,
                                "Error: Employee ID already exists, please choose another one",
                                "Duplicate Employee ID",
                                JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckEmpSSN)
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: SSN is already registered with us. Please enter your personal SSN and not anothers",
                            "Duplicate SSN Found",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeFName.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: First name field is empty, please enter your first name",
                            "Empty First Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeMName.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Middle name field is empty, please enter your middle name",
                            "Empty Middle Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeLName.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Last name field is empty, please enter your last name",
                            "Empty Last Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeEmailAddress.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Email field is empty, please enter an email",
                            "Empty Create Account Email",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(!EmployeeEmailAddress.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Email is not in the right format",
                            "Wrong Email Format",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeAddress.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Address field is empty, please enter your address",
                            "Empty Address",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeePhoneNumber.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Phone number field is empty, please enter your phone number",
                            "Empty Phone Number",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeePhoneNumber.length() != 10)
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Phone number is incorrect length, please enter your 10 digit phone number",
                            "Invalid Phone Number Length",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeSSN.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: SSN field is empty, please enter your SSN",
                            "Empty SSN",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(EmployeeSSN.length() != 9)
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: SSN length is incorrect length, please enter a valid 9 digit SSN",
                            "Invalid SSN Length",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if (EmployeePassword.isEmpty())
                {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: password field is empty, please enter your password",
                            "Empty Create Account Password",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(CreateAccountForm.this,
                            "Error: Account Created Failed",
                            "Account Not Created",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        //Go to Login Page
        GoToLoginPageButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                EmployeeLoginForm employeeLoginForm = new EmployeeLoginForm(null);
            }
        });

        setVisible(true);
    }

    public Employee weisNewEmployee;

    //Create Account
    private Employee createNewWeisEmployee(String Employee_id, String EmployeeFName, String EmployeeMName, String EmployeeLName, String EmployeeEmailAddress, String EmployeeAddress, String EmployeePhoneNumber, String EmployeeSSN, String EmployeePassword)
    {
        Employee newWeisEmployee = null;

        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            Statement statement = connection.createStatement();

            String sql = "INSERT INTO EMPLOYEE (Employee_id, First_name, Middle_name, Last_name, Email, Address, Phone, Ssn, Password)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Employee_id);
            preparedStatement.setString(2, EmployeeFName);
            preparedStatement.setString(3, EmployeeMName);
            preparedStatement.setString(4, EmployeeLName);
            preparedStatement.setString(5, EmployeeEmailAddress);
            preparedStatement.setString(6, EmployeeAddress);
            preparedStatement.setString(7, EmployeePhoneNumber);
            preparedStatement.setString(8, EmployeeSSN);
            preparedStatement.setString(9, EmployeePassword);

            int employeeTableRows = preparedStatement.executeUpdate();

            if (employeeTableRows > 0)
            {
                newWeisEmployee = Employee.getInstance(Employee_id, EmployeeEmailAddress, EmployeePassword);
                newWeisEmployee.EmployeeFName = EmployeeFName;
                newWeisEmployee.EmployeeMName = EmployeeMName;
                newWeisEmployee.EmployeeLName = EmployeeLName;
                newWeisEmployee.EmployeePhoneNumber = EmployeePhoneNumber;
                newWeisEmployee.EmployeeSSN = EmployeeSSN;
                newWeisEmployee.EmployeeAddress = EmployeeAddress;
            }

            statement.close();
            connection.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return newWeisEmployee;
    }


    public static void main(String[] args)
    {
        CreateAccountForm createAccountForm = new CreateAccountForm(null);

        Employee newWeisEmployee = createAccountForm.weisNewEmployee;

        if(newWeisEmployee != null)
        {
            System.out.println("Registration Successful");
        }
    }
}

