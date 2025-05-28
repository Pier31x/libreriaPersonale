package libreriaPersonale.database;

import libreriaPersonale.modello.Libro;

import java.util.List;


public interface LibreriaDAO {
    public void aggiungiLibro(Libro libro);
    public void rimuoviLibro(String ISBN);
    public void modificaLibro(Libro libro);
    
    public List<Libro> caricaLibri();
    public void salvaCatalogo(List<Libro> libri);
}

