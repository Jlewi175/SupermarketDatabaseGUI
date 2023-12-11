import javax.swing.*;
import java.awt.*;

public class CategoryPage extends JFrame {

    public CategoryPage (JFrame Parent) {
        setTitle("Category Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(1535,850));


        // Create a panel for the logo and the navbar
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BorderLayout());
        JLabel logoLabel = new JLabel(new ImageIcon("path/to/logo.png"));
        headerPanel.add(logoLabel, BorderLayout.WEST);

        // Create the navbar
        JPanel navbarPanel = new JPanel(new GridLayout(1, 0));
        String[] navItems = {"Home", "Category", "Products", "Shipments", "Suppliers", "Inventory", "Product Location", "Account"};
        for (String navItem : navItems) {
            navbarPanel.add(new JButton(navItem));
        }
        navbarPanel.setBackground(Color.red);
        headerPanel.add(navbarPanel, BorderLayout.CENTER);

        // Add the header panel to the frame
        add(headerPanel, BorderLayout.NORTH);

        // Add the CRUD buttons
        JPanel crudPanel = new JPanel();
        crudPanel.add(new JButton("Add Category"));
        crudPanel.add(new JButton("Update Category"));
        crudPanel.add(new JButton("Delete Category"));
        crudPanel.setBackground(Color.red);
        add(crudPanel, BorderLayout.WEST);

        // Add the form
        JPanel formPanel = new JPanel(new BorderLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));
        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        inputPanel.add(new JLabel("Category Name"));
        inputPanel.add(new JTextField());
        inputPanel.add(new JLabel("Category Description"));
        inputPanel.add(new JTextArea(7, 0));
        formPanel.add(inputPanel, BorderLayout.CENTER);
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JButton("Search"));
        formPanel.add(buttonPanel, BorderLayout.SOUTH);
        formPanel.setBackground(Color.red);
        add(formPanel, BorderLayout.CENTER);

        // Add the table
        JTable table = new JTable(new Object[][]{{"Category 1", "Category 1 description"}, {"Category 2", "Category 2 description"}, {"Category 3", "Category 3 description"}}, new Object[]{"Category Name", "Category Description"});
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        CategoryPage categoryPage = new CategoryPage(null);
    }
}
