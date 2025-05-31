package libreriaPersonale;

import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.filtri.Filtro;
import libreriaPersonale.modello.Libro;

import java.util.Comparator;
import java.util.List;


public interface GestoreCatalogoIF {
    boolean aggiungiLibro(Libro l);
    boolean modificaLibro(Libro l);
    boolean rimuoviLibro(Libro l);
    List<Libro> ordinaCatalogo(Comparator<Libro> comparator);
    List<Libro> filtraCatalogo(Filtro filtro);
    void salvaCatalogo();
    List<Libro> caricaCatalogo();
}
