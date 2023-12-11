import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.concurrent.Flow;

public class WeisScripting extends JFrame {
    private String URL = "jdbc:mysql://triton.towson.edu:3360/bdeguz1db";
    private String Username = "bdeguz1";
    private String Password = "COSC*bo29m";

    private JTextField inputField;
    private JButton submitButton;
    private JTable outputTable;
    private JButton HomeButton;
    private JButton CategoryButton;
    private JButton ProductsButton;
    private JButton ProductLocationButton;
    private JButton SuppliersButton;
    private JButton ShipmentsButton;
    private JButton InventoryButton;
    private JButton SearchButton;
    private JButton AccountButton;
    private JList<String> queryList;

    private Connection connection;
    private Employee weisEmployee;

    // Query scripts
    final String productPrices = "select Product_name, Price from PRODUCT";
    final String lowInventory = "select Product_name, Quantity from PRODUCT, INVENTORY where PRODUCT.Product_id = INVENTORY.Product_id AND Quantity <= 15";
    final String highInventory = "select Product_name, Quantity from PRODUCT, INVENTORY where PRODUCT.Product_id = INVENTORY.Product_id AND Quantity > 15";
    final String cheapProduct = "select Product_name, Price from PRODUCT where Price <= 5";
    final String expensiveProduct = "select Product_name, Price from PRODUCT where Price > 5";
    final String categoryAthroughL = "SELECT Category_name FROM CATEGORY WHERE Category_name BETWEEN \"A\" AND \"L\" ORDER BY Category_name";
    final String categoryMthroughZ = "SELECT Category_name FROM CATEGORY WHERE Category_name BETWEEN \"M\" AND \"Z\" ORDER BY Category_name";
    final String shipmentsArrivingSoon = "SELECT Shipment_id, Delivery_date, Quantity, Product_id FROM SHIPMENT WHERE Delivery_date BETWEEN curdate() AND curdate() + 7;";
    final String productSupplier = "select Product_name, Supplier_name from PRODUCT;";
    final String handlesShipment = "select First_name, Last_name, Shipment_id from EMPLOYEE, SHIPMENT WHERE EMPLOYEE.Employee_id = SHIPMENT.Employee_id";
    final String handlesInventory = "select First_name, Last_name, Inventory_id from EMPLOYEE, INVENTORY WHERE EMPLOYEE.Employee_id = INVENTORY.Employee_id";
    final  String shipmentDate = "select Shipment_id, Order_date, Delivery_date from SHIPMENT";
    final String defaultSQL = "select Product_id, Product_name, Price from PRODUCT";
    public WeisScripting(Employee weisEmployee) {
        // Set title, default close operation, and size of frame
        this.weisEmployee = weisEmployee;
        setTitle("Java Swing With MySQL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1535, 850);
        setLocationRelativeTo(null);
        //getRootPane().setBorder(BorderFactory.createLineBorder(Color.red, 10));

        //Create fields and buttons for input panel
        inputField = new JTextField(30);
        submitButton = new JButton("Send");
        submitButton.addActionListener(new SubmitButtonListener());
        HomeButton = new JButton("Home");
        CategoryButton = new JButton("Category");
        ProductsButton = new JButton("Product");
        ProductLocationButton = new JButton("Product Location");
        SuppliersButton = new JButton("Suppliers");
        ShipmentsButton = new JButton("Shipments");
        InventoryButton = new JButton("Inventory");
        SearchButton = new JButton("Search");
        AccountButton = new JButton("Account");
        HomeButton.addActionListener(new goToHomePageListener());
        CategoryButton.addActionListener(new categoryListener());
        ProductsButton.addActionListener(new productListener());
        ProductLocationButton.addActionListener(new productLocationListener());
        SuppliersButton.addActionListener(new supplierListener());
        ShipmentsButton.addActionListener(new shipmentListener());
        InventoryButton.addActionListener(new inventoryListener());
        SearchButton.addActionListener(new searchListener());
        AccountButton.addActionListener(new accountListener());

        // Create the input field and submit button panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));

        //Create top menu panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel imageLabel = new JLabel(new ImageIcon("src/Weis Logo.png"));
        topPanel.add(imageLabel);
        topPanel.add(HomeButton);
        topPanel.add(CategoryButton);
        topPanel.add(ProductsButton);
        topPanel.add(ProductLocationButton);
        topPanel.add(SuppliersButton);
        topPanel.add(ShipmentsButton);
        topPanel.add(InventoryButton);
        topPanel.add(SearchButton);
        topPanel.add(AccountButton);

        //Panel for search bar and submitButton
        JPanel bottomPanel = new JPanel();
        bottomPanel.add(inputField);
        bottomPanel.add(submitButton);

        //Panel that combines both the top and bottom panel and puts some space between the two
        inputPanel.add(topPanel);
        inputPanel.add(Box.createVerticalStrut(20));
        inputPanel.add(bottomPanel);


        add(inputPanel, BorderLayout.NORTH);
        inputPanel.setBackground(Color.red);
        topPanel.setBackground(Color.red);
        bottomPanel.setBackground(Color.red);
        inputField.setPreferredSize(new Dimension(200, 50));
        inputField.setFont(new Font("Arial", Font.BOLD, 20));

        //Set Button Colors
        HomeButton.setBackground(Color.WHITE);
        CategoryButton.setBackground(Color.WHITE);
        ProductsButton.setBackground(Color.WHITE);
        ProductLocationButton.setBackground(Color.WHITE);
        SuppliersButton.setBackground(Color.WHITE);
        ShipmentsButton.setBackground(Color.WHITE);
        InventoryButton.setBackground(Color.WHITE);
        SearchButton.setBackground(Color.WHITE);
        AccountButton.setBackground(Color.WHITE);

        inputField.setForeground(Color.red);
        submitButton.setBackground(Color.WHITE);
        submitButton.setForeground(Color.red);
        submitButton.setFont(new Font("Arial", Font.BOLD, 20));
        //inputPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        //inputField.setBorder(BorderFactory.createLineBorder(Color.red, 1));

        // Create the output table panel
        outputTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(outputTable);
        add(scrollPane, BorderLayout.CENTER);
        outputTable.setBackground(Color.red);
        outputTable.setForeground(Color.white);
       // outputTable.setBorder(BorderFactory.createLineBorder(Color.red, 1));
       // scrollPane.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        scrollPane.setBackground(Color.red);
        scrollPane.setForeground(Color.red);

        // Create list model to use with the JList instruction panel
        DefaultListModel<String> listModel = new DefaultListModel<>();
        listModel.addElement("- product price\n");
        listModel.addElement("- low inventory (15 and below)");
        listModel.addElement("- high inventory (more than 15)");
        listModel.addElement("- cheap product (5 and below)");
        listModel.addElement("- expensive product (more than 5)");
        listModel.addElement("- categories a through l");


        DefaultListModel<String> listModel2 = new DefaultListModel<>();
        listModel2.addElement("   - categories m through z");
        listModel2.addElement("   - shipments arriving soon (next 7 days)");
        listModel2.addElement("   - product supplier");
        listModel2.addElement("   - handles shipment");
        listModel2.addElement("   - handles inventory");
        listModel2.addElement("   - shipment date");

        // Create instruction panel
        JPanel instructPanel = new JPanel();
        JLabel label = new JLabel("EXECUTABLE QUERIES");
        JList<String> list = new JList<>(listModel);
        JList<String> list2 = new JList<>(listModel2);
        instructPanel.add(label, BorderLayout.LINE_START);
        instructPanel.add(list, BorderLayout.NORTH);
        instructPanel.add(list2, BorderLayout.EAST);
        add(instructPanel, BorderLayout.SOUTH);
        //instructPanel.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        list.setBackground(Color.red);
        list.setForeground(Color.white);
        list2.setBackground(Color.red);
        list2.setForeground(Color.white);
        list2.setFont(new Font("Arial", Font.BOLD, 15));
        list.setFont(new Font("Arial", Font.BOLD, 15));
        label.setPreferredSize(new Dimension(500, 200));
        label.setForeground(Color.white);
        label.setFont(new Font("Arial", Font.BOLD, 25));
       // label.setBorder(BorderFactory.createLineBorder(Color.red, 1));
       // list.setBorder(BorderFactory.createLineBorder(Color.red, 1));
        instructPanel.setBackground(Color.red);


        // Connect to database
        try {
            connection = DriverManager.getConnection(URL, Username, Password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        setVisible(true);
    }

    private class SubmitButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String input;
            switch (inputField.getText()) {
                case "product price":
                    input = productPrices;
                    break;
                case "low inventory":
                    input = lowInventory;
                    break;
                case "high inventory":
                    input = highInventory;
                    break;
                case "cheap product":
                    input = cheapProduct;
                    break;
                case "expensive product":
                    input = expensiveProduct;
                    break;
                case "categories a through l":
                    input = categoryAthroughL;
                    break;
                case "categories m through z":
                    input = categoryMthroughZ;
                    break;
                case "shipments arriving soon":
                    input = shipmentsArrivingSoon;
                    break;
                case "product supplier":
                    input = productSupplier;
                    break;
                case "handles shipment":
                    input = handlesShipment;
                    break;
                case "handles inventory":
                    input = handlesInventory;
                    break;
                case "shipment date":
                    input = shipmentDate;
                    break;

                    default:
                    input = defaultSQL;
                    break;

            }


            // String input = inputField.getText().toUpperCase();
            // System.out.println(input);
            try {
                // Create statement
                Statement statement = connection.createStatement();

                // Execute query or update
                if (statement.execute(input)) {
                    // Display result in table
                    ResultSet resultSet = statement.getResultSet();

                    // Get metadata for result set
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    System.out.println(resultSet);
                    System.out.println(metaData);
                    int columnCount = metaData.getColumnCount();

                    // Create column headers for output table
                    Object[] columnNames = new Object[columnCount];
                    for (int i = 1; i <= columnCount; i++) {
                        columnNames[i - 1] = metaData.getColumnLabel(i);
                    }

                    // Create default table model with column headers
                    DefaultTableModel model = new DefaultTableModel(columnNames, 0);

                    // Loop through result set and add rows to table model
                    while (resultSet.next()) {
                        Object[] row = new Object[columnCount];
                        for (int i = 1; i <= columnCount; i++) {
                            row[i - 1] = resultSet.getObject(i);
                        }
                        model.addRow(row);
                    }
                    // Set table model in output table
                    outputTable.setModel(model);
                } else {
                    // Display update count
                    int updateCount = statement.getUpdateCount();
                    DefaultTableModel model = new DefaultTableModel(new Object[]{"Output"}, 0);
                    model.addRow(new Object[]{"Updated " + updateCount + " rows"});
                    outputTable.setModel(model);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            // Empty the text field after query
            inputField.setText("");
        }
    }

    //Go Home
    private class goToHomePageListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            HomePage homePage = new HomePage(weisEmployee);
        }
    }

    //Go to Category
    private class categoryListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            try {
                new CategoryPages(weisEmployee);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //Go to Products
    private class productListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new ProductsPage(weisEmployee);
        }
    }

    //Go to Product Location
    private class productLocationListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            try {
                new ProductsLocationPage(weisEmployee);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //Go to Suppliers
    private class supplierListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            try {
                new SuppliersPage(weisEmployee);
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    //Go to Shipments
    private class shipmentListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new ShipmentsPage(weisEmployee);
        }
    }

    //Go to Inventory
    private class inventoryListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new InventoryPage(weisEmployee);
        }
    }

    //Go to Search
    private class searchListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new WeisScripting(weisEmployee);
        }
    }

    //Go to Account
    private class accountListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
            dispose();
            new AccountPage(weisEmployee);
        }
    }

    public static void main(String[] args) {

        new WeisScripting(null);
    }
}
