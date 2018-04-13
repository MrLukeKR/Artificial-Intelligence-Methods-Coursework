package com.g52aim.lab08;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class LateAcceptance {
	
	/*
	 * The array storing the objective values of list_length
	 * previously accepted solutions.
	 */
	public double[] late_accepting_list;
	
	/*
	 * The length of the list.
	 */
	public final int list_length;
	
	/*
	 * A counter which "points" to the objective value of the 
	 * solution accepted list_length iterations previously.
	 */
	public int list_pointer;
	
	/**
	 * Constructs a Late Acceptance method of list length list_length and 
	 * initialises each value to f(s_0).
	 *
	 * @param list_length The length of the late accepting list.
	 * @param initial_solution_fitness The objective value of the initial solution.
	 */
	public LateAcceptance(int list_length, double initial_solution_fitness) {
		
		this.list_length = list_length;
		this.list_pointer = 0;
		
		late_accepting_list = new double[list_length];
		
		for(int x = 0; x < list_length; x++)
			late_accepting_list[x] = initial_solution_fitness;
		}
	
	/**
	 * Performs a single iteration of the late acceptance method (see pseudocode in the exercise sheet).
	 * This should return whether the move should be accepted or rejected as well as updating the list value and list pointer.
	 *
	 * @param current_fitness The objective value of the current solution.
	 * @param candidate_fitness The objective value of the candidate solution.
	 * @return Whether to accept or reject the current move.
	 */
	public boolean accept(double current_fitness, double candidate_fitness) {
		boolean accept = false;
		
			if(candidate_fitness <= getCurrentThreshold() || candidate_fitness <= current_fitness){
				updateListValue(candidate_fitness);
				accept = true;
			}else
				updateListValue(current_fitness);

			advanceListPointer();

			return accept;
	}
	
	/**
	 * Retrieves the threshold value contained in the late_accepting_list at the current index.
	 *
	 * @return The threshold value contained in the late_accepting_list at the current index.
	 */
	public double getCurrentThreshold() {
		
		return late_accepting_list[list_pointer];
	}
	
	/**
	 * Updates the value in the late_accepting_list to value at the current index.
	 */
	public void updateListValue(double value) {
		late_accepting_list[list_pointer] = value;
	}
	
	/**
	 * Advances the list pointer to the next index, wrapping around if at the end.
	 */
	public void advanceListPointer() {

		list_pointer = (list_pointer + 1) % list_length;
	}
	
	/****************************************
		Utility methods useful for debugging
	 ****************************************/
	 
	public void printList() {
		
		String arr = "[" + Arrays.stream(late_accepting_list).boxed().map( d -> d.toString()).collect(Collectors.joining(", ")) + "]";
		int length = ("[" + Arrays.stream(late_accepting_list, 0, list_pointer).boxed().map( d -> d.toString() ).collect(Collectors.joining(", ")) + ",").length() + 1;
		StringBuffer pointer = new StringBuffer(length + 1);
		
		if(length == 3) {
			length = 1;
		}
		
		for(int i = 0; i < length; i++) {
			pointer.append(" ");
		}
		
		pointer.append("^");
		System.out.println(arr);
		System.out.println(pointer.toString());
	}
}
