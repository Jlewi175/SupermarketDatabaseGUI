import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class InventoryPage extends JDialog {
    private JPanel InventoryPagePane;
    private JPanel InventoryPagePanel;
    private JButton productLocationButton;
    private JButton categoriesButton;
    private JButton homeButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JButton productButton;
    private JTextField inventoryValueTxtField;
    private JTextField inventoryIDTxtField;
    private JButton addInventoryButton;
    private JButton updateInventoryButton;
    private JButton deleteInventoryButton;
    private JScrollPane inventoryScrollPane;
    private JTable inventoryTable;
    private JTextField updatedDateTxtField;
    private JTextField quantityTxtField;
    private JComboBox employeeIDComboBox;
    private JComboBox locationIDComboBox;
    private JComboBox productIDComboBox;
    private JTextField selectedProductIDtxtField;
    private JTextField selectedEmployeeIDTxtField;
    private JTextField selectedLocationIDTxtField;
    private JButton searchButton;

    private Employee weisEmployee;

    public InventoryPage(Employee weisEmployee) {
        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Inventory Page");
        setContentPane(InventoryPagePanel);
        setMinimumSize(new Dimension(1535, 850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        try {
            createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Go home
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

        //Go to Product Location
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

        //Go to Account
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountPage accountPage = new AccountPage(weisEmployee);
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

        //Go to Search
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
                WeisScripting weisScripting = new WeisScripting(weisEmployee);
            }
        });


        //Add Inventory
        addInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Inventory_id = inventoryIDTxtField.getText();
                String Inventory_value = inventoryValueTxtField.getText();
                String Updated_date = updatedDateTxtField.getText();
                String Quantity = quantityTxtField.getText();
                String selectedProductID = productIDComboBox.getSelectedItem().toString();
                String selectedEmployeeID = employeeIDComboBox.getSelectedItem().toString();
                String selectedLocationID = locationIDComboBox.getSelectedItem().toString();

                //Boolean Variables to check if Product_id, Employee_id, and Location_id exists already
                Boolean CheckProductID = false;
                Boolean CheckInventoryID = false;

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM INVENTORY WHERE Inventory_id = ? OR Product_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Inventory_id);
                    preparedStatement.setString(2, selectedProductID);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the PRODUCT Table check if the Product_id, Product_name, or Product_serial is the same as the entered in Product_id, Product_name, or Product_serial
                    while (resultSet.next()) {
                        if (resultSet.getString("Inventory_id").equals(Inventory_id)) {
                            CheckInventoryID = true;
                        }

                        if (resultSet.getString("Product_id").equals(selectedProductID)) {
                            CheckProductID = true;
                        }

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Get Product Selection
                productIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedProductIDtxtField.setText(selectedProductID);
                    }
                });

                //Get Employee Selection
                employeeIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedEmployeeIDTxtField.setText(selectedEmployeeID);
                    }
                });

                //Get Location Selection
                locationIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedLocationIDTxtField.setText(selectedLocationID);
                    }
                });

                if(Inventory_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory ID is empty, please enter one in",
                            "EmptyInventory ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckProductID)
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory Entry for that Product ID has already been created, please enter a new Product ID",
                            "Duplicate Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckInventoryID)
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory ID has already been used, please enter a new one",
                            "Duplicate Inventory ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Inventory_value.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory value is empty, please enter one in",
                            "Empty Inventory Value",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Updated_date.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Updated Date is empty, please enter one in",
                            "Empty Updated Date",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Quantity.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Quantity is empty, please enter one in",
                            "Empty Quantity",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedProductID.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Product ID is empty, please enter one in",
                            "Empty Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedEmployeeID.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Employee ID is empty, please enter one in",
                            "Empty Employee ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedLocationID.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Selected Location is empty, please enter one in",
                            "Empty Location ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    double Price = 0;
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                        String sql = "SELECT Price FROM PRODUCT WHERE Product_id = ?";
                        PreparedStatement preparedStatement;
                        preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, selectedProductID);
                        ResultSet resultSet1 = preparedStatement.executeQuery();

                        if (resultSet1.next()) {
                            Price = resultSet1.getDouble("Price");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }


                    //Convert Quantity into double and then mutliply quantity * price
                    double convertQuantityToDouble = Double.parseDouble(Quantity);
                    double CheckInventoryValue = convertQuantityToDouble * Price;


                    //If else that checks if inventory value is equal to quantity * product price, if it is then add inventory else prompt user with error
                    if (Double.compare(CheckInventoryValue, Double.parseDouble(Inventory_value)) == 0) {
                        try {
                            Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                            String sql2 = "INSERT INTO INVENTORY (Inventory_id, Inventory_value, Updated_date, Quantity, Product_id, Employee_id, Location_id)"
                                    + "VALUES (?, ?, ?, ?, ?, ?, ?)";

                            PreparedStatement preparedStatement = connection.prepareStatement(sql2);
                            preparedStatement.setString(1, Inventory_id);
                            preparedStatement.setString(2, Inventory_value);
                            preparedStatement.setString(3, Updated_date);
                            preparedStatement.setString(4, Quantity);
                            preparedStatement.setString(5, selectedProductID);
                            preparedStatement.setString(6, selectedEmployeeID);
                            preparedStatement.setString(7, selectedLocationID);

                            preparedStatement.executeUpdate();

                            PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM INVENTORY");
                            ResultSet resultSet = selectStatement.executeQuery();
                            inventoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                            inventoryIDTxtField.setText("");
                            inventoryValueTxtField.setText("");
                            updatedDateTxtField.setText("");
                            quantityTxtField.setText("");
                            selectedProductIDtxtField.setText("");
                            selectedEmployeeIDTxtField.setText("");
                            selectedLocationIDTxtField.setText("");


                            productIDComboBox.setVisible(true);
                            employeeIDComboBox.setVisible(true);
                            locationIDComboBox.setVisible(true);

                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(InventoryPage.this,
                                "Error: Inventory Value does not equal quantity * product price. Please refer back to the products page to check the price and apply it accordingly",
                                "Incorrect Inventory Value",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });


        //Update Inventory
        updateInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Inventory_id = inventoryIDTxtField.getText();
                String Inventory_value = inventoryValueTxtField.getText();
                String Updated_date = updatedDateTxtField.getText();
                String Quantity = quantityTxtField.getText();
                String selectedProductID = productIDComboBox.getSelectedItem().toString();
                String selectedEmployeeID = employeeIDComboBox.getSelectedItem().toString();
                String selectedLocationID = locationIDComboBox.getSelectedItem().toString();

                //Boolean Variables to check if Product_id, Employee_id, and Location_id exists already
                Boolean CheckProductID = false;
                Boolean CheckInventoryID = false;

                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM INVENTORY WHERE Inventory_id = ? OR Product_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Inventory_id);
                    preparedStatement.setString(2, selectedProductID);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the PRODUCT Table check if the Product_id, Product_name, or Product_serial is the same as the entered in Product_id, Product_name, or Product_serial
                    while (resultSet.next()) {
                        if (resultSet.getString("Inventory_id").equals(Inventory_id)) {
                            CheckInventoryID = true;
                        }

                        if(resultSet.getString("Product_id").equals(selectedProductID))
                        {
                            CheckProductID = true;
                        }

                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Get Product Selection
                productIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedProductIDtxtField.setText(selectedProductID);
                    }
                });

                //Get Employee Selection
                employeeIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedEmployeeIDTxtField.setText(selectedEmployeeID);
                    }
                });

                //Get Location Selection
                locationIDComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        selectedLocationIDTxtField.setText(selectedLocationID);
                    }
                });

                //Constraint to find out if the inventory value is not equal to the quantity * price
                Connection connection = null;
                double Price = 0;
                try {
                    connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                    String sql = "SELECT Price FROM PRODUCT WHERE Product_id = ?";
                    PreparedStatement preparedStatement;
                    preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, selectedProductID);
                    ResultSet resultSet1 = preparedStatement.executeQuery();

                    if (resultSet1.next()) {
                        Price = resultSet1.getDouble("Price");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

                double convertQuantityToDouble = Double.parseDouble(Quantity);
                double CheckInventoryValue = convertQuantityToDouble * Price;

                if (Double.compare(CheckInventoryValue, Double.parseDouble(Inventory_value)) != 0)
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory Value does not equal quantity * product price. Please refer back to the products page to check the price and apply it accordingly",
                            "Incorrect Inventory Value",
                            JOptionPane.ERROR_MESSAGE);

                }
                else if (Inventory_value.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory ID is empty, please enter one in",
                            "EmptyInventory ID",
                            JOptionPane.ERROR_MESSAGE);
                }

                else
                {

                    try {

                        connection = DriverManager.getConnection("URL", "TEST", "test");

                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE INVENTORY SET Inventory_value = ?, Updated_date = ?, Quantity = ?, Product_id = ?, Employee_id = ?, Location_id = ? WHERE Inventory_id = ?");
                        preparedStatement.setString(1, Inventory_value);
                        preparedStatement.setString(2, Updated_date);
                        preparedStatement.setString(3, Quantity);
                        preparedStatement.setString(4, selectedProductID);
                        preparedStatement.setString(5, selectedEmployeeID);
                        preparedStatement.setString(6, selectedLocationID);
                        preparedStatement.setString(7, Inventory_id);

                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM INVENTORY");
                        ResultSet resultSet = selectStatement.executeQuery();
                        inventoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        inventoryIDTxtField.setText("");
                        inventoryValueTxtField.setText("");
                        updatedDateTxtField.setText("");
                        quantityTxtField.setText("");
                        selectedProductIDtxtField.setText("");
                        selectedEmployeeIDTxtField.setText("");
                        selectedLocationIDTxtField.setText("");


                        productIDComboBox.setVisible(true);
                        employeeIDComboBox.setVisible(true);
                        locationIDComboBox.setVisible(true);


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Delete Inventory
        deleteInventoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Inventory_id = inventoryIDTxtField.getText();
                String Inventory_value = inventoryValueTxtField.getText();
                String Updated_date = updatedDateTxtField.getText();
                String Quantity = quantityTxtField.getText();
                String selectedProductID = productIDComboBox.getSelectedItem().toString();
                String selectedEmployeeID = employeeIDComboBox.getSelectedItem().toString();
                String selectedLocationID = locationIDComboBox.getSelectedItem().toString();

                //Boolean Variables to check if Product_id, Employee_id, and Location_id exists already
                Boolean CheckProductID = false;
                Boolean CheckInventoryID = false;

                if(Inventory_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(InventoryPage.this,
                            "Error: Inventory ID is empty, please enter one in to be deleted",
                            "EmptyInventory ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM INVENTORY WHERE Inventory_id = ?");
                        preparedStatement.setString(1, Inventory_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM INVENTORY");
                        ResultSet resultSet = selectStatement.executeQuery();
                        inventoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        inventoryIDTxtField.setText("");
                        inventoryValueTxtField.setText("");
                        updatedDateTxtField.setText("");
                        quantityTxtField.setText("");
                        selectedProductIDtxtField.setText("");
                        selectedEmployeeIDTxtField.setText("");
                        selectedLocationIDTxtField.setText("");


                        productIDComboBox.setVisible(true);
                        employeeIDComboBox.setVisible(true);
                        locationIDComboBox.setVisible(true);


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        setVisible(true);
    }

    public void calculateInventoryValue() throws SQLException
    {
        String selectedProductID = productIDComboBox.getSelectedItem().toString();
        Connection connection = null;
        double Price = 0;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT Price FROM PRODUCT WHERE Product_id = ?";
            PreparedStatement preparedStatement;
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, selectedProductID);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            if (resultSet1.next()) {
                Price = resultSet1.getDouble("Price");
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }


    //Create Connection and Populate ComboBoxes
    public void createConnection() throws SQLException {

        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM INVENTORY");
        ResultSet resultSet = selectStatement.executeQuery();
        inventoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

        //Check if ProductID, ProductName, or ProductSerial Exists
        try {
            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT * FROM PRODUCT";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet2 = preparedStatement.executeQuery();

            while (resultSet2.next()) {
                productIDComboBox.addItem(resultSet2.getString("Product_id"));
                productIDComboBox.setVisible(true);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT * FROM EMPLOYEE";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet2 = preparedStatement.executeQuery();

            while (resultSet2.next()) {
                employeeIDComboBox.addItem(resultSet2.getString("Employee_id"));
                employeeIDComboBox.setVisible(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        try {
            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT * FROM LOCATION";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet2 = preparedStatement.executeQuery();

            while (resultSet2.next()) {
                locationIDComboBox.addItem(resultSet2.getString("Location_id"));
                locationIDComboBox.setVisible(true);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args)
    {
        InventoryPage inventoryPage = new InventoryPage(null);
    }
}

//connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
/*
 if(CheckProductID)
                    {
                        JOptionPane.showMessageDialog(InventoryPage.this,
                                "Error: Inventory Entry for that Product ID has already been created, please enter a new Product ID",
                                "Duplicate Product ID",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else if(CheckInventoryID)
                    {
                        JOptionPane.showMessageDialog(InventoryPage.this,
                                "Error: Inventory ID has already been used, please enter a new one",
                                "Duplicate Inventory ID",
                                JOptionPane.ERROR_MESSAGE);
                    }
                    else {

                    }
  else if(Inventory_id.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Inventory ID is empty, please enter one in",
         "EmptyInventory ID",
         JOptionPane.ERROR_MESSAGE);
         } else if(Inventory_value.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Inventory value is empty, please enter one in",
         "Empty Inventory Value",
         JOptionPane.ERROR_MESSAGE);
         } else if(Updated_date.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Updated Date is empty, please enter one in",
         "Empty Updated Date",
         JOptionPane.ERROR_MESSAGE);
         } else if(Quantity.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Quantity is empty, please enter one in",
         "Empty Quantity",
         JOptionPane.ERROR_MESSAGE);
         } else if(selectedProductID.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Product ID is empty, please enter one in",
         "Empty Product ID",
         JOptionPane.ERROR_MESSAGE);
         } else if(selectedEmployeeID.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Employee ID is empty, please enter one in",
         "Empty Employee ID",
         JOptionPane.ERROR_MESSAGE);
         } else if(selectedLocationID.isEmpty())
         {
         JOptionPane.showMessageDialog(InventoryPage.this,
         "Error: Selected Location is empty, please enter one in",
         "Empty Location ID",
         JOptionPane.ERROR_MESSAGE);
         }

 */