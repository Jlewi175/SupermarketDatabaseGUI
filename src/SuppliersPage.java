import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import java.sql.*;

public class SuppliersPage extends JDialog {
    private JPanel SuppliersPagePanel;

    private Employee weisEmployee;
    private JButton productLocationButton;
    private JButton categoriesButton;
    private JButton homeButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JTextField supplierAddressTxtField;
    private JButton addSupplierButton;
    private JButton updateSupplierButton;
    private JButton deleteSupplierButton;
    private JScrollPane supplierScrollPane;
    private JTable supplierTable;
    private JTextField supplierPhoneTxtField;
    private JTextField supplierEmailTxtField;
    private JTextField supplierNameTxtField;
    private JTextField supplierIDTxtField;
    private JButton productButton;
    private JButton searchButton;

    public SuppliersPage(Employee weisEmployee) throws SQLException
    {
        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Suppliers Page");
        setContentPane(SuppliersPagePanel);
        setMinimumSize(new Dimension(1535,850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM SUPPLIER");
        ResultSet resultSet = selectStatement.executeQuery();
        supplierTable.setModel(DbUtils.resultSetToTableModel(resultSet));


        //Go Home
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
                    ProductsLocationPage productsLocationPage = new ProductsLocationPage(weisEmployee);
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
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
            }
        });

        //Go to Shipments
        shipmentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ShipmentsPage shipmentsPage = new ShipmentsPage(weisEmployee);
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

