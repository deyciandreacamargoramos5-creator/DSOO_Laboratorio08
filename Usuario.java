public class Usuario {
    protected String nombreUsuario;
    protected String dni;
    protected int edad;
    protected String contraseña;
    protected String correo;
    protected boolean estado; 
    // constructor
    public Usuario(String nombreUsuario, String dni,int edad,String contraseña, String correo,boolean estado) {
        setNombreUsuario(nombreUsuario);
        setDni(dni);
        setEdad(edad);
        setContraseña(contraseña);
        setCorreo(correo);
        setEstado(estado);
    }
    // getters y setters NombreUsuario
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    // getters y setters Dni
    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni=dni;
    }
    // getters y setters Edad
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad=edad;
    }
    // getters y setters Contraseña
    public String getContraseña() {
        return contraseña;
    }
    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    // getters y setters Correo
    public String getCorreo() {
        return correo;
    }
    public void setCorreo(String correo) {
        this.correo=correo;
    }
    // getters y setters Estado
    public boolean isEstado() {
        return estado;
    }
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
    // metodo login
    public boolean login(String usuario, String contraseña) {
        if (this.nombreUsuario.equals(usuario) && this.contraseña.equals(contraseña) && estado) {
            System.out.println("Inicio de sesión exitoso");
            return true;
        } else {
            System.out.println("Error en el inicio de sesión. Verifique sus datos o estado");
            return false;
        }
    }
    // metodo para mostrar los datos
    public void mostrarDatos() {
        System.out.println("=== DATOS DEL USUARIO ===");
        System.out.println("Usuario: " + nombreUsuario);
        System.out.println("Edad: "+edad);
        System.out.println("DNI: "+dni);
        System.out.println("Correo: "+correo);
        System.out.println("Contraseña: " + contraseña);
        System.out.println("Estado: " + estado);
    }
    // metodo para los permisos del usuario 
    public void mostrarPermisos() {
        System.out.println("Permisos generales de usuario");
    }
    public void menu (Banco banco) {
    System.out.println("Este usuario no tiene un menú interactivo definido.");
}
}
