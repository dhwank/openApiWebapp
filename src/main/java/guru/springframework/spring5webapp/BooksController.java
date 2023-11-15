package guru.springframework.spring5webapp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private  List<Book> books  ;

    @PostConstruct
    public void init(){
        this.books = initialBookData();
    }

    @GetMapping
    public List<Book> getAllBooks(){
      return books;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id){
        //find book
        Book foundBook = null;
        for(Book book: books){
            if(book.getId().equals(id)){
                foundBook = book;
                break;
            }
        }

        if(foundBook!=null){
            return ResponseEntity.ok(foundBook);
        }
        else {
            //return 404 if book not found
            return ResponseEntity.notFound().build();
        }

    }

    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book){
        books.add(book);
        return ResponseEntity.ok("Book added successfully");
        }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody Book updatedBook){
        for(Book existingBook: books){
            if(existingBook.getId().equals(id)){
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                ResponseEntity.ok("Book updated");
            }
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBook(@PathVariable Long id){
        for(Book existingBook: books){
            if(existingBook.getId().equals(id)){
                books.remove(existingBook);
                return ResponseEntity.ok("Book Deleted");
            }
        }
        return ResponseEntity.notFound().build();
    }
    public List<Book> initialBookData(){
        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book(1L, "DSA", "Geeks"));
        bookList.add(new Book(2L,"Harry Potter", "JK Rowling"));
        bookList.add(new Book(3L,"Pride and Prejudice", "Jane Austen"));
        return bookList;
    }
}
