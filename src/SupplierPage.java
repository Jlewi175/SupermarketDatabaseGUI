import javax.swing.*;
import java.awt.*;

public class SupplierPage extends JFrame {

    public SupplierPage(JFrame Parent) {
        // Set the window title
        setTitle("Suppliers Page");

        // Set the window size
        setSize(800, 600);

        // Set the background color
        getContentPane().setBackground(Color.RED);

        // Create a logo label
        JLabel logoLabel = new JLabel("Logo goes here");
        logoLabel.setIcon(new ImageIcon("path/to/logo.png"));
        logoLabel.setPreferredSize(new Dimension(50, 50));
        logoLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        // Create a button menu panel
        JPanel buttonMenuPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonMenuPanel.setBackground(Color.WHITE);
        buttonMenuPanel.setForeground(Color.RED);
        buttonMenuPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE));

        // Add the buttons to the button menu panel
        String[] buttonLabels = {"Home", "Category", "Products", "Shipments", "Suppliers", "Inventory", "Product Location", "Account"};
        for (String label : buttonLabels) {
            JButton button = new JButton(label);
            button.setFocusPainted(false);
            button.setForeground(Color.RED);
            button.setFont(button.getFont().deriveFont(Font.BOLD));
            buttonMenuPanel.add(button);
        }

        // Create a title label
        JLabel titleLabel = new JLabel("Suppliers Page", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Create a form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.WHITE),
                BorderFactory.createEmptyBorder(20, 0, 20, 0)
        ));

        // Add the form fields to the form panel
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(0, 0, 10, 0);
        formPanel.add(new JLabel("Supplier Name:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Address:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Phone Number:"), c);
        c.gridy++;
        formPanel.add(new JLabel("Email Address:"), c);
        c.gridx = 1;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        JTextField nameField = new JTextField(20);
        formPanel.add(nameField, c);
        c.gridy++;
        JTextField addressField = new JTextField(20);
        formPanel.add(addressField, c);
        c.gridy++;
        JTextField phoneField = new JTextField(20);
        formPanel.add(phoneField, c);
        c.gridy++;
        JTextField emailField = new JTextField(20);
        formPanel.add(emailField, c);
        c.gridx = 0;
        c.gridy++;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(20, 0, 0, 0);
        formPanel.add(new JButton("Add Supplier"), c);
        c.gridy++;
        formPanel.add(new JButton("Update Supplier"), c);
        c.gridy++;
        formPanel.add(new JButton("Delete Supplier"), c);

        // Create a table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        // Add the table to the table panel
        JTable table = new JTable(new Object[][]{}, new Object[]{"Supplier Name", "Address", "Phone Number", "Email Address"});
        table.setFillsViewportHeight(true);
        JScrollPane scrollPane = new JScrollPane(table);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Add the components to the window
        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(buttonMenuPanel, BorderLayout.NORTH);
        contentPane.add(titleLabel, BorderLayout.CENTER);
        contentPane.add(formPanel, BorderLayout.WEST);
        contentPane.add(tablePanel, BorderLayout.CENTER);
        setContentPane(contentPane);

        setVisible(true);
    }

    public static void main(String[] args) {
        SupplierPage supplierPage = new SupplierPage(null);
    }
}