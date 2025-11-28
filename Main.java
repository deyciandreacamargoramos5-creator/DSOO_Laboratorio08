import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // ---- Crear banco  
        Banco banco = new Banco("12345678901","Banco San Pedro");

        System.out.println("========================================");
        System.out.println("    INICIALIZANDO SISTEMA BANCARIO");
        System.out.println("========================================");
         
        // Crear clientes y registrarlos en el banco 
        Cliente c1 = new Cliente("Juan Perez", "74259631", "Av Lima 123", "987654321");
        Cliente c2 = new Cliente("Maria Rodriguez", "85142796", "Jr Arequipa 450", "912345678");
        banco.registrarCliente(c1);
        banco.registrarCliente(c2);

        // Crear cuentas y asociarlas a los clientes
        banco.crearCuentaParaCliente(c1, "Ahorros", "0010000001");
        banco.crearCuentaParaCliente(c2, "Corriente", "0010000002");

        // Crear empleados y registrarlos en el banco (SIN PUNTO EN LA DIRECCION)
        Empleado e1 = new Empleado("Pedro Lopez", "12345678", "Av Sucre 45", "912345679",
                                   "1001", "Cajero", 2000);
        Empleado e2 = new Empleado("Ana Garcia", "87654321", "Jr Union 200", "923456789",
                                   "1002", "Gerente", 3500);
        banco.registrarEmpleado(e1);
        banco.registrarEmpleado(e2);
        
        System.out.println("\n*** Sistema inicializado correctamente ***\n");

        // Crear usuarios
        Usuario uCliente = new UsuarioCliente("juan123", c1, "1234", c1.getDni(), true);
        Usuario uEmpleado = new UsuarioEmpleado("pedro234", e1, "4321", true);
        Usuario uAdmin = new UsuarioAdmi("admin", "9999", "00000000", true);

        // Crear menu y agregar usuarios 
        Menu menu = new Menu(banco);
        menu.agregarUsuario(uCliente);
        menu.agregarUsuario(uEmpleado);
        menu.agregarUsuario(uAdmin);

        // Logica de login
        while (true) { // siempre regresa a pedir usuario y contraseña
            System.out.println("\n===== SISTEMA BANCARIO =====");
            System.out.println("===== INICIAR SESION =====");
            System.out.print("Usuario: ");
            String user = sc.nextLine();
            System.out.print("Contraseña: ");
            String pass = sc.nextLine();

            Usuario usuarioLogeado = menu.buscarUsuario(user);

            if (usuarioLogeado == null || !usuarioLogeado.getContraseña().equals(pass)) {
                System.out.println("Usuario o contraseña incorrectos.");
                continue;
            }

            if (!usuarioLogeado.esEstado()) {
                System.out.println("Usuario inactivo.");
                continue;
            }

            System.out.println("\n========================================");
            System.out.println("Bienvenido " + usuarioLogeado.getNombreUsuario());
            System.out.println("========================================");

            // Invoca el menu polimorfico segun el tipo de usuario
            usuarioLogeado.menu(banco);
        }
    }
}