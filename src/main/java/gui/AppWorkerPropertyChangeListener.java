package gui;

import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class AppWorkerPropertyChangeListener implements PropertyChangeListener {
    private WorkerGraphicalManager manager;
    private SwingWorker worker;

    /**
     * PropertyChangeListener for WorkerGraphicalManager and SwingWorker
     *
     * @param manager Manager notified of worker's progress.
     * @param worker Worker whose progress's updates will be dispatched.
     * @see WorkerGraphicalManager
     * @see SwingWorker
     */
    AppWorkerPropertyChangeListener(WorkerGraphicalManager manager, SwingWorker worker) {
        this.manager = manager;
        this.worker = worker;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("progress")) {
            updateProgress(evt.getNewValue());
        } else if (evt.getPropertyName().equals("state")) {
            updateState((SwingWorker.StateValue) evt.getNewValue());
        }
    }

    /**
     * Notifies manager of the worker's progress change.
     *
     * @param progress New worker progress.
     * @see WorkerGraphicalManager
     */
    private void updateProgress(Object progress) {
        manager.setProgress((int) progress);
    }

    /**
     * Notifies manager of the worker's state change.
     *
     * @param state New worker state.
     * @see SwingWorker
     * @see WorkerGraphicalManager
     */
    private void updateState(SwingWorker.StateValue state) {
        switch (state) {
            case DONE:
                try {
                    manager.showResultDialog(
                            "Best solution calculated.",
                            "Success",
                            new GsonBuilder().setPrettyPrinting().create().toJson(worker.get()));
                } catch (InterruptedException | CancellationException e) {
                    manager.showMessageDialog(
                            "The solving process has been cancelled.",
                            "Execution interrupted",
                            JOptionPane.ERROR_MESSAGE);
                } catch (ExecutionException e) {
                    manager.showMessageDialog(
                            "The solving process has failed.",
                            "Error occurred",
                            JOptionPane.ERROR_MESSAGE);
                }
                manager.prepareInterfaceForWorker(false);
        }
    }
}
