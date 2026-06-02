package com.omrbranch.address;

import java.util.ArrayList;
import java.util.List;

import com.omrbranch.pojo.adduseraddress.AddUserAddress_Input_Pojo;
import com.omrbranch.pojo.adduseraddress.AddUserAddress_Output_Pojo;
import com.omrbranch.pojo.citylist.CityList;
import com.omrbranch.pojo.citylist.CityList_Input_Pojo;
import com.omrbranch.pojo.citylist.CityList_Output_Pojo;
import com.omrbranch.pojo.deleteaddress.DeleteAddress_Input_Pojo;
import com.omrbranch.pojo.deleteaddress.DeleteAddress_Output_Pojo;
import com.omrbranch.pojo.getuseraddress.GetUserAddress_Output_Pojo;
import com.omrbranch.pojo.statelist.StateList;
import com.omrbranch.pojo.statelist.StateList_Output_Pojo;
import com.omrbranch.postmanbasicauthlogin.Login;
import com.omrbranch.updateuseraddress.UpdateUserAddress_Input_Pojo;
import com.omrbranch.updateuseraddress.UpdateUserAddress_Output_Pojo;
import com.omrbranch.utility.BaseClass;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class Address extends BaseClass {

	String stateIdText;

	String cityId;

	int id;

	int cityID;
	int addressId;
	public static String addressID;

	public void cityList() {
		initRestAssured();

		// Header
		List<Header> lstHeader = new ArrayList<Header>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");

		lstHeader.add(h1);
		lstHeader.add(h2);

		Headers headers = new Headers(lstHeader);

		addHeaders(headers);

		// Basic Auth
		addBasicAuthentication("richardsonsam2805@gmail.com", "Richie@2805");

//		addPayload("{")

		CityList_Input_Pojo cityList_Input_Pojo = new CityList_Input_Pojo(stateIdText);

		addPayload(cityList_Input_Pojo);

		Response response = sendRequest("POST", "https://www.omrbranch.com/api/cityList");

		CityList_Output_Pojo cityList_Output_Pojo = response.as(CityList_Output_Pojo.class);

		ArrayList<CityList> cityList = cityList_Output_Pojo.getData();

		for (CityList eachCity : cityList) {

			String cityName = eachCity.getName();

			if (cityName.equals("Yercaud")) {
				cityID = eachCity.getId();
				cityId = String.valueOf(cityID);

				System.out.println(cityID);

			}

		}

//		for (CityList eachCity : cityList) {
//
//		    if (eachCity.getId() == 4323) {
//
//		        System.out.println(eachCity.getName());
//		        break;
//		    }
//		    
//		}
	}

	public void selectState() {

		initRestAssured();
		addHeader("accept", "application/json");
		addBasicAuthentication("richardsonsam2805@gmail.com", "Richie@2805");
		Response response = sendRequest("GET", "https://www.omrbranch.com/api/stateList" + "");

		StateList_Output_Pojo stateList_Output_Pojo = response.as(StateList_Output_Pojo.class);

		// Find the State id of TN
		ArrayList<StateList> data = stateList_Output_Pojo.getData();
		for (StateList eachStateList : data) {
			String name = eachStateList.getName();
			if (name.equals("Tamil Nadu")) {
				id = eachStateList.getId();

				stateIdText = String.valueOf(id);
				System.out.println(stateIdText);
				break;
			}
		}

		int statusCode = getStatusCode(response);
		System.out.println(statusCode);

	}

	public void addUserAddress() {
		initRestAssured();

		// Headers
		List<Header> lstHeader = new ArrayList<Header>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");

		// Bearer Token
		Header h3 = new Header("Authorization", "Bearer " + Login.logtoken);

		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);

		addHeaders(headers);

		// Payload
		AddUserAddress_Input_Pojo address_Input_Pojo = new AddUserAddress_Input_Pojo("sam", "Khundra", "1234567898",
				"apartment", id, cityID, 101, "202020", "64/63 partap nagar", "home");

		addPayload(address_Input_Pojo);

		Response response = sendRequest("POST", "https://www.omrbranch.com/api/addUserAddress");

		System.out.println(getStatusCode(response));
		System.out.println(response.asPrettyString());

		AddUserAddress_Output_Pojo outputPojo = response.as(AddUserAddress_Output_Pojo.class);
//
		addressId = outputPojo.getAddress_id();

		addressID = String.valueOf(addressId);
//
		System.out.println("Address ID : "+addressId);

	}

	public void updateUserAddress() {

		initRestAssured();

		// Headers
		List<Header> lstHeader = new ArrayList<Header>();

		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Content-Type", "application/json");
		Header h3 = new Header("Authorization", "Bearer " + Login.logtoken);

		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);

		Headers headers = new Headers(lstHeader);

		addHeaders(headers);

		// Payload
		UpdateUserAddress_Input_Pojo inputPojo = new UpdateUserAddress_Input_Pojo(addressID, "sam", "Richardson",
				"1234567898", "apartment", id, cityID, 101, "202020", "64/63 partap nagar", "home");

		addPayload(inputPojo);

		Response response = sendRequest("PUT", "https://www.omrbranch.com/api/updateUserAddress");

		System.out.println(getStatusCode(response));
		System.out.println(response.asPrettyString());

		UpdateUserAddress_Output_Pojo outputPojo = response.as(UpdateUserAddress_Output_Pojo.class);

		System.out.println("Update Message"+outputPojo.getMessage());
	}

	public void getUserAddress() {
		initRestAssured();

		List<Header> lstHeader = new ArrayList<Header>();

		Header h1 = new Header("accept", "application/json");

		Header h2 = new Header("Authorization", "Bearer " + Login.logtoken);
		lstHeader.add(h1);
		lstHeader.add(h2);
		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
		Response response = sendRequest("GET", "https://www.omrbranch.com/api/getUserAddress");
		System.out.println(getStatusCode(response));
//	System.out.println(response.asPrettyString());
		GetUserAddress_Output_Pojo outputPojo = response.as(GetUserAddress_Output_Pojo.class);

		String message = outputPojo.getMessage();

		System.out.println(message);

	}

public void deleteAddress() {
	
	initRestAssured();

	// Headers
	List<Header> lstHeader = new ArrayList<Header>();

	Header h1 = new Header("accept", "application/json");
	Header h2 = new Header("Content-Type", "application/json");
	Header h3 = new Header("Authorization", "Bearer " + Login.logtoken);

	lstHeader.add(h1);
	lstHeader.add(h2);
	lstHeader.add(h3);

	Headers headers = new Headers(lstHeader);

	addHeaders(headers);
	
	DeleteAddress_Input_Pojo deleteAddress=new DeleteAddress_Input_Pojo(addressID);
	
	addPayload(deleteAddress);
	Response response = sendRequest("DELETE", "https://www.omrbranch.com/api/deleteAddress");
	System.out.println(getStatusCode(response));
	DeleteAddress_Output_Pojo deleteMessage = response.as(DeleteAddress_Output_Pojo.class);
	System.out.println(deleteMessage.getMessage());
}
}
