package pokemon.tipos;

public class  TipoFuego extends TipoElemento {
    public TipoFuego() {
        super("Fuego", 75.0);
    }
    
    @Override
    public void mostrarInfo() {
        System.out.println("Tipo Fuego - Fuerza: " + fuerza);
    }
}