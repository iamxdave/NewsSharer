import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Client extends Window {
    private Button subscribeTopicButton = new Button();
    private Button refreshTopicButton = new Button();
    private Button unsubscribeTopicButton = new Button();

    private final Handler handler;

    public Client() {
        handler = new Handler("client", this);
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

        setTextArea(inputText, false);

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        centerPanel.add(new JLabel("Enter the topic:"));
        centerPanel.add(inputTopic);
        centerPanel.add(subscribeTopicButton);
        centerPanel.add(refreshTopicButton);
        centerPanel.add(unsubscribeTopicButton);

        inputTopic.setPreferredSize(new Dimension(100, 20));

        setButtons(subscribeTopicButton, "Subscribe");
        setButtons(refreshTopicButton, "Refresh");
        setButtons(unsubscribeTopicButton, "Unsubscribe");

        subscribeTopicButton.addActionListener(this::subscribeTopic);
        refreshTopicButton.addActionListener(this::refreshTopic);
        unsubscribeTopicButton.addActionListener(this::unsubscribeTopic);


        Panel panelNorth = new Panel(new BorderLayout(10, 10));
        setTextPanels(panelNorth, inputText, "Check news from the topic");


        mainPanel.add(panelNorth, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);

        pack();
    }

    private void subscribeTopic(ActionEvent actionEvent) {
        handler.write("subscribe," + inputTopic.getText());
        handler.read();
    }

    private void refreshTopic(ActionEvent actionEvent) {
        handler.write("refresh," + inputTopic.getText());
        handler.read();
    }

    private void unsubscribeTopic(ActionEvent actionEvent) {
        handler.write("unsubscribe," + inputTopic.getText());
        inputText.setText("");
        inputTopic.setText("");
        handler.read();
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.setDisplay(600, 420, "Client");
        client.setGUI();

    }
}
