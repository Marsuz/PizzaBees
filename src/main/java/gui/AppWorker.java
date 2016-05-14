package gui;

import javafx.util.Pair;

import javax.swing.*;
import java.util.List;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class AppWorker extends SwingWorker<String, Integer> {
    private final int iterations;
    private final WorkerGraphicalManager manager;
    private final String appInput;

    /**
     * Worker solving time-taking task on special thread. Thus, not blocking the UI.
     *
     * @param manager Manager who gets notified of the progress changes.
     * @param appInput Input for worker.
     * @param iterations Number of iterations the worker should do.
     * @see WorkerGraphicalManager
     * @see SwingWorker
     */
    public AppWorker(WorkerGraphicalManager manager, int iterations, String appInput) {
        this.manager = manager;
        this.iterations = iterations;
        this.appInput = appInput;
    }

    /**
     * Method solving time-taking task. Should publish progress with methods `firePropertyChange` and `publish`.
     *
     * @see SwingWorker
     */
    @Override
    protected String doInBackground() throws Exception {
        for (int i = 1; i <= iterations; i++) {
            Thread.sleep(2);
            publish(i);
            setProgress(progressForIteration(i));
            firePropertyChange("progress", progressForIteration(i-1), progressForIteration(i));
        }
        return "Finished in background.";
    }

    /**
     * Method processing published progress changes in chunks. Notifies manager of progress change.
     *
     * @see WorkerGraphicalManager
     * @see SwingWorker
     */
    @Override
    protected void process(List<Integer> chunks) {
        super.process(chunks);
        manager.setDetailedProgressMessage(chunks.get(chunks.size() - 1) + "/" + iterations);
    }

    /**
     * Convenience method for calculating percentage progress out of iteration number.
     */
    private int progressForIteration(int iteration) {
        return (int)(((float) iteration / iterations) * 100);
    }
}
