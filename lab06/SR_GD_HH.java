package com.g52aim.lab06;

import com.g52aim.HyFlexTestFrame;
import com.g52aim.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class SR_GD_HH extends HyperHeuristic {
	
	public SR_GD_HH(long seed) {
		
		super(seed);
	}

	/**
	  * TODO - Implement a simple random great deluge selection hyper-heuristic
	  *
	  * PSEUDOCODE:
	  *
	  * INPUT: problem_instance
	  * hs <- HyFlexUtilities.getHeuristicSetOfTypes( problem, HeuristicType, HeuristicType... );
	  * s <- initialiseSolution();
	  * initial_threshold <- f( s_0 );
	  * target_threshold <- 0.0;
	  *
	  * WHILE termination criterion is not met DO
	  *     h <- getNextHeuristicUsingSimpleRandom( hs );
	  *     s' <- h( s );
	  *     accept <- acceptMoveUsingGreatDeluge( f( s ), f( s' ), elapsed_time, total_time );
	  *
	  *     IF accept THEN
	  *         s <- s'; 						// HINT :: You will have to create a copy of the solution here!
	  *     END_IF
	  *
	  *     IF f( s' ) < f( s_{best} ) THEN		// HINT :: This is handled by HyFlex
	  *         s_{best} <- s';
	  *     END_IF
	  * END_WHILE
	  *
	  * return s_{best};						// HINT :: Also handled by HyFlex
	  */
	public void solve(ProblemDomain problem) {
		final int CURRENT_SOLUTION_INDEX = 0;
		final int CANDIDATE_SOLUTION_INDEX = 1;
		
		problem.initialiseSolution(CURRENT_SOLUTION_INDEX);
		
		double currentFitness;
		double candidateFitness;
		
		double initialThreshold = problem.getFunctionValue(CURRENT_SOLUTION_INDEX);
		double targetThreshold = 0.0;
		
		int currentHeuristic;
		int[] heuristics = HyFlexUtilities.getHeuristicSetOfTypes(problem, HeuristicType.MUTATION,HeuristicType.LOCAL_SEARCH);

		boolean accept;
		currentFitness = problem.getFunctionValue(CURRENT_SOLUTION_INDEX);

		while(!hasTimeExpired()){
			currentHeuristic = getNextHeuristicUsingSimpleRandom(heuristics);
			candidateFitness = problem.applyHeuristic(currentHeuristic, CURRENT_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);
			
			accept = acceptMoveUsingGreatDeluge(currentFitness, candidateFitness, initialThreshold, targetThreshold, getElapsedTime(), getTimeLimit());
	
			if(accept){
				problem.copySolution(CANDIDATE_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
				currentFitness = candidateFitness;
			}
		}
	}
	
	/**
	 * Simple random heuristic selection. This should not "apply" the heuristic,
	 * only choose it so that it can be applied within the main hyper-heuristic loop.
	 *
	 * PSEUDOCODE:
	 *
	 * INPUT: set_of_heuristics
	 * h <- random \in set_of_heuristics;
	 * return h;
	 * 
	 * @param setOfHeuristics The set of heuristics to choose from.
	 * @return The (ID of the) heuristic to apply.
	 */
	public int getNextHeuristicUsingSimpleRandom(int[] setOfHeuristics) {
		int heuristic = rng.nextInt(setOfHeuristics.length);
		
		return setOfHeuristics[heuristic];
	}
	
	/**
	 * Great Deluge for move acceptance.
	 *
	 * ( see exercise sheet for formula for calculating threshold value )
	 *
	 * PSEUDOCODE for non-stochastic threshold move acceptance accepting IE moves:
	 *
	 * th <- currentThresholdValue(); // calculated using great deluge!
	 * IF f( s' ) <= max( f( s ), th ) THEN
	 *     "accept()";
	 * ELSE
	 *     "reject()";
     * END_IF;
	 * 
	 * @param currentSolutionFitness The objective value of the current solution (s).
	 * @param candidateSolutionFitness The objective value of the candidate solution (s').
	 * @param initialThreshold The value of the initial threshold.
	 * @param targetThreshold The value of the target threshold.
	 * @param elapsedTime The amount of time currently elapsed.
	 * @param totalTime The amount of time allowed as defined by the termination criterion.
	 * @return Whether to accept (true) or reject (false) the candidate solution.
	 */
	public boolean acceptMoveUsingGreatDeluge(double currentSolutionFitness, double candidateSolutionFitness,
			double initialThreshold, double targetThreshold, long elapsedTime, long totalTime) {
		
		double threshold = initialThreshold - ((elapsedTime * (initialThreshold - targetThreshold))/totalTime);
		
		return candidateSolutionFitness <= Math.max(currentSolutionFitness, threshold);
	}
	
	public String toString() {

		return "SR-GD-HH";
	}

}
