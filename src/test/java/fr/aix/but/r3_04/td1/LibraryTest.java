package fr.aix.but.r3_04.td1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibraryTest {

    Library library, anotherLibrary;
    Book bloodBlockageBattlefrontVolume1, trigunVolume1, hellsingVolume1,
            driftersVolume1, genshikenVolume1, shangriLaFrontierVolume1;

    @BeforeEach
    void setUp() {
        library = new Library("Great library of Alexandria", "Under the sphinx, to the left");
        anotherLibrary = new Library("Little library of Alexandria", "Under the sphinx, to the right");
        bloodBlockageBattlefrontVolume1 =
                new Book("Blood Blockade Battlefront Volume 1", "Yasuhiro Nightow", "Kaze", 196);
        trigunVolume1 =
                new Book("Trigun Volume 1", "Yasuhiro Nightow", "Kaze", 196);
        hellsingVolume1 =
                new Book("Hellsing Volume 1", "Kōta Hirano", "Delcourt/Tonkam", 196);
        driftersVolume1 =
                new Book("Drifters Volume 1", "Kōta Hirano", "Delcourt/Tonkam", 196);
        genshikenVolume1 =
                new Book("Genshiken Volume 1", "Shimoku Kio", "Kurokawa", 196);
        shangriLaFrontierVolume1 =
                new Book("Shangri-La Frontier Volume 1",
                        "Kata Rina / Fuji Ryosuke", "Glenat", 196);


    }

    @Test
    void addOneBook() throws TooManyBooksInLibraryException {
        checkIfBooksIsEmpty();

        library.addBook(bloodBlockageBattlefrontVolume1);
        assertNumberOfBooksExpected(1);
        assertBooksInOrder(new Book[]{bloodBlockageBattlefrontVolume1});
    }

    private void checkIfBooksIsEmpty() {
        assertNumberOfBooksExpected(0);
    }

    private void assertNumberOfBooksExpected(int numberOfBooksExpected) {
        assertEquals(numberOfBooksExpected, library.getMax());
        for (int i = numberOfBooksExpected; i < 100; ++i) {
            assertNull(library.getBooks()[i]);
        }
    }

    @Test
    void addBooksUntilTooManyBooksInLibraryExceptionThrows() throws Exception {
        checkIfBooksIsEmpty();

        int i = 0;
        for (; i < Integer.MAX_VALUE; ++i) {
            try {
                library.addBook(bloodBlockageBattlefrontVolume1);
            } catch (TooManyBooksInLibraryException exception) {
                assertNumberOfBooksExpected(100);
                return;
            }
        }

        throw new Exception("This test should throw");
    }

    @Test
    void removeBook() throws TooManyBooksInLibraryException, BookDoesNotExistException {
        checkIfBooksIsEmpty();
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(trigunVolume1);
        library.addBook(shangriLaFrontierVolume1);
        library.addBook(bloodBlockageBattlefrontVolume1);
        assertNumberOfBooksExpected(4);

        library.removeBook(bloodBlockageBattlefrontVolume1);

        assertNumberOfBooksExpected(2);
        assertBooksInOrder(new Book[]{trigunVolume1, shangriLaFrontierVolume1});
    }

    private void assertBooksInOrder(Book[] books) {
        for (int i = 0; i < books.length; i++) {
            assertEquals(books[i], library.getBooks()[i]);
        }
    }

    @Test
    void tryToRemoveAnInexistantBook() throws Exception {
        checkIfBooksIsEmpty();
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(trigunVolume1);
        assertNumberOfBooksExpected(2);

        try {
            library.removeBook(genshikenVolume1);

        } catch (BookDoesNotExistException exception) {
            assertBooksInOrder(new Book[]{bloodBlockageBattlefrontVolume1, trigunVolume1});
            return;
        }

        throw new Exception("This test should throw");
    }

    @Test
    void removeDuplicate() throws TooManyBooksInLibraryException {
        checkIfBooksIsEmpty();
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(trigunVolume1);
        library.addBook(genshikenVolume1);
        library.addBook(hellsingVolume1);
        library.addBook(driftersVolume1);
        library.addBook(genshikenVolume1);
        library.addBook(shangriLaFrontierVolume1);
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(hellsingVolume1);
        library.addBook(trigunVolume1);
        library.addBook(genshikenVolume1);
        assertNumberOfBooksExpected(11);

        assertEquals(5, library.removeDuplicate());

        assertBooksInOrder(
                new Book[]{
                        bloodBlockageBattlefrontVolume1,
                        trigunVolume1,
                        genshikenVolume1,
                        hellsingVolume1,
                        driftersVolume1,
                        shangriLaFrontierVolume1
                }
        );

    }

    @Test
    void findSharedBooksWithThisLibrary() throws TooManyBooksInLibraryException {
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(hellsingVolume1);
        library.addBook(driftersVolume1);
        library.addBook(genshikenVolume1);
        anotherLibrary.addBook(trigunVolume1);
        anotherLibrary.addBook(driftersVolume1);
        anotherLibrary.addBook(genshikenVolume1);

        List<Book> sharedBooks = library.findSharedBooksWithThisLibrary(anotherLibrary);
        assertEquals(2, sharedBooks.size());
        assertTrue(sharedBooks.contains(driftersVolume1));
        assertTrue(sharedBooks.contains(genshikenVolume1));
    }
    @Test
    void tryToFindSharedBooksWithThisLibraryButWeDontShareAnyBooks() throws TooManyBooksInLibraryException {
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(hellsingVolume1);
        library.addBook(driftersVolume1);
        anotherLibrary.addBook(trigunVolume1);
        anotherLibrary.addBook(genshikenVolume1);
        anotherLibrary.addBook(genshikenVolume1);

        List<Book> sharedBooks = library.findSharedBooksWithThisLibrary(anotherLibrary);
        assertEquals(0, sharedBooks.size());
    }

    @Test
    void sortBooksByAuthor() throws TooManyBooksInLibraryException {
        library.addBook(trigunVolume1);
        library.addBook(genshikenVolume1);
        library.addBook(bloodBlockageBattlefrontVolume1);
        library.addBook(driftersVolume1);
        library.addBook(shangriLaFrontierVolume1);
        library.addBook(hellsingVolume1);
        library.addBook(driftersVolume1);
        library.addBook(genshikenVolume1);

        library.sortBooksByAuthor();

        assertBooksInOrder(new Book[]{
                shangriLaFrontierVolume1,
                driftersVolume1,
                driftersVolume1,
                hellsingVolume1,
                genshikenVolume1,
                genshikenVolume1,
                bloodBlockageBattlefrontVolume1,
                trigunVolume1
        });
    }
}