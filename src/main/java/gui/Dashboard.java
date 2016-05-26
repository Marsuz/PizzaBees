package gui;

import app.AppInput;
import app.savedSate.SavedState;
import model.Courier;
import model.Restaurant;
import model.Setting;
import org.apache.log4j.Logger;
import solver.SolverParameters;
import solver.setting.SettingFactory;
import solver.setting.random.RandomSettingFactory;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class Dashboard extends JFrame implements WorkerGraphicalManager {
    private JPanel rootPanel;
    private JTextArea inputTextArea;
    private JTextField fileTextField;
    private JButton chooseFileButton;
    private JButton runButton;
    private JProgressBar progressBar;
    private JLabel progressLabel;
    private JSlider wagesSlider;
    private JTextField scoutsTextField;
    private JLabel wagesLabel;
    private JTextField eliteQuantityTextField;
    private JTextField normalQuantityTextField;
    private JTextField selectedSitesTextField;
    private JTextField bestSitesTextField;
    private JTextField iterationsTextField;
    private JTextField movesTextField;

    private JFileChooser fileChooser = new JFileChooser();

    private SolverParameters solverParameters;
    private SwingWorker appWorker = null;

    /**
     * Returns JFrame object properly configured for showing with `setVisible(true)`.
     */
    Dashboard() {
        super("PizzaBees");
        solverParameters = new SolverParameters();
        wagesSlider.addChangeListener(e -> updateWagesLabel());
        updateWagesLabel();
        fileChooser.setFileFilter(new FileNameExtensionFilter("JSON input file", "json"));
        chooseFileButton.addActionListener(e -> chooseFileOfInput());
        runButton.addActionListener(e -> runButtonClicked());
        setContentPane(rootPanel);
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void updateWagesLabel() {
        int distanceValue = wagesSlider.getMaximum() - wagesSlider.getValue();
        int timeValue = wagesSlider.getValue();
        double distanceWage = (double)distanceValue / Math.max(distanceValue, timeValue);
        double timeWage = (double)timeValue / Math.max(distanceValue, timeValue);
        solverParameters.setDistanceWage(distanceWage);
        solverParameters.setTimeWage(timeWage);
        wagesLabel.setText(String.format(Locale.forLanguageTag("pl_PL"), "%.2f:%.2f", distanceWage, timeWage));
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
    private SwingWorker initializeAppWorker() throws IOException {
        SavedState state = AppInput.stringToState(this.inputTextArea.getText());
        Restaurant.P = state.getP();
        Courier.velocity = state.getV();

        if (state == null) {
            throw new IOException("Could not deserialize state.");
        }

        SettingFactory settingFactory = new RandomSettingFactory(state.getRestaurants(), state.getOrders());

        solverParameters.setScouts(Integer.parseInt(scoutsTextField.getText()));
        solverParameters.setSelectedSites(Integer.parseInt(selectedSitesTextField.getText()));
        solverParameters.setBestSites(Integer.parseInt(bestSitesTextField.getText()));
        solverParameters.setEliteQuantity(Integer.parseInt(eliteQuantityTextField.getText()));
        solverParameters.setNormalQuantity(Integer.parseInt(normalQuantityTextField.getText()));
        solverParameters.setIterations(Integer.parseInt(iterationsTextField.getText()));
        solverParameters.setMoves(Integer.parseInt(movesTextField.getText()));

        SolverWorker worker = new SolverWorker(this, settingFactory, solverParameters);
        worker.addPropertyChangeListener(new AppWorkerPropertyChangeListener(this, worker));
        return worker;
    }

    /**
     * Handler called when run button is clicked. Executes or cancels worker.
     *
     * @see AppWorker
     */
    private void runButtonClicked() {
        if (appWorker == null || appWorker.isCancelled() || appWorker.isDone()) {
            try {
                appWorker = initializeAppWorker();
                appWorker.execute();
            } catch (com.google.gson.JsonSyntaxException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error in JSON syntax", JOptionPane.ERROR_MESSAGE);
            } catch (com.google.gson.JsonParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error in JSON parsing", JOptionPane.ERROR_MESSAGE);
            } catch (NumberFormatException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Could not parse one of the parameters as integer.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                prepareInterfaceForWorker(appWorker != null && !appWorker.isDone() && !appWorker.isCancelled());
            }
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

        wagesSlider.setEnabled(controlsEnabled);
        scoutsTextField.setEnabled(controlsEnabled);
        eliteQuantityTextField.setEnabled(controlsEnabled);
        normalQuantityTextField.setEnabled(controlsEnabled);
        selectedSitesTextField.setEnabled(controlsEnabled);
        bestSitesTextField.setEnabled(controlsEnabled);
        iterationsTextField.setEnabled(controlsEnabled);
        movesTextField.setEnabled(controlsEnabled);

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

    /**
     * Shows result dialog to the user with specified title, message and result.
     *
     * @param message Message shown in the dialog.
     * @param title Title of the dialog.
     * @param result Result shown in text area of the dialog.
     * @see ResultDialog
     * @see WorkerGraphicalManager
     */
    public void showResultDialog(String message, String title, String result) {
        SwingUtilities.invokeLater(() -> {
            ResultDialog dialog = new ResultDialog(
                    title,
                    message,
                    result
            );
            dialog.pack();
            dialog.setVisible(true);
        });
    }
}
