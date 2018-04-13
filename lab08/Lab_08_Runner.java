package com.g52aim.lab08;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import BinPacking.BinPacking;
import SAT.SAT;


public class Lab_08_Runner {
	
	final Lab08TestFrameConfig config;
	
	final int TOTAL_RUNS;
	final String[] DOMAINS;
	final int[][] INSTANCE_IDs;
	final long RUN_TIME;
	final long[] SEEDS;
	final int listLength;
	final int defaultScore, lowerBound, upperBound;
	
	public Lab_08_Runner(Lab08TestFrameConfig config) {
	
		this.config = config;
		
		this.TOTAL_RUNS = config.getTotalRuns();
		this.DOMAINS = config.PROBLEM_DOMAINS;
		this.INSTANCE_IDs = config.getInstanceIDs();
		this.SEEDS = config.getSeeds();
		this.RUN_TIME = config.getRunTime();
		this.listLength = config.LIST_LENGTH;
		this.defaultScore = config.DEFAULT_SCORE;
		this.lowerBound = config.LOWER_BOUND;
		this.upperBound = config.UPPER_BOUND;
	}

	public void runTests() {
		
		String hyperHeuristicName = null;
		String problemDomain = null;
		double[] bestSolutionFitness_s = new double[TOTAL_RUNS];
		
		for(int domain = 0; domain < DOMAINS.length; domain++) {

			for(int instance = 0; instance < INSTANCE_IDs[domain].length; instance++) {
				
				int instanceID = INSTANCE_IDs[domain][instance];
				
				for(int run = 0; run < TOTAL_RUNS; run++) {
					
					long seed = SEEDS[run];
					
					HyperHeuristic hh = new GRL_LA_HH(seed, listLength, defaultScore, lowerBound, upperBound);
					ProblemDomain problem = getNewDomain(DOMAINS[domain], seed);
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
	}
	
	public ProblemDomain getNewDomain(String domain, long seed) {
		
		ProblemDomain domainObject;
		
		switch(domain.toUpperCase()) {
		case "SAT":
			domainObject = new SAT(seed);
			break;
		case "BP":
			domainObject = new BinPacking(seed);
			break;
		default:
			domainObject = null;
		}
		
		return domainObject;
	}
	
	public static void main(String [] args) {
		
		new Lab_08_Runner(new Lab08TestFrameConfig()).runTests();
	}
}
