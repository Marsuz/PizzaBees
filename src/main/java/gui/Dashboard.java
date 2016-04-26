package gui;

import app.AppInput;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class Dashboard extends JFrame {
    private static final Logger logger = Logger.getLogger(Dashboard.class);

    private JPanel rootPanel;
    private JTextArea inputTextArea;
    private JTextField fileTextField;
    private JButton chooseFileButton;
    private JButton runButton;
    private JProgressBar progressBar;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JPanel argumentsPanel;

    private JFileChooser fileChooser;

    public Dashboard() {
        super("PizzaBees");
        fileChooser = initializeFileChooser();
        chooseFileButton.addActionListener(e -> openFileChooserDialog());
        setContentPane(rootPanel);
        setMinimumSize(new Dimension(800, 494));
        setMaximumSize(new Dimension(800, 494));
        setSize(800, 494);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void openFileChooserDialog() {
        logger.info("Opening choose file dialog.");
        int returnValue = fileChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            loadFile(selectedFilePath);
            fileTextField.setText(selectedFilePath);
        }
    }

    private void loadFile(String filePath) {
        try {
            FileReader fileReader = new FileReader(filePath);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String inputFile = "";
            String textFieldReadable = bufferedReader.readLine();
            while (textFieldReadable != null){
                inputFile += textFieldReadable + "\n";
                textFieldReadable = bufferedReader.readLine();
            }
            inputTextArea.setText(inputFile);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(rootPanel,
                    "Could not read file.\n" + e.getLocalizedMessage(),
                    "File read error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private JFileChooser initializeFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("JSON input file", "json");
        fileChooser.setFileFilter(filter);
        return fileChooser;
    }
}
