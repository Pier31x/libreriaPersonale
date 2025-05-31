package libreriaPersonale;

import libreriaPersonale.comparatori.Comparators;
import libreriaPersonale.comparatori.EnumComparatori;
import libreriaPersonale.database.LibreriaDAO;
import libreriaPersonale.filtri.Filtro;
import libreriaPersonale.modello.Catalogo;
import libreriaPersonale.modello.Libro;

import java.util.*;

public class GestoreCatalogo implements GestoreCatalogoIF {
    private Catalogo catalogo;
    private LibreriaDAO libreriaDAO;
    private LinkedList<Libro> libriDaAggiungere = new LinkedList<>();
    private LinkedList<Libro> libriDaModificare = new LinkedList<>();
    private LinkedList<Libro> libriDaRimuovere = new LinkedList<>();

    public GestoreCatalogo(LibreriaDAO libreriaDAO) {
        this.libreriaDAO = libreriaDAO;
        List<Libro> libri = libreriaDAO.caricaLibri();
        this.catalogo = new Catalogo(libri != null ? libri : new ArrayList<>());
    }

    @Override
    public boolean aggiungiLibro(Libro l) {
        if(catalogo.aggiungiLibro(l)){
            aggiornaLibriDaAggiungere(l);
            return true;
        }
        return false;
    }

    @Override
    public boolean modificaLibro(Libro l) {
        if(catalogo.modificaLibro(l)){
            aggiornaLibriDaModificare(l);
            return true;
        }
        return false;
    }

    @Override
    public boolean rimuoviLibro(Libro l) {
        if(catalogo.rimuoviLibro(l)){
            aggiornaLibriDaRimuovere(l);
            return true;
        }
        return false;
    }

    void aggiornaLibriDaAggiungere(Libro l) {
        if(libriDaRimuovere.remove(l)) {
            libriDaModificare.add(l);
        }
        else
            libriDaAggiungere.add(l);
    }

    void aggiornaLibriDaModificare(Libro l) {
        for(Libro libro : libriDaAggiungere){
            if(libro.equals(l)){
                libro.setLibro(l);
                return;
            }
        }
        for(Libro libro : libriDaModificare){
            if(libro.equals(l)){
                libro.setLibro(l);
                return;
            }
        }
        libriDaModificare.add(l);
    }

    void aggiornaLibriDaRimuovere(Libro l) {
        if(libriDaAggiungere.remove(l))
            return;
        libriDaModificare.remove(l);
        libriDaRimuovere.add(l);
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
        libreriaDAO.salvaCatalogo(libriDaAggiungere, libriDaModificare, libriDaRimuovere);
    }

    @Override
    public List<Libro> caricaCatalogo() {
        return libreriaDAO.caricaLibri();
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }
}