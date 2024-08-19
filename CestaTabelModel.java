import javax.swing.table.DefaultTableModel;

public class CestaTableModel extends DefaultTableModel {
    private static final String[] COLUMN_NAMES = {"Ime", "Dolžina (km)", "Številka", "Vrsta", "Dodatne Informacije"};
    private Cesta[] cestniSeznam;

    public CestaTableModel() {
        super(COLUMN_NAMES, 0);
        cestniSeznam = new Cesta[0];
    }

    public void addCesta(Cesta cesta) {
        Object[] rowData = new Object[5];
        rowData[0] = cesta.getIme();
        rowData[1] = cesta.getDolzina();
        rowData[2] = cesta.getStevilka();
        rowData[3] = cesta.getVrsta();
        rowData[4] = cesta instanceof Avtocesta ? "Max Hitrost: " + ((Avtocesta) cesta).getMaxHitrost() :
                      cesta instanceof KmeckaCesta ? "Asfaltirana: " + ((KmeckaCesta) cesta).isAsfaltirana() : "";

        addRow(rowData);
    }

    public void updateCesta(int rowIndex, Cesta cesta) {
        if (rowIndex >= 0 && rowIndex < getRowCount()) {
            setValueAt(cesta.getIme(), rowIndex, 0);
            setValueAt(cesta.getDolzina(), rowIndex, 1);
            setValueAt(cesta.getStevilka(), rowIndex, 2);
            setValueAt(cesta.getVrsta(), rowIndex, 3);
            setValueAt(cesta instanceof Avtocesta ? "Max Hitrost: " + ((Avtocesta) cesta).getMaxHitrost() :
                      cesta instanceof KmeckaCesta ? "Asfaltirana: " + ((KmeckaCesta) cesta).isAsfaltirana() : "", rowIndex, 4);
        }
    }
}