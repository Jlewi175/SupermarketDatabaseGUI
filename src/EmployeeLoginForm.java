import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class EmployeeLoginForm extends JDialog
{
    private JPanel EmployeeLoginForm;
    private JTextField TextFieldLoginEmailAddress;
    private JPasswordField PasswordFieldLoginPassword;
    private JButton LoginPageLoginButton;
    private JButton LoginPageCreateAccountButton;
    private JTextField employeeIDTextField;

    public EmployeeLoginForm(JFrame parent)
    {
        super(parent);
        setTitle("Weis Markets - Employee Login Page");
        setContentPane(EmployeeLoginForm);
        setMinimumSize(new Dimension(650,800));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        LoginPageLoginButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                    String Employee_id = employeeIDTextField.getText();
                    String EmployeeEmailAddress = TextFieldLoginEmailAddress.getText();
                    String EmployeePassword = String.valueOf(PasswordFieldLoginPassword.getPassword());


                    weisEmployee = ValidatedUser(Employee_id, EmployeeEmailAddress, EmployeePassword);


                    if (weisEmployee != null)
                    {
                        dispose();
                        AccountPage accountPage = new AccountPage(weisEmployee);
                    }
                    else if(EmployeeEmailAddress.isEmpty())
                    {
                        JOptionPane.showMessageDialog(EmployeeLoginForm.this,
                                "Error: Email field is empty, please enter a valid email",
                                "Empty Email",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else if(EmployeePassword.isEmpty())
                    {
                        JOptionPane.showMessageDialog(EmployeeLoginForm.this,
                                "Error: Password field is empty, please enter a password",
                                "Empty Password",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(EmployeeLoginForm.this,
                                "Error: Employee credentials not found in our system! Make sure your email and password are both correct!",
                                "Invalid Credentials",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
        });

        LoginPageCreateAccountButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                dispose();
                CreateAccountForm createAccountForm = new CreateAccountForm(null);
            }
        });

        setVisible(true);
    }

    public Employee weisEmployee;

    private Employee ValidatedUser(String Employee_id, String EmployeeEmailAddress, String EmployeePassword)
    {
        Employee weisEmployee = null;

        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            Statement statement = connection.createStatement();

            //localhost:3306

            String sql = "SELECT * FROM EMPLOYEE WHERE Employee_id = ? AND Email=? AND Password=?";

            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, Employee_id);
            preparedStatement.setString(2, EmployeeEmailAddress);
            preparedStatement.setString(3, EmployeePassword);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
            {
                weisEmployee = Employee.getInstance(Employee_id, EmployeeEmailAddress, EmployeePassword);
            }

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return weisEmployee;
    }

    public static void main(String[] args)
    {
        EmployeeLoginForm employeeLoginForm = new EmployeeLoginForm(null);

        Employee weisEmployee = employeeLoginForm.weisEmployee;
    }
}
