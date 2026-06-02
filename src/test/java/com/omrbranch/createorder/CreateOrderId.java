package com.omrbranch.createorder;

import java.util.ArrayList;
import java.util.List;

import com.omrbranch.address.Address;
import com.omrbranch.pojo.addtocart.AddToCart_Input_Pojo;
import com.omrbranch.pojo.createorder.CreateOrder_Input_Pojo;
import com.omrbranch.pojo.createorder.CreateOrder_Output_Pojo;
import com.omrbranch.pojo.getcartitems.GetCartItems_Output_Pojo;
import com.omrbranch.pojo.getcartitems.GetCartList;
import com.omrbranch.pojo.getsearchproduct.GetSearchProduct_Input_Pojo;
import com.omrbranch.pojo.getsearchproduct.GetSearchProduct_Output_Pojo;
import com.omrbranch.pojo.getsearchproduct.GetSearchproductList_Pojo;
import com.omrbranch.pojo.getsearchproduct.Option;
import com.omrbranch.pojo.getsearchproduct.Variation_Pojo;
import com.omrbranch.pojo.searchproduct.SearchProduct_Input_Pojo;
import com.omrbranch.pojo.searchproduct.SearchProduct_Output_Pojo;
import com.omrbranch.pojo.searchproduct.SearchProduct_Pojo;
import com.omrbranch.pojo.setaddress.SetAddress_Input_Pojo;
import com.omrbranch.pojo.setaddress.SetAddress_Output_Pojo;
import com.omrbranch.postmanbasicauthlogin.Login;
import com.omrbranch.utility.BaseClass;

import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;

public class CreateOrderId extends BaseClass {
	String txtProductId;
	String txtCategoryId;
	String txtVariationId;
	String txtCart_id;
	
	

	public void searchProduct() {
		initRestAssured();

		List<Header> lstHeader = new ArrayList<Header>();

		Header h1 = new Header("accept", "application/json");

		Header h2 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);
		Headers headers = new Headers(lstHeader);
		addHeaders(headers);
		SearchProduct_Input_Pojo searchProduct_Input_Pojo = new SearchProduct_Input_Pojo("nuts");
		addPayload(searchProduct_Input_Pojo);
		Response response = sendRequest("POST", "https://www.omrbranch.com/api/searchProduct");
		System.out.println(getStatusCode(response));
		SearchProduct_Output_Pojo searchProduct_Output_Pojo = response.as(SearchProduct_Output_Pojo.class);

		for (SearchProduct_Pojo eachProduct : searchProduct_Output_Pojo.getData()) {

			String text = eachProduct.getText();

			if (text.equals("Tata Sampann 100% Iranian Pistachios Roasted & Salted in Fruit & Nuts")) {

				int id = eachProduct.getId();
				int categoryId = eachProduct.getCategory_id();

				txtCategoryId = String.valueOf(categoryId);
				txtProductId = String.valueOf(id);

				System.out.println("Product Id : " + id);
				System.out.println("Category Id : " + categoryId);

				break;
			}
		}

	}

	public void getSearchResult() {
		initRestAssured();
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
		GetSearchProduct_Input_Pojo searchProduct_Input_Pojo = new GetSearchProduct_Input_Pojo(txtCategoryId,
				txtProductId, "category");
		System.out.println(txtCategoryId);
		addPayload(searchProduct_Input_Pojo);

		Response response = sendRequest("POST", "https://www.omrbranch.com/api/getSearchResult");

		GetSearchProduct_Output_Pojo getSearchProduct_Output_Pojo = response.as(GetSearchProduct_Output_Pojo.class);

		ArrayList<GetSearchproductList_Pojo> data = getSearchProduct_Output_Pojo.getData();

		for (GetSearchproductList_Pojo eachProduct : data) {

			ArrayList<Variation_Pojo> variations = eachProduct.getVariations();

			for (Variation_Pojo eachVariation : variations) {

				String specification = eachVariation.getSpecifications();

				if (specification.equals("1 kg")) {

					ArrayList<Option> options = eachVariation.getOptions();

					int variationId = options.get(0).getVariation_id();
					txtVariationId = String.valueOf(variationId);

					System.out.println("variation Id :" + variationId);

					break;
				}
			}
		}

	}

	public void addToCart() {
		initRestAssured();
		List<Header> lstHeader = new ArrayList<Header>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + Login.logtoken);
		Header h3 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);
		Headers headers = new Headers(lstHeader);

		addHeaders(headers);

		AddToCart_Input_Pojo addToCart_Input_Pojo = new AddToCart_Input_Pojo(

				txtProductId, txtVariationId, "plus"

		);
		addPayload(addToCart_Input_Pojo);
		Response response = sendRequest("POST", "https://www.omrbranch.com/api/addToCart");
		System.out.println(response.asPrettyString());

	}

	public void getCartItems() {

		initRestAssured();
		List<Header> lstHeader = new ArrayList<Header>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + Login.logtoken);
		lstHeader.add(h1);
		lstHeader.add(h2);
		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

		Response response = sendRequest("GET", "https://www.omrbranch.com/api/getCartItems");
		GetCartItems_Output_Pojo getCartItems_Output_Pojo = response.as(GetCartItems_Output_Pojo.class);
		ArrayList<GetCartList> data = getCartItems_Output_Pojo.getData();
		for (GetCartList getCartList : data) {
			int cart_id = getCartList.getCart_id();

			txtCart_id = String.valueOf(cart_id);
			System.out.println("Cart id : " + cart_id);

		}

		System.out.println(response.asPrettyString());
	}

	public void setAddress() {

		initRestAssured();
		List<Header> lstHeader = new ArrayList<Header>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + Login.logtoken);
		Header h3 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);
		Headers headers = new Headers(lstHeader);

		addHeaders(headers);

		SetAddress_Input_Pojo address_Input_Pojo = new SetAddress_Input_Pojo(Address.addressID, txtCart_id);
		addPayload(address_Input_Pojo);
		Response response = sendRequest("POST", "https://www.omrbranch.com/api/setAddress");
		SetAddress_Output_Pojo setAddress_Output_Pojo = response.as(SetAddress_Output_Pojo.class);
		System.out.println(setAddress_Output_Pojo.getMessage());

	}

	public void createOrder() {
		initRestAssured();
		List<Header> lstHeader = new ArrayList<Header>();
		Header h1 = new Header("accept", "application/json");
		Header h2 = new Header("Authorization", "Bearer " + Login.logtoken);
		Header h3 = new Header("Content-Type", "application/json");
		lstHeader.add(h1);
		lstHeader.add(h2);
		lstHeader.add(h3);
		Headers headers = new Headers(lstHeader);
		addHeaders(headers);

		CreateOrder_Input_Pojo createOrder_Input_Pojo = new CreateOrder_Input_Pojo(

				"debit_card", "5555555555552222", "visa", "2033", "03", "123"

		);
		addPayload(createOrder_Input_Pojo);
		Response response = sendRequest("POST", "https://www.omrbranch.com/api/createOrder");
		CreateOrder_Output_Pojo createOrder_Output_Pojo = response.as(CreateOrder_Output_Pojo.class);
		
		System.out.println("Order ID is : " + createOrder_Output_Pojo.getOrder_id());
	}

	

}
