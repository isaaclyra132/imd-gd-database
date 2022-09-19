package com.mongodb;

import static spark.Spark.*;

public class HelloWorldSparkStyle {
    public static void main(String[] args) {
    	
    	//Changing port because port 4567 raises
    	// java.net.BindException: Address already in use: bind
    	//port(8080);
        get("/", (req, res) -> {return "Hello World";});
    }
}