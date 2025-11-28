public class Persona {
    private String nombre;
    private String dni;
    private String direccion;
    private String telefono;
    public Persona(String nombre, String dni, String direccion, String telefono) {
        setNombre(nombre);
        setDni(dni);
        setDireccion(direccion);
        setTelefono(telefono);
    } 
    public String getNombre() {
        return nombre;
    }
    public String getDni() {
        return dni;
    }
    public String getDireccion() {
        return direccion;
    }
    public String getTelefono() {
        return telefono;
    }
    // SETTERS con validacion
    // return true = dato correcto
    // return false = dato invalido
    public boolean setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) return false;
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) return false;
        if (nombre.length() < 2) return false;
        this.nombre = nombre;
        return true;
    }
    public boolean setDni(String dni) {
        if (dni == null || dni.trim().isEmpty()) return false;
        if (!dni.matches("\\d+")) return false;
        if (dni.length() != 8) return false;

        this.dni = dni;
        return true;
    }
    public boolean setDireccion(String direccion) {
        if (direccion == null || direccion.trim().isEmpty()) return false;
        // formato simple recomendado:
        // Ej: Av Lima 123
        if (!direccion.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+\\s\\d+")) return false;

        this.direccion = direccion;
        return true;
    }
    public boolean setTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) return false;
        if (!telefono.matches("\\d+")) return false;
        if (telefono.length() != 9) return false;
        if (!telefono.startsWith("9")) return false;//empiece con 9
        this.telefono = telefono;
        return true;
    }
    public String toString() {
        return "\nNombre: " + nombre +
               "\nDNI: " + dni +
               "\nDireccion: " + direccion +
               "\nTelefono: " + telefono;
    }
}
