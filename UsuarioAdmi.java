import java.util.*;

public class UsuarioAdmi extends Usuario {

    public UsuarioAdmi(String nombreUsuario, String contrasena, String dni, boolean estado){
        super(nombreUsuario, contrasena, dni, estado);
    }

    @Override
    public void mostrarPermisos(){
        System.out.println("Permisos de administrador:");
        System.out.println("- Gestión total del sistema");
        System.out.println("- Registrar clientes, empleados y cuentas");
        System.out.println("- Eliminar clientes, empleados y cuentas");
        System.out.println("- Consultar listas y transacciones");
    }

    @Override
    public void menu(Banco banco){
        Scanner sc = new Scanner(System.in);
        int opcion;

        do{
            System.out.println("\n========== MENU ADMINISTRADOR ==========");
            System.out.println("===== VISUALIZAR LISTAS =====");
            System.out.println("1. Mostrar clientes");
            System.out.println("2. Mostrar empleados");
            System.out.println("3. Mostrar cuentas");
            System.out.println("4. Mostrar tarjetas");
            System.out.println("5. Mostrar transacciones");
            System.out.println("===== BUSCAR =====");
            System.out.println("6. Buscar empleado por codigo");
            System.out.println("7. Buscar cliente por DNI");
            System.out.println("8. Buscar cuenta por numero");
            System.out.println("9. Buscar transaccion por codigo");
            System.out.println("===== REGISTRAR =====");
            System.out.println("10. Registrar nuevo empleado");
            System.out.println("11. Registrar nuevo cliente");
            System.out.println("12. Registrar nueva cuenta");
            System.out.println("===== ELIMINAR =====");
            System.out.println("13. Eliminar empleado");
            System.out.println("14. Eliminar cliente");
            System.out.println("15. Eliminar cuenta");
            System.out.println("16. Salir");
            System.out.println("========================================");
            System.out.print("Seleccione una opcion: ");

            try {
                opcion = Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                opcion = -1;
                System.out.println("Opcion invalida. Intente nuevamente.");
                continue;
            }

            switch(opcion){
                case 1: banco.mostrarClientes(); break;
                case 2: banco.mostrarEmpleados(); break;
                case 3: banco.mostrarCuentas(); break;
                case 4: banco.mostrarTarjetas(); break;
                case 5: banco.mostrarTransacciones(); break;
                case 6:
                    String codigoEmpleado = leerNumero(sc,"Ingrese codigo del empleado (X para cancelar): ");
                    if(codigoEmpleado != null){
                    Empleado empleadoEncontrado = banco.buscarEmpleado(codigoEmpleado);
                    banco.mostrarEmpleado(empleadoEncontrado);}
                    break;
                case 7:
                    String dniCliente = leerDNI(sc,"Ingrese DNI del cliente (X para cancelar): ");
                    if(dniCliente != null){
                    Cliente clienteEncontrado = banco.buscarCliente(dniCliente);
                    banco.mostrarCliente(clienteEncontrado); }
                    else{
                        System.out.print("Cliente no encontrado");
                    }
                    break;
                case 8:
                    String numeroCuenta = leerNumero(sc,"Ingrese numero de cuenta (X para cancelar): ");
                    if(numeroCuenta != null){
                    Cuenta cuentaEncontrada = banco.buscarCuenta(numeroCuenta);
                    banco.mostrarCuenta(cuentaEncontrada);}
                    else{
                        System.out.print("Cuenta no encontrada");
                    }
                    break;
                case 9:
                    String codigoTransaccion = leerCadena(sc,"Ingrese codigo de transaccion (X para cancelar): ");
                    if(codigoTransaccion != null) {
                        Transaccion transaccionEncontrada = banco.buscarTransaccionPorCodigo(codigoTransaccion);
                        banco.mostrarTransaccion(transaccionEncontrada);
                    }
                    break;
                case 10: registrarEmpleado(sc, banco); break;
                case 11: registrarCliente(sc,banco); break;
                case 12: registrarCuenta(sc,banco); break;
                case 13:
                    String codigoEliminarEmpleado = leerNumero(sc,"Ingrese codigo del empleado a eliminar (X para cancelar): ");
                    if(codigoEliminarEmpleado != null) {
                        if(banco.eliminarEmpleado(codigoEliminarEmpleado)) {
                            System.out.println("Empleado eliminado exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar el empleado.");
                        }
                    }
                    break;
                case 14:
                    String dniEliminarCliente = leerDNI(sc,"Ingrese DNI del cliente a eliminar (X para cancelar): ");
                    if(dniEliminarCliente != null) {
                        if(banco.eliminarCliente(dniEliminarCliente)) {
                            System.out.println("Cliente eliminado exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar el cliente.");
                        }
                    }
                    break;
                case 15:
                    String numeroEliminarCuenta = leerNumero(sc,"Ingrese numero de cuenta a eliminar (X para cancelar): ");
                    if(numeroEliminarCuenta != null) {
                        if(banco.eliminarCuenta(numeroEliminarCuenta)) {
                            System.out.println("Cuenta eliminada exitosamente.");
                        } else {
                            System.out.println("No se pudo eliminar la cuenta.");
                        }
                    }
                    break;
                case 16:
                    System.out.println("Cerrando sesion de administrador...");
                    return;
                default:
                    System.out.println("Opción invalida. Intente nuevamente.");
            }

        } while(opcion != 16);
    }

