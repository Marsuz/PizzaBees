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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class Dashboard extends JFrame implements WorkerGraphicalManager {
    private static final int iterations = 3000;

    private JPanel rootPanel;
    private JTextArea inputTextArea;
    private JTextField fileTextField;
    private JButton chooseFileButton;
    private JButton runButton;
    private JProgressBar progressBar;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JLabel progressLabel;

    private JFileChooser fileChooser = new JFileChooser();

    private AppWorker appWorker = null;

    /**
     * Returns JFrame object properly configured for showing with `setVisible(true)`.
     */
    Dashboard() {
        super("PizzaBees");
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON input file", "json"));
        chooseFileButton.addActionListener(e -> chooseFileOfInput());
        runButton.addActionListener(e -> runButtonClicked());
        setContentPane(rootPanel);
        setMinimumSize(new Dimension(800, 494));
        setMaximumSize(new Dimension(800, 494));
        setSize(800, 494);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    /**
     * Convenience method for opening file chooser dialog and reading the path to file text field.
     *
     */
    private void chooseFileOfInput() {
        if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            String selectedFilePath = fileChooser.getSelectedFile().getAbsolutePath();
            try {
                String selectedFileContents = new String(Files.readAllBytes(Paths.get(selectedFilePath)));
                inputTextArea.setText(selectedFileContents);
                fileTextField.setText(selectedFilePath);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(rootPanel,
                        "Could not read file.\n" + e.getLocalizedMessage(),
                        "File read error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Returns AppWorker object with configured access to progressLabel.
     * Also, the worker has already property change listener bound.
     *
     * @see AppWorker
     * @see AppWorkerPropertyChangeListener
     * @return      AppWorker object
     */
    private AppWorker initializeAppWorker() {
        AppWorker worker = new AppWorker(this, iterations, inputTextArea.getText());
        worker.addPropertyChangeListener(new AppWorkerPropertyChangeListener(this, worker));
        return worker;
    }

    /**
     * Handler called when run button is clicked. Executes or cancels worker.
     *
     * @see AppWorker
     */
    private void runButtonClicked() {
        if (appWorker == null) {
            prepareInterfaceForWorker(true);
            appWorker = initializeAppWorker();
            appWorker.execute();
        } else {
            appWorker.cancel(true);
            appWorker = null;
            prepareInterfaceForWorker(false);
        }
    }

    /**
     * Updates interface according to worker state.
     *
     * @param running State of the worker
     * @see AppWorker
     */
    public void prepareInterfaceForWorker(boolean running) {
        boolean controlsEnabled = !running;

        comboBox1.setEnabled(controlsEnabled);
        comboBox2.setEnabled(controlsEnabled);
        inputTextArea.setEnabled(controlsEnabled);
        fileTextField.setEnabled(controlsEnabled);
        chooseFileButton.setEnabled(controlsEnabled);

        runButton.setText(running ? "Cancel" : "Run");

        progressBar.setValue(0);
        progressLabel.setText("");
    }

    /**
     * Updates progress bar according to the new progress.
     *
     * @see JProgressBar
     * @see WorkerGraphicalManager
     */
    public void setProgress(int progress) {
        progressBar.setValue(progress);
    }

    public void setDetailedProgressMessage(String message) {
        progressLabel.setText(message);
    }

    /**
     * Shows message dialog to the user with specified title, message and dialog type.
     *
     * @param message Message shown in the dialog.
     * @param title Title of the dialog.
     * @param messageType Type of the dialog, one of the JOptionPane constants.
     * @see JOptionPane
     * @see WorkerGraphicalManager
     */
    public void showMessageDialog(Object message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }
}
