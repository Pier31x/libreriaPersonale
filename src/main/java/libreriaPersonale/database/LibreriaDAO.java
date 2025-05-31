package libreriaPersonale.database;

import libreriaPersonale.modello.Libro;

import java.util.List;


public interface LibreriaDAO {
    void aggiungiLibro(Libro libro);
    void rimuoviLibro(String ISBN);
    void modificaLibro(Libro libro);
    
    List<Libro> caricaLibri();
    void salvaCatalogo(List<Libro> libri);
    void salvaCatalogo(List<Libro> daAggiungere, List<Libro> daModificare, List<Libro> daRimuovere);
}

