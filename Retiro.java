public class Retiro extends Transaccion {
    private Cuenta cuentaOrigen;
    private boolean saldoDisponible;

    public Retiro(String id, double monto, String fecha, String hora, Cuenta cuentaOrigen) {
        super(id, monto, fecha, hora, "Retiro", "Pendiente");
        this.cuentaOrigen = cuentaOrigen;
        this.saldoDisponible = false;
    }

    public Cuenta getCuentaOrigen() { 
        return cuentaOrigen; 
    }

    public boolean validarSaldo() {
        double saldoActual = cuentaOrigen.getSaldo();
        double montoRetiro = getMonto();

        if (saldoActual >= montoRetiro) {
            return true;
        } else {
            return false;
        }
    }


    public void procesar() {
        if (validarSaldo()) {
            double antes = cuentaOrigen.getSaldo();
            double nuevo = antes - getMonto();
            cuentaOrigen.setSaldo(nuevo); 
            saldoDisponible = true;
            setEstado("Procesada");
            System.out.println("Retiro: " + getMonto() + " retirado de cuenta " + cuentaOrigen.getNumeroCuenta() +"Saldo anterior: " + antes + "Nuevo saldo: " + nuevo);
        } else {
            saldoDisponible = false;
            setEstado("Rechazada - Saldo insuficiente");
            System.out.println("Retiro rechazado: saldo insuficiente en cuenta " + cuentaOrigen.getNumeroCuenta());
        }
    }

    public boolean isSaldoDisponible() { 
        return saldoDisponible; }

    @Override
    public String toString() {
        return "\n--->Retiro<---\n" + super.toString()+"\nCuentaOrigen: "+cuentaOrigen.getNumeroCuenta()+"\nSaldoDisponible: "+saldoDisponible;
    }
}
