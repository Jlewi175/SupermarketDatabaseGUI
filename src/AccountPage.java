import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;

public class AccountPage extends JDialog{
    private JPanel AccountPagePanel;
    private JButton homeButton;
    private JButton categoriesButton;
    private JButton productLocationButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JLabel employeeEmail;
    private JLabel employeePassword;
    private JButton logOutButton;
    private JButton switchAccountsButton;
    private JLabel employeeID;
    private JButton productButton;
    private JButton searchButton;

    private Employee weisEmployee;


    private int Employee_id;


    public AccountPage (Employee weisEmployee) {

        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Home Page");
        setContentPane(AccountPagePanel);
        setMinimumSize(new Dimension(1535, 850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        employeeID.setText(weisEmployee.getEmployee_id());
        employeeEmail.setText(weisEmployee.getEmployeeEmailAddress());
        employeePassword.setText(weisEmployee.getEmployeePassword());

        //Go to Home
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomePage homePage = new HomePage(weisEmployee);
            }
        });

        //Go to Categories
        categoriesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();

                try {
                    CategoryPages categoryPages = new CategoryPages(weisEmployee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        });

        //Go to Products Location
        productLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    ProductsLocationPage productLocationPage = new ProductsLocationPage(weisEmployee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //ProductLocationPage productLocationPage = new ProductLocationPage(weisEmployee);
            }
        });

        //Go to Suppliers
        suppliersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                try {
                    SuppliersPage suppliersPage = new SuppliersPage(weisEmployee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
                //SupplierPage supplierPage = new SupplierPage(weisEmployee);
            }
        });

        //Go to Shipments
        shipmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ShipmentsPage shipmentsPage = new ShipmentsPage(weisEmployee);
                //ShipmentPage shipmentPage = new ShipmentPage(weisEmployee);
            }
        });


        //Go to Inventory
        inventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InventoryPage inventoryPage = new InventoryPage(weisEmployee);
            }
        });


        //Go to Account
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountPage accountPage = new AccountPage(weisEmployee);
            }
        });

        //Completely Log Out and Close Application
        logOutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        //Switch Accounts
        switchAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                EmployeeLoginForm employeeLoginForm = new EmployeeLoginForm(null);
            }
        });

        //Go to Product
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ProductsPage productsPage = new ProductsPage(weisEmployee);
            }
        });


        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                WeisScripting weisScripting = new WeisScripting(weisEmployee);
            }
        });
        setVisible(true);
    }

    public static void main(String[] args)
    {
        String Employee_id = "";
        String EmployeeEmailAddress = "";
        String EmployeePassword = "";
        Employee weisEmployee = Employee.getInstance(Employee_id, EmployeeEmailAddress, EmployeePassword);
        AccountPage accountPage = new AccountPage(weisEmployee);
    }
}
