package com.g52aim.lab00;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.SATHeuristic;

/**
 * A random walk search method.
 *
 * @author Warren G. Jackson
 */
public class RandomWalk {
	
	/**
	 * The SAT instance
	 */
	private SAT sat;
	
	/**
	 * The heuristic which we will use. 
	 * In this case, it is a random bit flip move operator.
	 */
	private SATHeuristic randomBitFlip;
	
	private final int SINGLE_POINT_SOLUTION_INDEX = 0;
	
	private double timeTakenSeconds;

	/**
	 * 
	 * @param sat The SAT problem instance.
	 * @param random The random number generator.
	 */
	public RandomWalk(SAT sat, Random random) {
		
		this.sat = sat;
		this.randomBitFlip = new RandomBitFlipHeuristic(random);
	}
	
	public void run() {
		
		long start = System.currentTimeMillis();
		
		// initialise a solution as a random bit string
		sat.createRandomSolution(SINGLE_POINT_SOLUTION_INDEX);
		
		// apply the search method until the time runs out
		while(!sat.hasTimeExpired()) {
			
			// applies the bit flip operator to the solution
			randomBitFlip.applyHeuristic(sat);
			
			// call required for termination criteria but not used in random walk
			sat.getObjectiveFunctionValue(SINGLE_POINT_SOLUTION_INDEX);
		}
		
		long end = System.currentTimeMillis();
		this.timeTakenSeconds = (double)(end - start) * 1E-3;
	}
	
	public double getTimeTaken() {
		
		return this.timeTakenSeconds;
	}
	
	
}