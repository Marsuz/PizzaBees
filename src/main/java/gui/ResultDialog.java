package gui;

import solver.SolverParameters;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

/**
 * Created by sjchmiela on 16.05.2016.
 */
public class ResultDialog extends JFrame {
    private JPanel rootPanel;
    private JTextArea resultTextArea;
    private JButton okButton;
    private JLabel messageLabel;

    ResultDialog(String title, String message, String result) {
        super(title);
        setMinimumSize(new Dimension(640, 480));
        setMaximumSize(new Dimension(640, 480));
        setSize(640, 480);
        messageLabel.setText(message);
        resultTextArea.setText(result);
        okButton.addActionListener(e -> okButtonClicked());
        setContentPane(rootPanel);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void okButtonClicked() {
        dispose();
    }
}
