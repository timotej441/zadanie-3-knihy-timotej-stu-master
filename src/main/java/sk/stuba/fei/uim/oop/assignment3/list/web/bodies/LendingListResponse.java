package sk.stuba.fei.uim.oop.assignment3.list.web.bodies;

import lombok.Getter;

import sk.stuba.fei.uim.oop.assignment3.book.web.bodies.BookResponse;
import sk.stuba.fei.uim.oop.assignment3.list.data.LendingList;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class LendingListResponse {
    private final long id;
    private final List<BookResponse> lendingList;
    private final boolean lended;

    public LendingListResponse(LendingList l){
        this.id = l.getId();
        this.lendingList = l.getLendList().stream().map(BookResponse::new).collect(Collectors.toList());
        this.lended = l.isLended();
    }
}
