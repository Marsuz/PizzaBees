package gui;

import javax.swing.*;

/**
 * Created by sjchmiela on 26.04.2016.
 */
public interface WorkerGraphicalManager {
    void prepareInterfaceForWorker(boolean running);
    void setProgress(int progress);
    void setDetailedProgressMessage(String message);
    void showMessageDialog(Object message, String title, int messageType);
}
