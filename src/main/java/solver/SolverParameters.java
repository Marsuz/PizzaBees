package solver;

public class SolverParameters {
	private double distanceWate;
	private int scouts;
	private double timeWage;
	private int selectedSites;
	private int bestSites;
	private int eliteQuantity;
	private int normalQuantity;
	private int iterations;
	private int moves;

	public void setDistanceWage(double distanceWate) {
		this.distanceWate = distanceWate;
	}

	double getDistanceWage() {
		return distanceWate;
	}

	public void setScouts(int scouts) {
		this.scouts = scouts;
	}

	int getScouts() {
		return scouts;
	}

	public void setTimeWage(double timeWage) {
		this.timeWage = timeWage;
	}

	double getTimeWage() {
		return timeWage;
	}

	public void setSelectedSites(int selectedSites) {
		this.selectedSites = selectedSites;
	}

	int getSelectedSites() {
		return selectedSites;
	}

	public void setBestSites(int bestSites) {
		this.bestSites = bestSites;
	}

	int getBestSites() {
		return bestSites;
	}

	public void setEliteQuantity(int eliteQuantity) {
		this.eliteQuantity = eliteQuantity;
	}

	public void setNormalQuantity(int normalQuantity) {
		this.normalQuantity = normalQuantity;
	}

	public void setIterations(int iterations) {
		this.iterations = iterations;
	}

	public void setMoves(int moves) {
		this.moves = moves;
	}

	public int getIterations() {
		return iterations;
	}

	int getNormalQuantity() {
		return normalQuantity;
	}

	int getEliteQuantity() {
		return eliteQuantity;
	}

	int getMoves() {
		return moves;
	}
}
