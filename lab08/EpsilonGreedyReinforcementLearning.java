package com.g52aim.lab08;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class EpsilonGreedyReinforcementLearning implements ReinforcementLearning {
	
	/**
	 * Pre-implemented - The Comparator used for ordering the HeuristicScore Objects
	 * while allowing duplicate heuristic scores within a Set based on differing
	 * heuristic IDs.
	 */
	private static Comparator<? super HeuristicScore> heuristicScoreComparator = (a, b) -> Double.compare(a.getScore() + (1.0 / (a.getHeuristicId() + 2)), b.getScore() + (1.0 / (b.getHeuristicId() + 2)));

	/**
	 * The datastructure which should be used for storing heuristic IDs and their scores.
	 */
	public TreeSet<HeuristicScore> scores;
		
	/**
	 * The upper bound on the heuristic scores as used by reinforcement learning.
	 */
	public final int upper_bound;
	
	/**
	 * The lower bound on the heuristic scores as used by reinforcement learning.
	 */
	public final int lower_bound;
	
	/**
	 * The random number generator.
	 */
	public final Random rng;
	
	/**
	 * The epsilon parameter setting where 0 < epsilon < 1.
	 */
	public final double EPSILON = 1;
	
	/**
	 * 
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound The lower bound on the heuristic scores.
	 * @param upper_bound The upper bound on the heuristic scores.
	 * @param rng The random number generator.
	 */
	public EpsilonGreedyReinforcementLearning(int[] heuristic_ids, int default_score, int lower_bound, int upper_bound, Random rng) {
		
		this(new TreeSet<HeuristicScore>(heuristicScoreComparator),
				heuristic_ids, default_score, lower_bound, upper_bound, rng);
	}
	
	/**
	 * 
	 * @param heuristic_scores An empty Set containing heuristic ID's and scores.
	 * @param heuristic_ids The set of heuristic IDs.
	 * @param default_score The default score to give each heuristic.
	 * @param lower_bound The lower bound on the heuristic scores.
	 * @param upper_bound The upper bound on the heuristic scores.
	 * @param rng The random number generator.
	 */
	public EpsilonGreedyReinforcementLearning(TreeSet<HeuristicScore> scores, int[] heuristic_ids, int default_score, int lower_bound, int upper_bound, Random rng) {
		
		this.scores = scores;
		this.upper_bound = upper_bound;
		this.lower_bound = lower_bound;
		this.rng = rng;
		
		for(int id : heuristic_ids){
			HeuristicScore h_score = new HeuristicScore(id, default_score);
			scores.add(h_score);
		}
	}
	
	/**
	 * 
	 * @param heuristic_id The ID of the heuristic.
	 * @return The score for the aforementioned heuristic.
	 */	
	public int getScore(int heuristic_id) {
		return getHeuristicScore(heuristic_id).getScore();
	}
	
	/**
	 *
	 * @param heuristic_id The ID of the heuristic.
	 * @return The HeuristicScore Object from "scores" corresponding to the heuristic with ID heuristic_id.
	 */
	public HeuristicScore getHeuristicScore(int heuristic_id) {
		Iterator<HeuristicScore> it = scores.iterator();
		
		while(it.hasNext()){
			HeuristicScore hs = it.next();
			if(hs.getHeuristicId() == heuristic_id)
				return hs;
		}
		
		return null;
	}
	
	/**
	 * Increments the score of the specified heuristic respecting the upper and lower bounds.
	 * 
	 * @param heuristic_id The ID of the heuristic whose score should be incremented.
	 */
	public void incrementScore(int heuristic_id) {
		HeuristicScore hs = getHeuristicScore(heuristic_id);
		int newScore = getHeuristicScore(heuristic_id).getScore() + 1;
		
		if(newScore <= upper_bound){
			scores.remove(hs);
			hs.setScore(newScore);
			scores.add(hs);
		}
	}
	
	/**
	 * Decrements the score of the specified heuristic respecting the upper and lower bounds.
	 * 
	 * @param heuristic_id The ID of the heuristic whose score should be decremented.
	 */
	public void decrementScore(int heuristic_id) {
		HeuristicScore hs = getHeuristicScore(heuristic_id);
		int newScore = getHeuristicScore(heuristic_id).getScore() - 1;
		
		
		if(newScore >= lower_bound){
			scores.remove(hs);
			hs.setScore(newScore);
			scores.add(hs);
		}
	}
	
	/**
	 * 
	 * @return The sum of scores of all heuristics.
	 */
	public int getTotalScore() {

		int total = 0;
		Iterator<HeuristicScore> hs = this.scores.iterator();
		
		while(hs.hasNext()) {
			total += hs.next().getScore();
		}
		
		return total;
	}
	
	/**
	 * See exercise sheet for pseudocode!
	 *
	 * @return A heuristic based on epsilon-greedy selection method.
	 */
	public int selectNextHeuristic() {
		int chosenHeuristic = 0;
		double bestScore = scores.last().getScore();
		ArrayList<HeuristicScore> bestHs = new ArrayList<HeuristicScore>();	
		Iterator<HeuristicScore> it = scores.descendingIterator();
		
		while(it.hasNext()){
			HeuristicScore hs = it.next();
			if(hs.getScore() == bestScore)
				bestHs.add(hs);
			else
				break;
		}
		
			if(rng.nextDouble() < (1 - EPSILON)){
				int i = 0;
				int ind = rng.nextInt(scores.size());
				
				Iterator<HeuristicScore> it1 = scores.iterator();
				
				while(it1.hasNext()){
					HeuristicScore hs1 = it1.next();
					if(ind == i++)
						chosenHeuristic = hs1.getHeuristicId();	
				}
			}else{
				int ind = rng.nextInt(bestHs.size());
				
				chosenHeuristic = bestHs.get(ind).getHeuristicId();			
			}
		
		return chosenHeuristic;
	}
	
	/****************************************
		Utility methods useful for debugging
	 ****************************************/
	
	/**
	 * Prints the heuristic IDs into the console.
	 */
	public void printHeuristicIds() {
		
		String ids = "[" + scores.stream().map( e -> Integer.toString(e.getHeuristicId())).collect(Collectors.joining(", ")) + "]";
		System.out.println("IDs    = " + ids);
	}
	
	/**
	 * Prints the heuristic scores into the console.
	 */
	public void printHeuristicScores() {
		
		String scores = "[" + this.scores.stream().map( e -> Integer.toString(e.getScore())).collect(Collectors.joining(", ")) + "]";
		System.out.println("Scores = " + scores);
	}

}
