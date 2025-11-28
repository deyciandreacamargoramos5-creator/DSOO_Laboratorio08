public class Cuenta {

    private String tipo;
    private Cliente titular;
    private Tarjeta tarjeta;
    private double saldo;
    private String numeroCuenta;

    public Cuenta(String tipo, Cliente titular, String numeroCuenta) {

        if (!setTipo(tipo)) {
            this.tipo = "INDEFINIDO";
        }

        if (!setTitular(titular)) {
            // Nunca debe quedar null
            this.titular = new Cliente("SIN_NOMBRE", "00000000", "SIN_DIRECCION", "900000000");
        }

        if (!setNumeroCuenta(numeroCuenta)) {
            this.numeroCuenta = "0000000000"; // 10 ceros por defecto
        }

        this.saldo = 0.0;
        this.tarjeta = null;
    }

    // GETTERS
    public String getTipo() {
        return tipo;
    }

    public Cliente getTitular() {
        return titular;
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    // SETTERS con validacion
    public boolean setTipo(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) return false;
        if (!tipo.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) return false;//nombres o textos hechos de letras, espacios y uno o mas caracteres permitidos (regex)
        this.tipo = tipo;
        return true;
    }

    public boolean setTitular(Cliente titular) {
        if (titular == null) return false;
        this.titular = titular;
        return true;
    }

    public boolean setTarjeta(Tarjeta tarjeta) {
        if (tarjeta == null) return false;
        if (this.tarjeta != null) return false; // no reemplazar
        this.tarjeta = tarjeta;
        return true;
    }

    public boolean setSaldo(double saldo) {
        if (saldo < 0) return false;
        this.saldo = saldo;
        return true;
    }

    public boolean setNumeroCuenta(String numeroCuenta) {
        if (numeroCuenta == null) return false;
        if (!numeroCuenta.matches("\\d{10}")) return false;
        this.numeroCuenta = numeroCuenta;
        return true;
    }
    // Metodo compatible con Banco
    public boolean asignarTarjeta(Tarjeta tarjeta) {
        return setTarjeta(tarjeta);
    }

    // Metodos de negocio
    public boolean depositar(double monto) {
        if (monto <= 0) return false;
        saldo += monto;
        return true;
    }

    public boolean puedeDebitar(double monto) {
        return monto > 0 && monto <= saldo;
    }

    public boolean retirar(double monto) {
        if (!puedeDebitar(monto)) return false;
        saldo -= monto;
        return true;
    }

    @Override
    public String toString() {
        return "\n---> Cuenta <---"
             + "\nTipo: " + tipo
             + "\nNumero: " + numeroCuenta
             + "\nSaldo: " + saldo
             + "\nTitular: " + titular.getNombre()
             + "\nTarjeta: " + (tarjeta == null ? "Sin tarjeta" 
                              : "Tarjeta asignada (datos ocultos)");
    }
}
