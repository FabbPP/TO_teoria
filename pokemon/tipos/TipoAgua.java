package pokemon.tipos;

public class  TipoAgua extends TipoElemento {
    public TipoAgua() {
        super("Agua", 65.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Agua - Fuerza: " + fuerza);
    }
}