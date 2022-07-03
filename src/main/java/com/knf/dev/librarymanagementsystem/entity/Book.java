package com.knf.dev.librarymanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "books")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "isbn", length = 50, nullable = false, unique = true)
	private String isbn;

	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@Column(name = "serialName", length = 50, nullable = false)
	private String serialName;

	@Column(name = "description", length = 250, nullable = false)
	private String description;

	@Column(name = "inventory", length = 250, nullable = false)
	private int inventory;


	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
	@JoinTable(name = "books_authors", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "author_id") })
	private Set<Author> authors = new HashSet<Author>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "books_categories", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "category_id") })
	private Set<Category> categories = new HashSet<Category>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "books_publishers", joinColumns = { @JoinColumn(name = "book_id") }, inverseJoinColumns = {
			@JoinColumn(name = "publisher_id") })
	private Set<Publisher> publishers = new HashSet<Publisher>();

//	@ManyToOne(fetch = FetchType.LAZY, optional = false)
//	@JoinColumn(name = "user_id")
//	private User user;

	@ManyToMany
	@JoinTable(name = "user_book",
			joinColumns = @JoinColumn(name = "book_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id"))
	//@ToString.Exclude
	@JsonBackReference
	private Set<User> users = new HashSet<User>(); ;
	public Book(String isbn, String name, String serialName, String description, int inventory) {
		this.isbn = isbn;
		this.name = name;
		this.serialName = serialName;
		this.description = description;
		this.inventory = inventory;
	}

	public void addAuthors(Author author) {
		this.authors.add(author);
		author.getBooks().add(this);
	}

	public void removeAuthors(Author author) {
		this.authors.remove(author);
		author.getBooks().remove(this);
	}

	public void addCategories(Category category) {
		this.categories.add(category);
		category.getBooks().add(this);
	}

	public void removeCategories(Category category) {
		this.categories.remove(category);
		category.getBooks().remove(this);
	}

	public void addPublishers(Publisher publisher) {
		this.publishers.add(publisher);
		publisher.getBooks().add(this);
	}
	public void addUsers(User user) {
		this.users.add(user);
		user.getBooks().add(this);
	}
	public void removeUsers(User user) {
		this.users.remove(user);
		user.getBooks().remove(this);
	}
	public void removePublishers(Publisher publisher) {
		this.publishers.remove(publisher);
		publisher.getBooks().remove(this);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSerialName() {
		return serialName;
	}

	public void setSerialName(String serialName) {
		this.serialName = serialName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(Set<Author> authors) {
		this.authors = authors;
	}

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Publisher> getPublishers() {
		return publishers;
	}

	public void setPublishers(Set<Publisher> publishers) {
		this.publishers = publishers;
	}

	public int getInventory() {
		return inventory;
	}

	public void setInventory(int inventory) {
		this.inventory = inventory;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public Book() {
		super();
	}

	@Override
	public String toString() {
		return "Book{" +
				"id=" + id +
				", isbn='" + isbn + '\'' +
				", name='" + name + '\'' +
				", serialName='" + serialName + '\'' +
				", description='" + description + '\'' +
				", inventory=" + inventory +
				", authors=" + authors +
				", categories=" + categories +
				", publishers=" + publishers +
				'}';
	}
}
