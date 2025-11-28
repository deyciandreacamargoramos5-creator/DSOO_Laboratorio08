import java.util.*;

public class Usuario {

    protected String nombreUsuario;
    protected String contraseña;
    protected String dni; 
    protected boolean estado; 
    Scanner sc = new Scanner(System.in);

    public Usuario(String nombreUsuario, String contraseña, String dni, boolean estado){
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
        this.dni = dni;
        this.estado = estado;
    }

    public String getNombreUsuario(){
        return nombreUsuario;
    }

    public String getContraseña(){
        return contraseña;
    }

    public String getDni(){
        return dni;
    }

    public boolean esEstado(){
        return estado;
    }

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    // Login simple (3 intentos se manejan afuera)
    public boolean login(String usuario, String clave){
        if(usuario.equals(nombreUsuario) && clave.equals(contraseña) && estado){
            return true;
        }
        return false;
    }

    public void mostrarPermisos(){
        System.out.println("Permisos basicos de usuario");
    }

    public void menu(Banco banco){
        System.out.println("Este usuario no tiene menu asignado");
    }
}
