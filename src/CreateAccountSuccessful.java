import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountSuccessful extends JDialog
{
    private JPanel CreateAccountSuccessfulPanel;
    private JButton GoToLoginPageButton2;

    public CreateAccountSuccessful (JFrame parent)
    {
        super(parent);
        setTitle("Weis Markets - Create Account Successful");
        setContentPane(CreateAccountSuccessfulPanel);
        setMinimumSize(new Dimension(450 , 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GoToLoginPageButton2.addActionListener(new ActionListener()
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

    public static void main(String[] args)
    {
        CreateAccountSuccessful createAccountSuccessful = new CreateAccountSuccessful(null);

    }
}
