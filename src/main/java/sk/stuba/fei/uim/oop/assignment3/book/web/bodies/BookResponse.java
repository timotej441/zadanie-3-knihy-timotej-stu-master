package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;

import sk.stuba.fei.uim.oop.assignment3.book.data.Book;


@Getter
public class BookResponse {
    private final long id;
    private final String name;
    private final String description;
    private final long author;
    private final long pages;
    private final long amount;
    private final long lendCount;

    public BookResponse(Book book) {
        this.id = book.getId();
        this.name = book.getName();
        this.description = book.getDescription();
        this.author = book.getAuthor().getId();
        this.pages = book.getPages();
        this.amount = book.getAmount();
        this.lendCount = book.getLendCount();
    }
}
