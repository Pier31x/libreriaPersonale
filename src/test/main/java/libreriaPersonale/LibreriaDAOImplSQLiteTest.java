package libreriaPersonale;

import libreriaPersonale.database.LibreriaDAOImplSQLite;
import libreriaPersonale.modello.Libro;
import libreriaPersonale.modello.Stato;
import org.junit.jupiter.api.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LibreriaDAOImplSQLiteTest {

    static LibreriaDAOImplSQLite dao;
    static Path tempDbPath;

    @BeforeAll
    static void setupAll() throws Exception {
        //Creo un database temporaneo
        tempDbPath = Files.createTempFile("test_libreria", ".db");
        dao = new LibreriaDAOImplSQLite("jdbc:sqlite:" + tempDbPath.toAbsolutePath());
    }

    @BeforeEach
    void setup() throws SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + tempDbPath.toAbsolutePath());
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM libri");
        }
    }

    @Test
    void testAggiungiCaricaLibro() {
        Libro libro = new Libro("111", "Test Book", "Author", "Genre", 4, Stato.IN_LETTURA);
        dao.aggiungiLibro(libro);

        List<Libro> libri = dao.caricaLibri();
        assertEquals(1, libri.size());
        assertEquals("Test Book", libri.get(0).getTitolo());
    }

    @Test
    void testModificaLibro() {
        Libro libro = new Libro("222", "Original", "Author", "Genre", 3, Stato.IN_LETTURA);
        dao.aggiungiLibro(libro);

        Libro modificato = new Libro("222", "Modificato", "Author", "Genre", 5, Stato.LETTO);
        dao.modificaLibro(modificato);

        List<Libro> libri = dao.caricaLibri();
        assertEquals(1, libri.size());
        assertEquals("Modificato", libri.get(0).getTitolo());
        assertEquals(5, libri.get(0).getValutazione());
    }

    @Test
    void testRimuoviLibro() {
        dao.aggiungiLibro(new Libro("333", "To Remove", "Author", "Genre", 2, Stato.IN_PRESTITO));
        dao.rimuoviLibro("333");

        List<Libro> libri = dao.caricaLibri();
        assertTrue(libri.isEmpty());
    }

    @Test
    void testSalvaCatalogo() {
        List<Libro> catalogo = List.of(
                new Libro("444", "Libro1", "Autore1", "Genere1", 3, Stato.LETTO),
                new Libro("555", "Libro2", "Autore2", "Genere2", 5, Stato.IN_LETTURA)
        );

        dao.salvaCatalogo(catalogo);

        List<Libro> libriCaricati = dao.caricaLibri();
        assertEquals(2, libriCaricati.size());
        assertTrue(libriCaricati.stream().anyMatch(l -> l.getIsbn().equals("444")));
        assertTrue(libriCaricati.stream().anyMatch(l -> l.getTitolo().equals("Libro2")));
    }

    @AfterAll
    static void cleanup() throws Exception {
        Files.deleteIfExists(tempDbPath);
    }
}
