// Podrazred KmeckaCesta
public class KmeckaCesta extends Cesta {
    private boolean jeAsfaltirana;

    public KmeckaCesta(String ime, double dolzina, int stevilka, boolean jeAsfaltirana) {
        super(ime, dolzina, stevilka);
        this.jeAsfaltirana = jeAsfaltirana;
    }

    public boolean isAsfaltirana() {
        return jeAsfaltirana;
    }

    @Override
    public String getVrsta() {
        return "Kmečka cesta";
    }
}
