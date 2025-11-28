import java.util.Scanner;

public class UsuarioEmpleado extends Usuario {
    private Empleado empleado;  // vinculacion al empleado real

    public UsuarioEmpleado(String nombreUsuario, Empleado empleado, String contraseña, boolean estado){
        super(nombreUsuario, contraseña, empleado.getDni(), estado);
        this.empleado = empleado;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("===== PERMISOS DE EMPLEADO =====");
        System.out.println("- Registrar nuevos clientes");
        System.out.println("- Consultar informacion de clientes");
        System.out.println("- Crear cuentas para clientes");
        System.out.println("- Crear tarjetas");
        System.out.println("- Registrar nuevos prestamos");
        System.out.println("- Registrar pagos de prestamos");
        System.out.println("- Ver historial de transacciones");
    }

    @Override
    public void menu(Banco banco) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n========== MENU EMPLEADO ==========");
            System.out.println("1. Registrar cliente");
            System.out.println("2. Consultar cliente por DNI");
            System.out.println("3. Crear cuenta SIN tarjeta");
            System.out.println("4. Crear cuenta CON tarjeta");
            System.out.println("5. Crear tarjeta para cuenta existente");
            System.out.println("6. Ver todas las cuentas");
            System.out.println("7. Solicitar prestamo (NUEVO)");
            System.out.println("8. Registrar pago de prestamo");
            System.out.println("9. Ver historial de transacciones");
            System.out.println("10. Salir");
            System.out.println("===================================");
            System.out.print("Elige una opcion: ");
            opcion = sc.nextInt();
            sc.nextLine();

