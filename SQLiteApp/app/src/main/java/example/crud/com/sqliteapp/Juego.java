package example.crud.com.sqliteapp;

public class Juego {

    private int id;
    private String titulo;
    private String genero;
    private int valor;

    public Juego(){}

    public Juego(String title, String author) {
        super();
        this.titulo = titulo;
        this.genero = genero;
        this.valor = valor;
    }

    //getters & setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }

    public int getValor() {
        return valor;
    }
    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Juego [id=" + id + ", título=" + titulo + ", género=" + genero + ", valor=" + valor + "]";
    }
}
