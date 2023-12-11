import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProductsLocationPage extends JDialog {
    private JPanel ProductLocationPanel;
    private JButton productLocationButton;
    private JButton categoriesButton;
    private JButton homeButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JTextField productLocationIDTxtField;
    private JButton addProductLocation;
    private JButton updateProductLocationButton;
    private JButton deleteProductLocationButton;
    private JScrollPane productLocationScrollPane;
    private JTable productLocationTable;
    private JTextField productLocationShelfTxtField;
    private JTextField productLocationAisleNumberTxtField;
    private JButton productButton;
    private JButton searchButton;

    private Employee weisEmployee;
    public ProductsLocationPage(Employee weisEmployee) throws SQLException
    {
        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Products Location Page");
        setContentPane(ProductLocationPanel);
        setMinimumSize(new Dimension(1535,850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM LOCATION");
        ResultSet resultSet = selectStatement.executeQuery();
        productLocationTable.setModel(DbUtils.resultSetToTableModel(resultSet));

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

        //Add Product Location
        addProductLocation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Location_id = productLocationIDTxtField.getText();
                String Aisle = productLocationAisleNumberTxtField.getText();
                String Shelf = productLocationShelfTxtField.getText();

                //Boolean Variable used to check if Product Location ID already exists
                boolean CheckProductLocationID = false;

                //Check if Product Location ID Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM LOCATION WHERE Location_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Location_id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the Product Location Table check if the Location_id is the same the entered in Location_id
                    while (resultSet.next()) {
                        if (resultSet.getString("Location_id").equals(Location_id)) {
                            CheckProductLocationID = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Check if ProductLocation Matches, if so Prompt Error
                if(CheckProductLocationID)
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Product Location ID already exists, please choose another one",
                            "Duplicate Product Location ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Location_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Location ID field is empty, please enter a Locaiton ID",
                            "Empty Location ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Aisle.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Aisle field is empty, please enter a Aisle",
                            "Empty Aisle",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Shelf.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Shelf field is empty, please enter a Shelf",
                            "Empty Shelf",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                        String sql = "INSERT INTO LOCATION (Location_id, Aisle, Shelf)"
                                + "VALUES (?, ?, ?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, Location_id);
                        preparedStatement.setString(2, Aisle);
                        preparedStatement.setString(3, Shelf);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM LOCATION");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productLocationTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productLocationIDTxtField.setText("");
                        productLocationAisleNumberTxtField.setText("");
                        productLocationShelfTxtField.setText("");

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Update Product Location
        updateProductLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Location_id = productLocationIDTxtField.getText();
                String Aisle = productLocationAisleNumberTxtField.getText();
                String Shelf = productLocationShelfTxtField.getText();


                if(Location_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Location ID field is empty, please enter a Locaiton ID",
                            "Empty Location ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Aisle.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Aisle field is empty, please enter a Aisle",
                            "Empty Aisle",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Shelf.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Shelf field is empty, please enter a Shelf",
                            "Empty Shelf",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE LOCATION SET Aisle = ?, Shelf = ? WHERE Location_id = ?");

                        preparedStatement.setString(1, Aisle);
                        preparedStatement.setString(2, Shelf);
                        preparedStatement.setString(3, Location_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM LOCATION");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productLocationTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productLocationIDTxtField.setText("");
                        productLocationAisleNumberTxtField.setText("");
                        productLocationShelfTxtField.setText("");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Delete Product Location
        deleteProductLocationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Location_id = productLocationIDTxtField.getText();

                if(Location_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsLocationPage.this,
                            "Error: Location ID field is empty, please enter a Locaiton ID to be deleted",
                            "Empty Location ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM LOCATION WHERE Location_id = ?");

                        preparedStatement.setString(1, Location_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM LOCATION");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productLocationTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productLocationIDTxtField.setText("");
                        productLocationAisleNumberTxtField.setText("");
                        productLocationShelfTxtField.setText("");
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        ProductsLocationPage productsLocationPage = new ProductsLocationPage(null);
    }
}
