package io.javabrains.betterreadsdataloader.book;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class BookController {

    private final String COVERS_URL = "http://covers.openlibrary.org/b/id/";

    @Autowired
    private BookRepository bookRepository;

    @GetMapping(value = "/books/{bookId}")
    public String getBook(@PathVariable String bookId, Model model) {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            Book book = optBook.get();
            List<String> coverIds = book.getCoverIds();
            String coverUrl = "/images/no-image.png";
            if (coverIds != null && !coverIds.isEmpty()) {
                coverUrl = COVERS_URL + coverIds.get(0) + "-L.jpg";
                model.addAttribute("coverUrl", coverUrl);
            }
            model.addAttribute("book", book);
            return "book";
        }
        return "book-not-found";
    }

}
