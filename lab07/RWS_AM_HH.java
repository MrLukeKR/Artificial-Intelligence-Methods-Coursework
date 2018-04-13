package com.g52aim.lab07;

import java.util.Random;

import com.g52aim.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class RWS_AM_HH extends HyperHeuristic {
	
	private final int defaultScore, lowerBound, upperBound;
	
	/**
	 * 
	 * @param seed The experimental seed.
	 * @param defaultScore The default score to give each heuristic in RWS.
	 * @param lowerBound The lower bound for each heuristic's score in RWS.
	 * @param upperBound The upper bound for each heursitic's score in RWS.
	 */
	public RWS_AM_HH(long seed, int defaultScore, int lowerBound, int upperBound) {
		
		super(seed);
		
		this.defaultScore = defaultScore;
		this.lowerBound = lowerBound;
		this.upperBound = upperBound;
	}

	/**
	 * TODO - Implement a selection hyper-heuristic using a reinforcement learning based
	 * roulette wheel selection (RWS) heuristic selection method accepting all moves (AM).
	 * 
	 * PSEUDOCODE:
	 * 
	 * INPUT: problem_instance, default_score, lower_bound, upper_bound
	 * hs <- { MUTATION U LOCAL_SEARCH U RUIN_RECREATE }
	 * s <- initialiseSolution();
	 * rws <- initialiseNewRouletteWheelSelectionMethod();
	 * 
	 * WHILE termination criterion is not met DO
	 *     h <- rws.performRouletteWheelSelection();
	 *     s' <- h(s);
	 *     
	 *     IF f( s' ) < f ( s ) THEN
	 *         incrementHeuristicScore();
	 *     ELSE
	 *         decrementHeuristicScore();
	 *     END_IF
	 *     
	 *     accept();
	 * END_WHILE
	 * 
	 * return s_{best};
	 */
	public void solve(ProblemDomain problem) {
		final int CANDIDATE_SOLUTION_INDEX = 1; //Only need candidate as it's All Move Acceptance
		
		problem.initialiseSolution(CANDIDATE_SOLUTION_INDEX); 
		
		double currentFitness;
		double candidateFitness;
		
		int currentHeuristic;
		int[] heuristics = HyFlexUtilities.getHeuristicSetOfTypes(problem, HeuristicType.MUTATION, HeuristicType.LOCAL_SEARCH, HeuristicType.RUIN_RECREATE);
		
		RouletteWheelSelection rws = new RouletteWheelSelection(heuristics, defaultScore, lowerBound, upperBound, this.rng);
		
		currentFitness = problem.getFunctionValue(CANDIDATE_SOLUTION_INDEX);

		while(!hasTimeExpired()){
			currentHeuristic = rws.performRouletteWheelSelection();
			candidateFitness = problem.applyHeuristic(currentHeuristic, CANDIDATE_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);
			
			if(candidateFitness < currentFitness)
				rws.incrementScore(currentHeuristic);
			else
				rws.decrementScore(currentHeuristic);

			currentFitness = candidateFitness;
		}
	}
	
	public String toString() {

		return "RWS_AM_HH";
	}

}
