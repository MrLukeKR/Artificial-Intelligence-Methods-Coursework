package com.g52aim.lab00;

import java.util.Arrays;
import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;

/**
 * Class for running the experiment(s) in Lab 0.
 *
 * @author Warren G. Jackson
 */
public class Lab_00_Runner {
	
	static double totalTimeTaken = 0;
	static double bestSolution  = Double.MAX_VALUE;
	static double totalSolution = 0;
	static double worstSolution = 0;
	
	public Result runTest(long seed, int instance, int timeLimit) {
		
		Random random = new Random(seed);
    	SAT sat = new SAT(instance, timeLimit, random);
    	RandomWalk rw = new RandomWalk(sat, random);
    	rw.run();
    	
    	return new Result(seed, sat.getBestSolutionValue(), rw.getTimeTaken());
	}
	
	public void calculateAndPrintResult(Result result){
		totalTimeTaken += result.timeTaken;
		totalSolution  += result.best; 
		bestSolution    = Math.min(bestSolution, result.best);
		worstSolution   = Math.max(worstSolution, result.best);
		
		printResult(result);
	}
	
	public void printResult(Result result) {
		
		System.out.println(result.seed + "," + result.best + "," + result.timeTaken);
	}
	
	public static void main(String [] args) {
		
		Lab_00_Runner runner = new Lab_00_Runner();
		
		// do not change these values!
		final int instance = 1;
		final int timeLimit = 10;
		
		// change me in questions 1 and 2
		Long[] seeds = { 10022517L, 10122017L, 14322017L, 10012017L, 43222017L, 10546317L, 10177517L, 10365557L, 43252017L, 10025546L };
		
		System.out.println("seed,f_best,time_taken");
		
		Arrays.stream(seeds).map( s -> {
			return runner.runTest(s, instance, timeLimit);
		}).forEachOrdered(runner::calculateAndPrintResult);
		
		printFinalResults(seeds.length);
	}
	
	private static void printFinalResults(int noOfSeeds){
		System.out.println("Using " + noOfSeeds + " seeds:");
		System.out.println("\tAverage Solution Quality:\t" + totalSolution / noOfSeeds);
		System.out.println("\tWorst Solution Quality  [MAX]:\t" + worstSolution);
		System.out.println("\tBest Solution Quality [MIN]:\t" + bestSolution);
		System.out.println("\tAverage Time Taken:\t\t" + totalTimeTaken / noOfSeeds);
	}
	
	class Result {
		
		protected final long seed;
		
		protected final double best;
		
		protected final double timeTaken;
		
		public Result(long seed, double best, double timeTaken) {
			
			this.seed = seed;
			this.best = best;
			this.timeTaken = timeTaken;
		}
	}
}
