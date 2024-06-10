package br.com.contas.infrastructure.adapters.input.rest.data;

import br.com.contas.domain.model.PayableAccountStatus;
import br.com.contas.infrastructure.adapters.input.rest.data.payload.PayableAccountPayload;
import br.com.contas.infrastructure.adapters.output.persitence.entity.PayableAccountEntity;
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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setDueDate(null);
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setAmount(BigDecimal.valueOf(-0.01d));
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setAmount(BigDecimal.ZERO);
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setDescription(null);
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setDescription(" ");
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        payableAccountEntity.setStatus(null);
        String json = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String jsonInsert = PayableAccountPayload.toJson(payableAccountEntity);

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

        payableAccountEntity.setDescription("Update");
        String jsonUpdate = PayableAccountPayload.toJson(payableAccountEntity);

        given()
                .contentType(ContentType.JSON)
                .body(jsonUpdate)
                .when()
                .put("/accounts/v1/payable-accounts/{id}", id)
                .then()
                .statusCode(201)
                .body("id", equalTo(id));
    }

    @Test
    public void testUpdatePayableAccountStatus() {

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String jsonInsert = PayableAccountPayload.toJson(payableAccountEntity);

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

        payableAccountEntity.setStatus(PayableAccountStatus.PAID);
        String jsonUpdate = PayableAccountPayload.toJson(payableAccountEntity);

        given()
                .contentType(ContentType.JSON)
                .body(jsonUpdate)
                .when()
                .patch("/accounts/v1/payable-accounts/{id}/status", id)
                .then()
                .statusCode(200)
                .body("status", equalTo("PAID"));
    }

    @Test
    public void testGetPayableAccountById() {

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String jsonInsert = PayableAccountPayload.toJson(payableAccountEntity);

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

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String jsonInsert = PayableAccountPayload.toJson(payableAccountEntity);

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
                .queryParam("dueDate", payableAccountEntity.getDueDate().toString())
                .queryParam("description", payableAccountEntity.getDescription())
                .when()
                .get("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(200)
                .body("content", notNullValue());
    }

    @Test
    public void testGetPayableAccountsAmountByPeriod() {

        PayableAccountEntity payableAccountEntity = PayableAccountPayload.buildPayableAccountEntity();
        String jsonInsert = PayableAccountPayload.toJson(payableAccountEntity);

        given()
                .contentType(ContentType.JSON)
                .body(jsonInsert)
                .when()
                .post("/accounts/v1/payable-accounts/")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue());

        given()
                .queryParam("startDate", payableAccountEntity.getDueDate().toString())
                .queryParam("endDate", payableAccountEntity.getDueDate().toString())
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
                .body(equalTo("Arquivo importado com successo"));
    }

}