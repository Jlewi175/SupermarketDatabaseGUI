import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginSuccessful extends JDialog
{
    private JPanel LoginSuccessfulPanel;
    private JButton GoToAccountButton;

    public LoginSuccessful (JFrame parent)
    {
        super(parent);
        setTitle("Weis Markets - Login Successful");
        setContentPane(LoginSuccessfulPanel);
        setMinimumSize(new Dimension(450 , 400));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GoToAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountPage accountPage = new AccountPage(null);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args)
    {
        LoginSuccessful loginSuccessful = new LoginSuccessful(null);
    }
}
