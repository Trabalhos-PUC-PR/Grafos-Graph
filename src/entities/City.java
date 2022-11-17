package entities;

import interfaces.Plotable;

public class City implements Plotable{

	private String name;
	private int lat;
	private int lng;
	private String country;
	
	public City(String[] data){
		
		data[2] = data[2].split("\\.")[0];
		data[3] = data[3].split("\\.")[0];
		
		this.name = data[0];
		this.lat = Integer.parseInt(data[2]);
		this.lng = Integer.parseInt(data[3]);
		this.country = data[4];
	}

	public String getCountry() {
		return country;
	}
	
	public int getLat() {
		return lat;
	}

	public int getLng() {
		return lng;
	}

	@Override
	public String getLabel() {
		return name;
	}

	@Override
	public void setLabel(String label) {
	}

	@Override
	public String toString() {
		return "City [name=" + name + ", lat=" + lat + ", lng=" + lng + ", country=" + country + "]";
	}
	
}