        //Go to Accounts
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountPage accountPage = new AccountPage(weisEmployee);
            }
        });

        //Go to Products
        productButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ProductsPage productsPage = new ProductsPage(weisEmployee);
            }
        });

        //Go to Search
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                dispose();
                WeisScripting weisScripting = new WeisScripting(weisEmployee);
            }
        });

        //Add Supplier
        addSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Supplier_id = supplierIDTxtField.getText();
                String Supplier_name = supplierNameTxtField.getText();
                String Supplier_address = supplierAddressTxtField.getText();
                String Supplier_phone = supplierPhoneTxtField.getText();
                String Supplier_email = supplierEmailTxtField.getText();


                //Boolean Variables to check for duplicate SupplierID, Supplier Name
                Boolean CheckSupplierID = false;
                Boolean CheckSupplierName = false;

                //Check if SupplierID and SupplierName exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM SUPPLIER WHERE Supplier_id = ? OR Supplier_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Supplier_id);
                    preparedStatement.setString(2, Supplier_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the SUPPLIER Table check if the Supplier_id, Supplier_name is the same as the entered in Supplier_id, Supplier_name
                    while (resultSet.next()) {
                        if (resultSet.getString("Supplier_id").equals(Supplier_id)) {
                            CheckSupplierID = true;
                        }

                        if(resultSet.getString("Supplier_name").equals(Supplier_name))
                        {
                            CheckSupplierName = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                //Check if duplicate supplierID or supplierName exists as well as other constraints if it does prompt user with error otherwise add new supplier
                if(CheckSupplierID)
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier ID already exists, please choose another one",
                            "Duplicate Supplier ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier ID field is empty, please enter a Supplier ID",
                            "Empty Supplier ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Name field is empty, please enter a Supplier Name",
                            "Empty Supplier Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_address.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Address field is empty, please enter a Supplier Address",
                            "Empty Supplier Address",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_phone.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Phone number field is empty, please enter a Supplier Phone Number",
                            "Empty Supplier Phone Number",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_email.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Address field is empty, please enter a Supplier Address",
                            "Empty Supplier Address",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(!Supplier_email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Email is not in the right format",
                            "Wrong Email Format",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_phone.length() != 10)
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Phone number is incorrect length, please enter your 10 digit phone number",
                            "Invalid Phone Number Length",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckSupplierName)
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Name already exists, please choose another one",
                            "Duplicate Supplier Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                        String sql = "INSERT INTO SUPPLIER (Supplier_id, Supplier_name, Supplier_address, Supplier_phone, Supplier_email)"
                                + "VALUES (?, ?, ?, ?, ?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, Supplier_id);
                        preparedStatement.setString(2, Supplier_name);
                        preparedStatement.setString(3, Supplier_address);
                        preparedStatement.setString(4, Supplier_phone);
                        preparedStatement.setString(5, Supplier_email);

                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM SUPPLIER");
                        ResultSet resultSet = selectStatement.executeQuery();
                        supplierTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        supplierIDTxtField.setText("");
                        supplierNameTxtField.setText("");
                        supplierAddressTxtField.setText("");
                        supplierPhoneTxtField.setText("");
                        supplierEmailTxtField.setText("");

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Update Supplier
        updateSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Supplier_id = supplierIDTxtField.getText();
                String Supplier_name = supplierNameTxtField.getText();
                String Supplier_address = supplierAddressTxtField.getText();
                String Supplier_phone = supplierPhoneTxtField.getText();
                String Supplier_email = supplierEmailTxtField.getText();

                //Boolean Variables to check for duplicate Supplier Name
                Boolean CheckSupplierName = false;

                //Check if SupplierName exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM SUPPLIER WHERE Supplier_id = ? OR Supplier_name = ? ";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Supplier_id);
                    preparedStatement.setString(2, Supplier_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the SUPPLIER Table check if the Supplier_name is the same as the entered in Supplier_name
                    while (resultSet.next()) {
                        if(resultSet.getString("Supplier_id").equals(Supplier_id))
                        {
                            break;
                        }
                        if(resultSet.getString("Supplier_name").equals(Supplier_name))
                        {
                            CheckSupplierName = true;
                        }
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Check if duplicate supplierName exists as well as other constraints, if it does prompt user with error otherwise update supplier
                if(Supplier_id.equals(Supplier_name))
                {

                }
                else if(CheckSupplierName)
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Name already exists, please choose another one",
                            "Duplicate Supplier Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier ID field is empty, please enter a Supplier ID",
                            "Empty Supplier ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Name field is empty, please enter a Supplier Name",
                            "Empty Supplier Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_address.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Address field is empty, please enter a Supplier Address",
                            "Empty Supplier Address",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_phone.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Phone number field is empty, please enter a Supplier Phone Number",
                            "Empty Supplier Phone Number",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_email.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Address field is empty, please enter a Supplier Address",
                            "Empty Supplier Address",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(!Supplier_email.matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"))
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Email is not in the right format",
                            "Wrong Email Format",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_phone.length() != 10)
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Phone number is incorrect length, please enter your 10 digit phone number",
                            "Invalid Phone Number Length",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE SUPPLIER SET Supplier_name = ?, Supplier_address = ?, Supplier_phone = ?, Supplier_email = ? WHERE Supplier_id = ?");


                        preparedStatement.setString(1, Supplier_name);
                        preparedStatement.setString(2, Supplier_address);
                        preparedStatement.setString(3, Supplier_phone);
                        preparedStatement.setString(4, Supplier_email);
                        preparedStatement.setString(5, Supplier_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM SUPPLIER");
                        ResultSet resultSet = selectStatement.executeQuery();
                        supplierTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        supplierIDTxtField.setText("");
                        supplierNameTxtField.setText("");
                        supplierAddressTxtField.setText("");
                        supplierPhoneTxtField.setText("");
                        supplierEmailTxtField.setText("");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Delete Supplier
        deleteSupplierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Supplier_id = supplierIDTxtField.getText();
                String Supplier_name = supplierNameTxtField.getText();

                if(Supplier_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier ID field is empty, please enter a Supplier ID to be deleted",
                            "Empty Supplier ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Supplier_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(SuppliersPage.this,
                            "Error: Supplier Name field is empty, please enter a Supplier Name to be deleted",
                            "Empty Supplier Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM SUPPLIER WHERE Supplier_id = ? AND Supplier_name = ?");

                        preparedStatement.setString(1, Supplier_id);
                        preparedStatement.setString(2, Supplier_name);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM SUPPLIER");
                        ResultSet resultSet = selectStatement.executeQuery();
                        supplierTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        supplierIDTxtField.setText("");
                        supplierNameTxtField.setText("");
                        supplierAddressTxtField.setText("");
                        supplierPhoneTxtField.setText("");
                        supplierEmailTxtField.setText("");
                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }

                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        SuppliersPage suppliersPage = new SuppliersPage(null);
    }
}
