public class Tarjeta {
    private String numeroTarjeta;
    private String tipo;
    private String fechaVencimiento;
    private int cvv;
    private String estado;

    Tarjeta (String numeroTarjeta,String tipo,String fechaVencimiento,int cvv,String estado) {
        setNumeroTarjeta(numeroTarjeta);
        setTipo(tipo);
        setFechaVencimiento(fechaVencimiento);
        setCvv(cvv);
        setEstado(estado);
    }
 
    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }
    public String getNumeroTarjeta () {
        return numeroTarjeta;
    }
    public void setTipo (String tipo) {
        this.tipo=tipo;
    }
    public String getTipo () {
        return tipo;
    }

    public void setFechaVencimiento (String fechaVencimiento) {
        this.fechaVencimiento=fechaVencimiento;

    }
    public String getFechaVencimiento () {
        return fechaVencimiento;
    }

    public void setCvv (int cvv) {
        this.cvv=cvv;
    }
    public int getCvv () {
        return cvv;
    }

    public void setEstado (String estado) {
        this.estado=estado;
    }
    public String getEstado () {
        return estado;
    }

    public String toString () {
        return "\n--->Tarjeta<----\nTipo:"+tipo+"\nFecha de vencimiento: "+fechaVencimiento+"\nCvv: "+cvv+"\nEstado: "+estado;
    }
}