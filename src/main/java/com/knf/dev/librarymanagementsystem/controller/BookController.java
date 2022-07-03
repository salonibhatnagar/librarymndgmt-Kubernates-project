package com.knf.dev.librarymanagementsystem.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.qos.logback.core.CoreConstants;
import com.knf.dev.librarymanagementsystem.entity.User;
import com.knf.dev.librarymanagementsystem.entity.UserBook;
import com.knf.dev.librarymanagementsystem.repository.UserRepository;
import com.knf.dev.librarymanagementsystem.service.*;
import com.knf.dev.librarymanagementsystem.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.knf.dev.librarymanagementsystem.entity.Book;

@Controller
public class BookController {

	@Autowired
	BookService bookService;
	@Autowired
	AuthorService authorService;

	@Autowired
	UserService userService;
	@Autowired
	CategoryService categoryService;
	@Autowired
	PublisherService publisherService;

	@Autowired
	UserServiceImpl userServiceImpl;

	@RequestMapping({ "/books", "/" })
	public String findAllBooks(Model model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, Principal principal) {
		//String name = principal.getName();
		//User u = userServiceImpl.findUserByEmail(name);
		//u.getBooks();
		var currentPage = page.orElse(1);
		var pageSize = size.orElse(5);

		var bookPage = bookService.findPaginated(PageRequest.of(currentPage - 1, pageSize));

		model.addAttribute("books", bookPage);
		var totalPages = bookPage.getTotalPages();
		if (totalPages > 0) {
			var pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().collect(Collectors.toList());
			model.addAttribute("pageNumbers", pageNumbers);
		}
		return "list-books";
	}

	@RequestMapping("/searchBook")
	public String searchBook(@Param("keyword") String keyword, Model model) {

		model.addAttribute("books", bookService.searchBooks(keyword));
		model.addAttribute("keyword", keyword);
		return "list-books";
	}

	@RequestMapping("/book/{id}")
	public String findBookById(@PathVariable("id") Long id, Model model) {

		model.addAttribute("book", bookService.findBookById(id));
		return "list-book";
	}

	@GetMapping("/add")
	public String showCreateForm(Book book, Model model) {

		model.addAttribute("categories", categoryService.findAllCategories());
		model.addAttribute("authors", authorService.findAllAuthors());
		model.addAttribute("publishers", publisherService.findAllPublishers());
		return "add-book";
	}

	@RequestMapping("/add-book")
	public String createBook(Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "add-book";
		}

		bookService.createBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}

	@GetMapping("/update/{id}")
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {

		model.addAttribute("book", bookService.findBookById(id));
		return "update-book";
	}

	@RequestMapping("/update-book/{id}")
	public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
		if (result.hasErrors()) {
			book.setId(id);
			return "update-book";
		}

		bookService.updateBook(book);
		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}

	@RequestMapping("/remove-book/{id}")
	public String deleteBook(@PathVariable("id") Long id, Model model) {
		bookService.deleteBook(id);
		model.addAttribute("book", bookService.findAllBooks());
		return "redirect:/books";
	}
@RequestMapping("/userBooks")
public String getUserBook( Model model,Principal principal ) {
	String name = principal.getName();
	User user = userServiceImpl.findUserByEmail(name);
	Collection<Book> books=user.getBooks();
	System.out.println(user);
	model.addAttribute("books", books);
	return "list-books";
}
	@GetMapping("/adduser-book/{id}")
	public String showAddUserBookForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("book", bookService.findBookById(id));
		model.addAttribute("userBook", new UserBook());
		System.out.println( bookService.findBookById(id));
		return "add-user-book";
	}
	@RequestMapping(value = "/saveuser-book")
	public String saveUserBook(@ModelAttribute("userBook") UserBook userBook, BindingResult result, Principal principal, Model model) {
		if (result.hasErrors()) {
			//userBook.setId(id);
			return "add-user-book";
		}
		User user = userServiceImpl.findUserByEmail(userBook.getEmail());
		Book book2 = bookService.findBookByName(userBook.getBook_name());
		book2.addUsers(user);
		book2.setInventory((book2.getInventory()>0)?book2.getInventory()-1:0);
		bookService.updateBook(book2);
		return "redirect:/books";
	}
	@GetMapping("/removeuser-book/{id}")
	public String showRemoveUserBookForm(@PathVariable("id") long id, Model model) {
		model.addAttribute("book", bookService.findBookById(id));
		model.addAttribute("userBook", new UserBook());
		System.out.println( bookService.findBookById(id));
		return "remove-user-book";
	}
	@RequestMapping(value = "/removeuser-book")
	public String removeUserBook(@ModelAttribute("userBook") UserBook userBook, BindingResult result, Principal principal, Model model) {
		if (result.hasErrors()) {
			//userBook.setId(id);
			return "remove-user-book";
		}
		User user = userServiceImpl.findUserByEmail(userBook.getEmail());
		Book book2 = bookService.findBookByName(userBook.getBook_name());
		book2.addUsers(user);
		book2.removeUsers(user);
		book2.setInventory((book2.getInventory()>0)?book2.getInventory()+1:0);
		bookService.updateBook(book2);
		return "redirect:/books";
	}
}
