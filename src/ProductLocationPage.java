import javax.swing.*;
import java.awt.*;

public class ProductLocationPage extends JFrame {

    private JLabel logo;
    private JPanel topNav;
    private JButton addLocationBtn;
    private JButton updateLocationBtn;
    private JButton deleteLocationBtn;
    private JLabel aisleNumberLabel;
    private JTextField aisleNumberTextField;
    private JLabel shelfLocationLabel;
    private JComboBox<String> shelfLocationComboBox;
    private JButton searchBtn;
    private JTable table;
    private JButton button1;
    private JRadioButton radioButton1;

    public ProductLocationPage(JFrame parent) {
        setTitle("Product Location Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Logo label
        logo = new JLabel("Logo goes here");
        logo.setPreferredSize(new Dimension(100, 50));

        // Top navigation bar panel
        topNav = new JPanel();
        topNav.setLayout(new FlowLayout(FlowLayout.LEFT));
        topNav.setBackground(Color.RED);
        topNav.add(new JButton("Home"));
        topNav.add(new JButton("Category"));
        topNav.add(new JButton("Products"));
        topNav.add(new JButton("Shipments"));
        topNav.add(new JButton("Suppliers"));
        topNav.add(new JButton("Inventory"));
        topNav.add(new JButton("Product Location"));
        topNav.add(new JButton("Account"));

        // CRUD buttons
        addLocationBtn = new JButton("Add Location");
        updateLocationBtn = new JButton("Update Location");
        deleteLocationBtn = new JButton("Delete Location");

        // Form
        aisleNumberLabel = new JLabel("Aisle Number:");
        aisleNumberTextField = new JTextField(10);

        shelfLocationLabel = new JLabel("Shelf Location:");
        shelfLocationComboBox = new JComboBox<String>(new String[]{"Bottom", "Middle", "Top"});

        searchBtn = new JButton("Search");

        JPanel formPanel = new JPanel(new GridLayout(0, 1));
        formPanel.add(aisleNumberLabel);
        formPanel.add(aisleNumberTextField);
        formPanel.add(shelfLocationLabel);
        formPanel.add(shelfLocationComboBox);
        formPanel.add(searchBtn);

        // Table
        table = new JTable(new Object[][]{{"1", "Bottom"}, {"2", "Middle"}, {"3", "Top"}},
                new String[]{"Aisle Number", "Shelf Location"});

        // Add components to frame
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(logo, BorderLayout.WEST);
        getContentPane().add(topNav, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.add(addLocationBtn);
        buttonPanel.add(updateLocationBtn);
        buttonPanel.add(deleteLocationBtn);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(formPanel, BorderLayout.CENTER);
        getContentPane().add(new JScrollPane(table), BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

    public static void main(String[] args) {
        ProductLocationPage productLocationPage = new ProductLocationPage(null);
    }
}
