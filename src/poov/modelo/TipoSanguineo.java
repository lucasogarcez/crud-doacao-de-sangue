package poov.modelo;

public enum TipoSanguineo {
    A, B, AB, O, DESCONHECIDO;

    private TipoSanguineo tipoSanguineo;

    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }

    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }

    
}
