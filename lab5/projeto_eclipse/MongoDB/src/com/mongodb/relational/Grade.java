package com.mongodb.relational;

import java.util.Date;

public class Grade {
	private int id;
	private Date date;
	private String grade;
	private Integer score;
	
	public Grade(Date date, String grade, Integer score) {
		this.setDate(date);
		this.setGrade(grade);
		this.setScore(score);
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}
	
    public String getInsertSQL(String restaurant) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("INSERT INTO GRADE (");
        buffer.append(this.retornarCamposBD());
        buffer.append(") VALUES (");
        buffer.append(this.retornarValoresBD(restaurant));
        buffer.append(")");
        String sql = buffer.toString();
        return sql;	
}

	protected String retornarCamposBD() {
		return "date, grade, score, restaurant_fk";
	}
	
	protected String retornarValoresBD(String restaurant) {
		String sqlDate = 
				(new java.sql.Date(this.getDate().getTime())).toString(); 
		
		String sql = 
				Util.retornarValorStringBD(sqlDate)
		        + ", "
		        + Util.retornarValorStringBD(this.getGrade())
		        + ", "
		        + this.getScore()
		        + ", "
		        + Util.retornarValorStringBD(restaurant);

		return sql;
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

		Grade other = (Grade) obj;

		boolean simple_fields = 
        		this.getGrade().equals(other.getGrade());

		boolean score = true; 
		if (this.getScore() != null && other.getScore() != null) {
			score = this.getScore().intValue() == other.getScore().intValue(); 
		} else if (this.getScore() != null && other.getScore() == null) {
			score = false;
		} else if (this.getScore() == null && other.getScore() != null) {
			score = false;
		}

		boolean date = 
				this.getDate().getYear() == other.getDate().getYear()
				&& this.getDate().getMonth() == other.getDate().getMonth()
				&& this.getDate().getDay() == other.getDate().getDay();

        return simple_fields & score & date;
	}
	
}
