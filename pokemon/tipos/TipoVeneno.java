package pokemon.tipos;

public class TipoVeneno extends TipoElemento {
    public TipoVeneno() {
        super("Veneno", 60.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Veneno - Fuerza: " + fuerza);
    }
}