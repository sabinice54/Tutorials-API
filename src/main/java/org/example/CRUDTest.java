package org.example;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CRUDTest {

    private static int tutorialId;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = "http://test.com/api/tutorials";
    }


    @Test
    @Order(1)
    public void createNewTutorial() {

        String requestBody = """
                {
                "title": "Title Test",
                "description": "Tutorial description"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://195.38.164.139:6677/api/tutorials")
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Title Test"))
                .body("description", equalTo("Tutorial description"))
                .extract()
                .response();

        tutorialId = response.path("id");
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }

    @Test
    @Order(2)
    public void createTutorialWithTruePublished() {

        String requestBody = """
                {
                "title": "Title Test",
                "description": "Tutorial description",
                "published": true
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("http://195.38.164.139:6677/api/tutorials")
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Title Test"))
                .body("description", equalTo("Tutorial description"))
                .body("published", equalTo(true))
                .extract()
                .response();

        tutorialId = response.path("id");
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }

    @Test
    @Order(3)
    public void createTutorialInCyrillic() {

        String requestBodyInCyrillic = """
                {
                "title": "Туториал",
                "description": "Описание туториала"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInCyrillic)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Туториал"))
                .body("description", equalTo("Описание туториала"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }


    @Test
    @Order(4)
    public void createTutorialInCyrillicAndLatinic() {

        String requestBodyInCyrillic = """
                {
                "title": "Новый Tutorial",
                "description": "Описание tutorials"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInCyrillic)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Новый Tutorial"))
                .body("description", equalTo("Описание tutorials"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }


    @Test
    @Order(5)
    public void createTutorialWithNumbers() {

        String requestBodyInCyrillic = """
                {
                "title": "1234567890",
                "description": "1234567890"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInCyrillic)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("1234567890"))
                .body("description", equalTo("1234567890"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }

    @Test
    @Order(6)
    public void createTutorialWithSpecialCharacters() {

        String requestBodyWithSpecialCharacters = """
                {
                "title": "~!@#$%^&*()_+{}|:”>?<Ё!”№;%:?*()_+/Ъ,/.,;’[]|",
                "description": "~!@#$%^&*()_+{}|:”>?<Ё!”№;%:?*()_+/Ъ,/.,;’[]|"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyWithSpecialCharacters)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("~!@#$%^&*()_+{}|:”>?<Ё!”№;%:?*()_+/Ъ,/.,;’[]|"))
                .body("description", equalTo("~!@#$%^&*()_+{}|:”>?<Ё!”№;%:?*()_+/Ъ,/.,;’[]|"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }


    @Test
    @Order(7)
    public void createTutorialWithEmodji() {

        String requestBodyWithSpecialCharacters = """
                {
                "title": "🌸",
                "description": "🌸"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyWithSpecialCharacters)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("🌸"))
                .body("description", equalTo("🌸"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }

    @Test
    @Order(8)
    public void createTutorialWithoutTitle() {
        String RequestBodyWithoutTitle =  """
                {
                "description": "Tutorial description",
                "published": false
                }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithoutTitle)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("description", equalTo("Tutorial description"))
                 .body("published", equalTo(false))
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }

    @Test
    @Order(9)
    public void createTutorialWithoutDescription() {
        String RequestBodyWithoutDescription =  """
                {
                "title": "Title Test",
                "published": false
                }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithoutDescription)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Title Test"))
                .body("published", equalTo(false))
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }


    @Test
    @Order(10)
    public void createTutorialWithoutPublished() {
        String RequestBodyWithoutPublished =  """
                {
                "title": "Title Test",
                "description": "Tutorial description"
                }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithoutPublished)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Title Test"))
                .body("description", equalTo("Tutorial description"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");
    }




    @Test
    @Order(11)
    public void createTutorialWithEmptyValues() {
        String RequestBodyWithEmptyValues =  """
                {
                "title": "",
                "description": "",
                "published": ""
                 }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithEmptyValues)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }


    @Test
    @Order(12)
    public void createTutorialWithOneSymbol() {
        String RequestBodyWithOneSymbol =  """
                {
                "title": "T",
                "description": "T"
                 }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithOneSymbol)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("T"))
                .body("description", equalTo("T"))
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }



    @Test
    @Order(13)
    public void createEmptyTutorial() {
        String emptyRequestBody =  "{}";

        Response response = given()
                .header("Content-Type", "application/json")
                .body(emptyRequestBody)
                .when()
                .post()
                .then()
                .log().all()
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }

    @Test
    @Order(14)
    public void createTutorialWithSpaceValue() {
        String RequestBodyWithSpaceValue =  """
                {
                "title": " ",
                "description": " "
                 }
                """;

        Response response = given()
                .header("Content-Type", "application/json")
                .body(RequestBodyWithSpaceValue)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo(" "))
                .body("description", equalTo(" "))
                .extract()
                .response();
        assertEquals(400, response.statusCode(), "Status code 400 Bad request.");
    }



    @Test
    @Order(15)
    public void createTutorialInRandomInOrder() {

        String requestBodyInRandomInOrder = """
                {
                "published": true,
                "description": "Tutorial description",
                "title": "Title Test"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInRandomInOrder)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("Title Test"))
                .body("description", equalTo("Tutorial description"))
                .body("published", equalTo(true))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }



    @Test
    @Order(16)
    public void createTutorialInUpperCase() {

        String requestBodyInUpperCase = """
                {
                "title": "TITLE TEST",
                "description": "TUTORIAL DESCRIPTION",
                "published": "FALSE"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInUpperCase)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("TITLE TEST"))
                .body("description", equalTo("TUTORIAL DESCRIPTION"))
                .body("published", equalTo("FALSE"))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }


    @Test
    @Order(17)
    public void createTutorialInLowerCase() {

        String requestBodyInLowerCase = """
                {
                "title": "title test",
                "description": "tutorial description",
                "published": false
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(requestBodyInLowerCase)
                .when()
                .post()
                .then()
                .log().all()
                .header("Content-Type", "application/json")
                .body("title", equalTo("title test"))
                .body("description", equalTo("tutorial description"))
                .body("published", equalTo(false))
                .extract()
                .response();
        assertEquals(201, response.statusCode(), "Status code 201 Created.");

    }


    @Test
    @Order(18)
    public void getResourceAll() {
        Response response = given()
                .when()
                .get()
                .then()
                .log().all()
                .extract()
                .response();
        if(response.asString().isEmpty()){
            response.then().statusCode(204);
        }else{
            response.then()
                    .statusCode(200);
//                   .body(hasSize(greaterThan(0)))
//                    .body("id", everyItem(notNullValue()))
//                   .body("title", everyItem(notNullValue()));
        }
    }

    @Test
    @Order(19)
    public void UpdateATutorialById () {
        String requestBody = "{\"title\": \"Updated Tutorial\", \"description\": \"Test Description\", \"published\": true}";
        Response response = given()
                .body(requestBody)
                .contentType("application/json")
                .pathParam("id", tutorialId)
                .when()
                .put("/{id}")
                .then()
                .log().all()
                .statusCode(200)
                .body("title", equalTo("Updated Tutorial"))
                .body("description", equalTo("Test Description"))
                .body("published", equalTo(true))
                .extract()
                .response();
        String tutorialUpdatedID = response.jsonPath().getString("id");
        System.out.println("UpdateID: " + tutorialUpdatedID);
    }

    @Test
    @Order(20)
    public void UpdateATutorialByInvalidId () {
        String requestBody = """
                {
                "title": "Updated Tutorial",
                "description": "Test Description",
                "published": true
                }
                """;

        Response response = given()
                .body(requestBody)
                .contentType("application/json")
                .pathParam("id", "0")
                .when()
                .put("/{id}")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
    }


    @Test
    @Order(21)
    public void UpdateATutorialWithEmptyId () {
        String requestBody = """
                {
                "title": "Updated Tutorial",
                "description": "Test Description",
                "published": true
                }
                """;

        Response response = given()
                .body(requestBody)
                .contentType("application/json")
                .pathParam("id", "")
                .when()
                .put("/{id}")
                .then()
                .log().all()
                .statusCode(404)
                .extract()
                .response();
    }


    @Test
    @Order(22)
    public void getTutorialById() {


        Response response = given()
                .pathParam("id", tutorialId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .body("id", equalTo(tutorialId))
                .body("title", equalTo("Updated Tutorial"))
                .body("description", equalTo("Test Description"))
                .body("published", equalTo(true))
                .log().all()
                .extract()
                .response();

    }

    @Test
    @Order(23)
    public void getTutorialByInvalidId() {
        int invalidId = 999;
        Response response = given()
                .pathParam("id", invalidId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404)
                .log().all()
                .extract()
                .response();

    }

    @Test
    @Order(24)
    public void getTutorialByNegativeNumberId() {
        int negativeNumberId = -1;
        Response response = given()
                .pathParam("id", negativeNumberId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404)
                .log().all()
                .extract()
                .response();

    }

    @Test
    @Order(25)
    public void getTutorialByStringId() {
        String stringId = "id";
        Response response = given()
                .pathParam("id", stringId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(400)
                .log().all()
                .extract()
                .response();

    }


    @Test
    @Order(26)
    public void GetAllTutorialsByTitleKeyword() {

        String keyword = "Tutorial";

        Response response = given()
                .header("Content-Type", "application/json")
                .queryParam("title", keyword)
                .when()
                .get()
                .then()
                .log().all()
                //.statusCode(200)
                .extract()
                .response();
        if(response.asString().isEmpty()){
            response.then().statusCode(204);
        }else{
            response.then()
                    .statusCode(200)
//                   .body("",hasSize(greaterThan(0)))
                    .body("title", everyItem(containsString(keyword)));  // Проверка, что заголовок содержит ключевое слово
//                    .body("id", everyItem(notNullValue()));
        }
    }

    @Test
    @Order(27)
    public void DeleteATutorialById() {
        Response response = given()
                .header("Content-Type", "application/json")
                .pathParam("id", tutorialId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204)
                .log().all()
                .extract()
                .response();

        // Проверка, что туториал действительно удален

        given()
                .header("Content-Type", "application/json")
                .pathParam("id", tutorialId)
                .when()
                .get("/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(28)
    public void deleteTutorialByNegativeNumberId() {
        int negativeNumberId = -1;
        Response response = given()
                .pathParam("id", negativeNumberId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(204)
                .log().all()
                .extract()
                .response();

    }

    @Test
    @Order(29)
    public void deleteTutorialByStringId() {
        String stringId = "id";
        Response response = given()
                .pathParam("id", stringId)
                .when()
                .delete("/{id}")
                .then()
                .statusCode(400)
                .log().all()
                .extract()
                .response();

    }

    @Test
    @Order(30)
    public void DeleteAllTutorials() {
        Response response = given()
                .header("Content-Type", "application/json")
                .when()
                .delete()
                .then()
                .statusCode(204)
                .log().all()
                .extract()
                .response();
    }

}
