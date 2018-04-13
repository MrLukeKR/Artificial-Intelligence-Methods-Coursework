package com.g52aim.lab04;

import java.util.Random;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.genetics.CrossoverHeuristic;
import g52aim.satheuristics.genetics.PopulationHeuristic;
import g52aim.satheuristics.genetics.PopulationReplacement;
import g52aim.searchmethods.PopulationBasedSearchMethod;

/**
 * Example memetic algorithm ( local search should to be added per the report exercise ).
 * 
 * @author Warren G. Jackson
 *
 */
public class MemeticAlgorithm extends PopulationBasedSearchMethod {

	private final CrossoverHeuristic crossover;
	private final PopulationHeuristic mutation;
	private final PopulationHeuristic localSearch;
	private final PopulationReplacement replacement;
	private final TournamentSelection selection;
	
	public MemeticAlgorithm(SAT problem, Random rng, int populationSize, CrossoverHeuristic crossover, 
			PopulationHeuristic mutation, PopulationHeuristic localSearch, PopulationReplacement replacement) {
		
		super(problem, rng, populationSize);
		
		this.crossover = crossover;
		this.mutation = mutation;
		this.localSearch = localSearch;
		this.replacement = replacement;
		this.selection = new TournamentSelection(problem, rng, populationSize);
	}

	/**
	  * Memetic Algorithm pseudocode
	  * Note there is no exact pseudocode since the purpose of this
	  * exercise is that you experiment with applying local search
	  * in different places of the MA.
	  *
	  * BASIC PSEUDO CODE (GA) not MA
	  * (population already initialised)
	  * 
	  * FOR 0 -> populationSize / 2
	  *		select parents using tournament selection
	  *     apply crossover to generate offspring
	  *     apply mutation to offspring
	  * ENDFOR
	  *
	  * do population replacement
	  *
	  */
	public void runMainLoop() {
		
		for(int i = 0; i < POPULATION_SIZE; i += 2) {
			
			// select parents
			int p1 = selection.tournamentSelection(3);
			int p2 = selection.tournamentSelection(3);
			
			int c1 = POPULATION_SIZE + i;
			int c2 = POPULATION_SIZE + i + 1;
			
			// apply crossover

			//localSearch.applyHeuristic(p1);
			//localSearch.applyHeuristic(p2);
			crossover.applyHeuristic(p1, p2, c1, c2);

			//localSearch.applyHeuristic(c1);
			//localSearch.applyHeuristic(c2);
			
			// apply mutation to children
			mutation.applyHeuristic(c1);
			mutation.applyHeuristic(c2);	
			//localSearch.applyHeuristic(c1);
			//localSearch.applyHeuristic(c2);
			
		}
		
		replacement.doReplacement(problem, POPULATION_SIZE);
	}
	
	@Override
	public String toString() {
		
		return "Genetic Algorithm";
	}
}
