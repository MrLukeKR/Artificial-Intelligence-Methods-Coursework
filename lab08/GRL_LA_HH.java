package com.g52aim.lab08;

import com.g52aim.HyFlexUtilities;

import AbstractClasses.HyperHeuristic;
import AbstractClasses.ProblemDomain;
import AbstractClasses.ProblemDomain.HeuristicType;

public class GRL_LA_HH extends HyperHeuristic {
	
	private final int CURRENT_SOLUTION_INDEX = 0;
	
	private final int CANDIDATE_SOLUTION_INDEX = 1;
	
	private final int LIST_LENGTH;
	
	private final int default_score;
	private final int lower_bound;
	private final int upper_bound;
	
	public GRL_LA_HH(long seed, int list_length, int default_score, int lower_bound, int upper_bound) {
		
		super(seed);
		
		this.LIST_LENGTH = list_length;
		this.default_score = default_score;
		this.lower_bound = lower_bound;
		this.upper_bound = upper_bound;
	}

	@Override
	protected void solve(ProblemDomain problem) {
		
		problem.initialiseSolution(CURRENT_SOLUTION_INDEX); 
		
		double currentFitness;
		double candidateFitness;
		
		int currentHeuristic;
		int[] heuristics = HyFlexUtilities.getHeuristicSetOfTypes(problem, HeuristicType.MUTATION, HeuristicType.LOCAL_SEARCH, HeuristicType.RUIN_RECREATE);
		
		EpsilonGreedyReinforcementLearning egrl = new EpsilonGreedyReinforcementLearning(heuristics, default_score, lower_bound, upper_bound, rng);
		
		currentFitness = problem.getFunctionValue(CURRENT_SOLUTION_INDEX);
		LateAcceptance la = new LateAcceptance(this.LIST_LENGTH, currentFitness);

		while(!hasTimeExpired()) {
			
			// heuristic selection
			currentHeuristic = egrl.selectNextHeuristic();
			candidateFitness = problem.applyHeuristic(currentHeuristic, CURRENT_SOLUTION_INDEX, CANDIDATE_SOLUTION_INDEX);

			// reinforcement learning
			if(candidateFitness < currentFitness)
				egrl.incrementScore(currentHeuristic);
			else
				egrl.decrementScore(currentHeuristic);

			
			// move acceptance
			if(la.accept(currentFitness, candidateFitness)){
				problem.copySolution(CANDIDATE_SOLUTION_INDEX, CURRENT_SOLUTION_INDEX);
				currentFitness = candidateFitness;
			}
		}
	}

	@Override
	public String toString() {
		
		return "GRL_LA_HH";
	}

}
