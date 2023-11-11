package sk.stuba.fei.uim.oop.assignment3.author.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.author.data.Author;
import sk.stuba.fei.uim.oop.assignment3.author.data.AuthorRepository;
import sk.stuba.fei.uim.oop.assignment3.author.web.bodies.AuthorRequest;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;

import java.util.List;

@Service
public class AuthorService implements IAuthorService{

    @Autowired
    private AuthorRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public List<Author> getAll() {
        return this.repository.findAll();
    }

    @Override
    public Author create(AuthorRequest request) {
        return this.repository.save(new Author(request));
    }

    @Override
    public Author getById(long id) throws NotFoundException {
        Author author = this.repository.findAuthorById(id);
        if (author == null) {
            throw new NotFoundException();
        }

        return author;
    }

    @Override
    public Author update(long id, AuthorRequest request) throws NotFoundException {
        Author author = this.getById(id);
        if (request.getName() != null) {
            author.setName(request.getName());
        }
        if (request.getSurname() != null) {
            author.setSurname(request.getSurname());
        }

        return this.repository.save(author);
    }

    @Override
    public void delete(long id) throws NotFoundException {
        this.repository.delete(this.getById(id));
    }

    public void addBookToAuthor(long authorId, Book book){
        Author author = this.repository.findAuthorById(authorId);
        author.getBooks().add(book);
        book.setAuthor(author);
        this.bookService.save(book);

        this.repository.save(author);
    }

    public void removeBookFromAuthor(long authorId, Book book){
        Author author = this.repository.findAuthorById(authorId);
        author.getBooks().remove(book);
        this.repository.save(author);
    }
}
