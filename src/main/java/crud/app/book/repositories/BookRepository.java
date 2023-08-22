package crud.app.book.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import crud.app.book.entities.Book;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

}
