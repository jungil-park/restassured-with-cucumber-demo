package stepdefs.balance;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class StepDefinitions {

	private Response response;
	private ValidatableResponse json;
	private RequestSpecification request;
	String token;

	//AACB
	//private String loginUrl = "http://gbvlixaacpn02u.metis.prd:8080/auth/login?username=nxchange&password=passw0rd";
    //private String balanceUrl = "http://gbvlixaacpn02u.metis.prd:8080/api/v2/balance/";

    // Amazon
	private String loginUrl = "http://18.195.214.115:8089/auth/login?username=nxchange&password=passw0rd";
    private String balanceUrl = "http://18.195.214.115:8089/api/v2/balance/";
   
    
    

	@Given("a client exists with id of (.*)")
	public void getID(String id){
		System.out.println("balance start");
		Response loginResponse = given().when().get(loginUrl);
		token = loginResponse.jsonPath().getString("token");
		System.out.println(token);
		
		balanceUrl = balanceUrl + id;
		
		request = given().header("X-Amz-Date", "20180621T133419Z")
				.header("Authorization","AWS4-HMAC-SHA256 Credential=AKIAJ7RLUES45HHM3G2Q/20180621/us-east-1/execute-api/aws4_request, SignedHeaders=content-length;content-type;host;x-amz-date, Signature=4b741cc01e15b52462823b958114e1f428aa12591aeb2f0a55378c352d311710");
 
		
		System.out.println("response: " + balanceUrl);
	}
 
	@When("a client retrieves the balance by id and instrument (.*)")
	public void getInstrument(String instrument){
		
		balanceUrl = balanceUrl + "/" + instrument + "?token="+token;
		System.out.println("response: " + balanceUrl);
		response = request.when().get(balanceUrl);
		token = response.jsonPath().getString("token");
		System.out.println("response: " + response.prettyPrint());
	}

	@Then("the status code is (\\d+)")
	public void verify_status_code(int statusCode){
		json = response.then().statusCode(statusCode);
		
	 ArrayList<Map<String, ?>> jsonArray = json.extract().jsonPath().get();
/*	 
	 for (Map<String, ?> entry : jsonArray) {
		    for (String key : entry.keySet()) {
		        Object value = entry.get(key);
		        System.out.println("key = " + key);
		        System.out.println("value = " + value);
		    }
		}
	 */
		 
	// jsonArray.forEach(Map<String, ?>) ->;
		//System.out.println("json: " + json.toString());
	}

/*	@And("response includes the following$")
	public void response_equals(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			if(StringUtils.isNumeric(field.getValue())){
				json.body(field.getKey(), equalTo(Integer.parseInt(field.getValue())));
			}
			else{
				json.body(field.getKey(), equalTo(field.getValue()));
				 
			}
		}
	}
*/
	@And("response includes the following in any order")
	public void response_contains_in_any_order(Map<String,String> responseFields){
		for (Map.Entry<String, String> field : responseFields.entrySet()) {
			json.body(field.getKey(), containsInAnyOrder(field.getValue()));
		}
	}
}