    // =========== METODOS DE REGISTRO =================

    private void registrarEmpleado(Scanner sc, Banco banco){
        String nombre = leerCadena(sc,"Nombre completo del empleado (X para cancelar): ");
        if(nombre == null) return;

        String dni = leerDNI(sc,"DNI del empleado (X para cancelar): ");
        if(dni == null) return;

        String direccion = leerCadena(sc,"Direccion del empleado (X para cancelar): ");
        if(direccion == null) return;

        String telefono = leerNumero(sc,"Telefono del empleado (X para cancelar): ");
        if(telefono == null) return;

        String codigo = leerNumero(sc,"Codigo del empleado (X para cancelar): ");
        if(codigo == null) return;

        String cargo = leerCadena(sc,"Cargo del empleado (X para cancelar): ");
        if(cargo == null) return;

        double salario = leerDouble(sc,"Salario del empleado (X para cancelar): ");
        if(salario == -1) return;

        Empleado empleado = new Empleado(nombre, dni, direccion, telefono, codigo, cargo, salario);
        banco.registrarEmpleado(empleado);
        System.out.println("Empleado registrado correctamente.");
    }

    private void registrarCliente(Scanner sc, Banco banco){
        String nombre = leerCadena(sc,"Nombre completo del cliente (X para cancelar): ");
        if(nombre == null) return;

        String dni = leerDNI(sc,"DNI del cliente (X para cancelar): ");
        if(dni == null) return;

        String direccion = leerCadena(sc,"Direccion del cliente (X para cancelar): ");
        if(direccion == null) return;

        String telefono = leerNumero(sc,"Telefono del cliente (X para cancelar): ");
        if(telefono == null) return;

        Cliente cliente = new Cliente(nombre, dni, direccion, telefono);
        banco.registrarCliente(cliente);
        System.out.println("Cliente registrado correctamente.");
    }

    private void registrarCuenta(Scanner sc, Banco banco){
        String tipoCuenta = leerCadena(sc,"Tipo de cuenta (Ahorros/Corriente) (X para cancelar): ");
        if(tipoCuenta == null) return;

        String dniTitular = leerNumero(sc,"DNI del titular de la cuenta (X para cancelar): ");
        if(dniTitular == null) return;

        Cliente titular = banco.buscarCliente(dniTitular);
        if(titular == null){
            System.out.println("Cliente no encontrado.");
            return;
        }

        String numeroCuenta = leerNumero(sc,"Numero de cuenta (X para cancelar): ");
        if(numeroCuenta == null) return;

        Cuenta cuenta = new Cuenta(tipoCuenta, titular, numeroCuenta);

        String numeroTarjeta = leerNumero(sc,"Numero de tarjeta (X para cancelar): ");
        if(numeroTarjeta == null) return;

        String tipoTarjeta = leerCadena(sc,"Tipo de tarjeta (X para cancelar): ");
        if(tipoTarjeta == null) return;

        String fechaVencimiento = leerCadena(sc,"Fecha de vencimiento (X para cancelar): ");
        if(fechaVencimiento == null) return;

        double cvvDouble = leerDouble(sc,"CVV de la tarjeta (X para cancelar): ");
        if(cvvDouble == -1) return;
        int cvv = (int) cvvDouble;

        String estadoTarjeta = leerCadena(sc,"Estado de la tarjeta (X para cancelar): ");
        if(estadoTarjeta == null) return;

        Tarjeta tarjeta = new Tarjeta(numeroTarjeta, tipoTarjeta, fechaVencimiento, cvv, estadoTarjeta);
        cuenta.setTarjeta(tarjeta);

        banco.registrarCuenta(cuenta);
        System.out.println("Cuenta registrada correctamente.");
    }

    // ======== METODOS DE LECTURA ================

    private int leerEntero(Scanner sc, String mensaje){
        String entrada;
        while(true){
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if(entrada.equalsIgnoreCase("x")) return -1;
            if(entrada.matches("\\d+")) return Integer.parseInt(entrada);
            System.out.println("Debe ingresar un número válido.");
        }
    }

    private double leerDouble(Scanner sc, String mensaje){
        String entrada;
        while(true){
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if(entrada.equalsIgnoreCase("x")) return -1;
            if(entrada.matches("\\d+(\\.\\d+)?")) return Double.parseDouble(entrada);
            System.out.println("Debe ingresar un numero valido.");
        }
    }

    private String leerCadena(Scanner sc, String mensaje){
        String entrada;
        while(true){
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if(entrada.equalsIgnoreCase("x")) return null;
            if(!entrada.isEmpty()) return entrada;
            System.out.println("No puede estar vacio.");
        }
    }

    private String leerNumero(Scanner sc, String mensaje){
        String entrada;
        while(true){
            System.out.print(mensaje);
            entrada = sc.nextLine().trim();
            if(entrada.equalsIgnoreCase("x")) return null;
            if(entrada.matches("\\d+")) return entrada;
            System.out.println("Debe ingresar solo numeros. Intente nuevamente o X para salir.");
        }
    }

    public static String leerDNI(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String dni = sc.nextLine().trim();
            if (dni.equalsIgnoreCase("X")) return null;
            if (dni.matches("\\d{8}")) {
                return dni;
            }
            System.out.println("DNI invalido. Debe tener exactamente 8 digitos numericos.");
        }
    }
}