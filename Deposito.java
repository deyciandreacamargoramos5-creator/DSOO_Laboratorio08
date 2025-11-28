public class Deposito extends Transaccion {

    public Deposito(Cuenta cuentaOrigen, double monto, Cuenta cuentaDestino) {
        super(monto, "DEPOSITO", cuentaDestino);
    }

    // GETTERS
    public Cuenta getCuentaDestino() {
        return getCuenta(); // cuenta heredada de Transaccion
    }

    // METODO POLIMORFICO
    @Override
    public boolean procesar() {

        // Validar destino
        if (getCuenta() == null) {
            setEstado("Rechazada");
            System.out.println("Deposito rechazado: cuenta destino no asignada.");
            return false;
        }

        // Validar monto
        if (getMonto() <= 0) {
            setEstado("Rechazada");
            System.out.println("Deposito rechazado: monto debe ser mayor que 0.");
            return false;
        }

        // Aplicar depósito
        double nuevoSaldo = getCuenta().getSaldo() + getMonto();

        if (!getCuenta().setSaldo(nuevoSaldo)) {
            setEstado("Rechazada");
            return false;
        }

        setEstado("Procesada");
        System.out.println("Deposito procesado correctamente.");
        return true;
    }
    //BOLETA de deposito
    @Override
    public String toString() {
        return "\n===== DEPOSITO =====" +
               "\nDestino: " + (getCuenta() != null ? getCuenta().getNumeroCuenta() : "No asignada") +
               "\nMonto: " + getMonto() +
               "\nFecha: " + getFecha() +
               "\nHora: " + getHora() +
               "\nEstado: " + getEstado() +
               "\nCodigo: " + getCodigo() +
               "\n====================\n";
    }
}
