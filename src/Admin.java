import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Admin extends Window {
    private Button addTopicButton = new Button();
    private Button updateTopicButton = new Button();
    private Button removeTopicButton = new Button();

    private final Handler handler;

    public Admin() {
        handler = new Handler("admin", this);
        handler.connect("localhost", 6660);
    }
    public void setDisplay(int width, int height, String title) {
        setPreferredSize(new Dimension(width, height));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle(title);

        pack();

        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);

    }


    public void setGUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        setTextArea(inputText, true);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        centerPanel.add(new JLabel("Enter the topic:"));
        centerPanel.add(inputTopic);
        centerPanel.add(addTopicButton);
        centerPanel.add(updateTopicButton);
        centerPanel.add(removeTopicButton);

        inputTopic.setPreferredSize(new Dimension(100, 20));

        setButtons(addTopicButton, "Add");
        setButtons(updateTopicButton, "Update");
        setButtons(removeTopicButton, "Remove");

        addTopicButton.addActionListener(this::addTopic);
        updateTopicButton.addActionListener(this::updateTopic);
        removeTopicButton.addActionListener(this::removeTopic);


        Panel panelNorth = new Panel(new BorderLayout(10, 10));
        setTextPanels(panelNorth, inputText, "Add news to the topic");


        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        pack();
    }
    private void addTopic(ActionEvent actionEvent) {
        if (inputTopic.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter the topic before adding it on the server", "Error Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            handler.write("add," + inputTopic.getText() + "," + inputText.getText());
            handler.read();
        }
        inputText.setText("");
    }
    private void updateTopic(ActionEvent actionEvent) {
        if (inputTopic.getText().isEmpty()) {
            JOptionPane.showMessageDialog(new JFrame(), "Enter the topic before updating it on the server", "Error Message", JOptionPane.INFORMATION_MESSAGE);
        } else {
            handler.write("update," + inputTopic.getText() + "," + inputText.getText());
            handler.read();
        }
        inputText.setText("");
    }

    private void removeTopic(ActionEvent actionEvent) {
        handler.write("remove," + inputTopic.getText());
        inputText.setText("");
        inputTopic.setText("");
        handler.read();
    }

    public static void main(String[] args) {
        Admin admin = new Admin();
        admin.setDisplay(600, 420, "Administrator");
        admin.setGUI();

    }
}
