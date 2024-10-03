package fr.aix.but.r3_04.td1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Library {
    private static final int MAX_BOOKS = 100;
    private String name, address;
    private int max;
    private Book[] books;

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
        this.books = new Book[MAX_BOOKS];
        this.max = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public Book[] getBooks() {
        return books;
    }

    public void setBooks(Book[] books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Library{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", max=" + max +
                ", books=" + Arrays.toString(books) +
                '}';
    }

    public void addBook(Book book) throws TooManyBooksInLibraryException {
        if(max >= MAX_BOOKS) {
            throw new TooManyBooksInLibraryException();
        }
        this.books[max] = book;
        ++max;
    }

    private void removeHolesInBooks()
    {
        for (int i = 0; i < MAX_BOOKS; i++) {
          if (books[i] != null) {
              continue;
          }

          for (int j = i + 1; j < MAX_BOOKS; j++) {
              if (books[j] == null) {
                  continue;
              }
              books[i] = books[j];
              books[j] = null;
              break;
          }
        }
    }

    public void removeBook(Book book) throws BookDoesNotExistException {
        int maxSizeBeforeRemoving = max;
        for (int i = 0; i < maxSizeBeforeRemoving; i++) {
            if (books[i].equals(book)) {
                books[i] = null;
                --max;
            }
        }
        if (maxSizeBeforeRemoving == max) {
            throw new BookDoesNotExistException();
        }
        removeHolesInBooks();
        recomputeMax();
    }

    private void recomputeMax() {
        int currentBooksInLibrary = 0;
        for (int i = 0; i < MAX_BOOKS; i++) {
            if (books[i] != null) {
                ++currentBooksInLibrary;
            }
        }

        max = currentBooksInLibrary;
    }

    public int removeDuplicate() {
        int duplicateFound = 0;
        for (int i = 0; i < max; i++) {
            Book currentBook = books[i];
            for (int j = i + 1; j < max; j++) {
                if (books[j] == null) {
                    continue;
                }
                if(books[j].equals(currentBook)) {
                    books[j] = null;
                    ++duplicateFound;
                }
            }
        }


        removeHolesInBooks();
        recomputeMax();
        return duplicateFound;
    }

    private boolean containsBook(Book[] books, Book book) {
        for (Book value : books) {
            if (value == null) {
                continue;
            }

            if (value.equals(book)) {
                return true;
            }
        }

        return false;
    }
    public List<Book> findSharedBooksWithThisLibrary(Library library)
    {
        List<Book> sharedBooks = new ArrayList<>();
        for(Book book: books) {
            if(containsBook(library.books, book)) {
                sharedBooks.add(book);
            }
        }
        return sharedBooks;
    }

    public void sortBooksByAuthor()
    {
        int n = books.length;
        int i, j;
        Book temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (books[j] == null && books[j+1] == null) {
                    continue;
                }
                assert books[j] != null;
                if (books[j].compareByAuthorFirst(books[j + 1]) > 0) {

                    temp = books[j];
                    books[j] = books[j + 1];
                    books[j + 1] = temp;
                    swapped = true;
                }
            }

            if (!swapped)
                break;
        }
        removeHolesInBooks();
    }
}
