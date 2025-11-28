package DSOO_Laboratorio08;
pushpublic class Tarjeta {

    private String numeroTarjeta;
    private String tipo;
    private String fechaVencimiento;
    private int cvv;
    private String estado;

    public Tarjeta(String numeroTarjeta, String tipo, String fechaVencimiento, int cvv, String estado) {
        setNumeroTarjeta(numeroTarjeta);
        setTipo(tipo);
        setFechaVencimiento(fechaVencimiento);
        setCvv(cvv);
        setEstado(estado);
    }

    // SETTERS con validacion
    public void setNumeroTarjeta(String numeroTarjeta) {
        if (numeroTarjeta != null && numeroTarjeta.matches("\\d{16}")) {
            this.numeroTarjeta = numeroTarjeta;
        } else {
            System.out.println("Numero de tarjeta inválido (debe contener 16 dígitos numéricos)");
            this.numeroTarjeta = "0000000000000000";
        }
    }

    public void setTipo(String tipo) {
        if (tipo == null) {
            this.tipo = "Desconocido";
            return;
        }

        tipo = tipo.trim().toLowerCase();

        if (tipo.equals("debito") || tipo.equals("débito")) {
            this.tipo = "Débito";
        } else if (tipo.equals("credito") || tipo.equals("crédito")) {
            this.tipo = "Crédito";
        } else {
            System.out.println("Tipo de tarjeta no válido. Usando 'Desconocido'");
            this.tipo = "Desconocido";
        }
    }

    public void setFechaVencimiento(String fechaVencimiento) {
        if (validarFecha(fechaVencimiento)) {
            this.fechaVencimiento = fechaVencimiento;
        } else {
            System.out.println("Fecha inválida o vencida (formato correcto: MM/AA)");
            this.fechaVencimiento = "00/00";
        }
    }

    private boolean validarFecha(String f) {
        if (f == null || !f.matches("\\d{2}/\\d{2}")) return false;

        int mes = Integer.parseInt(f.substring(0, 2));
        int año = Integer.parseInt(f.substring(3, 5));  // 25 → 2025

        if (mes < 1 || mes > 12) return false;

        java.time.YearMonth actual = java.time.YearMonth.now();
        java.time.YearMonth venc = java.time.YearMonth.of(2000 + año, mes);

        return !venc.isBefore(actual); // true si NO está vencida
    }

    public void setCvv(int cvv) {
        if (cvv >= 100 && cvv <= 999) {
            this.cvv = cvv;
        } else {
            System.out.println("CVV invalido (debe ser de 3 digitos)");
            this.cvv = -1;
        }
    }

    public void setEstado(String estado) {
        if (estado == null) {
            this.estado = "INACTIVA";
            return;
        }

        estado = estado.trim().toUpperCase();

        switch (estado) {
            case "ACTIVA":
            case "BLOQUEADA":
            case "INACTIVA":
            case "EXPIRADA":
                this.estado = estado;
                break;

            default:
                System.out.println("Estado invalido. Usando 'INACTIVA'");
                this.estado = "INACTIVA";
        }
    }

    // GETTERS
    public String getNumeroTarjeta() { return numeroTarjeta; }
    public String getTipo() { return tipo; }
    public String getFechaVencimiento() { return fechaVencimiento; }
    public int getCvv() { return cvv; }
    public String getEstado() { return estado; }

    // METODOS EXTRA ÚTILES
    public boolean estaActiva() {
        return "ACTIVA".equalsIgnoreCase(estado);
    }

    public boolean estaVencida() {
        return !validarFecha(fechaVencimiento);
    }

    @Override
    public String toString() {
        return "\n---Tarjeta ---" +
               "\nTipo: " + tipo +
               "\nNumero: " + numeroTarjeta +
               "\nFecha de vencimiento: " + fechaVencimiento +
               "\nCVV: " + cvv +
               "\nEstado: " + estado;
    }
}
