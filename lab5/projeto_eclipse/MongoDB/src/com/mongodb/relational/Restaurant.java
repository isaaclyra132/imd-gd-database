package com.mongodb.relational;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import javax.print.Doc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.util.Helpers.printJson;

public class Restaurant {

	private String _id;
	private List<Address> addresses;
	private String borough;
	private String cuisine;
	private List<Grade> grades;
	private String name;
	private String restaurant_id;

	public Restaurant() {
	}
	
	public Restaurant(String _id, List<Address> addresses, String borough,
			String cuisine, List<Grade> grades, String name,
			String restaurant_id) {
		super();
		this._id = _id;
		this.addresses = addresses;
		this.borough = borough;
		this.cuisine = cuisine;
		this.grades = grades;
		this.name = name;
		this.restaurant_id = restaurant_id;
	}
	public String get_id() {
		return _id;
	}
	public void setId(String _id) {
		this._id = _id;
	}
	public List<Address> getAddresses() {
		return addresses;
	}
	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	public String getBorough() {
		return borough;
	}
	public void setBorough(String borough) {
		this.borough = borough;
	}
	public String getCuisine() {
		return cuisine;
	}
	public void setCuisine(String cuisine) {
		this.cuisine = cuisine;
	}
	public List<Grade> getGrades() {
		return grades;
	}
	public void setGrades(List<Grade> grades) {
		this.grades = grades;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRestaurant_id() {
		return restaurant_id;
	}
	public void setRestaurant_id(String restaurant_id) {
		this.restaurant_id = restaurant_id;
	}
	
    public String getInsertSQL() {
	        StringBuffer buffer = new StringBuffer();
	        buffer.append("INSERT INTO RESTAURANT (");
	        buffer.append(this.retornarCamposBD());
	        buffer.append(") VALUES (");
	        buffer.append(this.retornarValoresBD());
	        buffer.append(")");
	        String sql = buffer.toString();
	        return sql;	
	}

	protected String retornarCamposBD() {
    	return "_id, borough, cuisine, name, restaurant_id";
    }

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		Restaurant other = (Restaurant) obj;

		boolean simple_fields = 
        		this.get_id().equals(other.get_id())
        		&& this.getBorough().equals(other.getBorough())
        		&& this.getCuisine().equals(other.getCuisine())
        		&& this.getName().equals(other.getName())
        		&& this.getRestaurant_id().equals(other.getRestaurant_id());
        		
		boolean grades = true;
		
		List<Grade> myGrades = this.getGrades();
		List<Grade> otherGrades = other.getGrades();
		
		if (myGrades.size() == otherGrades.size()) {
			for (int i=0; i < myGrades.size(); i++) {
				if (!myGrades.get(i).equals(otherGrades.get(i)) ) {
		            grades = false;
				}
			}
		} else {
            grades = false;
		}
		
		boolean addresses = true;
		
		List<Address> myAddresses = this.getAddresses();
		List<Address> otherAddresses = other.getAddresses();
		
		if (myAddresses.size() == otherAddresses.size()) {
			for (int i=0; i < myAddresses.size(); i++) {
				if (!myAddresses.get(i).equals(otherAddresses.get(i)) ) {
		            addresses = false;
				}
			}
		} else {
			addresses = false;
		}

		
        return simple_fields & grades & addresses;
	}
	
    protected String retornarValoresBD() {
    	return
    		Util.retornarValorStringBD(this.get_id())
	        + ", "
	        + Util.retornarValorStringBD(this.getBorough())
	        + ", "
	        + Util.retornarValorStringBD(this.getCuisine())
	        + ", "
	        + Util.retornarValorStringBD(this.getName())
	        + ", "
	        + Util.retornarValorStringBD(this.getRestaurant_id());
    }
    
}
	