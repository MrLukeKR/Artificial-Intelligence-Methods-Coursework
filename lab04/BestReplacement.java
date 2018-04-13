package com.g52aim.lab04;

import java.util.PriorityQueue;

import g52aim.domains.chesc2014_SAT.SAT;
import g52aim.satheuristics.genetics.PopulationReplacement;

public class BestReplacement extends PopulationReplacement {

	protected int[] getNextGeneration(SAT problem, int populationSize) {
		
		int[] population = new int[populationSize];
		double[] populationQualities = new double[populationSize * 2];
		PriorityQueue<Double> pq = new PriorityQueue<Double>();
		
		for(int i = 0; i < populationSize * 2; i++) {
			
			double quality = problem.getObjectiveFunctionValue(i);
			pq.add(quality);
			populationQualities[i] = quality;
		}
		
		for(int i = 0; i < populationSize; i++) {
			
			double nextBestQuality = pq.remove();
			
			// find index of individual corresponding to 
			// the next best quality with preference for
			// offspring
			for(int j = populationSize * 2 - 1; j >= 0; j--) {
				
				if(populationQualities[j] == nextBestQuality) {
					
					population[i] = j;
					
					// set this solution to NaN to prevent re-selection
					populationQualities[j] = Double.NaN;
					break;
				}
			}
		}

		return population;
	}
}
