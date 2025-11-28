import java.util.ArrayList;
import java.util.Scanner;
public class Menu {
    private Banco banco;
    private ArrayList<Usuario> usuarios;
 
    public Menu(Banco banco) {
        this.banco = banco;
        this.usuarios = new ArrayList<>();
    }
    // Metodos de usuarios 
    public void agregarUsuario(Usuario u) {
        if (u != null) {
            usuarios.add(u);
        }
    }
    public Usuario buscarUsuario(String nombreUsuario) {
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equalsIgnoreCase(nombreUsuario)) {
                return u;
            }
        }
        return null;
    }
    public void mostrarUsuarios() {
        System.out.println("\n=== LISTA DE USUARIOS REGISTRADOS ===");
        for (Usuario u : usuarios) {
            System.out.println("- " + u.getNombreUsuario() + " (" + u.getClass().getSimpleName() + ")");
        }
    }
    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
    // Login y menu principal
    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        Usuario usuarioLogeado;
        while (true) {
            usuarioLogeado = null;
            System.out.println("\n===== INICIAR SESION =====");
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();
            // Buscar usuario
            for (Usuario u : usuarios) {
                if (u.getNombreUsuario().equals(user) && u.getContraseña().equals(pass) && u.esEstado()) {
                    usuarioLogeado = u;
                    break;
                }
            }
            if (usuarioLogeado == null) {
                System.out.println("Usuario o contraseña incorrectos o inactivo. Intente nuevamente.\n");
                continue; // vuelve a pedir login
            }
            System.out.println("\nBienvenido " + usuarioLogeado.getNombreUsuario());
            // Polimorfismo: invoca el menu correspondiente
            usuarioLogeado.menu(banco);
            // Cuando sale del menu, regresa a login
            System.out.println("\nSesion finalizada. Volviendo al login...");
        }
    }
}
