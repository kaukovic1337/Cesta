// Podrazred Avtocesta
public class Avtocesta extends Cesta {
    private int maxHitrost;

    public Avtocesta(String ime, double dolzina, int stevilka, int maxHitrost) {
        super(ime, dolzina, stevilka);
        this.maxHitrost = maxHitrost;
    }

    public int getMaxHitrost() {
        return maxHitrost;
    }

    @Override
    public String getVrsta() {
        return "Avtocesta";
    }
}
