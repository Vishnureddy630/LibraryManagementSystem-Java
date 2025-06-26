import java.util.*;

public class LibraryManagementSystem {

    // ---------- Book Class ----------
    static class Book {
        private String title;
        private String author;
        private boolean isIssued;

        public Book(String title, String author) {
            this.title = title;
            this.author = author;
            this.isIssued = false;
        }

        public String getTitle() {
            return title;
        }

        public boolean isIssued() {
            return isIssued;
        }

        public void issue() {
            isIssued = true;
        }

        public void returnBook() {
            isIssued = false;
        }

        public String toString() {
            return title + " by " + author + (isIssued ? " (Issued)" : " (Available)");
        }
    }

    // ---------- User Class ----------
    static class User {
        private String name;
        private List<Book> issuedBooks;

        public User(String name) {
            this.name = name;
            this.issuedBooks = new ArrayList<>();
        }

        public String getName() {
            return name;
        }

        public void borrow(Book book) {
            issuedBooks.add(book);
        }

        public void giveBack(Book book) {
            issuedBooks.remove(book);
        }

        public void showBooks() {
            System.out.println("Books issued to " + name + ":");
            if (issuedBooks.isEmpty()) {
                System.out.println(" - No books issued.");
            } else {
                for (Book book : issuedBooks) {
                    System.out.println(" - " + book.getTitle());
                }
            }
        }
    }

    // ---------- Library Class ----------
    static class Library {
        private Map<String, Book> books = new HashMap<>();
        private Map<String, User> users = new HashMap<>();

        public void addBook(Book book) {
            books.putIfAbsent(book.getTitle().toLowerCase(), book);
        }

        public void addUser(User user) {
            users.putIfAbsent(user.getName().toLowerCase(), user);
        }

        public void issueBook(String title, String userName) {
            Book book = books.get(title.toLowerCase());
            User user = users.get(userName.toLowerCase());

            if (book == null) {
                System.out.println("Book not found.");
                return;
            }
            if (user == null) {
                System.out.println("User not found.");
                return;
            }
            if (book.isIssued()) {
                System.out.println("Book already issued.");
                return;
            }

            book.issue();
            user.borrow(book);
            System.out.println(userName + " issued \"" + title + "\".");
        }

        public void returnBook(String title, String userName) {
            Book book = books.get(title.toLowerCase());
            User user = users.get(userName.toLowerCase());

            if (book == null || user == null) {
                System.out.println("Return failed. Book or user not found.");
                return;
            }

            if (!book.isIssued()) {
                System.out.println("Book was not issued.");
                return;
            }

            book.returnBook();
            user.giveBack(book);
            System.out.println(userName + " returned \"" + title + "\".");
        }

        public void showAllBooks() {
            System.out.println("Library Books:");
            for (Book book : books.values()) {
                System.out.println(" - " + book);
            }
        }

        public void showUserBooks(String userName) {
            User user = users.get(userName.toLowerCase());
            if (user == null) {
                System.out.println("User not found.");
            } else {
                user.showBooks();
            }
        }
    }

    // ---------- Main Method ----------
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Library library = new Library();

        // Sample Data
        library.addBook(new Book("Java Basics", "James Gosling"));
        library.addBook(new Book("Clean Code", "Robert C. Martin"));
        library.addUser(new User("Alice"));
        library.addUser(new User("Bob"));

        int choice;
        do {
            System.out.println("\n--- Library Menu ---");
            System.out.println("1. Show All Books");
            System.out.println("2. Issue Book");
            System.out.println("3. Return Book");
            System.out.println("4. Show User's Issued Books");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            while (!sc.hasNextInt()) {
                System.out.println("Enter a valid number.");
                sc.next(); // discard invalid input
            }
            choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    library.showAllBooks();
                    break;
                case 2:
                    System.out.print("Enter book title: ");
                    String issueTitle = sc.nextLine();
                    System.out.print("Enter user name: ");
                    String issueUser = sc.nextLine();
                    library.issueBook(issueTitle, issueUser);
                    break;
                case 3:
                    System.out.print("Enter book title: ");
                    String returnTitle = sc.nextLine();
                    System.out.print("Enter user name: ");
                    String returnUser = sc.nextLine();
                    library.returnBook(returnTitle, returnUser);
                    break;
                case 4:
                    System.out.print("Enter user name: ");
                    String userToCheck = sc.nextLine();
                    library.showUserBooks(userToCheck);
                    break;
                case 5:
                    System.out.println("Exiting Library System. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 5);

        sc.close();
    }
}