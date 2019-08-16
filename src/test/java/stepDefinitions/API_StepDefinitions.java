package stepDefinitions;

import static io.restassured.RestAssured.given;
import static utils.PropertyReader.prop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import payloads.API_Payloads;
import resources.API_Resources;
import utils.Excel_Utility;

public class API_StepDefinitions {

	public static RequestSpecification request;
	public static List<Map<String, String>> excelDataMapList;
	
	
	
	@Given("^load the requried base url$")
	public void loadURI() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = prop.getProperty("addAPI_host");
	}

	@When("^populate the required quey parameters$")
	public void populateQueryParameters() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		request=given().queryParam("key", prop.getProperty("addplace_key"));
		
		//.headers("keyValue","value");
	}

	@Then("^build the payload,Hit the post request, perform validations and validate the response$")
	public void hitAndValidateResponse() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
		for (Map<String, String> map : excelDataMapList) {
			Response response=request.queryParam("key", prop.getProperty("addplace_key")).when()
					.body(API_Payloads.addMap_Payload(map))
					.post(API_Resources.placePostData())
				.then()
					.assertThat().statusCode(200)
				.and()
					.contentType(ContentType.JSON)
					.extract().response();
		
		System.out.println("Response :"+response.asString());
		Excel_Utility.writeToFile(response.asString());
		}	
	
	}

	@And("^load the excel data into list from the sheet \"([^\"]*)\"$")
    public void load_the_excel_data_into_list_from_the_sheet_something(String sheetName) throws Throwable {
        excelDataMapList=Excel_Utility.readData();
        
    }

}
