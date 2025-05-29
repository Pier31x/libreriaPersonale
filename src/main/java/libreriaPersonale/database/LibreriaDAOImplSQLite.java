package libreriaPersonale.database;

import libreriaPersonale.modello.Libro;
import libreriaPersonale.modello.Stato;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibreriaDAOImplSQLite implements LibreriaDAO {

    private static String DB_URL = "";

    public LibreriaDAOImplSQLite(String DB) {
        DB_URL = DB;
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = """
                CREATE TABLE IF NOT EXISTS libri (
                    isbn TEXT PRIMARY KEY NOT NULL,
                    titolo TEXT,
                    autore TEXT,
                    genere TEXT,
                    valutazione INTEGER,
                    statoLettura TEXT
                );
            """;

            stmt.execute(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void aggiungiLibro(Libro libro) {
        String sql = "INSERT OR REPLACE INTO libri (isbn, titolo, autore, genere, valutazione, statoLettura) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, libro.getIsbn());
            pstmt.setString(2, libro.getTitolo());
            pstmt.setString(3, libro.getAutore());
            pstmt.setString(4, libro.getGenere());
            pstmt.setInt(5, libro.getValutazione());
            pstmt.setString(6, libro.getStato().toString());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void rimuoviLibro(String isbn) {
        String sql = "DELETE FROM libri WHERE isbn = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, isbn);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void modificaLibro(Libro libro) {
        String sql = """
            UPDATE libri
            SET titolo = ?, autore = ?, genere = ?, valutazione = ?, statoLettura = ?
            WHERE isbn = ?
        """;

        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, libro.getTitolo());
            pstmt.setString(2, libro.getAutore());
            pstmt.setString(3, libro.getGenere());
            pstmt.setInt(4, libro.getValutazione());
            pstmt.setString(5, libro.getStato().toString());
            pstmt.setString(6, libro.getIsbn());

            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Libro> caricaLibri() {
        List<Libro> libri = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM libri")) {

            while (rs.next()) {
                Libro libro = new Libro(
                        rs.getString("isbn"),
                        rs.getString("titolo"),
                        rs.getString("autore"),
                        rs.getString("genere"),
                        rs.getInt("valutazione"),
                        Stato.valueOf(rs.getString("statoLettura"))  // FIXATO
                );
                libri.add(libro);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return libri;
    }

    @Override
    public void salvaCatalogo(List<Libro> libri) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
            conn.setAutoCommit(false);

            try (PreparedStatement pstmt = conn.prepareStatement("DELETE FROM libri")) {
                pstmt.executeUpdate();
            }

            String insertSql = "INSERT INTO libri (isbn, titolo, autore, genere, valutazione, statoLettura) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(insertSql)) {
                for (Libro libro : libri) {
                    pstmt.setString(1, libro.getIsbn());
                    pstmt.setString(2, libro.getTitolo());
                    pstmt.setString(3, libro.getAutore());
                    pstmt.setString(4, libro.getGenere());
                    pstmt.setInt(5, libro.getValutazione());
                    pstmt.setString(6, libro.getStato().toString());
                    pstmt.executeUpdate();
                }
            }

            conn.commit();
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
