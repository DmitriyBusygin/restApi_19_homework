package tests;

import org.junit.jupiter.api.Test;
import specs.Specs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

class BooksApplicationTests {

    @Test
    public void getAllBooksTest() {
        given().spec(Specs.request)
                .when().get("/books/all")
                .then()
                .spec(Specs.responseSpec)
                .body("findAll{it.title}.title.flatten()",
                        hasItem("Гарри Поттер и Филосовский камень"))
                .body("findAll{it.title}.title.flatten()",
                        hasItem("Игра Эндера"));
    }

    @Test
    public void getBooksByAuthorTest() {
        given().spec(Specs.request)
                .when().get("books/get/Орсон")
                .then()
                .spec(Specs.responseSpec)
                .body("title", contains("Игра Эндера"));
    }

    @Test
    public void addBookTest() {
        given().spec(Specs.request)
                .body("{\"title\": \"Название книги\"," +
                        "\"author\": \"Автор книги\"}")
                .when().post("books/add")
                .then()
                .spec(Specs.responseSpec)
                .body("title", is("Название книги"));
    }
}


