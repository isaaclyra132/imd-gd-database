package com.mongodb.relational;

public class Coordinate {
	private double longitude;
	private double latitude;
	
	public Coordinate(double longitude, double latitude) {
		this.setLongitude(longitude);
		this.setLatitude(latitude);
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Coordinate other = (Coordinate) obj;

		boolean simple_fields = 
        		this.getLongitude() == other.getLongitude()
        		& this.getLatitude() == other.getLatitude();
	
        return simple_fields;
	}
	
}
