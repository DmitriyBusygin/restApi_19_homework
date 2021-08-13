package com.example.demo.controller;

import com.example.demo.domain.Book;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

@RestController
public class BooksController {

    private Map<Integer, Book> books = new HashMap<>(Map.of(
            1, Book.builder().title("Гарри Поттер и Филосовский камень").author("Джоан Кэтлин Роулинг").build(),
            2, Book.builder().title("Гарри Поттер и Тайная комната").author("Джоан Кэтлин Роулинг").build(),
            3, Book.builder().title("Гарри Поттер и Кубок огня").author("Джоан Кэтлин Роулинг").build(),
            4, Book.builder().title("Властелин колец: братство кольца").author("Джон Рональд Руэл Толкин").build(),
            5, Book.builder().title("Властелин колец: две крепости").author("Джон Рональд Руэл Толкин").build(),
            6, Book.builder().title("Игра Эндера").author("Орсон Скотт Кард").build())
     );

    @GetMapping("/books/all")
    @ApiOperation("Получить все книги")
    public List<Book> getBooksAll() {
        return books.entrySet()
                .stream()
                .map(Map.Entry::getValue)
                .collect(toList());
        }

    @GetMapping("books/get/{author}")
    @ApiOperation("Получить книги по автору")
    public List<Book> getBooksByAuthor(@PathVariable("author") String author) {
        List<Book> list = books.entrySet()
                    .stream()
                    .map(Map.Entry::getValue)
                    .filter(book -> book.getAuthor().contains(author))
                    .collect(toList());
        if (list.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Already exist");
        } else {
            return list;
        }
    }

    @PostMapping(value = "books/add", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation("Добавить книгу с указанием автора")
    public Book addBook(@RequestBody Book book) {
        books.put(books.size() + 1, book);
        return Book.builder()
                .title(book.getTitle())
                .author(book.getAuthor())
                .build();
    }
}
