package crud.app.book;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import crud.app.book.entities.Book;
import crud.app.book.repositories.BookRepository;
import crud.app.book.services.BookService;

@SpringBootTest
class BookAppCrudApplicationTests {

	@Autowired
	private BookService bookService;

	@MockBean
	private BookRepository bookRepository;

	private List<Book> bookList;

	@BeforeEach
	void setUp() {
		bookList = new ArrayList<>();
		bookList.add(new Book(1L, "Title 1", "Author 1", 2020));
		bookList.add(new Book(2L, "Title 2", "Author 2", 2021));
	}

	@Test
    void testGetAllBooks() {
        when(bookRepository.findAll()).thenReturn(bookList);
        List<Book> result = bookService.getAllBooks();
        assertEquals(2, result.size());
    }

	@Test
	void testGetBookById() {
		Long id = 1L;
		when(bookRepository.findById(id)).thenReturn(Optional.of(bookList.get(0)));
		Book result = bookService.getBookById(id);
		assertEquals("Title 1", result.getTitle());
	}

	@Test
	void testSaveBook() {
		Book newBook = new Book(null, "New Title", "New Author", 2022);
		when(bookRepository.save(newBook)).thenReturn(newBook);
		Book result = bookService.saveBook(newBook);
		assertEquals("New Title", result.getTitle());
	}

	@Test
	void testUpdateBook() {
		Long id = 1L;
		Book updatedBook = new Book(id, "Updated Title", "Updated Author", 2023);
		when(bookRepository.existsById(id)).thenReturn(true);
		when(bookRepository.save(updatedBook)).thenReturn(updatedBook);
		Book result = bookService.updateBook(id, updatedBook);
		assertEquals("Updated Title", result.getTitle());
	}

	@Test
	void testDeleteBook() {
		Long id = 1L;
		doNothing().when(bookRepository).deleteById(id);
		bookService.deleteBook(id);
		verify(bookRepository, times(1)).deleteById(id);
	}

}
