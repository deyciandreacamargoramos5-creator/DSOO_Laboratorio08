import java.util.Scanner;

public class UsuarioCliente extends Usuario {
    private Cliente cliente;

    public UsuarioCliente(String nombreUsuario, Cliente cliente, String contraseña, String dni, boolean estado) {
        super(nombreUsuario, contraseña, dni, estado);
        this.cliente = cliente;
    }

    @Override
    public void mostrarPermisos() {
        System.out.println("=== PERMISOS DEL CLIENTE BANCARIO ===");
        System.out.println("- Consultar saldo");
        System.out.println("- Consultar datos de cuenta");
        System.out.println("- Consultar transacciones");
        System.out.println("- Realizar depósitos");
        System.out.println("- Realizar retiros");
        System.out.println("- Realizar pago de préstamo");
    }

    @Override
    public void menu(Banco banco) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n=== MENU CLIENTE: " + cliente.getNombre() + " ===");
            System.out.println("1. Consultar saldo");
            System.out.println("2. Consultar datos de cuenta");
            System.out.println("3. Consultar transacciones");
            System.out.println("4. Depositar");
            System.out.println("5. Retirar");
            System.out.println("6. Pago de prestamo");
            System.out.println("7. Salir");
            opcion = leerEntero(sc, "Opcion");

