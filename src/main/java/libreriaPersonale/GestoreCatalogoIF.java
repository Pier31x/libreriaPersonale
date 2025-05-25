package libreriaPersonale;

import libreriaPersonale.filtri.Filtro;

import java.util.List;

//TODO
public interface GestoreCatalogoIF {
    public boolean aggiungiLibro(Libro l);
    public boolean modificaLibro(Libro l);
    public boolean rimuoviLibro(Libro l);
    public List<Libro> ordinaCatalogo(EnumComparatori comparator);
    public List<Libro> filtraCatalogo(Filtro filtro);
    public void salvaCatalogo();
    List<Libro> caricaCatalogo();
}
