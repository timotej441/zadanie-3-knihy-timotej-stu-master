package sk.stuba.fei.uim.oop.assignment3.book.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.stuba.fei.uim.oop.assignment3.author.logic.IAuthorService;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.data.BookRepository;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookRequest;
import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookUpdateRequest;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class BookService implements IBookService{
    @Autowired
    private BookRepository repository;

    @Autowired
    private IAuthorService authorService;

    @Override
    public List<Book> getAll() {
        return this.repository.findAll();
    }

    @Override
    public void save(Book book){
        this.repository.save(book);
    }

    @Override
    public Book create(BookRequest request) throws NotFoundException {
        Book book = this.repository.save(new Book(request));
        authorService.addBookToAuthor(request.getAuthor(), book);

        return book;
    }

    @Override
    public Book getById(long id) throws NotFoundException {
        Book book = this.repository.findBookById(id);
        if (book == null) {
            throw new NotFoundException();
        }

        return book;
    }

    @Override
    public Book update(long id, BookUpdateRequest request) throws NotFoundException {
        Book book = this.getById(id);
        if (request.getName() != null) {
            book.setName(request.getName());
        }
        if (request.getDescription() != null) {
            book.setDescription(request.getDescription());
        }
        if (request.getAuthor() != 0) {
            authorService.removeBookFromAuthor(book.getAuthor().getId(), book);
            authorService.addBookToAuthor(request.getAuthor(), book);
        }
        if (request.getPages() != 0) {
            book.setPages(request.getPages());
        }

        return this.repository.save(book);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        Book book = this.getById(id);
        authorService.removeBookFromAuthor(book.getAuthor().getId(), book);
        this.repository.delete(book);
    }

    @Override
    public long getAmount(long id) throws NotFoundException {
        return this.getById(id).getAmount();
    }

    @Override
    public long addAmount(long id, long increment) throws NotFoundException {
        Book book = this.getById(id);
        book.setAmount(book.getAmount() + increment);
        this.repository.save(book);

        return book.getAmount();
    }

    @Override
    public long getLendCount(long id) throws NotFoundException {
        return this.getById(id).getLendCount();
    }

    public void incrementLendCount(long id) throws NotFoundException, IllegalOperationException {
        Book book = this.getById(id);
        if(book.getLendCount().equals(book.getAmount())){
            throw new IllegalOperationException();
        }
        book.setLendCount(book.getLendCount()+1);
        this.save(book);
    }

    public void decrementLendCount(long id) {
        Book book = this.repository.findBookById(id);
        book.setLendCount(book.getLendCount()-1);
        this.save(book);
    }
}
