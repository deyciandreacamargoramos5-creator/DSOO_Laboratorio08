public class PagoPrestamo extends Transaccion {
    private double interes;
    private double capital;
    private int meses;
    private double tasaMensual;
    private double cuotaMensual;
    // CONSTRUCTOR
    public PagoPrestamo(double monto, double interes, double capital,
                        int meses, double tasaAnual, Cuenta cuenta) {
        super(monto, "PAGO_PRESTAMO", cuenta); // pasamos la cuenta al constructor padre
 
        setInteres(interes);
        setCapital(capital);
        setMeses(meses);
        setTasaMensual(tasaAnual);

        calcularCuotaMensual();
    }
    // SETTERS
    public boolean setInteres(double interes) {
        if (interes < 0) return false;
        this.interes = interes;
        return true;
    }
    public boolean setCapital(double capital) {
        if (capital < 0) return false;
        this.capital = capital;
        return true;
    }
    public boolean setMeses(int meses) {
        if (meses <= 0) return false;
        this.meses = meses;
        return true;
    }
    public boolean setTasaMensual(double tasaAnual) {
        if (tasaAnual < 0) return false;
        this.tasaMensual = tasaAnual / 12 / 100;
        return true;
    }
    // CÁLCULO DE CUOTA
    private void calcularCuotaMensual() {
        if (tasaMensual == 0) {
            cuotaMensual = capital / meses;
        } else {
            cuotaMensual =
                (capital * tasaMensual)
                / (1 - Math.pow(1 + tasaMensual, -meses));
        }
    }
    // GETTERS
    public double getInteres() { return interes; }
    public double getCapital() { return capital; }
    public int getMeses() { return meses; }
    public double getTasaMensual() { return tasaMensual; }
    public double getCuotaMensual() { return cuotaMensual; }
    public Cuenta getCuentaPrestamo() { return getCuenta(); }

    public boolean estaPagado() {
        return capital <= 0 && interes <= 0;
    }
    // MÉTODO POLIMÓRFICO
    @Override
    public boolean procesar() {
        if (getCuenta() == null) {
            setEstado("Rechazada");
            return false;
        }
        double pago = getMonto();

        if (pago <= 0) {
            setEstado("Rechazada");
            return false;
        }
        // 1) Se paga interés primero
        if (interes > 0) {
            if (pago >= interes) {
                pago -= interes;
                interes = 0;
            } else {
                interes -= pago;
                pago = 0;
            }
        }
        // 2) Luego capital
        if (pago > 0) {
            if (pago >= capital) {
                pago -= capital;
                capital = 0;
            } else {
                capital -= pago;
            }
        }
        setEstado("Procesada");
        return true;
    }
    // TO STRING
    @Override
    public String toString() {
        return "\n====== PAGO DE PRESTAMO ======" +
               "\nCuenta: " + (getCuenta() != null ? getCuenta().getNumeroCuenta() : "No asignada") +
               "\nMonto pagado: " + getMonto() +
               "\nCuota mensual: " + cuotaMensual +
               "\nInterés restante: " + interes +
               "\nCapital restante: " + capital +
               "\nFecha: " + getFecha() +
               "\nHora: " + getHora() +
               "\nCódigo: " + getCodigo() +
               "\nEstado: " + getEstado() +
               "\n==============================\n";
    }
}
