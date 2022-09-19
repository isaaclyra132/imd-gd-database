package com.mongodb.relational;

import static com.mongodb.util.Helpers.printJson;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class Tests {

	private String URL;
	private String NOME;
	private String SENHA;
	private int BANCO;
	
	private Connection con;
	private Statement comando;
	
	public Tests(String server, String user, String password, int banco) throws SQLException {
		this.URL = server;
		this.NOME = user;
		this.SENHA = password;
		this.BANCO = banco;
	}

	public List<Restaurant> loadRestaurantsFromMongoDB() {
        MongoClient client = new MongoClient();
        MongoDatabase database = client.getDatabase("tourinfo");
        MongoCollection<Document> collection = database.getCollection("restaurants");

        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();       
        List<Document> restaurant_docs = collection.find().into(new ArrayList<Document>());
        
        for (Document restaurant_doc : restaurant_docs) {
            // printJson(cur);
        	String _id = restaurant_doc.get("_id").toString();
        	
        	Document address_doc = (Document)restaurant_doc.get("address");
        	Address address = null;
        	List<Address> addresses =  new ArrayList<>();
        	if (address_doc != null) {
        		String building = address_doc.getString("building");

        		Coordinate coord = null;
            	List coordinates = (List<Document>) address_doc.get("coord");
            	if (coordinates.size() >= 2) {
            		Double longitude = ((Double)coordinates.get(0)).doubleValue();
            		Double latitude = ((Double)coordinates.get(1)).doubleValue();
            		coord = new Coordinate(longitude, latitude);
            	}
        		
        		String street = address_doc.getString("street");
        		String zipcode = address_doc.getString("zipcode");
        		
        		address = new Address(building,coord,street, zipcode);
        		addresses.add(address);
        	}

        	String borough = restaurant_doc.getString("borough");
        	String cuisine = restaurant_doc.getString("cuisine");
        	
        	List<Grade> grades =  new ArrayList<>();
        	List<Document> grades_doc = (List<Document>) restaurant_doc.get("grades");
        	
            if (grades_doc != null) {
                for (Document grade_doc : grades_doc) {
                    Date date = grade_doc.getDate("date");
                    String grade = grade_doc.getString("grade");
                    Integer score = (Integer)grade_doc.getInteger("score");
                    
                    Grade g = new Grade(date, grade, score);
                    grades.add(g);
                }
            }  
            
        	String name = restaurant_doc.getString("name");
        	String restaurant_id = restaurant_doc.getString("restaurant_id");
        	
        	Restaurant r = new Restaurant(_id, addresses, borough,
        			cuisine, grades, name, restaurant_id);
        	
        	restaurants.add(r);
        	
        }
        
        return restaurants;
    }
	
	private void insertRestaurantsIntoMySQL(List<Restaurant> restaurants)  {

		int count_restaurants  = 0;

		try {
			
			conectar();
			
			String sql = "";
			
	        for (Restaurant r : restaurants) {
				count_restaurants++;
				
		    	sql = r.getInsertSQL();
		        comando.executeUpdate(sql);
		    			
		    	List<Grade> grades = r.getGrades();
		    	if (grades != null) {
	                for (Grade g : grades) {
	        	    	sql = g.getInsertSQL(r.get_id());
	    		        comando.executeUpdate(sql);
	                }	
		    	}

		    	List<Address> addresses = r.getAddresses();
		    	if (addresses != null) {
	                for (Address a : addresses) {
	    		    	sql = a.getInsertSQL(r.get_id());
	    		        comando.executeUpdate(sql);
	                }	
		    	}
		    	
		        
			}
			commit();
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e){
			e.printStackTrace();
		} 
		
		System.out.println(count_restaurants + " restaurantes inserted.");
		
	}
	
	private void conectar() throws ClassNotFoundException, SQLException  {
        con = ConFactory.conexao(URL, NOME, SENHA, BANCO);  
        con.setAutoCommit(false);
        comando = con.createStatement();  
	}	  
	
    public void commit() throws Exception {
    	try {
			this.commitTransaction();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
    	
    }
    
	public void cancelTransaction() throws Exception {
	    if (con == null) {
	        throw new Exception("There is no opened connection");
	    }
	    try {
	        con.rollback();
	    } finally {
	        con.close();
	        con = null;
	    }
	}

	public void commitTransaction() throws Exception {
	    if (con == null) {
	        throw new Exception("There is no opened connection");
	    }
	    try {
	        con.commit();
	    } finally {
	        con.close();
	        con = null;
	    }
	}

	public List<Restaurant> loadRestaurantsFromMySQLNaive() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        try {
            ResultSet rs_restaurants = null;
            try {
            	conectar();
            	String query = "SELECT * FROM restaurant";
                //System.out.println(query);
                rs_restaurants = comando.executeQuery(query);
            	
                while (rs_restaurants.next()) {
                	Restaurant r = new Restaurant();
                	
                	r.setId(rs_restaurants.getString("_id"));
                	r.setBorough(rs_restaurants.getString("borough"));
                	r.setCuisine(rs_restaurants.getString("cuisine"));
                	r.setName(rs_restaurants.getString("name"));
                	r.setRestaurant_id(rs_restaurants.getString("restaurant_id"));
                	restaurants.add(r);
                }
            } finally {
    			if (rs_restaurants != null) {
    				try {
    					rs_restaurants.close();
    				} catch (SQLException sqlEx) { 
    					sqlEx.printStackTrace();
    				} 
    				rs_restaurants = null;
    			}
            }
            
            ResultSet rs_addresses = null;
            try {
            	conectar();
                for (Restaurant r : restaurants) {
                    String query = "SELECT * FROM (restaurant JOIN address ON _id=restaurant_fk) WHERE _id = "+Util.retornarValorStringBD(r.get_id());
                    rs_addresses = comando.executeQuery(query);

                    List<Address> addresses = new ArrayList<Address>();
                    while (rs_addresses.next()) {
                    	int id = rs_addresses.getInt("id");
                    	
                    	String building = rs_addresses.getString("building");
                    	String street = rs_addresses.getString("street");
                    	String zipcode = rs_addresses.getString("zipcode");
                    	
                    	Coordinate coord = null;
                    	if (rs_addresses.getObject("longitude") != null && 
                    			rs_addresses.getObject("latitude") != null) {
                        	double longitude = rs_addresses.getDouble("longitude");
                        	double latitude = rs_addresses.getDouble("latitude");
                        	coord = new Coordinate(longitude,latitude);
                    	}
                    	
                    	Address address = new Address(building, coord, street, zipcode);
                    	address.setId(id);
                    	
                    	addresses.add(address);
                    }
                	r.setAddresses(addresses);
                }
            } finally {
    			if (rs_addresses != null) {
    				try {
    					rs_addresses.close();
    				} catch (SQLException sqlEx) {
    					sqlEx.printStackTrace();
    				}
    				rs_addresses = null;
    			}
}
            
            ResultSet rs_grades = null;
            try {
            	conectar();
                for (Restaurant r : restaurants) {
                    String query = "SELECT * FROM (restaurant JOIN grade ON _id=restaurant_fk) WHERE _id = "+Util.retornarValorStringBD(r.get_id());
                    // System.out.println(query);
                    rs_grades = comando.executeQuery(query);

                    List<Grade> grades = new ArrayList<Grade>();
                    
                    while (rs_grades.next()) {
                    	int id = rs_grades.getInt("id");
                    	Date date = rs_grades.getDate("date");
                    	String grade = rs_grades.getString("grade");
                    	Integer score = null;
                    	if (rs_grades.getObject("score") != null) {
                    		score = rs_grades.getInt("score");
                    	}
                    	Grade g = new Grade(date,grade,score);
                    	g.setId(id);
                    	grades.add(g);
                    	
                    }
                	r.setGrades(grades);
                }
            } finally {
    			if (rs_grades != null) {
    				try {
    					rs_grades.close();
    				} catch (SQLException sqlEx) {
    					sqlEx.printStackTrace();
    				}
    				rs_grades = null;
    			}
    			if (comando != null) {
    				try {
    					comando.close();
    				} catch (SQLException sqlEx) { 
    				}
    				comando = null;
    			}
            }

            
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        return restaurants;
    }

	public List<Restaurant> loadRestaurantsFromMySQLOneBDAccess() {
        List<Restaurant> restaurants = new ArrayList<Restaurant>();
        try {
            ResultSet rs_restaurants = null;
            try {
            	conectar();
            	String query = 
            			"SELECT _id, borough, cuisine, name, restaurant_id, "
            			+ "g.id AS grade_id, date, grade, score, "
            			+ "a.id AS address_id, building, longitude, "
            			+ "latitude, street, zipcode "
            			+ "FROM (restaurant AS r LEFT JOIN grade as g ON r._id=g.restaurant_fk) "
            			+ "LEFT JOIN address AS a ON r._id=a.restaurant_fk ORDER BY r._id";
                rs_restaurants = comando.executeQuery(query);
            	
                while (rs_restaurants.next()) {
                	Restaurant r = new Restaurant();
                	
                	r.setId(rs_restaurants.getString("_id"));
                	r.setBorough(rs_restaurants.getString("borough"));
                	r.setCuisine(rs_restaurants.getString("cuisine"));
                	r.setName(rs_restaurants.getString("name"));
                	r.setRestaurant_id(rs_restaurants.getString("restaurant_id"));

                	int address_id = rs_restaurants.getInt("address_id");
                	String building = rs_restaurants.getString("building");
                	String street = rs_restaurants.getString("street");
                	String zipcode = rs_restaurants.getString("zipcode");


                	Coordinate coord = null;
                	if (rs_restaurants.getObject("longitude") != null && 
                			rs_restaurants.getObject("latitude") != null) {
                    	double longitude = rs_restaurants.getDouble("longitude");
                    	double latitude = rs_restaurants.getDouble("latitude");
                    	coord = new Coordinate(longitude,latitude);
                	}
                	
                	Address address = new Address(building, coord, street, zipcode);
                	address.setId(address_id);

                	List<Address> addresses = new ArrayList<Address>();
                	addresses.add(address);
                	r.setAddresses(addresses);

                    List<Grade> grades = new ArrayList<Grade>();

                	if (rs_restaurants.getObject("grade_id") != null) {
                        int grade_id = rs_restaurants.getInt("grade_id");
                        Date date = rs_restaurants.getDate("date");
                    	String grade = rs_restaurants.getString("grade");
                    	Integer score = null;
                    	if (rs_restaurants.getObject("score") != null) {
                    		score = rs_restaurants.getInt("score");
                    	}
                    	Grade g = new Grade(date,grade,score);
                    	g.setId(grade_id);
                    	grades.add(g);
                	}
                	
                	r.setGrades(grades);
                	
                	restaurants.add(r);
                }
            } finally {
    			if (rs_restaurants != null) {
    				try {
    					rs_restaurants.close();
    				} catch (SQLException sqlEx) { 
    					sqlEx.printStackTrace();
    				} 
    				rs_restaurants = null;
    			}
    			if (comando != null) {
    				try {
    					comando.close();
    				} catch (SQLException sqlEx) { 
    				}
    				comando = null;
    			}
            }
        } catch (SQLException SQLe) {
            SQLe.printStackTrace();
        } catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

        List<Restaurant> restaurants_filtered = new ArrayList<Restaurant>();

        Restaurant current = null;
		for (Restaurant r: restaurants) {
			// This is an extremely expensive way of filtering
			//if (!restaurants_filtered.contains(r)){

			if (current == null || !current.get_id().equals(r.get_id())) {
				current = r;
				restaurants_filtered.add(current);
			} else {
				for (Address a: r.getAddresses()) {
					if (!current.getAddresses().contains(a)) {
						current.getAddresses().add(a);
					}
				}
				current.getGrades().addAll(r.getGrades());
			}
		} 
        
        return restaurants_filtered;
    }
	
	public void compareLists(List<Restaurant> list_1, List<Restaurant> list_2) {
		boolean result = true;
		if (list_1.size() == list_2.size()) {
			for (int i=0; i < list_1.size() && result; i++) {
				Restaurant restaurant_a = list_1.get(i);
				Restaurant restaurant_b = list_2.get(i);
				
				boolean equals = restaurant_a.equals(restaurant_b);
				
				if (!equals) {
					result = false;
				}
			}
		} else {
            System.out.println("Ups! They do not have the same size.");
			result = false;
		}
		if (result) {
            System.out.println("OK! They are equal.");
		} else {
            System.out.println("Ups! They are not equal.");
		}
	}
	
	
	public static void main(String args[]){
	    try {
			Tests tests = new Tests("jdbc:mysql://localhost/restaurants?useTimezone=true&serverTimezone=America/Fortaleza","root","root",ConFactory.MYSQL);

			// Use to search for all restaurants in MongoDB
            System.out.println("MongoDB: Loading restaurants from MongoDB...");
			long begin = System.currentTimeMillis();
			List<Restaurant> restaurantsMongoDB = tests.loadRestaurantsFromMongoDB();
			long end = System.currentTimeMillis();
            System.out.println("MongoDB: Loaded "+restaurantsMongoDB.size()+ " restaurants in "+(end-begin)+"ms");
			
			// Use to insert the restaurants from MongoDB into MySQL
            System.out.println("My SQL: Inserting restaurants in MySQL...");
			begin = System.currentTimeMillis();
			tests.insertRestaurantsIntoMySQL(restaurantsMongoDB);
			end = System.currentTimeMillis();
            System.out.println("Inserting in MySQL: Inserted in "+(end-begin)+"ms");
			
			// Use to search for all restaurants in MySQL (Naive)
            System.out.println("MySQL Naive: Loading restaurants from MySQL...");
			begin = System.currentTimeMillis();
			List<Restaurant> restaurantsMySQLNaive = tests.loadRestaurantsFromMySQLNaive();
			end = System.currentTimeMillis();
            System.out.println("MySQL Naive: Loaded "+restaurantsMySQLNaive.size()+ " restaurants in "+(end-begin)+"ms");
            
			// Use to search for all restaurants in MySQL (Smart)
            System.out.println("MySQL One Access: Loading restaurants from MySQL...");
			begin = System.currentTimeMillis();
			List<Restaurant> restaurantsMySQLOneAccess = tests.loadRestaurantsFromMySQLOneBDAccess();
			end = System.currentTimeMillis();
            System.out.println("MySQL One Access: Loaded "+restaurantsMySQLOneAccess.size()+ " restaurants in "+(end-begin)+"ms");

            System.out.println("Results: Comparing MongoDB data with MySQL One Access data...");
			begin = System.currentTimeMillis();
			tests.compareLists(restaurantsMongoDB, restaurantsMySQLOneAccess);
			end = System.currentTimeMillis();
            System.out.println("Results: Finished in "+(end-begin)+"ms");
            
            System.out.println("Results: Comparing MongoDB data with MySQL Naive data...");
			begin = System.currentTimeMillis();
			tests.compareLists(restaurantsMongoDB, restaurantsMySQLNaive);
			end = System.currentTimeMillis();
            System.out.println("Results: Finished in "+(end-begin)+"ms");
			
	    } catch (Exception e) {
	    	e.printStackTrace();
	    }
	}
	
}
