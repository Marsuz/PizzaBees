package gui;

import javafx.util.Pair;

import javax.swing.*;
import java.util.List;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public class AppWorker extends SwingWorker<String, Integer> {
    private final int iterations;
    private final JLabel progressLabel;

    public AppWorker(JLabel progressLabel, int iterations, String appInput) {
        this.progressLabel = progressLabel;
        this.iterations = iterations;
    }

    @Override
    protected String doInBackground() throws Exception {
        for (int i = 1; i <= iterations; i++) {
            Thread.sleep(2);
            publish(i);
            setProgress((i/iterations) * 100);
        }
        return "Finished in background.";
    }

    @Override
    protected void process(List<Integer> chunks) {
        super.process(chunks);
        progressLabel.setText(chunks.get(chunks.size() - 1) + "/" + iterations);
        firePropertyChange("progress", 0, chunks.get(chunks.size() - 1));
    }
}
