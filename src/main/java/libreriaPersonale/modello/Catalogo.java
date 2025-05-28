package libreriaPersonale.modello;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Catalogo {
    private List<Libro> catalogo;

    public Catalogo(List<Libro> catalogo) {
        this.catalogo = new ArrayList<>(catalogo);
    }

    public List<Libro> getCatalogo() {return catalogo;}

    public boolean aggiungiLibro(Libro libro) {
       for (Libro l : catalogo) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                return false;
            }
        }
        return catalogo.add(libro);
    }

    public boolean rimuoviLibro(Libro libro) {
        Iterator<Libro> it = catalogo.iterator();
        while (it.hasNext()) {
            Libro l = it.next();
            if (l.getIsbn().equals(libro.getIsbn())) {
                it.remove();
                return true;
            }
        }
        return false;
    }

    public boolean modificaLibro(Libro libro) {
        for (Libro l : catalogo) {
            if (l.getIsbn().equals(libro.getIsbn())) {
                l.setLibro(libro);
                return true;
            }
        }
        return false;
    }

}
