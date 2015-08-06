package com.example.myearthquake;



import java.text.SimpleDateFormat;
import java.util.Date;

import android.location.Location;

public class Quake {

	private String detail;
	private Date date;
	private Location location;
	private double magnitude;
	private String link;
	public Quake() {

	}
	
	public Quake(String detail, Date date, Location location, double magnitude,
			String link) {
		super();
		this.detail = detail;
		this.date = date;
		this.location = location;
		this.magnitude = magnitude;
		this.link = link;
	}

	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Location getLocation() {
		return location;
	}
	public void setLocation(Location location) {
		this.location = location;
	}
	public double getMagnitude() {
		return magnitude;
	}
	public void setMagnitude(double magnitude) {
		this.magnitude = magnitude;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}

	@Override
	 public String toString() {
	    SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");
	    String dateString = sdf.format(date);
	    return dateString + ": " + magnitude + " " + detail;
	  }
	


}
