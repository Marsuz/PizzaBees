package gui;

import javax.swing.*;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class Dashboard extends JFrame {
    private JPanel rootPanel;
    private JTextArea inputTextArea;
    private JTextField fileTextField;
    private JButton chooseFileButton;
    private JButton runButton;
    private JProgressBar progressBar;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JPanel argumentsPanel;

    public Dashboard() {
        super("PizzaBees");
        setContentPane(rootPanel);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
