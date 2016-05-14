package gui;

import model.Setting;
import solver.Solver;
import solver.SolverParameters;
import solver.setting.SettingFactory;

import javax.swing.*;
import java.util.List;

/**
 * Created by sjchmiela on 14.05.2016.
 */
public class SolverWorker extends SwingWorker<Setting, Integer> {
    private final SettingFactory factory;
    private final WorkerGraphicalManager manager;
    private final SolverParameters solverParameters;
    private List<Setting> settings;
    private final Solver solver;

    /**
     * Worker solving time-taking task on special thread. Thus, not blocking the UI.
     *
     * @param manager Manager who gets notified of the progress changes.
     * @param factory SettingFactory for solver.
     * @param solverParameters Solving parameters;
     * @see WorkerGraphicalManager
     * @see SwingWorker
     * @see SettingFactory
     * @see SolverParameters
     */
    public SolverWorker(WorkerGraphicalManager manager, SettingFactory factory, SolverParameters solverParameters) {
        this.manager = manager;
        this.factory = factory;
        this.solverParameters = solverParameters;
        this.solver = new Solver();
        this.settings = solver.initialSettings(factory, solverParameters);
    }

    /**
     * Method solving time-taking task. Should publish progress with methods `firePropertyChange` and `publish`.
     *
     * @see SwingWorker
     */
    @Override
    protected Setting doInBackground() throws Exception {
        for(int i = 1; i <= solverParameters.getIterations(); i++) {
            settings = solver.solveIteration(settings, factory, solverParameters);
            publish(i);
            setProgress(progressForIteration(i));
            firePropertyChange("progress", progressForIteration(i-1), progressForIteration(i));
        }

        return solver.bestSetting(settings, solverParameters);
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
        manager.setDetailedProgressMessage(chunks.get(chunks.size() - 1) + "/" + solverParameters.getIterations());
    }

    /**
     * Convenience method for calculating percentage progress out of iteration number.
     */
    private int progressForIteration(int iteration) {
        return (int)(((float) iteration / solverParameters.getIterations()) * 100);
    }
}
