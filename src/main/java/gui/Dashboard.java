package gui;

import app.AppInput;
import org.apache.log4j.Logger;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

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
    private JLabel progressLabel;

    private JFileChooser fileChooser;

    private AppWorker appWorker = null;

    public Dashboard() {
        super("PizzaBees");
        fileChooser = initializeFileChooser();
        chooseFileButton.addActionListener(e -> openFileChooserDialog());
        runButton.addActionListener(e -> runButtonClicked());
        setContentPane(rootPanel);
        setMinimumSize(new Dimension(800, 494));
        setMaximumSize(new Dimension(800, 494));
        setSize(800, 494);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private class AppWorkerPropertyChangeListener implements PropertyChangeListener {
        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("progress")) {
                progressBar.setValue((int) evt.getNewValue());
            } else if (evt.getPropertyName().equals("state")) {
                switch ((SwingWorker.StateValue) evt.getNewValue()) {
                    case DONE:
                        try {
                            showCompletionDialog(appWorker.get());
                        } catch (InterruptedException | CancellationException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(rootPanel,
                                    "The solving process has been cancelled.",
                                    "Execution interrupted",
                                    JOptionPane.ERROR_MESSAGE);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            JOptionPane.showMessageDialog(rootPanel,
                                    "The solving process has failed.",
                                    "Error occurred",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                        progressBar.setValue(0);
                        runButton.setText("Run");
                        appWorker = null;
                }
            }
        }
    }

    private AppWorker initializeAppWorker() {
        AppWorker worker = new AppWorker(progressLabel, 3000, "");
        progressBar.setMinimum(0);
        progressBar.setMaximum(3000);
        worker.addPropertyChangeListener(new AppWorkerPropertyChangeListener());
        return worker;
    }

    private void runButtonClicked() {
        if (appWorker == null) {
            appWorker = initializeAppWorker();
            appWorker.execute();
        } else {
            appWorker.cancel(true);
            appWorker = null;
        }

        if (appWorker == null) {
            argumentsSetEnabled(true);
            runButton.setText("Run");
        } else {
            argumentsSetEnabled(false);
            runButton.setText("Cancel");
        }
    }

    private void argumentsSetEnabled(boolean value) {
        comboBox1.setEnabled(value);
        comboBox2.setEnabled(value);
        inputTextArea.setEnabled(value);
        fileTextField.setEnabled(value);
        chooseFileButton.setEnabled(value);
    }

    private void showCompletionDialog(String message) {
        JOptionPane.showMessageDialog(rootPanel,
                "Finished running solver. Calculated solution:\n" + message,
                "Solving finished!",
                JOptionPane.INFORMATION_MESSAGE);
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
