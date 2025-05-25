package libreriaPersonale;

import java.util.List;

public class Catalogo {
    private List<Libro> catalogo;

    //TODO
    public Catalogo(List<Libro> catalogo) {
        //Dovrebbe andar bene anche solo questa copia così
        this.catalogo = catalogo;
    }

    public List<Libro> getCatalogo() {return catalogo;}

    public void aggiungiLibro(Libro libro) {
        catalogo.add(libro);
    }

    public void rimuoviLibro(Libro libro) {
        catalogo.remove(libro);
    }

    //TODO
    //Controlla che la modifica venga fatta effettivamente sul libro che ha lo stesso ISBN
    public void modificaLibro(Libro libro) {
        for(Libro lib : catalogo) {
            if(libro.equals(lib))
                lib = libro;
        }
    }


    public boolean isbnGiaPresente(String isbn) {
        for (Libro libro : catalogo) {
            if (libro.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }
}
