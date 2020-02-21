
import java.sql.*;
import java.util.Scanner;

public class labb2databas {

    private static Scanner scanner = new Scanner(System.in);

    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:c:/SQL_lite Min databas/labb2.db";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    private static void printActions() {
        System.out.println("\nVälj:\n");
        System.out.println("0  - Stäng av\n" +
                "1  - Visa alla filmer\n" +
                "2  - Lägga till en ny film\n" +
                "3  - Uppdatera en film\n" +
                "4  - Ta bort en film\n" +
                "5  - Visa en lista över alla val.");
    }


    private static void deleteFilm(){
        System.out.println("Skriv in id:t på filmen som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();
    }

    private static void selectAll(){
        String sql = "SELECT * FROM film";

        try {
            Connection conn = connect();
            Statement stmt  = conn.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("filmid") +  "\t" +
                        rs.getString("filmTitel"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void insert(String titel,String knamn) {
        System.out.println("Skriv in titel på filmen: ");
        String titell = scanner.nextLine();
        System.out.println("Skriv in huvudrolls karaktär: ");
        String karaknamn = scanner.nextLine();



        String sql = "INSERT INTO film(filmTitel) VALUES(?)" +
                "INSERT INTO karaktar(karaktarNamn) VALUES(?)";

        try{
            Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, titell);
            pstmt.setString(2, karaknamn);
            pstmt.executeUpdate();
            System.out.println("Du har lagt till en ny film");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
/*private static void deleteFilm(){
        System.out.println("Skriv in id:t på filmen som ska tas bort: ");
        int inputId = scanner.nextInt();
        delete(inputId);
        scanner.nextLine();*/

    private static void update(String titel, int nr) {
        String sql = "UPDATE film SET filmTitel = ?  WHERE filmId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setString(1, titel);
            pstmt.setInt(2, nr);
            // update
            pstmt.executeUpdate();
            System.out.println("Du har uppdaterat vald film");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void delete(int id) {
        String sql = "DELETE FROM film WHERE filmId = ?";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // set the corresponding param
            pstmt.setInt(1, id);
            // execute the delete statement
            pstmt.executeUpdate();
            System.out.println("Du har tagit bort filmen");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {

        boolean quit = false;
        printActions();
        while(!quit) {
            System.out.println("\nVälj (5 för att visa val):");
            int action = scanner.nextInt();
            scanner.nextLine();

            switch (action) {
                case 0:
                    System.out.println("\nStänger ner...");
                    quit = true;
                    break;

                case 1:
                    selectAll();
                    break;

                case 2:
                   
                    insert("", "" );
                    break;

                case 3:
                    update("First contact", 1);
                    break;

                case 4:
                    //delete(1);
                    deleteFilm();
                    break;

                case 5:
                    printActions();
                    break;
            }
        }

    }

}

