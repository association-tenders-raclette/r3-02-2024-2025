package fr.aix.but.r3_04.td1;

import java.util.Objects;

public class Book{
    public String title, author, editor;
    public int pageNb;

    public Book(String title, String author, String editor, int pageNb) {
        this.title = title;
        this.author = author;
        this.editor = editor;
        this.pageNb = pageNb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return pageNb == book.pageNb && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(editor, book.editor);
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", editor='" + editor + '\'' +
                ", pageNb=" + pageNb +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEditor() {
        return editor;
    }

    public void setEditor(String editor) {
        this.editor = editor;
    }

    public int getPageNb() {
        return pageNb;
    }

    public void setPageNb(int pageNb) {
        this.pageNb = pageNb;
    }

    public int compareByAuthorFirst(Book o)
    {
        if (o == null) {
            return -1;
        }
        int authorCompared = this.author.compareTo(o.author);
        if (authorCompared != 0) {
            return authorCompared;
        }

        return this.title.compareTo(o.title);
    }
}
