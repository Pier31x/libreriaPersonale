package libreriaPersonale;

import libreriaPersonale.filtri.Filtro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GestoreCatalogoTest {

    private TestLibreriaDAO testLibreriaDAO;
    private GestoreCatalogo gestoreCatalogo;
    private Libro libro1;
    private Libro libro2;
    private Libro libro3;

    //Implementazione fittizia del DAO per i test
    static class TestLibreriaDAO implements LibreriaDAO {
        private List<Libro> libri = new ArrayList<>();

        @Override
        public void aggiungiLibro(Libro libro) {
            libri.add(libro);
        }

        @Override
        public void rimuoviLibro(String ISBN) {
            for(Libro libro : libri) {
                if (libro.getIsbn().equals(ISBN)) {
                    libri.remove(libro);
                    return;
                }
            }
        }

        @Override
        public void modificaLibro(Libro libro) {
            for (int i = 0; i < libri.size(); i++) {
                if (libri.get(i).getIsbn().equals(libro.getIsbn())) {
                    libri.set(i, libro);
                    return;
                }
            }
        }

        @Override
        public List<Libro> caricaLibri() {
            return new ArrayList<>(libri);
        }

        @Override
        public void salvaCatalogo(List<Libro> libri) {
            this.libri = new ArrayList<>(libri);
        }
    }

    @BeforeEach
    void setUp() {
        testLibreriaDAO = new TestLibreriaDAO();

        libro1 = new Libro("978-0321765723", "J.R.R. Tolkien", "The Lord of the Rings", "Fantasy", 5, Libro.Stato.LETTO);
        libro2 = new Libro("978-0743273565", "F. Scott Fitzgerald", "The Great Gatsby", "Classic", 4, Libro.Stato.IN_LETTURA);
        libro3 = new Libro("978-0061120084", "Harper Lee", "To Kill a Mockingbird", "Classic", 5, Libro.Stato.DA_LEGGERE);

        testLibreriaDAO.salvaCatalogo(new ArrayList<>(Arrays.asList(libro1, libro2)));

        gestoreCatalogo = new GestoreCatalogo(testLibreriaDAO);
    }

    @Test
    void aggiungiLibro_libroNonEsistente_aggiungeConSuccesso() {
        boolean aggiunto = gestoreCatalogo.aggiungiLibro(libro3);
        assertTrue(aggiunto);
        //Non ho salvato il catalogo, quindi non deve ancora essere presente in memoria secondaria
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertFalse(catalogo.contains(libro3));
    }

    @Test
    void aggiungiLibro_libroEsistente_nonAggiunge() {
        Libro libroDuplicato = new Libro("978-0321765723", "Another Title", "Another Author", "Sci-Fi", 3, Libro.Stato.IN_LETTURA);
        boolean aggiunto = gestoreCatalogo.aggiungiLibro(libroDuplicato);
        assertFalse(aggiunto);
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertEquals(2, catalogo.size());
    }

    @Test
    void modificaLibro_libroEsistente_modificaConSuccesso() {
        Libro libroModificato = new Libro("978-0321765723", "The Lord of the Rings - Revised", "J.R.R. Tolkien", "Fantasy", 5, Libro.Stato.IN_PRESTITO);
        boolean modificato = gestoreCatalogo.modificaLibro(libroModificato);
        assertTrue(modificato);
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertTrue(catalogo.contains(libroModificato)); //Ritorna true perché l'ISBN è lo stesso
        assertFalse(catalogo.stream().anyMatch(l -> l.getIsbn().equals(libro1.getIsbn()) && !l.equals(libroModificato)));
    }

    @Test
    void modificaLibro_libroNonEsistente_nonModifica() {
        Libro libroNonEsistente = new Libro("978-1234567890", "Non Existing Book", "Unknown", "Mystery", 2, Libro.Stato.LETTO);
        boolean modificato = gestoreCatalogo.modificaLibro(libroNonEsistente);
        assertFalse(modificato);
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertEquals(2, catalogo.size());
    }

    @Test
    void rimuoviLibro_libroEsistente_rimuoveConSuccesso() {
        boolean rimosso = gestoreCatalogo.rimuoviLibro(libro1);
        assertTrue(rimosso);
        //Continua però a esistere in memoria secondaria, perché non è stato salvato nulla
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertTrue(catalogo.stream().anyMatch(l -> l.getIsbn().equals(libro1.getIsbn())));
        assertEquals(2, catalogo.size());
    }

    @Test
    void rimuoviLibro_libroNonEsistente_nonRimuove() {
        Libro libroNonEsistente = new Libro("978-1234567890", "Non Existing Book", "Unknown", "Mystery", 2, Libro.Stato.LETTO);
        boolean rimosso = gestoreCatalogo.rimuoviLibro(libroNonEsistente);
        assertFalse(rimosso);
        List<Libro> catalogo = testLibreriaDAO.caricaLibri();
        assertEquals(2, catalogo.size());
    }

    @Test
    void ordinaCatalogo_perTitolo_ordinaCorrettamente() {
        List<Libro> ordinato = gestoreCatalogo.ordinaCatalogo(EnumComparatori.TITOLO);
        assertEquals("The Great Gatsby", ordinato.get(0).getTitolo());
        assertEquals("The Lord of the Rings", ordinato.get(1).getTitolo());
    }

    @Test
    void ordinaCatalogo_perAutore_ordinaCorrettamente() {
        List<Libro> ordinato = gestoreCatalogo.ordinaCatalogo(EnumComparatori.AUTORE);
        assertEquals("F. Scott Fitzgerald", ordinato.get(0).getAutore());
        assertEquals("J.R.R. Tolkien", ordinato.get(1).getAutore());
    }

    @Test
    void ordinaCatalogo_comparatoreNullo_restituisceListaNonOrdinata() {
        List<Libro> ordinato = gestoreCatalogo.ordinaCatalogo(null);
        assertEquals(libro1, ordinato.get(0));
        assertEquals(libro2, ordinato.get(1));
    }

    @Test
    void filtraCatalogo_perGenere_filtraCorrettamente() {
        Filtro filtroFantasy = libro -> libro.getGenere().equals("Fantasy");
        List<Libro> filtrato = gestoreCatalogo.filtraCatalogo(filtroFantasy);
        assertEquals(1, filtrato.size());
        assertEquals("The Lord of the Rings", filtrato.get(0).getTitolo());
    }

    @Test
    void filtraCatalogo_filtroNullo_restituisceListaNonFiltrata() {
        List<Libro> filtrato = gestoreCatalogo.filtraCatalogo(null);
        assertEquals(2, filtrato.size());
    }

    @Test
    void salvaCatalogo_chiamaDAOCorrettamente() {
        List<Libro> nuoviLibri = Arrays.asList(libro1, libro2, libro3);
        gestoreCatalogo.salvaCatalogo();
        List<Libro> catalogoSalvato = testLibreriaDAO.caricaLibri();
        assertEquals(3, catalogoSalvato.size()); // Dovrebbe contenere lo stato dopo setUp
    }

    @Test
    void caricaCatalogo_chiamaDAOCorrettamente() {
        List<Libro> caricato = gestoreCatalogo.caricaCatalogo();
        assertEquals(2, caricato.size());
        assertEquals(libro1, caricato.get(0));
        assertEquals(libro2, caricato.get(1));
    }
}