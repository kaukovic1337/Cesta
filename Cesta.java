// Osnovni razred Cesta
public abstract class Cesta {
    private String ime;
    private double dolzina;
    private int stevilka;

    public Cesta(String ime, double dolzina, int stevilka) {
        this.ime = ime;
        this.dolzina = dolzina;
        this.stevilka = stevilka;
    }

    public String getIme() {
        return ime;
    }

    public double getDolzina() {
        return dolzina;
    }

    public int getStevilka() {
        return stevilka;
    }

    public abstract String getVrsta();

    @Override
    public String toString() {
        return ime + " (" + dolzina + " km, št. " + stevilka + ")";
    }
}