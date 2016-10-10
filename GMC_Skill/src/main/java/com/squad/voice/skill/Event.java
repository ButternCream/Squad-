package com.squad.voice.skill;

import java.io.*;
import java.net.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
//import org.xml.sax.XMLReader;

public class Event{
	
//	public static void main(String[] args) {
//	 	System.out.println("test");
//	 	Event e = new Event();
//	 	System.out.println(e.getFirst3Events());
//	}

	private String eventName, date, startTime, cost, category, description;
	private final String url = "http://25livepub.collegenet.com/calendars/Highlighted_Event.rss";
	
	//Default constructor
	public Event(){
		eventName = "";
		date = "";
		startTime = "";
		cost = "";
		category = "";
		description = "";
	}
	//Constructor
	public Event(String eventName, String date, String startTime, String cost, String categ, String desc){
		this.eventName = eventName;
		this.date = date;
		this.startTime = startTime;
		this.cost = cost;
		category = categ;
		description = desc;
	}
	
	//Setters
	public void setEventName(String name){eventName = name;}
	public void setDate(String date){this.date = date;}
	public void setTime(String time){startTime = time;}
	public void setCost(String cost){this.cost = cost;}
	public void setCategory(String category){this.category = category;}
	public void setDesc(String desc){description = desc;}
	//Getters
	public String getEventName(){return eventName;}
	public String getDate(){return date;}
	public String getTime(){ return startTime;}
	public String getCost(){ return cost;}
	public String getCategroy(){ return category;}
	public String getDesc(){ return description;}

	public static String replaceAll(String source, String pattern, String replacement) {
        if (source == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int index;
        int patIndex = 0;
        while ((index = source.indexOf(pattern, patIndex)) != -1) {
            sb.append(source.substring(patIndex, index));
            sb.append(replacement);
            patIndex = index + pattern.length();
        }
        sb.append(source.substring(patIndex));
        return sb.toString();
    }
	//Gets the first 3 events and their dates
    public String getFirst3Events(){
    	String titles = "";
    	try{
				//String url = "http://25livepub.collegenet.com/calendars/Highlighted_Event.rss";
				DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
				DocumentBuilder db = dbf.newDocumentBuilder();
				Document doc = db.parse(new URL(url).openStream());
				
				//System.out.println("----------Titles-----------");
				for (int i = 2; i < 5; i++){
					//System.out.println("Title:\n" + doc.getElementsByTagName("title").item(i).getTextContent());
					String currentDate = doc.getElementsByTagName("category").item(i-2).getTextContent();
					int start = currentDate.indexOf("(");
					int end = currentDate.indexOf(")");
					String toBeReplaced = currentDate.substring(start-1, end+1);
					currentDate = currentDate.replace(toBeReplaced, "");
					currentDate = replaceAll(currentDate, "/", "");
					titles += doc.getElementsByTagName("title").item(i).getTextContent() 
							+ " on" + "<say-as interpret-as=\"date\">" + currentDate + "</say-as>." + "<break strength=\"strong\"/>";
				}
				/*System.out.println("----------Descriptions-----------");
				for (int i = 0; i < doc.getElementsByTagName("description").getLength(); i++){
					String desc = "\nDescription:\n" + doc.getElementsByTagName("description").item(i).getTextContent().replaceAll("\\<.*?>", "") + "\n";
					desc = replaceAll(desc, "&quot;", "\"");
					desc = replaceAll(desc, "&amp;", "&");
					desc = replaceAll(desc, "&rsquo;", "'");
					desc = replaceAll(desc, "&nbsp;", " ");
					desc = replaceAll(desc, "&ndash;", "-");
					System.out.println(desc);
				}
				System.out.println("----------Dates/Category-----------");
				for (int i = 0; i < doc.getElementsByTagName("category").getLength(); i++){
					System.out.println(doc.getElementsByTagName("category").item(i).getTextContent());
				}*/
			}catch(Exception e){
				System.out.println("Error");
			}
			return "The next 3 events are <break strength=\"medium\"/>" + titles;
	}

	
}
	