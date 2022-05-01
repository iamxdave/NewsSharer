import javax.swing.*;
import java.awt.*;

public class Window extends JFrame{
    protected JTextArea inputText = new JTextArea();
    protected JTextField inputTopic = new JTextField();

    public JTextArea getInputText() {
        return inputText;
    }

    public JTextField getInputTopic() {
        return inputTopic;
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

    public void setTextArea(JTextArea area, boolean editable) {
        area.setFont(new Font("Arial", Font.PLAIN, 20));
        area.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        area.setMinimumSize(new Dimension(300, 250));
        area.setLineWrap(true);
        area.setEditable(editable);
    }

    public void setButtons(Button button, String text) {
        button.setLabel(text);
        button.setFocusable(false);
        button.setPreferredSize(new Dimension(100, 70));
        button.setBackground(new Color(80, 150, 250));
        button.setForeground(Color.WHITE);
    }

    public void setTextPanels(Panel panel, JTextArea area, String title) {
        JScrollPane scrollPane = new JScrollPane(area, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(250, 230));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        titleLabel.setBackground(new Color(40, 20, 100));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(true);

        panel.add(titleLabel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
    }
}
