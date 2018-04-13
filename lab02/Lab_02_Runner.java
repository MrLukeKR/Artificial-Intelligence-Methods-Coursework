package com.g52aim.lab02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Stream;

import com.g52aim.RunData;
import com.g52aim.TestFrame;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.SATHeuristic;
import g52aim.searchmethods.SearchMethod;
import g52aim.statistics.BoxPlot;
import g52aim.statistics.LineGraph;


public class Lab_02_Runner extends TestFrame {

	private static final int HEURISTIC_TESTS = 1;
	
	private static int depthOfSearch;
	
	private static int intensityOfMutation;

	public Lab_02_Runner(Lab02TestFrameConfig config, long seed) {
		
		super(config, seed);
		
		depthOfSearch = config.depthOfSearch;
		intensityOfMutation = config.intensityOfMutation;
	}
	
	public void runTests() {
		
		double[][] data = new double[CFG.getTotalRuns()][HEURISTIC_TESTS];
		String[] names = new String[HEURISTIC_TESTS];
		
		Stream<Stream<RunData>> resultStream = rangeAsStream(0, HEURISTIC_TESTS - 1).parallel().map( this::runExperimentsForHeuristicId );
		
		resultStream.forEach( resultList -> {
			
			RunData[] result = resultList.toArray(RunData[]::new);
			
			RunData best = Arrays.stream(result).min( (a, b) -> { return a.best.compareTo(b.best);}).get();
			RunData worst = Arrays.stream(result).max( (a, b) -> { return a.best.compareTo(b.best);}).get();
			Arrays.stream(result).forEach( r -> {
				data[r.trialId][r.heuristicId] = r.best;
				names[r.heuristicId] = r.heuristicName;
			});
			
			if(((Lab02TestFrameConfig)CFG).DISPLAY_PLOTS) {
				new LineGraph(best.heuristicName + " Best Fitness Trace", CFG.getInstanceId()).createGraph(best.trace);
				new LineGraph(worst.heuristicName + " Worst Fitness Trace", CFG.getInstanceId()).createGraph(worst.trace);
			}
			
			Double[] results = Arrays.stream(result).map( r -> { return (r.best); }).toArray(Double[]::new);
			String resultsString = Arrays.toString(results);
			saveData(best.heuristicName + "_" + CFG.getTotalRuns() + "Runs.csv",
					"Heuristic,Run Time,Instance ID,Intensity Of Mutation,Depth Of Search",
					best.heuristicName + "," + CFG.getRunTime() + "," + CFG.getInstanceId() + "," + intensityOfMutation +
					"," + depthOfSearch + "," + resultsString.substring(1, resultsString.length() - 1) + "," + best.solution);
		});
		
		if(((Lab02TestFrameConfig)CFG).DISPLAY_PLOTS) {
			new BoxPlot(CFG.getBoxPlotTitle(), false).createPlot(data, names);
		}
	}
	
	public Stream<RunData> runExperimentsForHeuristicId(int heuristicId) {
		
		Stream<RunData> dat = rangeAsStream(0, CFG.getTotalRuns() - 1).parallel().map( run -> {
			Random random = new Random(SEEDS[run]);
			SAT sat = new SAT(CFG.getInstanceId(), CFG.getRunTime(), random);
			ArrayList<Double> fitnessTrace = new ArrayList<Double>();

			SATHeuristic ls = new DBHC_OI(random);
			SATHeuristic mtn = new RandomMutation(random);
			SearchMethod searchMethod = new IteratedLocalSearch(sat, random, mtn, ls, intensityOfMutation, depthOfSearch);
			
			while(!sat.hasTimeExpired()) {
				
				searchMethod.run();
				double fitness = sat.getObjectiveFunctionValue(SATHeuristic.CURRENT_SOLUTION_INDEX);
				fitnessTrace.add(fitness);
			}
			
			logResult(searchMethod.toString(), run, sat.getBestSolutionValue(), sat.getBestSolutionAsString());
			
			return new RunData(fitnessTrace, sat.getBestSolutionValue(), searchMethod.toString(), heuristicId, run, sat.getBestSolutionAsString());
		});
		
		return dat;
	}
	
	public static void main(String [] args) {
		
		long seed = 10022017l; // date of first lab session
		Lab02TestFrameConfig cfg = new Lab02TestFrameConfig();

				new Lab_02_Runner(cfg, seed).runTests();
}
}
