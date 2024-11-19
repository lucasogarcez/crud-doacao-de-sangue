package poov.modelo;

public enum RH {
    POSITIVO, NEGATIVO, DESCONHECIDO;

    private RH Rh;

    public RH getRh() {
        return Rh;
    }

    public void setRh(RH rh) {
        Rh = rh;
    }
}
