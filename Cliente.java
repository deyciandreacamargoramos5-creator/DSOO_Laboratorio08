import java.util.*;

public class Cliente extends Persona {

    private ArrayList<Cuenta> cuentas;
    private List<PagoPrestamo> prestamos;
    private List<Transaccion> transacciones;


    public Cliente(String nombre, String dni, String direccion, String telefono) {
        super(nombre, dni, direccion, telefono);
        this.cuentas = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.transacciones = new ArrayList<>();
    }

    public List<PagoPrestamo> getPrestamos() {
        return prestamos;
    }

    public void agregarPrestamo(PagoPrestamo p) {
        prestamos.add(p);
    }

    public void eliminarPrestamo(PagoPrestamo p) {
        prestamos.remove(p);
    }

    // // agregar una cuenta al cliente
    public boolean agregarCuenta(Cuenta cuenta) {
        if (cuenta == null) return false;
        cuentas.add(cuenta);
        return true;
    }

    // devolver todas las cuentas
    public ArrayList<Cuenta> getCuentas() {
        return cuentas;
    }

    public List<Transaccion> getTransacciones() { 
    return transacciones; 
    }

    // mostrar datos del cliente + sus cuentas
    public String toString() {
        return super.toString() + "\nCantidad de cuentas: " + cuentas.size();
    }
}
