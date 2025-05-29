package libreriaPersonale;

import libreriaPersonale.comparatori.Comparators;
import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.database.LibreriaDAO;
import libreriaPersonale.filtri.Filtro;
import libreriaPersonale.modello.Catalogo;
import libreriaPersonale.modello.Libro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GestoreCatalogo implements GestoreCatalogoIF {
    private Catalogo catalogo;
    private LibreriaDAO libreriaDAO;

    public GestoreCatalogo(LibreriaDAO libreriaDAO) {
        this.libreriaDAO = libreriaDAO;
        List<Libro> libri = libreriaDAO.caricaLibri();
        this.catalogo = new Catalogo(libri != null ? libri : new ArrayList<>());
    }

    @Override
    public boolean aggiungiLibro(Libro l) {
        return catalogo.aggiungiLibro(l);
    }

    @Override
    public boolean modificaLibro(Libro nuovoLibro) {
        return catalogo.modificaLibro(nuovoLibro);
    }

    @Override
    public boolean rimuoviLibro(Libro l) {
        return catalogo.rimuoviLibro(l);
    }

    @Override
    public List<Libro> ordinaCatalogo(Comparator<Libro> comparator) {
        List<Libro> catalogoOrdinato = new ArrayList<>(catalogo.getCatalogo());
        if (comparator == null) {
            return catalogoOrdinato;
        }
        catalogoOrdinato.sort(comparator);
        return catalogoOrdinato;
    }

    @Override
    public List<Libro> filtraCatalogo(Filtro filtro) {
        List<Libro> catalogoCompleto = new ArrayList<>(catalogo.getCatalogo());
        List<Libro> catalogoFiltrato = new ArrayList<>();
        if (filtro == null) {
            return new ArrayList<>(catalogoCompleto);
        }
        for (Libro libro : catalogoCompleto) {
            if (filtro.applicaFiltro(libro)) {
                catalogoFiltrato.add(libro);
            }
        }
        return catalogoFiltrato;
    }

    @Override
    public void salvaCatalogo() {
        libreriaDAO.salvaCatalogo(catalogo.getCatalogo());
    }

    @Override
    public List<Libro> caricaCatalogo() {
        return libreriaDAO.caricaLibri();
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }
}