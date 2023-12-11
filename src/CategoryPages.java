import net.proteanit.sql.DbUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

public class CategoryPages extends JDialog {
    private JPanel CategoryPagePane;
    private JPanel CategoryPagePanel;
    private JButton productLocationButton;
    private JButton categoriesButton;
    private JButton homeButton;
    private JButton suppliersButton;
    private JButton shipmentsButton;
    private JButton inventoryButton;
    private JButton accountButton;
    private JTextField categoryIDTxtField;
    private JTextField categoryNameTxtField;
    private JTextField categoryDescriptionTxtField;
    private JButton addCategoryButton;
    private JButton updateCategoryButton;
    private JButton deleteCategoryButton;
    private JTable categoryTable;
    private JScrollPane categoryScrollPane;
    private JButton productButton;
    private JButton searchButton;
    private Employee weisEmployee;
    public CategoryPages(Employee weisEmployee) throws SQLException {

        this.weisEmployee = weisEmployee;
        setTitle("Weis Markets - Category Page");
        setContentPane(CategoryPagePanel);
        setMinimumSize(new Dimension(1535,850));
        setModal(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM CATEGORY");
        ResultSet resultSet = selectStatement.executeQuery();
        categoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));


        //Go Home
        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomePage homePage = new HomePage(weisEmployee);
            }
        });

        //Go to Category
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

        //Go to Account
        accountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                AccountPage accountPage = new AccountPage(weisEmployee);
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

        //Add Category
        addCategoryButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e) {
                String Category_id = categoryIDTxtField.getText();
                String Category_name = categoryNameTxtField.getText();
                String Category_description = categoryDescriptionTxtField.getText();


                //Boolean variables used to check if CategoryID and CategoryName exists
                boolean CheckCategoryID = false;
                boolean CheckCategoryName = false;


                //Check if Category ID Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM CATEGORY WHERE Category_id = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Category_id);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the CATEGORY Table check if the Category_id is the same the entered in Category_id
                    while (resultSet.next()) {
                        if (resultSet.getString("Category_id").equals(Category_id)) {
                            CheckCategoryID = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }


                //Check if Category Name Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM CATEGORY WHERE Category_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Category_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the CATEGORY Table check if the Category_name is the same the entered in Category_name
                    while (resultSet.next()) {
                        if (resultSet.getString("Category_name").equals(Category_name)) {
                            CheckCategoryName = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
                //Check if Category_Id is the same
                if (CheckCategoryID) {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category ID already exists, please choose another one",
                            "Duplicate Category ID",
                            JOptionPane.ERROR_MESSAGE);
                    //Check if the Category_name is the same
                } else if (CheckCategoryName) {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Name already exists, please choose another one",
                            "Duplicate Category Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category ID field is empty, please enter a Category ID",
                            "Empty Category ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_description.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Description field is empty, please enter a category description",
                            "Empty Category Description",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Name field is empty, please enter a Category Name",
                            "Empty Category Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                //If Category_Id and Category_name are not found in the database, add the entry to the database and table
                else {
                    try {

                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");

                        String sql = "INSERT INTO CATEGORY (Category_id, Category_name, Category_description)"
                                + "VALUES (?, ?, ?)";

                        PreparedStatement preparedStatement = connection.prepareStatement(sql);
                        preparedStatement.setString(1, Category_id);
                        preparedStatement.setString(2, Category_name);
                        preparedStatement.setString(3, Category_description);
                        preparedStatement.executeUpdate();


                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM CATEGORY");
                        ResultSet resultSet = selectStatement.executeQuery();
                        categoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        categoryIDTxtField.setText("");
                        categoryNameTxtField.setText("");
                        categoryDescriptionTxtField.setText("");


                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }
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

        //Update Category
        updateCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String Category_id = categoryIDTxtField.getText();
                String Category_name = categoryNameTxtField.getText();
                String Category_description = categoryDescriptionTxtField.getText();

                //Boolean Variable to check if duplicate Category Name is found
                boolean CheckCategoryName = false;

                //Check if Category Name Exists
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                    String sql = "SELECT * FROM CATEGORY WHERE Category_id = ? OR Category_name = ?";
                    PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    preparedStatement.setString(1, Category_id);
                    preparedStatement.setString(2, Category_name);

                    ResultSet resultSet = preparedStatement.executeQuery();

                    //While there is an entry in the CATEGORY Table check if the Category_name is the same the entered in Category_name
                    while (resultSet.next()) {
                        if(resultSet.getString("Category_id").equals(Category_id))
                        {
                            break;
                        }
                        if (resultSet.getString("Category_name").equals(Category_name)) {
                            CheckCategoryName = true;
                        }
                    }

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                //Check if the Category_name is the same
                if(Category_id.equals(Category_name))
                {

                }
                else if(CheckCategoryName)
                {
                JOptionPane.showMessageDialog(CategoryPages.this,
                        "Error: Category Name already exists, please choose another one",
                        "Duplicate Category Name",
                        JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category ID field is empty, please enter a Category ID",
                            "Empty Category ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_description.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Description field is empty, please enter a category description",
                            "Empty Category Description",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Name field is empty, please enter a Category Name",
                            "Empty Category Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try {
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE CATEGORY SET Category_name = ?, Category_description = ? WHERE Category_id = ?");

                        preparedStatement.setString(1, Category_name);
                        preparedStatement.setString(2, Category_description);
                        preparedStatement.setString(3, Category_id);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM CATEGORY");
                        ResultSet resultSet = selectStatement.executeQuery();
                        categoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        categoryIDTxtField.setText("");
                        categoryNameTxtField.setText("");
                        categoryDescriptionTxtField.setText("");
                    }
                    catch (SQLException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });

        //Delete Category
        deleteCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String Category_id = categoryIDTxtField.getText();
                String Category_name = categoryNameTxtField.getText();

                //Check if the category id and category name is empty if so the user cant delete anything and they are prompted with an error otherwise the category will be deleted
                if(Category_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category ID field is empty, please enter a Category ID to be deleted",
                            "Empty Category ID",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(Category_name.isEmpty())
                {
                    JOptionPane.showMessageDialog(CategoryPages.this,
                            "Error: Category Name field is empty, please enter a Category Name to be deleted",
                            "Empty Category Name",
                            JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    try{
                        Connection connection = DriverManager.getConnection("jdbc:mysql://triton.towson.edu:3360/bdeguz1db", "bdeguz1", "COSC*bo29m");
                        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM CATEGORY WHERE Category_id = ? AND Category_name = ?");

                        preparedStatement.setString(1, Category_id);
                        preparedStatement.setString(2, Category_name);
                        preparedStatement.executeUpdate();

                        PreparedStatement selectStatement = connection.prepareStatement("SELECT * FROM CATEGORY");
                        ResultSet resultSet = selectStatement.executeQuery();
                        categoryTable.setModel(DbUtils.resultSetToTableModel(resultSet));

                        categoryIDTxtField.setText("");
                        categoryNameTxtField.setText("");
                        categoryDescriptionTxtField.setText("");
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

    public static void main(String[] args) throws SQLException
    {
        CategoryPages categoryPages = new CategoryPages(null);
    }
}
