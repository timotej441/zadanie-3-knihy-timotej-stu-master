package sk.stuba.fei.uim.oop.assignment3.book.web.bodies;

import lombok.Getter;

@Getter
public class BookRequest {
    private String name;
    private String description;
    private long author;
    private long pages;
    private long amount;
    private long lendCount;
}
