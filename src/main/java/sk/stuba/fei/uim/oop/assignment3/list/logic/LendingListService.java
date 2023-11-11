package sk.stuba.fei.uim.oop.assignment3.list.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sk.stuba.fei.uim.oop.assignment3.book.data.Book;
import sk.stuba.fei.uim.oop.assignment3.book.logic.IBookService;
import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingListRepository;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.LendingListRequest;

import java.util.List;

@Service
public class LendingListService implements ILendingListService{

    @Autowired
    private LendingListRepository repository;

    @Autowired
    private IBookService bookService;

    @Override
    public List<LendingList> getAll(){
        return this.repository.findAll();
    }

    @Override
    public LendingList create(){
        return this.repository.save(new LendingList());
    }

    @Override
    public LendingList getById(long id) throws NotFoundException {
        LendingList list = this.repository.findLendingListById(id);
        if (list == null) {
            throw new NotFoundException();
        }

        return list;
    }

    @Override
    public void delete(long id) throws NotFoundException {
        LendingList list = this.getById(id);
        for(var book : list.getLendList()){
            bookService.decrementLendCount(book.getId());
        }
        this.repository.delete(list);
    }

    @Override
    public LendingList addBook(long id, LendingListRequest request) throws NotFoundException, IllegalOperationException {
        LendingList list = this.isUnlended(id);
        list.getLendList().add(this.containsBook(id, bookService.getById(request.getId())));

        return this.repository.save(list);
    }

    @Override
    public void removeBook(long id, LendingListRequest request) throws NotFoundException, IllegalOperationException {
        Book book = bookService.getById(request.getId());
        this.isUnlended(id).getLendList().remove(book);
        bookService.decrementLendCount(book.getId());
    }

    @Override
    public void lendList(long id) throws NotFoundException, IllegalOperationException {
        LendingList list = this.isUnlended(id);
        for(var book : list.getLendList()){
            bookService.incrementLendCount(book.getId());
        }
        list.setLended(true);
    }

    private LendingList isUnlended(long id) throws NotFoundException, IllegalOperationException{
        LendingList list = this.getById(id);
        if(list.isLended()){
            throw new IllegalOperationException();
        }

        return list;
    }

    private Book containsBook(long listId , Book book) throws NotFoundException, IllegalOperationException{
        if(this.getById(listId).getLendList().contains(book)){
            throw new IllegalOperationException();
        }

        return book;
    }
}
