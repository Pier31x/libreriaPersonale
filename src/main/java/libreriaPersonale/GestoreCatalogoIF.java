package libreriaPersonale;

import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.filtri.Filtro;
import libreriaPersonale.modello.Libro;

import java.util.Comparator;
import java.util.List;


public interface GestoreCatalogoIF {
    public boolean aggiungiLibro(Libro l);
    public boolean modificaLibro(Libro l);
    public boolean rimuoviLibro(Libro l);
    public List<Libro> ordinaCatalogo(Comparator<Libro> comparator);
    public List<Libro> filtraCatalogo(Filtro filtro);
    public void salvaCatalogo();
    List<Libro> caricaCatalogo();
}
