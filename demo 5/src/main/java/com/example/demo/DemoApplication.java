package com.example.demo;

import java.io.Console;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

import javax.management.RuntimeErrorException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;
import org.json.JSONString;



@SpringBootApplication
@RestController
public class DemoApplication {

	// Class Methods
	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@GetMapping("/hello")
	public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
		return String.format("Hello %s!", name);
	}

	@GetMapping("/now")
	public String nowTime() {
		Date date=java.util.Calendar.getInstance().getTime();
		String dateString = date.toString();

		return dateString;
	}

	@GetMapping("/haha")
	public String haha() {
		return "haha";
	}

	@GetMapping("/coinspot")
	public String coinspotCoins() {
		// Creating the URL

		try {
			URL url = new URL("https://www.coinspot.com.au/pubapi/v2/latest");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("GET");

			conn.connect();

			// Get response code
			int response = conn.getResponseCode();

			// Handle request codes
			if (response != 200) {
				// Not OK
				System.out.println("What the hell is going on here;");
				return "Failed";
			
			}
			else {
				String inline = "";
				Scanner scanner = new Scanner(url.openStream());

				// Write JSON to inline ingest
				while (scanner.hasNext()) {
					inline += scanner.nextLine();
				}

			scanner.close();

			// Parse
			return inline;
		
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "REALLY TERRIBLY failed";
		
	}

}
