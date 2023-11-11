package sk.stuba.fei.uim.oop.assignment3.book.logic;

import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

public interface IBookService {
    List<Book> getAll();

    Book create(BookRequest request) throws NotFoundException;

    Book getById(long id) throws NotFoundException;

    Book update(long id, BookUpdateRequest request) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    long getAmount(long id) throws NotFoundException;

    long addAmount(long id, long increment) throws NotFoundException;

    long getLendCount(long id) throws NotFoundException;

    void incrementLendCount(long id) throws NotFoundException, IllegalOperationException;

    void decrementLendCount(long id);

    void save(Book book);
}
