package poov.modelo;

public enum Situacao {
    ATIVO, INATIVO;

    private Situacao situacao;

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }
}
