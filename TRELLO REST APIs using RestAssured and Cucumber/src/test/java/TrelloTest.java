import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class TrelloTest {


    public static void main(String[] args) {
        String baseUrl = "https://api.trello.com/";
        String apiKey = "d1a10ea2ce2dcaf7348121bb3b051e43";
        String apiToken = "ATTA134906c6ba21896696e6b7e6bb3cae568d7b73450bc00f7acaeb5823202eaa91F6765995";
        String organizationId;//createNewOrganization();
        String memberId = "63e7c2d9ef88cbc11ad9aac0";
        String boardId;//"63ea65f18ab3868d333628d1";
        String listId;

        // Create a new organization
        String displayName = "new organization 1";
        Response response = RestAssured.given().baseUri(baseUrl)
                .header("Accept", "application/json")
                .queryParam("displayName", displayName)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .body("")
                .when()
                .post("1/organizations");
        response.prettyPrint();
        JsonPath path = response.jsonPath();
        organizationId = path.get("id");
        System.out.println(organizationId);


        //get your member id
        response = RestAssured.given().baseUri(baseUrl).header("Accept", "application/json")
                .queryParam("key", apiKey).queryParam("token", apiToken)
                .when()
                .get("1/members/me");
        response.prettyPrint();
        path = response.jsonPath();
        memberId = path.getString("id");
        System.out.println(memberId);


        //Get Organizations for a member

        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .when()
                .get("1/members/" + memberId + "/organizations");
        response.prettyPrint();

        System.out.println(memberId);


        // Create a board inside an organization

        String boardName = "board1";
        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("name", boardName)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .body("")
                .when()
                .post("/1/boards/");
        response.prettyPrint();
        path = response.jsonPath();
        boardId = path.get("id");
        System.out.println(boardId);


        //Get boards in an organization

        response = RestAssured.given().baseUri(baseUrl)
                .header("Accept", "application/json")
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .when()
                .get("1/organizations/" + organizationId + "/boards");
        response.prettyPrint();
        System.out.println(organizationId);


        //Create a new list
        String listName = "list1";
        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("name", listName)
                .queryParam("idBoard", boardId)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .body("")
                .when()
                .post("1/lists");
        response.prettyPrint();
        path = response.jsonPath();
        listId = path.get("id");
        System.out.println(listId);

        //Get all lists on a board
        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .when()
                .get("1/boards/" + boardId + "/lists");
        response.prettyPrint();

        //Archive a list

        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .queryParam("value", true)
                .header("Accept", "application/json")
                .body("")
                .when()
                .put("/1/lists/" + listId + "/closed");
        response.prettyPrint();

        // Delete a board
        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .body("")
                .when()
                .delete("1/boards/" + boardId);
        response.prettyPrint();

        //Delete an organization

        response = RestAssured.given().baseUri(baseUrl)
                .queryParam("key", apiKey)
                .queryParam("token", apiToken)
                .header("Accept", "application/json")
                .body("")
                .when()
                .delete("1/organizations/" + organizationId);
        response.prettyPrint();


    }


}


