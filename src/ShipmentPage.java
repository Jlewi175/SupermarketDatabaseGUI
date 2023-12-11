import javax.swing.*;
import java.awt.*;

public class ShipmentPage extends JFrame {

    public ShipmentPage(JFrame parent) {
        setTitle("Shipments Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Set background color
        getContentPane().setBackground(Color.RED);

        // Logo
        JPanel logoPanel = new JPanel();
        JLabel logo = new JLabel("Logo goes here");
        logoPanel.add(logo);

        // Top navigation bar
        JPanel topNavPanel = new JPanel();
        topNavPanel.setBackground(Color.WHITE);
        topNavPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        String[] navLinks = {"Home", "Category", "Products", "Shipments", "Suppliers", "Inventory", "Product Location",
                "Account"};
        for (String navLink : navLinks) {
            JButton button = new JButton(navLink);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setFont(new Font("Arial", Font.PLAIN, 12));
            if (navLink.equals("Shipments")) {
                button.setBackground(Color.RED);
                button.setForeground(Color.WHITE);
            }
            topNavPanel.add(button);
        }

        // CRUD buttons
        JPanel crudPanel = new JPanel();
        crudPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton addBtn = new JButton("Add Shipment");
        JButton updateBtn = new JButton("Update Shipment");
        JButton deleteBtn = new JButton("Delete Shipment");
        addBtn.setBackground(Color.PINK);
        addBtn.setForeground(Color.RED);
        updateBtn.setBackground(Color.PINK);
        updateBtn.setForeground(Color.RED);
        deleteBtn.setBackground(Color.PINK);
        deleteBtn.setForeground(Color.RED);
        crudPanel.add(addBtn);
        crudPanel.add(updateBtn);
        crudPanel.add(deleteBtn);

        // Shipment form
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JLabel productNameLabel = new JLabel("Product Name");
        String[] productNames = {"Product 1", "Product 2", "Product 3"};
        JComboBox<String> productNameCombo = new JComboBox<>(productNames);
        JLabel supplierNameLabel = new JLabel("Supplier Name");
        String[] supplierNames = {"Supplier 1", "Supplier 2", "Supplier 3"};
        JComboBox<String> supplierNameCombo = new JComboBox<>(supplierNames);
        JLabel deliveryDateLabel = new JLabel("Estimated Delivery Date");
        String[] deliveryDates = {"1 week", "2 weeks", "3 weeks"};
        JComboBox<String> deliveryDateCombo = new JComboBox<>(deliveryDates);
        JLabel shipmentStatusLabel = new JLabel("Shipment Status");
        String[] shipmentStatuses = {"Shipped", "Delivered", "Not Delivered"};
        JComboBox<String> shipmentStatusCombo = new JComboBox<>(shipmentStatuses);
        JLabel priceLabel = new JLabel("Price");
        JTextField priceField = new JTextField();
        formPanel.add(productNameLabel);
        formPanel.add(productNameCombo);
        formPanel.add(supplierNameLabel);
        formPanel.add(supplierNameCombo);
        formPanel.add(deliveryDateLabel);
        formPanel.add(deliveryDateCombo);
        formPanel.add(shipmentStatusLabel);
        formPanel.add(shipmentStatusCombo);
        formPanel.add(priceLabel);
        formPanel.add(priceField);

        // Table
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        JTable table = new
                JTable(new Object[][]{
                {"Product 1", "Supplier 1", "1 week", "Shipped", "$100.00"},
                {"Product 2", "Supplier 2", "2 weeks", "Delivered", "$150.00"},
                {"Product 3", "Supplier 3", "3 weeks", "Not Delivered", "$200.00"}
        },
                new Object[]{"Product Name", "Supplier Name", "Estimated Delivery Date", "Shipment Status", "Price"});
        table.setPreferredScrollableViewportSize(new Dimension(750, 200));
        table.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(table);
        tablePanel.add(tableScrollPane);


        // Add components to content pane
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(logoPanel, BorderLayout.PAGE_START);
        contentPane.add(topNavPanel, BorderLayout.NORTH);
        contentPane.add(crudPanel, BorderLayout.PAGE_END);
        contentPane.add(formPanel, BorderLayout.CENTER);
        contentPane.add(tablePanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {

        ShipmentPage shipmentPage = new ShipmentPage(null);
    }
}