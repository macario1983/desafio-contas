package br.com.desafio.payableaccount.api.v1.controller;

import br.com.desafio.payableaccount.api.payload.PayableAccountPayload;
import br.com.desafio.payableaccount.domain.model.PayableAccount;
import br.com.desafio.payableaccount.domain.model.PayableAccountStatus;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PayableAccountControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void setup() {
        RestAssured.port = port;
    }

    @Test
    public void testCreatePayableAccount() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue());
    }

    @Test
    public void testCreatePayableAccountWithoutDueDate() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setDueDate(null);
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @Test
    public void testCreatePayableAccountWithAmountNegative() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setAmount(BigDecimal.valueOf(-0.01d));
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreatePayableAccountWithAmountZero() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setAmount(BigDecimal.ZERO);
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreatePayableAccountWithDescriptionNull() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setDescription(null);
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testCreatePayableAccountWithDescriptionEmtpy() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setDescription(" ");
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    public void testCreatePayableAccountWithStatusNull() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        payableAccount.setStatus(null);
        String json = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(json)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    public void testUpdatePayableAccount() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String jsonInsert = PayableAccountPayload.toJson(payableAccount);

        String id = given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract()
                .path("id");

        payableAccount.setDescription("Update");
        String jsonUpdate = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(jsonUpdate)
                .when()
                .put("/accounts/v1/payable-accounts/{id}", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("id", equalTo(id));
    }

    @Test
    public void testUpdatePayableAccountStatus() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String jsonInsert = PayableAccountPayload.toJson(payableAccount);

        String id = given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract()
                .path("id");

        payableAccount.setStatus(PayableAccountStatus.PAID);
        String jsonUpdate = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(jsonUpdate)
                .when()
                .patch("/accounts/v1/payable-accounts/{id}/status", id)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body("status", equalTo("PAID"));
    }

    @Test
    public void testGetPayableAccountById() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String jsonInsert = PayableAccountPayload.toJson(payableAccount);

        String id = given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .extract()
                .path("id");

        given()
                .when()
                .get("/accounts/v1/payable-accounts/{id}", id)
                .then()
                .statusCode(200)
                .body("id", equalTo(id));
    }

    @Test
    public void testGetPayableAccountsFiltered() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String jsonInsert = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue());

        given()
                .queryParam("page", 0)
                .queryParam("size", 10)
                .queryParam("dueDate", payableAccount.getDueDate().toString())
                .queryParam("description", payableAccount.getDescription())
                .when()
                .get("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(200)
                .body("content", notNullValue());
    }

    @Test
    public void testGetPayableAccountsAmountByPeriod() {

        PayableAccount payableAccount = PayableAccountPayload.buildPayableAccount();
        String jsonInsert = PayableAccountPayload.toJson(payableAccount);

        given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue());

        given()
                .queryParam("startDate", payableAccount.getDueDate().toString())
                .queryParam("endDate", payableAccount.getDueDate().toString())
                .when()
                .get("/accounts/v1/payable-accounts/total-paid")
                .then()
                .statusCode(200)
                .body(notNullValue());
    }

    @Test
    public void testImportCSV() {

        File file = new File("contas.csv");

        given()
                .multiPart("file", file)
                .when()
                .post("/accounts/v1/payable-accounts/import")
                .then()
                .statusCode(200)
                .body(equalTo("Arquivo importado com sucesso"));
    }
}