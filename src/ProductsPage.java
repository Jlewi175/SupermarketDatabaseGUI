import net.proteanit.sql.DbUtils;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ProductsPage extends JDialog{
    private JPanel ProductsPagePanel;
    private JButton productLocationButton;
    private JButton categoriesButton;
    private JButton homeButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JButton productButton;
    private JPanel ProductsPagePane;
    private JTextField productNameTxtField;
    private JTextField expirationDateTxtField;
    private JTextField productIDTxtField;
    private JButton addProductButton;
    private JButton updateProductButton;
    private JButton deleteProductButton;
    private JScrollPane productScrollPane;
    private JTable productTable;
    private JTextField productSerialNoTxtField;
    private JTextField priceTxtField;
    private JTextField productDescriptionTxtField;
    private JComboBox categoryNameComboBox;
    private JComboBox supplierIdComboBox;
    private JTextField categorySelectionTxtField;
    private JTextField supplierIDtxtField;
    private JButton searchButton;

    private Employee weisEmployee;

    public ProductsPage(Employee weisEmployee)
    {
        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Products Page");
        setContentPane(ProductsPagePanel);
        setMinimumSize(new Dimension(1535,850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Establish Connection and Populate the Form
        try {
            createConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

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

        //Go to Supplier
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


        //Go to Shipment
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


        //Go to Product Page
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

        //Add Products
        addProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Product_id = productIDTxtField.getText();
                String Exp_date = expirationDateTxtField.getText();
                String Product_name = productNameTxtField.getText();
                String Product_serial = productSerialNoTxtField.getText();
                String Price = priceTxtField.getText();
                String Product_description = productDescriptionTxtField.getText();
                String selectedCategory = categoryNameComboBox.getSelectedItem().toString();
                String selectedSupplier = supplierIdComboBox.getSelectedItem().toString();


                //Boolean Variables to check if Product_id, Product_serial, and Product_name exists already
                Boolean CheckProductID = false;
                Boolean CheckProductName = false;
                Boolean CheckProductSerial = false;


                //Check if ProductID, ProductName, or ProductSerial Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM PRODUCT WHERE Product_id = ? OR Product_name = ? OR Product_serial = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Product_id);
                    preparedStatement.setString(2, Product_name);
                    preparedStatement.setString(3, Product_serial);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the PRODUCT Table check if the Product_id, Product_name, or Product_serial is the same as the entered in Product_id, Product_name, or Product_serial
                    while (resultSet.next()) {
                        if (resultSet.getString("Product_id").equals(Product_id)) {
                            CheckProductID = true;
                        }

                        if(resultSet.getString("Product_name").equals(Product_name))
                        {
                            CheckProductName = true;
                        }

                        if(resultSet.getString("Product_serial").equals(Product_serial))
                        {
                            CheckProductSerial = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Get Category Selection
                categoryNameComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categorySelectionTxtField.setText(selectedCategory);
                    }
                });

                //Get Supplier Selection
                supplierIDtxtField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        supplierIDtxtField.setText(selectedSupplier);
                    }
                });


                //If else statement that prompts user error if duplicate productID, productName, or productSerial is found, if not add new product
                if(CheckProductID)
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product ID already exists, please choose another one",
                            "Duplicate Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product ID field is empty, please enter a Product ID",
                            "Empty Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_description.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product field is empty, please enter a Product Description",
                            "Empty Product Description",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Name field is empty, please enter a Product Name",
                            "Empty Product Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Exp_date.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Exp Date field is empty, please enter a Exp Date",
                            "Empty Exp Date",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Price.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Price field is empty, please enter a Price",
                            "Empty Price",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_serial.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Serial field is empty, please enter a Product Serial",
                            "Empty Product Serial",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedCategory.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Selected Category ComboBox is empty, please enter a Selected Category",
                            "Empty Selected Category",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedSupplier.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Selected Supplier field is empty, please enter a Selected Supplier",
                            "Empty Selected Supplier",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckProductName)
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product name already exists, please choose another one",
                            "Duplicate Product Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckProductSerial)
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Serial already exists, please choose another one",
                            "Duplicate Product Serial",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                        String sql = "INSERT INTO PRODUCT (Product_id, Exp_date, Product_name, Product_serial, Price, Product_description, Category_name, Supplier_name)"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, Product_id);
                        preparedStatement.setString(2, Exp_date);
                        preparedStatement.setString(3, Product_name);
                        preparedStatement.setString(4, Product_serial);
                        preparedStatement.setString(5, Price);
                        preparedStatement.setString(6, Product_description);
                        preparedStatement.setString(7, selectedCategory);
                        preparedStatement.setString(8, selectedSupplier);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM PRODUCT");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productIDTxtField.setText("");
                        expirationDateTxtField.setText("");
                        productNameTxtField.setText("");
                        productSerialNoTxtField.setText("");
                        priceTxtField.setText("");
                        productDescriptionTxtField.setText("");
                        categorySelectionTxtField.setText("");
                        supplierIDtxtField.setText("");

                        categoryNameComboBox.setVisible(true);
                        supplierIdComboBox.setVisible(true);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });

        //Update Products
        updateProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Product_id = productIDTxtField.getText();
                String Exp_date = expirationDateTxtField.getText();
                String Product_name = productNameTxtField.getText();
                String Product_serial = productSerialNoTxtField.getText();
                String Price = priceTxtField.getText();
                String Product_description = productDescriptionTxtField.getText();
                String selectedCategory = categoryNameComboBox.getSelectedItem().toString();
                String selectedSupplier = supplierIdComboBox.getSelectedItem().toString();

                //Boolean Variables to check if Product_serial, and Product_name exists already
                Boolean CheckProductName = false;
                Boolean CheckProductSerial = false;


                //Check if ProductName, or ProductSerial Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM PRODUCT WHERE Product_id = ? OR Product_name = ? OR Product_serial = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Product_id);
                    preparedStatement.setString(2, Product_name);
                    preparedStatement.setString(3, Product_serial);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the PRODUCT Table check if the Product_name, or Product_serial is the same as the entered in Product_id, Product_name, or Product_serial
                    while (resultSet.next()) {
                        if(resultSet.getString("Product_id").equals(Product_id))
                        {
                            break;
                        }
                        if(resultSet.getString("Product_name").equals(Product_name))
                        {
                            CheckProductName = true;
                        }

                        if(resultSet.getString("Product_serial").equals(Product_serial))
                        {
                            CheckProductSerial = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                //Get Category Selection
                categoryNameComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categorySelectionTxtField.setText(selectedCategory);
                    }
                });

                //Get Supplier Selection
                supplierIDtxtField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        supplierIDtxtField.setText(selectedSupplier);
                    }
                });

                //If else statement that prompts user error if duplicate productName, or productSerial is found, if not update product
                if(Product_id.equals(Product_name))
                {

                }
                else if(CheckProductName)
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product name already exists, please choose another one",
                            "Duplicate Product Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product ID field is empty, please enter a Product ID",
                            "Empty Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_description.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product field is empty, please enter a Product Description",
                            "Empty Product Description",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Name field is empty, please enter a Product Name",
                            "Empty Product Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Exp_date.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Exp Date field is empty, please enter a Exp Date",
                            "Empty Exp Date",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Price.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Price field is empty, please enter a Price",
                            "Empty Price",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Product_serial.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Serial field is empty, please enter a Product Serial",
                            "Empty Product Serial",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedCategory.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Selected Category ComboBox is empty, please enter a Selected Category",
                            "Empty Selected Category",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(selectedSupplier.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Selected Supplier field is empty, please enter a Selected Supplier",
                            "Empty Selected Supplier",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(CheckProductSerial)
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product Serial already exists, please choose another one",
                            "Duplicate Product Serial",
                            JOptionPane.ERROR_MESSAGE);
                }
                else {

                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCT SET Exp_date = ?, Product_name = ?, Product_serial = ?, Price = ?, Product_description = ?, Category_name = ?, Supplier_name = ? WHERE Product_id = ?");

                        preparedStatement.setString(1, Exp_date);
                        preparedStatement.setString(2, Product_name);
                        preparedStatement.setString(3, Product_serial);
                        preparedStatement.setString(4, Price);
                        preparedStatement.setString(5, Product_description);
                        preparedStatement.setString(6, selectedCategory);
                        preparedStatement.setString(7, selectedSupplier);
                        preparedStatement.setString(8, Product_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM PRODUCT");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productIDTxtField.setText("");
                        expirationDateTxtField.setText("");
                        productNameTxtField.setText("");
                        productSerialNoTxtField.setText("");
                        priceTxtField.setText("");
                        productDescriptionTxtField.setText("");
                        categorySelectionTxtField.setText("");
                        supplierIDtxtField.setText("");

                        categoryNameComboBox.setVisible(true);
                        supplierIdComboBox.setVisible(true);

                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Delete Products
        deleteProductButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Product_id = productIDTxtField.getText();
                String Exp_date = expirationDateTxtField.getText();
                String Product_name = productNameTxtField.getText();
                String Product_serial = productSerialNoTxtField.getText();
                String Price = priceTxtField.getText();
                String Product_description = productDescriptionTxtField.getText();
                String selectedCategory = categoryNameComboBox.getSelectedItem().toString();
                String selectedSupplier = supplierIdComboBox.getSelectedItem().toString();

                //Get Category Selection
                categoryNameComboBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        categorySelectionTxtField.setText(selectedCategory);
                    }
                });

                //Get Supplier Selection
                supplierIDtxtField.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        supplierIDtxtField.setText(selectedSupplier);
                    }
                });

                if(Product_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(ProductsPage.this,
                            "Error: Product ID is empty, please enter a Product ID to be deleted",
                            "Empty Product ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCT WHERE Product_id = ?");

                        preparedStatement.setString(1, Product_id);

                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM PRODUCT");
                        ResultSet resultSet = selectStatement.executeQuery();
                        productTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        productIDTxtField.setText("");
                        expirationDateTxtField.setText("");
                        productNameTxtField.setText("");
                        productSerialNoTxtField.setText("");
                        priceTxtField.setText("");
                        productDescriptionTxtField.setText("");
                        categorySelectionTxtField.setText("");
                        supplierIDtxtField.setText("");

                        categoryNameComboBox.setVisible(true);
                        supplierIdComboBox.setVisible(true);
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

    public void createConnection() throws SQLException
    {
        //Populate Products Table
        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM PRODUCT");
        ResultSet resultSet = selectStatement.executeQuery();
        productTable.setModel(DbUtils.resultSetToTableModel(resultSet));


        //Populate Supplier ComboBox
        String supplierID = null;
        try {

            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT * FROM SUPPLIER";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet1 = preparedStatement.executeQuery();

            while(resultSet1.next())
            {
                supplierIdComboBox.addItem(resultSet1.getString("Supplier_name"));
                supplierIdComboBox.setVisible(true);
            }

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }


        //Populate Category ComboBox
        String categoryID = null;
        try {
            connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

            String sql = "SELECT * FROM CATEGORY";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet2 = preparedStatement.executeQuery();

            while (resultSet2.next()) {
                categoryNameComboBox.addItem(resultSet2.getString("Category_name"));
                categoryNameComboBox.setVisible(true);
            }
        } catch (SQLException ex){
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws SQLException {
        ProductsPage productsPage = new ProductsPage(null);
    }
}