            switch (opcion) {
                case 1:
                    consultarSaldo(sc, banco);
                    break;
                case 2:
                    consultarDatos(sc, banco);
                    break;
                case 3:
                    consultarTransacciones(sc, banco);
                    break;
                case 4:
                    realizarDeposito(sc, banco);
                    break;
                case 5:
                    realizarRetiro(sc, banco);
                    break;
                case 6:
                    realizarPagoPrestamo(sc, banco);
                    break;
                case 7:
                    System.out.println("Saliendo del menu cliente...");
                    break;
                default:
                    System.out.println("Opcion invalida.");
            }
        } while(opcion != 7);
    }

    private void consultarSaldo(Scanner sc, Banco banco) {
        while(true) {
            String nc = leerCadena(sc, "Numero de cuenta");
            if(nc == null) return;
            Cuenta c = banco.buscarCuenta(nc);
            if(c != null) {
                System.out.println("Saldo actual: " + c.getSaldo());
                return;
            } else {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
            }
        }
    }

    private void consultarDatos(Scanner sc, Banco banco) {
        while(true) {
            String nc = leerCadena(sc, "Numero de cuenta");
            if(nc == null) return;
            Cuenta c = banco.buscarCuenta(nc);
            if(c != null) {
                System.out.println(c.toString());
                return;
            } else {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
            }
        }
    }

    private void consultarTransacciones(Scanner sc, Banco banco) {
        while(true) {
            String cod = leerCadena(sc, "Codigo de transaccion");
            if(cod == null) return;
            Transaccion t = banco.buscarTransaccionPorCodigo(cod);
            if(t != null) {
                System.out.println(t.toString());
                return;
            } else {
                System.out.println("Transaccion no encontrada. Intente nuevamente o X para salir.");
            }
        }
    }

    private void realizarDeposito(Scanner sc, Banco banco) {
        while(true) {
            String nc = leerCadena(sc, "Numero de cuenta destino");
            if(nc == null) return;

            Cuenta c = banco.buscarCuenta(nc);
            if(c == null) {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
                continue;
            }

            double monto = leerMonto(sc, "Monto a depositar");
            if(monto < 0) return;

            if(banco.realizarDeposito(c, monto, null)) {
                System.out.println("Deposito exitoso. Saldo: " + c.getSaldo());
            } else {
                System.out.println("No se pudo realizar el depósito.");
            }
            return; // Depósito hecho, salir del método
        }
    }

    private void realizarRetiro(Scanner sc, Banco banco) {
        while(true) {
            String nc = leerCadena(sc, "Numero de cuenta");
            if(nc == null) return;

            Cuenta c = banco.buscarCuenta(nc);
            if(c == null) {
                System.out.println("Cuenta no encontrada. Intente nuevamente o X para salir.");
                continue;
            }

            double monto = leerMonto(sc, "Monto a retirar");
            if(monto < 0) return;

            if(monto > c.getSaldo()) {
                System.out.println("Saldo insuficiente. Intente nuevamente o X para salir.");
                continue;
            }

            if(banco.realizarRetiro(c, monto)) {
                System.out.println("Retiro exitoso. Saldo: " + c.getSaldo());
            } else {
                System.out.println("No se pudo realizar el retiro.");
            }
            return;
        }
    }

    private void realizarPagoPrestamo(Scanner sc, Banco banco) {
        if (cliente.getPrestamos().isEmpty()) {
            System.out.println("No tienes prestamos registrados.");
            return;
        }

        // Mostrar lista de préstamos del cliente
        System.out.println("Prestamos de " + cliente.getNombre() + ":");
        for (PagoPrestamo pr : cliente.getPrestamos()) {
            System.out.println("Codigo: " + pr.getCodigo() +
                   " | Monto restante: " + (pr.getCapital() + pr.getInteres()) +
                   " | Cuota mensual: " + pr.getCuotaMensual());
        }

        // Seleccionar préstamo
        PagoPrestamo prestamoSeleccionado = null;
        while (true) {
            String cod = leerCadena(sc, "Codigo del prestamo a pagar");
            if (cod == null) return;

            for (PagoPrestamo pr : cliente.getPrestamos()) {
                if (pr.getCodigo().equalsIgnoreCase(cod)) {
                    prestamoSeleccionado = pr;
                    break;
                }
            }

            if (prestamoSeleccionado == null) {
                System.out.println("Prestamo no encontrado. Intente nuevamente o X para salir.");
            } else {
                break;
            }
        }

        // Mostrar cuentas del cliente
        if (cliente.getCuentas().isEmpty()) {
            System.out.println("No tienes cuentas para realizar el pago.");
            return;
        }

        System.out.println("Cuentas disponibles:");
        for (int i = 0; i < cliente.getCuentas().size(); i++) {
            Cuenta c = cliente.getCuentas().get(i);
            System.out.println((i + 1) + ". " + c.getNumeroCuenta() + " | Saldo: " + c.getSaldo());
        }

        // Seleccionar cuenta
        Cuenta cuentaPago = null;
        while (true) {
            int opcion = leerEntero(sc, "Selecciona la cuenta desde la que pagar");
            if (opcion == -1) return;

            if (opcion >= 1 && opcion <= cliente.getCuentas().size()) {
                cuentaPago = cliente.getCuentas().get(opcion - 1);
                break;
            } else {
                System.out.println("Opcion invalida. Intente nuevamente.");
            }
        }

        // Llamar al método de Banco con ambos parametros
        if (banco.realizarPagoPrestamo(prestamoSeleccionado, cuentaPago)) {
            System.out.println("Pago realizado correctamente.");
        } else {
            System.out.println("No se pudo realizar el pago.");
        }
    }


    // ============== METODOS ESTATICOS ==================
    public static String leerCadena(Scanner sc, String mensaje) {
        String entrada;
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            entrada = sc.nextLine();
            if(entrada.equalsIgnoreCase("X")) return null;
            if(!entrada.trim().isEmpty()) return entrada;
            System.out.println("No puede estar vacio.");
        }
    }

    public static double leerMonto(Scanner sc, String mensaje) {
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("X")) return -1;
            if(s.matches("\\d+(\\.\\d+)?")) return Double.parseDouble(s);
            System.out.println("Monto invalido, intente de nuevo.");
        }
    }

    public static int leerEntero(Scanner sc, String mensaje) {
        while(true) {
            System.out.print(mensaje + " (X para cancelar): ");
            String s = sc.nextLine();
            if(s.equalsIgnoreCase("X")) return -1;
            if(s.matches("\\d+")) return Integer.parseInt(s);
            System.out.println("Número invalido, intente de nuevo.");
        }
    }
}
