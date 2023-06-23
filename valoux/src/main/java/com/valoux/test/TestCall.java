package com.valoux.test;


import java.util.*; 

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.*; 
import com.twilio.sdk.resource.factory.*; 
import com.twilio.sdk.resource.instance.*; 
import com.twilio.sdk.resource.list.*; 
 
public class TestCall { 
 // Find your Account Sid and Token at twilio.com/user/account 
 /*public static final String ACCOUNT_SID = "AC5b115e04b4b0670735ae862389a641e1"; 
 public static final String AUTH_TOKEN = "e98fef2fe07a0fa0f78f91f0323a9040"; */
 
 public static final String ACCOUNT_SID = "ACd277a8550652cc11f18cc8fb792c77c8"; 
 public static final String AUTH_TOKEN = "d01f988dc8456793eb58f9847c2c8423";
 
 public static void main(String[]args) throws TwilioRestException { 
	TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN); 
 
	// Build the paparameters 
	List<NameValuePair> paparams = new ArrayList<NameValuePair>();
	paparams.add(new BasicNameValuePair("To", "+919655632890")); 
	paparams.add(new BasicNameValuePair("From", "+919894228950"));
	paparams.add(new BasicNameValuePair("Url", "http://demo.twilio.com/welcome/voice/"));  
	paparams.add(new BasicNameValuePair("Method", "GET"));  
	paparams.add(new BasicNameValuePair("FallbackMethod", "GET"));  
	paparams.add(new BasicNameValuePair("StatusCallbackMethod", "GET"));    
	paparams.add(new BasicNameValuePair("Record", "false")); 
 
	CallFactory callFactory = client.getAccount().getCallFactory(); 
	Call call = callFactory.create(paparams); 
	System.out.println(call.getSid()); 
 } 
}