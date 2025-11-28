import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Transaccion {
    private String id;
    private double monto;
    private String fecha;
    private String hora;
    private String tipo;
    private String estado;

    public Transaccion(String id, double monto, String fecha, String hora, String tipo, String estado) {
        this.id = id;
        this.monto = monto;
        this.fecha = fecha;
        this.hora = hora;
        this.tipo = tipo;
        this.estado = estado;
    }

    public String getId() { 
        return id; }
    public double getMonto() { 
        return monto; }
    public String getFecha() { 
        return fecha; }
    public void setFecha(String fechaEmision){
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate fecha=LocalDate.parse(fechaEmision, formato);
            if (fecha. isAfter (LocalDate.now())){
                System.out.println("La fecha no puede ser futura");
            }else{
                this. fecha= fechaEmision;
                System.out. println(" Fecha válida y asignada");
            }
        } catch (DateTimeParseException e) {
            System.out.println("Formato o fecha inválida (usa dd/MM/yyyy)");
        }
    }
    public String getHora() { 
        return hora; }
    public String getTipo() { 
        return tipo; }
    public String getEstado() { 
        return estado; }
    
    public void setEstado(String estado) { 
        this.estado = estado; }

    public void procesar() {
        this.estado = "Procesada";
        System.out.println("Transaccion " + id + " procesada (generica).");
    }

    @Override
    public String toString() {
        return "\n--->Transaccion<---\nId: " + id + "\nMonto: " + monto + "\nFecha: " + fecha + "\nHora: " + hora +"\nTipo: " + tipo + "\nEstado:" + estado;
    }
}