            switch (opcion) {
                case 1:
                    registrarCliente(sc, banco);
                    break;
                case 2:
                    String dniCliente = leerDNI(sc,"Ingrese DNI del cliente");
                    if(dniCliente != null){
                        Cliente clienteEncontrado = banco.buscarCliente(dniCliente);
                        banco.mostrarCliente(clienteEncontrado);
                    } else {
                        System.out.println("Cliente no encontrado");
                    }
                    break;
                case 3:
                    crearCuentaSinTarjeta(sc, banco);
                    break;
                case 4:
                    crearCuentaConTarjeta(sc, banco);
                    break;
                case 5:
                    crearTarjetaParaCuenta(sc, banco);
                    break;
                case 6:
                    banco.mostrarCuentas();
                    break;
                case 7:
                    registrarPrestamo(sc, banco);
                    break;
                case 8:
                    registrarPagoPrestamo(sc, banco);
                    break;
                case 9:
                    banco.mostrarTransacciones();
                    break;
                case 10:
                    System.out.println("Saliendo del menu de empleado...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while(opcion != 10);
    }

    // =========== MÉTODOS AUXILIARES =================
    public static void registrarCliente(Scanner sc, Banco banco) {
        String nombre = leerCadena(sc, "Nombre cliente");
        if(nombre == null) return;
        String dni = leerDNI(sc, "DNI cliente");
        if(dni == null) return;
        String direccion = leerCadena(sc, "Direccion cliente (Av Lima 123)");
        if(direccion == null) return;
        String telefono = leerCadena(sc, "Telefono cliente");
        if(telefono == null) return;

        Cliente nuevo = new Cliente(nombre, dni, direccion, telefono);
        banco.registrarCliente(nuevo);
        System.out.println("Cliente registrado correctamente.");
    }

    public static void crearCuentaSinTarjeta(Scanner sc, Banco banco) {
        while (true) {
            String dniTitular = leerDNI(sc, "DNI del cliente titular");
            if(dniTitular == null) return;
            Cliente titular = banco.buscarCliente(dniTitular);
            if(titular == null) {
                System.out.println("Cliente no encontrado. Intente nuevamente o X para salir.");
                continue;
            }
            String tipo = leerCadena(sc, "Tipo de cuenta (Ahorros/Corriente)");
            if(tipo == null) return;
            String numeroCuenta = leerCadena(sc, "Numero de cuenta (10 digitos)");
            if(numeroCuenta == null) return;

            banco.crearCuentaParaCliente(titular, tipo, numeroCuenta);
            System.out.println("Cuenta creada correctamente SIN tarjeta.");
            return;
        }
    }

    public static void crearCuentaConTarjeta(Scanner sc, Banco banco) {
        while (true) {
            String dniTitular = leerDNI(sc, "DNI del cliente titular");
            if(dniTitular == null) return;
            Cliente titular = banco.buscarCliente(dniTitular);
            if(titular == null) {
                System.out.println("Cliente no encontrado. Intente nuevamente o X para salir.");
                continue;
            }
            String tipo = leerCadena(sc, "Tipo de cuenta (Ahorros/Corriente)");
            if(tipo == null) return;
            String numeroCuenta = leerCadena(sc, "Numero de cuenta (10 digitos)");
            if(numeroCuenta == null) return;

            // Crear la cuenta primero
            if(!banco.crearCuentaParaCliente(titular, tipo, numeroCuenta)) {
                System.out.println("No se pudo crear la cuenta.");
                return;
            }

            Cuenta cuentaCreada = banco.buscarCuenta(numeroCuenta);
            if(cuentaCreada == null) {
                System.out.println("Error al recuperar la cuenta creada.");
                return;
            }

            // Ahora crear la tarjeta
            String numeroTarjeta = leerCadena(sc, "Numero de tarjeta (16 digitos)");
            if(numeroTarjeta == null) return;
            String tipoTarjeta = leerCadena(sc, "Tipo de tarjeta (Debito/Credito)");
            if(tipoTarjeta == null) return;
            String fechaVencimiento = leerCadena(sc, "Fecha de vencimiento (MM/AA)");
            if(fechaVencimiento == null) return;
            int cvv = leerEntero(sc, "CVV (3 digitos)");
            if(cvv == -1) return;
            String estadoTarjeta = leerCadena(sc, "Estado de la tarjeta (ACTIVA/INACTIVA/BLOQUEADA)");
            if(estadoTarjeta == null) return;

            Tarjeta tarjeta = new Tarjeta(numeroTarjeta, tipoTarjeta, fechaVencimiento, cvv, estadoTarjeta);
            
            if(banco.registrarTarjeta(tarjeta)) {
                if(cuentaCreada.setTarjeta(tarjeta)) {
                    System.out.println("Cuenta creada CON tarjeta exitosamente.");
                } else {
                    System.out.println("Cuenta creada pero no se pudo asignar la tarjeta.");
                }
            } else {
                System.out.println("Cuenta creada pero tarjeta rechazada.");
            }
            return;
        }
    }

    public static void crearTarjetaParaCuenta(Scanner sc, Banco banco) {
        while (true) {
            String numeroCuenta = leerCadena(sc, "Numero de cuenta");
            if(numeroCuenta == null) return;

            Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
            if(cuenta == null) {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
                continue;
            }

            if(cuenta.getTarjeta() != null) {
                System.out.println("Esta cuenta ya tiene una tarjeta asignada.");
                return;
            }

            String numeroTarjeta = leerCadena(sc, "Numero de tarjeta (16 digitos)");
            if(numeroTarjeta == null) return;
            String tipoTarjeta = leerCadena(sc, "Tipo de tarjeta (Debito/Credito)");
            if(tipoTarjeta == null) return;
            String fechaVencimiento = leerCadena(sc, "Fecha de vencimiento (MM/AA)");
            if(fechaVencimiento == null) return;
            int cvv = leerEntero(sc, "CVV (3 digitos)");
            if(cvv == -1) return;
            String estadoTarjeta = leerCadena(sc, "Estado de la tarjeta (ACTIVA/INACTIVA/BLOQUEADA)");
            if(estadoTarjeta == null) return;

            Tarjeta tarjeta = new Tarjeta(numeroTarjeta, tipoTarjeta, fechaVencimiento, cvv, estadoTarjeta);
            
            if(banco.registrarTarjeta(tarjeta)) {
                if(cuenta.setTarjeta(tarjeta)) {
                    System.out.println("Tarjeta creada y asignada exitosamente a la cuenta.");
                } else {
                    System.out.println("Tarjeta creada pero no se pudo asignar a la cuenta.");
                }
            } else {
                System.out.println("Tarjeta rechazada.");
            }
            return;
        }
    }

    public static void registrarPrestamo(Scanner sc, Banco banco) {
        String fecha;
        while (true) {
            fecha = leerCadena(sc, "Fecha de registro (dd/MM/yyyy) o X para salir");
            if (fecha == null) return;

            // Validar formato dd/MM/yyyy
            if (fecha.matches("^\\d{2}/\\d{2}/\\d{4}$")) {
                break;
            } else {
                System.out.println("Formato incorrecto. Debe ser dd/MM/yyyy. Intente nuevamente o X para salir.");
            }
        }
        while(true) {
            String numeroCuenta = leerCadena(sc, "Numero de cuenta asociada");
            if(numeroCuenta == null) return;

            Cuenta cuenta = banco.buscarCuenta(numeroCuenta);
            if(cuenta == null) {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
                continue;
            }

            Cliente cliente = cuenta.getTitular();
            if(cliente == null) {
                System.out.println("La cuenta no tiene cliente asociado.");
                return;
            }

            double monto = leerMonto(sc, "Monto del prestamo");
            if(monto < 0) return;

            double tasaAnual = leerMonto(sc, "Tasa anual (%)");
            if(tasaAnual < 0) return;

            int meses;
            while(true) {
                String mesesTxt = leerCadena(sc, "Cantidad de meses");
                if(mesesTxt == null) return;

                if(mesesTxt.matches("\\d+")) {
                    meses = Integer.parseInt(mesesTxt);
                    break;
                } else {
                    System.out.println("Debe ser entero positivo. Intente nuevamente o X para salir.");
                }
            }

            double interes = monto * (tasaAnual / 12 / 100);
            double capital = monto;

            PagoPrestamo nuevoPrestamo = new PagoPrestamo(
                monto, interes, capital, meses, tasaAnual, cuenta
            );
            
            //REGISTRAR PRESTAMO usando banco
            banco.registrarPrestamo(nuevoPrestamo, cliente);

            //MOSTRAR BOLETA (Banco lo maneja)
            banco.mostrarBoletaPrestamo(nuevoPrestamo);

            return;
        }
    }

    public static void registrarPagoPrestamo(Scanner sc, Banco banco) {
        System.out.println("\n=== REGISTRAR PAGO DE PRESTAMO ===");
        
        String dniCliente = leerDNI(sc, "DNI del cliente");
        if(dniCliente == null) return;
        
        Cliente cliente = banco.buscarCliente(dniCliente);
        if(cliente == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        if(cliente.getPrestamos().isEmpty()) {
            System.out.println("Este cliente no tiene prestamos registrados.");
            return;
        }

        // Mostrar prestamos del cliente
        System.out.println("\nPrestamos de " + cliente.getNombre() + ":");
        for(PagoPrestamo pr : cliente.getPrestamos()) {
            System.out.println("Codigo: " + pr.getCodigo() +
                   " | Monto restante: " + (pr.getCapital() + pr.getInteres()) +
                   " | Cuota mensual: " + pr.getCuotaMensual());
        }

        // Seleccionar prestamo
        PagoPrestamo prestamoSeleccionado = null;
        while(true) {
            String cod = leerCadena(sc, "Codigo del prestamo a pagar");
            if(cod == null) return;

            for(PagoPrestamo pr : cliente.getPrestamos()) {
                if(pr.getCodigo().equalsIgnoreCase(cod)) {
                    prestamoSeleccionado = pr;
                    break;
                }
            }

            if(prestamoSeleccionado == null) {
                System.out.println("Prestamo no encontrado. Intente nuevamente o X para salir.");
            } else {
                break;
            }
        }

        // Mostrar cuentas del cliente
        if(cliente.getCuentas().isEmpty()) {
            System.out.println("Este cliente no tiene cuentas para realizar el pago.");
            return;
        }

        System.out.println("\nCuentas disponibles:");
        for(int i = 0; i < cliente.getCuentas().size(); i++) {
            Cuenta c = cliente.getCuentas().get(i);
            System.out.println((i + 1) + ". " + c.getNumeroCuenta() + " | Saldo: " + c.getSaldo());
        }

        // Seleccionar cuenta
        Cuenta cuentaPago = null;
        while(true) {
            int opcion = leerEntero(sc, "Selecciona la cuenta desde la que pagar (numero)");
            if(opcion == -1) return;

            if(opcion >= 1 && opcion <= cliente.getCuentas().size()) {
                cuentaPago = cliente.getCuentas().get(opcion - 1);
                break;
            } else {
                System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }

        // Realizar el pago
        if(banco.realizarPagoPrestamo(prestamoSeleccionado, cuentaPago)) {
            System.out.println("Pago realizado correctamente.");
        } else {
            System.out.println("No se pudo realizar el pago.");
        }
    }

    // ============ METODOS DE UTILIDAD =================
    public static String leerCadena(Scanner sc, String mensaje) {
        String entrada;
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            entrada = sc.nextLine().trim();
            if(entrada.equalsIgnoreCase("X")) return null;
            if(!entrada.isEmpty()) return entrada;
            System.out.println("No puede estar vacio.");
        }
    }

    public static double leerMonto(Scanner sc, String mensaje) {
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String s = sc.nextLine().trim();
            if(s.equalsIgnoreCase("X")) return -1;
            if(s.matches("\\d+(\\.\\d+)?")) return Double.parseDouble(s);
            System.out.println("Monto invalido, intente de nuevo.");
        }
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String s = sc.nextLine().trim();
            if(s.equalsIgnoreCase("X")) return -1;
            if(s.matches("\\d+")) return Integer.parseInt(s);
            System.out.println("Número invalido, intente de nuevo.");
        }
    }

    public static String leerDNI(Scanner sc, String mensaje) {
        while (true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String dni = sc.nextLine().trim();
            if (dni.equalsIgnoreCase("X")) return null;
            if (dni.matches("\\d{8}")) {// Debe ser exactamente 8 digitos
                return dni;
            }
            System.out.println("DNI invalido. Debe tener exactamente 8 digitos numericos.");
        }
    }
}