package entities;

import java.util.Objects;

import interfaces.Plotable;

public class Adjacency implements Comparable<Adjacency>{

	private Plotable vertex;
	private Double weight;

	public Adjacency(Plotable adjacency) {
		this.vertex = adjacency;
		this.weight = null;
	}
	
	public Adjacency(Plotable adjacency, Double weight) {
		this.vertex = adjacency;
		this.weight = weight;
	}
	
	public Adjacency(Plotable adjacency, int weight) {
		this.vertex = adjacency;
		this.weight = (double) weight;
	}

	public void upWeightByOne() {
		this.weight++;
	}
	
	public void downWeightByOne() {
		this.weight--;
	}
	
	public Plotable getAdjacent() {
		return vertex;
	}
	
	public Double getWeight() { 
		return weight;
	}

	@Override
	public String toString() {
		return String.format("(%.2f) %s", weight, vertex.getLabel());
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(vertex);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Adjacency other = (Adjacency) obj;
		return Objects.equals(vertex, other.getAdjacent());
	}

	@Override
	public int compareTo(Adjacency o) {
		if(getWeight() > o.getWeight()) {
			return -1;
		}
		if(getWeight() < o.getWeight()) {
			return 1;
		}
		return 0;
	}
	
	
	
}
