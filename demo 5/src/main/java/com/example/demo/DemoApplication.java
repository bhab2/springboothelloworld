package com.example.demo;

import java.io.Console;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Scanner;

import javax.management.RuntimeErrorException;
import javax.websocket.Decoder.Binary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.json.JSONObject;
import org.json.JSONString;

enum MessageType {
	init, temperature, humidity, pressure
}


@SpringBootApplication
@RestController
public class DemoApplication {

	// Class Methods

	// Private methods
	private String parseHexBinary(String hex) {
		String digits = "0123456789ABCDEF";
  		hex = hex.toUpperCase();
		String binaryString = "";
		
		for(int i = 0; i < hex.length(); i++) {
			char c = hex.charAt(i);
			int d = digits.indexOf(c);
			if(d == 0)	binaryString += "0000"; 
			else  binaryString += Integer.toBinaryString(d);
		}
		return binaryString;
	}

	// Public methods
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

	@GetMapping("/tmpsckt1")
	public String ingresString(@RequestParam(value = "binMsg", defaultValue = "0000000000000000") String binaryMsg) {

		// This method is supposed to take a msg and push it to an SQL server

		// This part is the decoding.
		
		// Error handling

		if (binaryMsg == "") {
			// move to an "Actually empty" function later
			return "Your message device sent nothing.";
		}
		if (binaryMsg.length() < 16) {
			// The message has to be 16 bits. Support for larger msgs coming soon
			return "Message needs to be 16 bits. Check with RPI portral.";
		}
		try {
			int temporaryInt = Integer.parseInt(binaryMsg, 2);
		}
		catch (Exception e) {
			// Do nothing
			return "You cannot input non-binary messages.";
		}

		// Good. Nothing to worry about.
		
		// Decode values from message
		String deviceIdString = binaryMsg.substring(0,4);
		String msgTypeString = binaryMsg.substring(4,8);
		String msgValueString = binaryMsg.substring(8,binaryMsg.length());

		// Map to Enums
		int msgTypeInt = Integer.parseInt(msgTypeString,2);
		MessageType messageType = MessageType.init;
		switch (msgTypeInt) {
			case 14: // 1110
				messageType = MessageType.temperature;
				break;
			case 12: //1100
				messageType = MessageType.humidity;
				break;
			case 8: //1000
				messageType = MessageType.pressure;
				break;
			default:
				break;
		}

		int decimalMsgValue = Integer.parseInt(msgValueString,2);


		// Just testing for now
		return ( "Your device id is: " + deviceIdString + "\nYour message type was: " + messageType + "\nYour message value was: " + decimalMsgValue);

		// Try to push the id into 

		// SQL PUSH SECTION //

		// TODO //

		// END //


	}

	@GetMapping("/tmpsckt2")
	public String hexIngress(@RequestParam(value = "hexMsg", defaultValue = "FFFFFF") String hexMsg) {
		// Takes a Hex input and runs the same shenanigans as the binary

		// Error Handling

		if (hexMsg == "") {
			// move to an "Actually empty" function later
			return "Your message device sent nothing.";
		}

		try {
			int temporaryInt = Integer.parseInt(hexMsg, 16);
		}
		catch (Exception e) {
			// Do nothing
			return "You cannot input non-hex messages.";
		}

		// Okay. No errors.

		// now, just like, print it bro

		String binaryMsg = this.parseHexBinary(hexMsg);

		String mama = this.ingresString(binaryMsg);

		return mama;
	}

}
