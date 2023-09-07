package com.hubberspot.mockito.test_doubles.mock;

public class BookService {
	
	private BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public void addBook(Book book){
		
		if(book.getPrice() > 400){
			bookRepository.save(book);
		}
	}
	
}
