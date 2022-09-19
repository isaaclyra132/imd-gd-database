package com.mongodb.relational;

public class Address {

	private int id;
	private String building;
	private Coordinate coord;
	private String street;
	private String zipcode;
	
	public Address(String building, Coordinate coord, String street, String zipcode) {
		this.setBuilding(building);
		this.setCoord(coord);
		this.setStreet(street);
		this.setZipcode(zipcode);
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public Coordinate getCoord() {
		return coord;
	}

	public void setCoord(Coordinate coord) {
		this.coord = coord;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	
    public String getInsertSQL(String restaurant) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO ADDRESS (");
        buffer.append(this.retornarCamposBD());
        buffer.append(") VALUES (");
        buffer.append(this.retornarValoresBD(restaurant));
        buffer.append(")");
        String sql = buffer.toString();

        return sql;	
}

	protected String retornarCamposBD() {
		return "building, longitude, latitude, street, zipcode, restaurant_fk";
	}
	
	protected String retornarValoresBD(String restaurant) {
		String str = "";
		str = str + 
				Util.retornarValorStringBD(this.getBuilding())
		        + ", ";
		
		if (this.getCoord() != null) {
			str = str 
		        + this.getCoord().getLongitude()
		        + ", "
		        + this.getCoord().getLatitude()
		        + ", ";
		} else {
			str = str 
			        + "null"
			        + ", "
			        + "null"
			        + ",";
		}
		str = str 
				+ Util.retornarValorStringBD(this.getStreet())
		        + ", "
				+ Util.retornarValorStringBD(this.getZipcode())
		        + ", "
		        + Util.retornarValorStringBD(restaurant);

		return str;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Address other = (Address) obj;

		boolean simple_fields = 
        		this.getBuilding().equals(other.getBuilding())
        		& this.getStreet().equals(other.getStreet())
        		& this.getZipcode().equals(other.getZipcode());
	
		boolean coordinates = true;
		if (this.getCoord() != null && other.getCoord() != null) {
			coordinates = this.getCoord().equals(other.getCoord());
		} else if (this.getCoord() != null && other.getCoord() == null) {
			coordinates = false;
		} else if (this.getCoord() == null && other.getCoord() != null) {
			coordinates = false;
		}
		
        return simple_fields & coordinates;
	}
        
}
