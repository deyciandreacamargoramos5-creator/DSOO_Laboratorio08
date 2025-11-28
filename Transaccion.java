package DSOO_Laboratorio08;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public abstract class Transaccion {

    private static int contadorTransacciones = 0; // contador unico global para todas las transacciones

    private String codigo;    // codigo unico TR-1, TR-2, TR-3...
    private double monto;
    private String fecha;     // fecha de creacion
    private String hora;      // hora de creacion
    private String tipo;
    private String estado;

    protected Cuenta cuenta;        // cuenta asociada a la transaccion

    public Transaccion(double monto, String tipo, Cuenta cuenta) {
        contadorTransacciones++;
        this.codigo = "TR-" + contadorTransacciones;

        this.monto = monto > 0 ? monto : 0.0;

        this.fecha = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.hora  = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        this.tipo = (tipo != null && !tipo.trim().isEmpty()) ? tipo.trim() : "DESCONOCIDO";
        this.estado = "Pendiente";

        if (cuenta != null) {
            this.cuenta = cuenta;
        }
    }

    // -------------------------
    // GETTERS
    // -------------------------
    public String getCodigo() { return codigo; }
    public double getMonto() { return monto; }
    public String getFecha() { return fecha; }
    public String getHora() { return hora; }
    public String getTipo() { return tipo; }
    public String getEstado() { return estado; }
    public Cuenta getCuenta() { return cuenta; }

    // -------------------------
    // SETTERS
    // -------------------------
    public boolean setMonto(double monto) {
        if (monto <= 0) return false;
        this.monto = monto;
        return true;
    }

    public boolean setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return false;
        this.tipo = tipo.trim();
        return true;
    }

    public boolean setEstado(String estado) {
        if (estado == null || estado.trim().isEmpty()) return false;
        this.estado = estado.trim();
        return true;
    }

    public boolean setCuenta(Cuenta cuenta) {
        if (cuenta == null) return false;
        this.cuenta = cuenta;
        return true;
    }

    // Metodo abstracto que procesara la transaccion
    public abstract boolean procesar();

    // toString para imprimir boleta de transaccion
    @Override
    public String toString() {
        return "Transaccion{" +
                "codigo='" + codigo + '\'' +
                ", tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", estado='" + estado + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", cuenta=" + (cuenta != null ? cuenta.getNumeroCuenta() : "No asignada") +
                '}';
    }
}
