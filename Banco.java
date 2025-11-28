import java.util.*;

public class Banco {

    private String ruc;
    private String nombreBanco;

    // Listas 
    private  ArrayList<Cliente> clientes;
    private  ArrayList<Empleado> empleados;
    private  ArrayList<Cuenta> cuentas;
    private  ArrayList<Transaccion> transacciones;
    private  ArrayList<PagoPrestamo> prestamos;
    private  ArrayList<Tarjeta> tarjetas;

    public Banco(String ruc, String nombreBanco) {
        this.clientes = new ArrayList<>();
        this.empleados = new ArrayList<>();
        this.cuentas = new ArrayList<>();
        this.transacciones = new ArrayList<>();
        this.prestamos = new ArrayList<>();
        this.tarjetas = new ArrayList<>();

        this.ruc = ruc;
        this.nombreBanco = nombreBanco;
    }

    // BANCO(get/set) 
    public String getRuc() { return ruc; }
    public String getNombreBanco() { return nombreBanco; }

    public boolean setRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{11}")) return false;
        this.ruc = ruc;
        return true;
    }

    public boolean setNombreBanco(String nombreBanco) {
        if (nombreBanco == null || nombreBanco.trim().length() < 3) return false;
        if (!nombreBanco.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) return false;
        this.nombreBanco = nombreBanco.trim();
        return true;
    }

    // CLIENTES
    // registrar cliente: rechaza si datos esenciales invalidos
    public boolean registrarCliente(Cliente cliente) {
    if (cliente == null) return false;

    // VALIDAR DNI
    if (!cliente.setDni(cliente.getDni())) {
        System.out.println("Cliente rechazado: DNI invalido.");
        return false;
    }

    // VALIDAR NOMBRE
    if (!cliente.setNombre(cliente.getNombre())) {
        System.out.println("Cliente rechazado: nombre invalido.");
        return false;
    }

    // VALIDAR DIRECCION
    if (!cliente.setDireccion(cliente.getDireccion())) {
        System.out.println("Cliente rechazado: direccion invalida.");
        return false;
    }

    // VALIDAR TELEFONO
    if (!cliente.setTelefono(cliente.getTelefono())) {
        System.out.println("Cliente rechazado: telefono invalido.");
        return false;
    }

    // VALIDAR DUPLICADO
    if (existeClientePorDni(cliente.getDni())) {
        System.out.println("Cliente no registrado: ya existe un cliente con ese DNI.");
        return false;
    }

    // REGISTRAR
    clientes.add(cliente);
    return true;
    }


    public boolean eliminarCliente(String dni) {
        Cliente c = buscarCliente(dni);
        if (c == null) return false;
        // Eliminamos todas las cuentas del cliente
        for (int i = 0; i < c.getCuentas().size(); i++) {
            Cuenta cuenta = c.getCuentas().get(i);
            eliminarCuenta(cuenta.getNumeroCuenta());
            i--; // porque la lista se reduce al eliminar
        }
        clientes.remove(c);// Luego eliminamos al cliente
        return true;
    }

    public Cliente buscarCliente(String dni) {
        if (dni == null) return null;
        for (Cliente c : clientes) {
            if (dni.equals(c.getDni())) return c;
        }
        return null;
    }

    public void mostrarCliente(Cliente c) {
        if (c == null) {
            System.out.println("Cliente no encontrado.");
            return;
        }

        System.out.println("\n=== DATOS DEL CLIENTE ===");
        System.out.println(c); // usa su toString
    }

    public boolean existeClientePorDni(String dni) {
        return buscarCliente(dni) != null;
    }

    public void mostrarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("No hay clientes registrados");
            return;
        }
        for (Cliente c : clientes) {
            System.out.println(c.toString());
        }
    }

    // EMPLEADOS
    public boolean registrarEmpleado(Empleado empleado) {
    if (empleado == null) return false;

    // VALIDAR NOMBRE
    if (!empleado.setNombre(empleado.getNombre())) {
        System.out.println("Empleado rechazado: nombre invalido.");
        return false;
    }

    // VALIDAR DNI
    if (!empleado.setDni(empleado.getDni())) {
        System.out.println("Empleado rechazado: DNI invalido.");
        return false;
    }

    // VALIDAR DIRECCION
    if (!empleado.setDireccion(empleado.getDireccion())) {
        System.out.println("Empleado rechazado: direccion invalida.");
        return false;
    }

    // VALIDAR TELEFONO
    if (!empleado.setTelefono(empleado.getTelefono())) {
        System.out.println("Empleado rechazado: telefono invalido.");
        return false;
    }

    // VALIDAR CODIGO
    if (!empleado.setCodigo(empleado.getCodigo())) {
        System.out.println("Empleado rechazado: codigo invalido (deben ser 4 digitos).");
        return false;
    }

    // VALIDAR CARGO
    if (!empleado.setCargo(empleado.getCargo())) {
        System.out.println("Empleado rechazado: cargo invalido.");
        return false;
    }

    // VALIDAR SALARIO
    if (!empleado.setSalario(empleado.getSalario())) {
        System.out.println("Empleado rechazado: salario invalido.");
        return false;
    }

    // VALIDAR SI EL CODIGO YA EXISTE
    if (buscarEmpleado(empleado.getCodigo()) != null) {
        System.out.println("Empleado no registrado: codigo ya existe.");
        return false;
    }

    // REGISTRAR
    empleados.add(empleado);
    return true;
    }

    public boolean eliminarEmpleado(String codigo) {
        Empleado e = buscarEmpleado(codigo);
        if (e == null) return false;
        empleados.remove(e);
        return true;
    }

    public Empleado buscarEmpleado(String codigo) {
        if (codigo == null) return null;
        for (Empleado e : empleados) {
            if (codigo.equals(e.getCodigo())) return e;
        }
        return null;
    }

    public void mostrarEmpleado(Empleado emp) {
        if (emp == null) {
            System.out.println("Empleado no encontrado.");
            return;
        }

        System.out.println("\n=== DATOS DEL EMPLEADO ===");
        System.out.println(emp); // usa su toString
    }

    public void mostrarEmpleados() {
        if (empleados.isEmpty()) {
            System.out.println("No hay empleados registrados");
            return;
        }
        for (Empleado e : empleados) {
            System.out.println(e.toString());
        }
    }

    // CUENTAS 
    public boolean crearCuentaParaCliente(Cliente cliente, String tipo, String numeroCuenta) {
        if (cliente == null) return false;

        // validar cliente, no guardar si cliente invalido
        if (cliente.getDni() == null || !cliente.getDni().matches("\\d{8}")) {
            System.out.println("No se puede crear cuenta: titular con DNI invalido.");
            return false;
        }

        if (existeCuentaPorNumero(numeroCuenta)) {
            System.out.println("No se puede crear cuenta: numero ya existe.");
            return false;
        }

        Cuenta cuenta = new Cuenta(tipo, cliente, numeroCuenta);
        
        String num = cuenta.getNumeroCuenta();
        if (num == null || !num.matches("\\d{10}")) {// validar numero de cuenta real (10 digitos)
            System.out.println("Cuenta rechazada: numero de cuenta invalido.");
            return false;
        }

        // validar tipo
        String t = cuenta.getTipo();
        if (t == null || t.trim().equalsIgnoreCase("INDEFINIDO")) {
            System.out.println("Cuenta rechazada: tipo invalido.");
            return false;
        }

        // asociar al cliente y al banco
        if (!cliente.agregarCuenta(cuenta)) {
            System.out.println("Cuenta rechazada: no se pudo agregar a titular.");
            return false;
        }

        cuentas.add(cuenta);
        // System.out.println("Cuenta creada con exito: " + cuenta.getNumeroCuenta()); // COMENTADO PARA INICIALIZACIÓN
        return true;
    }

    public boolean registrarCuenta(Cuenta cuenta) {
        if (cuenta == null) return false;

        Cliente t = cuenta.getTitular();
        if (t == null) {
            System.out.println("Cuenta rechazada: titular invalido.");
            return false;
        }

        // validar titular con setters del Cliente
        if (!t.setDni(t.getDni())) {
            System.out.println("Cuenta rechazada: DNI del titular invalido.");
            return false;
        }

        if (!t.setNombre(t.getNombre())) {
            System.out.println("Cuenta rechazada: nombre del titular invalido.");
            return false;
        }

        if (!t.setTelefono(t.getTelefono())) {
            System.out.println("Cuenta rechazada: telefono del titular invalido.");
            return false;
        }

        // validar numero de cuenta usando el setter
        if (!cuenta.setNumeroCuenta(cuenta.getNumeroCuenta())) {
            System.out.println("Cuenta rechazada: numero de cuenta invalido.");
            return false;
        }

        // validar tipo usando setter
        if (!cuenta.setTipo(cuenta.getTipo())) {
            System.out.println("Cuenta rechazada: tipo invalido.");
            return false;
        }

        // validar saldo (si deseas)
        if (!cuenta.setSaldo(cuenta.getSaldo())) {
            System.out.println("Cuenta rechazada: saldo invalido.");
            return false;
        }

        // validar que no exista
        if (existeCuentaPorNumero(cuenta.getNumeroCuenta())) {
            System.out.println("Cuenta no registrada: numero ya existe.");
            return false;
        }

        // asociar titular si falta
        if (!t.getCuentas().contains(cuenta)) {
            t.agregarCuenta(cuenta);
        }

        cuentas.add(cuenta);
        System.out.println("Cuenta registrada con exito: " + cuenta.getNumeroCuenta());
        return true;
    }

    public boolean eliminarCuenta(String numeroCuenta) {
        Cuenta c = buscarCuenta(numeroCuenta);
        if (c == null) return false;

        Cliente t = c.getTitular();
        if (t != null) {
            List<Cuenta> lista = t.getCuentas();
            for (int i = 0; i < lista.size(); i++) {
                Cuenta cc = lista.get(i);
                if (cc.getNumeroCuenta().equals(numeroCuenta)) {
                    lista.remove(i);
                    break;
                }
            }
        }
        cuentas.remove(c);
        return true;
    }

    public Cuenta buscarCuenta(String numeroCuenta) {
        if (numeroCuenta == null) return null;
        for (Cuenta c : cuentas) {
            if (numeroCuenta.equals(c.getNumeroCuenta())) 
                return c;
        }
        return null;
    }

    public boolean existeCuentaPorNumero(String numeroCuenta) {
        return buscarCuenta(numeroCuenta) != null;
    }

    public void mostrarCuentas() {
        if (cuentas.isEmpty()) {
            System.out.println("No hay cuentas registradas");
            return;
        }
        for (Cuenta c : cuentas) {
            System.out.println(c.toString());
        }
    }

    public void mostrarCuenta(Cuenta c) {
        if (c == null) {
            System.out.println("Cuenta no encontrada.");
            return;
        }

        System.out.println("\n=== DATOS DE LA CUENTA ===");
        System.out.println(c); // usa su toString
    }

    //TARJETAS
    public boolean registrarTarjeta(Tarjeta t) {
        if (t == null) return false;

        // valida numero tarjeta 16 digitos
        String nt = t.getNumeroTarjeta();
        if (nt == null || !nt.matches("\\d{16}")) {
            System.out.println("Tarjeta rechazada: numero invalido.");
            return false;
        }

        // cvv 3 digitos
        int cvv = t.getCvv();
        if (cvv < 100 || cvv > 999) {
            System.out.println("Tarjeta rechazada: CVV invalido.");
            return false;
        }

        // fecha simple MM/AA 
        String fv = t.getFechaVencimiento();
        if (fv == null || !fv.matches("\\d{2}/\\d{2}")) {
            System.out.println("Tarjeta rechazada: fecha vencimiento invalida.");
            return false;
        }

        if (t.getEstado() == null || t.getEstado().trim().isEmpty()) {
            System.out.println("Tarjeta rechazada: estado invalido.");
            return false;
        }

        tarjetas.add(t);
        System.out.println("Tarjeta registrada: " + nt);
        return true;
    }

    public void mostrarTarjetas() {
        if (tarjetas.isEmpty()) {
            System.out.println("No hay tarjetas registradas");
            return;
        }
        for (Tarjeta t : tarjetas) {
            System.out.println(t.toString());
        }
    }

    //  TRANSACCIONES 
    public boolean registrarTransaccion(Transaccion t) {
        if (t == null) return false;

        // Validación usando setters
        if (!t.setMonto(t.getMonto())) {
            System.out.println("Transaccion rechazada: monto invalido.");
            return false;
        }
        if (!t.setTipo(t.getTipo())) {
            System.out.println("Transaccion rechazada: tipo invalido.");
            return false;
        }
        if (t.getCodigo() == null || t.getCodigo().trim().isEmpty()) {
            System.out.println("Transaccion rechazada: codigo invalido.");
            return false;
        }

        transacciones.add(t);
        System.out.println("Transaccion registrada: " + t.getCodigo());
        return true;
    }

    public List<Transaccion> listarTransacciones() {
        return new ArrayList<>(transacciones);
    }

    public Transaccion buscarTransaccionPorCodigo(String codigo) {
        if (codigo == null) return null;
        for (Transaccion t : transacciones) {
            if (codigo.equals(t.getCodigo())) return t;
        }
        return null;
    }

    public void mostrarTransaccion(Transaccion t) {
        if (t == null) {
            System.out.println("Transaccion no encontrada.");
            return;
        }

        System.out.println("\n=== DATOS DE LA TRANSACCION ===");
        System.out.println(t);
    }
    
    public void mostrarTransacciones() {
        if (transacciones.isEmpty()) {
            System.out.println("No hay transacciones registradas");
            return;
        }
        for (Transaccion t : transacciones) {
            System.out.println(t.toString());
        }
    }

    //Deposito
    public boolean realizarDeposito(Cuenta cuentaDestino, double monto, Cuenta cuentaOrigen) {
        if (cuentaDestino == null || monto <= 0) return false;

        Deposito d = new Deposito(cuentaOrigen, monto, cuentaDestino);

        boolean exito = d.procesar();
        if (exito) {
            registrarTransaccion(d);  // se guarda solo si pasa las validaciones internas
            System.out.println(d);    // boleta de deposito
        }
        return exito;
    }

    //Retiro
    public boolean realizarRetiro(Cuenta cuentaOrigen, double monto) {
        if (cuentaOrigen == null || monto <= 0) return false;

        Retiro r = new Retiro(monto, cuentaOrigen);

        boolean exito = r.procesar();
        if (exito) {
            registrarTransaccion(r); // boleta
            System.out.println(r);
        }
        return exito;
    }

    // PRESTAMOS 
    public boolean registrarPrestamo(PagoPrestamo prestamo, Cliente cliente) {
        if (prestamo == null || cliente == null) return false;

        Cuenta c = prestamo.getCuenta();
        if (c == null || c.getNumeroCuenta() == null || !c.getNumeroCuenta().matches("\\d{10}")) {
            System.out.println("Prestamo rechazado: cuenta asociada invalida.");
            return false;
        }

        // 1. Registrar el prestamo en el cliente
        cliente.agregarPrestamo(prestamo);

        // 2. Registrar en lista de prestamos del banco
        prestamos.add(prestamo);

        // 3. Registrar como transaccion general del banco
        transacciones.add(prestamo);

        // 4. Registrar como transaccion del cliente
        cliente.getTransacciones().add(prestamo);

        // 5. Depositar el dinero del prestamo en la cuenta
        c.depositar(prestamo.getMonto());

        System.out.println("Préstamo registrado: " + prestamo.getCodigo());
        return true;
    }

    public void mostrarBoletaPrestamo(PagoPrestamo p) {
        System.out.println("\n=== BOLETA DE PRESTAMO REGISTRADO ===");
        System.out.println("Cliente:        " + p.getCuenta().getTitular().getNombre());
        System.out.println("Cuenta:         " + p.getCuenta().getNumeroCuenta());
        System.out.println("Codigo:         " + p.getCodigo());
        System.out.println("Monto pedido:   " + p.getMonto());
        System.out.println("Capital:        " + p.getCapital());
        System.out.println("Interes mensual:" + p.getInteres());
        System.out.println("Tasa mensual: " + p.getInteres() + " (interes calculado)");
        System.out.println("Meses:          " + p.getMeses());
    }

    public boolean realizarPagoPrestamo(PagoPrestamo pago, Cuenta cuentaPago) {
        if (pago == null || cuentaPago == null) return false;

        // vincular cuenta pagadora
        pago.setCuenta(cuentaPago);

        // validar saldo suficiente
        if (cuentaPago.getSaldo() < pago.getCuotaMensual()) {
            System.out.println("Pago rechazado: saldo insuficiente en la cuenta " + cuentaPago.getNumeroCuenta());
            pago.setEstado("Rechazada");
            return false;
        }
        cuentaPago.setSaldo(cuentaPago.getSaldo() - pago.getCuotaMensual());// descontar de la cuenta

        // procesar pago metodo de polimorfismo
        pago.procesar();
        registrarTransaccion(pago);// registrar transaccion
        System.out.println(pago);// mostrar boleta

        return true;
    }
}