package com.g52aim.lab07;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RouletteWheelSelection {

	public LinkedHashMap<Integer, Integer> heuristic_scores;
		
	public final int upper_bound;
	
	public final int lower_bound;
	
	public final int default_score;
	
	public final Random rng;
	
	/**
	 * Constructs a Roulette Wheel Selection method using a LinkedHashMap.
	 * 
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound The lower bound on the heuristic scores.
	 * @param upper_bound The upper bound on the heuristic scores.
	 * @param rng The random number generator.
	 */
	public RouletteWheelSelection(int[] heuristic_ids, int default_score, int lower_bound, int upper_bound, Random rng) {
		
		this(new LinkedHashMap<Integer, Integer>(), heuristic_ids, default_score, lower_bound, upper_bound, rng);
	}
	
	/**
	 * Constructs a Roulette Wheel Selection method using the supplied Map.
	 * 
	 * @param heuristic_scores An empty Map for mapping heuristic IDs to heuristic scores.
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound The lower bound on the heuristic scores.
	 * @param upper_bound The upper bound on the heuristic scores.
	 * @param rng The random number generator.
	 */
	public RouletteWheelSelection(LinkedHashMap<Integer, Integer> heuristic_scores, int[] heuristic_ids, int default_score, int lower_bound, int upper_bound, Random rng) {
		
		this.heuristic_scores = heuristic_scores;
		this.upper_bound = upper_bound;
		this.lower_bound = lower_bound;
		this.rng = rng;
		this.default_score = default_score;
		
		for(int id : heuristic_ids)
			heuristic_scores.put(id, default_score);
	}
	
	/**
	 * 
	 * @param heuristic_id The ID of the heuristic.
	 * @return The score for the aforementioned heuristic.
	 */
	public int getScore(Integer heuristic_id) {
		return heuristic_scores.get(heuristic_id);
	}
	
	/**
	 * Increments the score of the specified heuristic respecting the upper and lower bounds.
	 * 
	 * @param heuristic_id The ID of the heuristic whose score should be incremented.
	 */
	public void incrementScore(Integer heuristic_id) {
		int score = getScore(heuristic_id);
		int newScore = score + 1;

		if(newScore <= upper_bound)
			heuristic_scores.put(heuristic_id, newScore);
	}
	
	/**
	 * Decrements the score of the specified heuristic respecting the upper and lower bounds.
	 * 
	 * @param heuristic_id The ID of the heuristic whose score should be decremented.
	 */
	public void decrementScore(Integer heuristic_id) {
		int score = getScore(heuristic_id);
		int newScore = score -1;
		
		if(newScore >= lower_bound)
			heuristic_scores.put(heuristic_id, newScore);
	}
	
	/**
	 * 
	 * @return The sum of scores of all heuristics.
	 */
	public int getTotalScore() {
		int total = 0;
		
		for(Integer key : heuristic_scores.keySet())
			total += getScore(key);
		
		return total;
	}
	
	/**
	 * See exercise sheet for pseudocode!
	 
	 * @return A heuristic based on the RWS method.
	 */
	public int performRouletteWheelSelection() {
		if(getTotalScore() == 0){
			Set<Integer> ids = heuristic_scores.keySet();
			for(int id: ids)
			{
				heuristic_scores.put(id, default_score);
			}	
		}
		
		
		int totalScore = getTotalScore();
		
		
		int value = rng.nextInt(totalScore);
		
		int cumulative = 0;
		int selectedHeuristic = 0;
		
		for(Integer key : heuristic_scores.keySet()){
			selectedHeuristic = key;
			cumulative += getScore(selectedHeuristic);
			
			if(cumulative > value)
				break;
		}
		
		return selectedHeuristic;
	}
	
	/****************************************
		Utility methods useful for debugging
	 ****************************************/
	
	/**
	 * Prints the heuristic IDs into the console.
	 */
	public void printHeuristicIds() {
		
		String ids = "[" + heuristic_scores.entrySet().stream().map( e -> e.getKey().toString()).collect(Collectors.joining(", ")) + "]";
		System.out.println("IDs    = " + ids);
	}
	
	/**
	 * Prints the heuristic scores into the console.
	 */
	public void printHeuristicScores() {
		
		String scores = "[" + heuristic_scores.entrySet().stream().map( e -> e.getValue().toString()).collect(Collectors.joining(", ")) + "]";
		System.out.println("Scores = " + scores);
	}
}
