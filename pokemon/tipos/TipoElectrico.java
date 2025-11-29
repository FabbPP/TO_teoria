package pokemon.tipos;

public class  TipoElectrico extends TipoElemento {
    public TipoElectrico() {
        super("Electrico", 80.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Electrico - Fuerza: " + fuerza);
    }
}