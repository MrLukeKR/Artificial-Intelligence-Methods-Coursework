package com.g52aim.lab03;

public class GeometricCooling implements CoolingSchedule {
	
	/**
	 * TODO - somehow change or set this in your experiments
	 */
	private double currentTemperature;
	
	/**
	 * TODO - somehow change or set this in your experiments
	 */
	private double alpha;
	
	/**
	 * 
	 * @param initialSolutionFitness
	 *            The objective value of the initial solution. Maybe useful (or
	 *            not) for some setting?
	 */
	public GeometricCooling(double initialSolutionFitness) {
		double c = 1.0;
		this.currentTemperature = c * initialSolutionFitness;	//Initial temperature has to be high
		this.alpha = 0.95;										//In the interval [0.9, 0.99]
	}

	@Override
	public double getCurrentTemperature() {
		
		return this.currentTemperature;
	}

	/**
	 * DEFINITION: T_{i + 1} = alpha * T_i
	 */
	@Override
	public void advanceTemperature() {
		currentTemperature *= alpha;
	}
	
	public String toString() {
		
		return "Geometric Cooling";
	}
}
