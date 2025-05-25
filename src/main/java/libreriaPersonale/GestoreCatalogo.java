package libreriaPersonale;

import libreriaPersonale.filtri.Filtro;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class GestoreCatalogo implements GestoreCatalogoIF {
    private List<Libro> catalogo;
    private LibreriaDAO libreriaDAO;

    public GestoreCatalogo(LibreriaDAO libreriaDAO) {
        this.libreriaDAO = libreriaDAO;
        this.catalogo = caricaCatalogo();
        if (this.catalogo == null) {
            this.catalogo = new ArrayList<>();
        }
    }

    @Override
    public boolean aggiungiLibro(Libro l) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(l.getIsbn())) {
                return false;
            }
        }
        catalogo.add(l);
        return true;
    }

    @Override
    public boolean modificaLibro(Libro nuovoLibro) {
        for (int i = 0; i < catalogo.size(); i++) {
            Libro libroEsistente = catalogo.get(i);
            if (libroEsistente.getIsbn().equals(nuovoLibro.getIsbn())) {
                catalogo.set(i, nuovoLibro);
                return true;
            }
        }
        return false; // Libro non trovato
    }

    //True se il libro esiste e viene rimosso
    @Override
    public boolean rimuoviLibro(Libro l) {
        Iterator<Libro> iterator = catalogo.iterator();
        while (iterator.hasNext()) {
            Libro libro = iterator.next();
            if (libro.getIsbn().equals(l.getIsbn())) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Libro> ordinaCatalogo(EnumComparatori comparator) {
        List<Libro> catalogoOrdinato = new ArrayList<>(catalogo);
        if (comparator == null) {
            return catalogoOrdinato;
        }
        Comparator<Libro> libroComparator = null;
        switch (comparator) {
            case TITOLO:
                libroComparator = Comparators.perTitolo;
                break;
            case AUTORE:
                libroComparator = Comparators.perAutore;
                break;
            case GENERE:
                libroComparator = Comparators.perGenere;
                break;
            case VALUTAZIONE:
                libroComparator = Comparators.perValutazione;
                break;
            case STATO:
                libroComparator = Comparators.perStato;
                break;
            default:
                return catalogoOrdinato; //In realtà è "ordinato" come "default" in questo caso
        }
        catalogoOrdinato.sort(libroComparator);
        return catalogoOrdinato;
    }

    @Override
    public List<Libro> filtraCatalogo(Filtro filtro) {
        List<Libro> catalogoFiltrato = new ArrayList<>();
        if (filtro == null) {
            return new ArrayList<>(catalogo);
        }
        for (Libro libro : catalogo) {
            if (filtro.applicaFiltro(libro)) {
                catalogoFiltrato.add(libro);
            }
        }
        return catalogoFiltrato;
    }

    @Override
    public void salvaCatalogo() {
        libreriaDAO.salvaCatalogo(catalogo);
    }

    @Override
    public List<Libro> caricaCatalogo() {
        return libreriaDAO.caricaLibri();
    }

}