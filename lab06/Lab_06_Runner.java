package com.g52aim.lab06;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import SAT.SAT;


public class Lab_06_Runner {
	
	final Lab06TestFrameConfig config;
	
	final int TOTAL_RUNS;
	final String[] DOMAINS;
	final int[] INSTANCE_IDs;
	final long RUN_TIME;
	final long[] SEEDS;
	
	public Lab_06_Runner(Lab06TestFrameConfig config) {
	
		this.config = config;
		
		this.TOTAL_RUNS = config.getTotalRuns();
		this.DOMAINS = config.PROBLEM_DOMAINS;
		this.INSTANCE_IDs = config.getInstanceIDs()[0];
		this.SEEDS = config.getSeeds();
		this.RUN_TIME = config.getRunTime();
	}

	public void runTests() {
		
		String hyperHeuristicName = null;
		String problemDomain = null;
		double[] bestSolutionFitness_s = new double[TOTAL_RUNS];
		
		for(int i = 0; i < INSTANCE_IDs.length; i++) {
			
			int instanceID = INSTANCE_IDs[i];
			
			for(int run = 0; run < TOTAL_RUNS; run++) {
				
				long seed = SEEDS[run];
				
				HyperHeuristic hh = new SR_GD_HH(seed);
				ProblemDomain problem = new SAT(seed);
				problem.loadInstance(instanceID);
				hh.setTimeLimit(RUN_TIME);	
				hh.loadProblemDomain(problem);
				hh.run();
				
				hyperHeuristicName = hh.toString();
				problemDomain = problem.toString();
				bestSolutionFitness_s[run] = hh.getBestSolutionValue();
				System.out.println("Instance ID: " + instanceID + "\tTrial: " + run + "\tf(s_{best}) = " + hh.getBestSolutionValue());
			}
			
			//print or save results
			StringBuilder sb = new StringBuilder();
			sb.append(hyperHeuristicName + "," + RUN_TIME + "," + problemDomain + "," + instanceID);
			for(double ofv : bestSolutionFitness_s) {
				sb.append("," + ofv);
			}
			
			config.saveData(hyperHeuristicName + ".csv", sb.toString());
		}
	}
	
	public static void main(String [] args) {
		
		new Lab_06_Runner(new Lab06TestFrameConfig()).runTests();
	}
}
