package libreriaPersonale.modello;

import java.util.Objects;

public class Libro {

    private String isbn;
    private String autore;
    private String titolo;
    private String genere;
    private int valutazione;
    private Stato stato;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Libro libro = (Libro) o;
        return Objects.equals(isbn, libro.isbn);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(isbn);
    }

    @Override
    public String toString() {
        return  "isbn='" + isbn + '\'' +
                ", titolo='" + titolo + '\'' +
                ", autore='" + autore + '\'' +
                ", genere='" + genere + '\'' +
                ", valutazione=" + valutazione;
    }

    public Libro(String isbn, String titolo, String autore, String genere, int valutazione, Stato stato) {
        this.isbn = isbn;
        this.titolo = titolo;
        this.autore = autore;
        this.genere = genere;
        this.valutazione = valutazione;
        this.stato = stato;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getAutore() {
        return autore;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getGenere() {
        return genere;
    }

    public int getValutazione() {
        return valutazione;
    }

    public Stato getStato() { return stato;}

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setAutore(String autore) {
        this.autore = autore;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public void setLibro(Libro libro) {
        this.titolo = libro.getTitolo();
        this.autore = libro.getAutore();
        this.genere = libro.getGenere();
        this.valutazione = libro.getValutazione();
        this.stato = libro.getStato();
    }

    public void setStato(Stato stato) { this.stato = stato;}
}
