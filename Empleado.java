public class Empleado extends Persona {

    private String codigo;   // del empleado
    private String cargo;    // Gerente, Cajero
    private double salario;

    public Empleado(String nombre, String dni, String direccion, String telefono,
                    String codigo, String cargo, double salario) {

        super(nombre, dni, direccion, telefono);

        setCodigo(codigo);   // // si no es valido, quedara null
        setCargo(cargo);     // // validacion simple
        setSalario(salario); // // validacion de monto positivo
    }

    public String getCodigo() {
        return codigo;
    }

    public String getCargo() {
        return cargo;
    }

    public double getSalario() {
        return salario;
    }

    // SETTERS con validacion
    public boolean setCodigo(String codigo) {
        if (codigo == null) return false;
        if (!codigo.matches("\\d{4}")) return false; // exactamente 4 digitos
        this.codigo = codigo;
        return true;
    }

    public boolean setCargo(String cargo) {
        if (cargo == null || cargo.trim().isEmpty()) return false;
        if (!cargo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) return false;
        this.cargo = cargo;
        return true;
    }

    public boolean setSalario(double salario) {
        if (salario <= 0) return false;
        this.salario = salario;
        return true;
    }

    public String toString() {
        return super.toString()
            + "\nCodigo: " + codigo
            + "\nCargo: " + cargo
            + "\nSalario: " + salario;
    }
}
