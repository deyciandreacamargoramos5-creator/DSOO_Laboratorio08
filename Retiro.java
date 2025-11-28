package DSOO_Laboratorio08;
public class Retiro extends Transaccion {

    public Retiro(double monto, Cuenta cuentaOrigen) {
        super(monto, "RETIRO", cuentaOrigen); // pasamos cuenta al constructor padre directamente
    }

    // VALIDACION DE SALDO
    private boolean validarSaldo() {
        if (getCuenta() == null) return false;
        return getMonto() > 0 && getCuenta().getSaldo() >= getMonto();
    }

    // METODO POLIMÓRFICO
    @Override
    public boolean procesar() {

        // 1. Validar cuenta
        if (getCuenta() == null) {
            setEstado("Rechazada");
            return false;
        }

        // 2. Validar saldo
        if (!validarSaldo()) {
            setEstado("Rechazada");
            return false;
        }

        // 3. Procesar
        double nuevoSaldo = getCuenta().getSaldo() - getMonto();

        if (!getCuenta().setSaldo(nuevoSaldo)) {
            setEstado("Rechazada");
            return false;
        }

        // 4. Marcar procesada
        setEstado("Procesada");
        return true;
    }

    @Override
    public String toString() {
        return "\n===== RETIRO =====" +
                "\nCuenta Origen: " + (getCuenta() != null ? getCuenta().getNumeroCuenta() : "No asignada") +
                "\nMonto: " + getMonto() +
                "\nFecha: " + getFecha() +
                "\nHora: " + getHora() +
                "\nEstado: " + getEstado() +
                "\nCódigo: " + getCodigo() +
                "\n==================\n";
    }
}
