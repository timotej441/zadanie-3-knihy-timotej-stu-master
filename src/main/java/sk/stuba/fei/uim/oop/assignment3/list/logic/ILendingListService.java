package sk.stuba.fei.uim.oop.assignment3.list.logic;

import sk.stuba.fei.uim.oop.assignment3.exception.IllegalOperationException;
import sk.stuba.fei.uim.oop.assignment3.exception.NotFoundException;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;
import sk.stuba.fei.uim.oop.assignment3.list.web.bodies.LendingListRequest;

import java.util.List;

public interface ILendingListService {
    List<LendingList> getAll();

    LendingList create();

    LendingList getById(long id) throws NotFoundException;

    void delete(long id) throws NotFoundException;

    LendingList addBook(long id, LendingListRequest request) throws NotFoundException, IllegalOperationException;

    void removeBook(long id, LendingListRequest request) throws NotFoundException, IllegalOperationException;

    void lendList(long id) throws NotFoundException, IllegalOperationException;
}
