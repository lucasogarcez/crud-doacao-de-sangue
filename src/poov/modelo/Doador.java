package poov.modelo;

public class Doador {
    private long codigo;
    private String nome;
    private String cpf;
    private String contato;
    private boolean tipoERhCorretos = false;
    private TipoSanguineo tipoSanguineo;
    private RH Rh;
    private Situacao situacao = Situacao.ATIVO;

    public Doador() {}

    public Doador(long codigo, String nome, String cpf, String contato, TipoSanguineo tipoSanguineo, RH rh, Situacao situacao) {
        this.codigo = codigo;
        this.nome = nome;
        this.cpf = cpf;
        this.contato = contato;
        this.tipoSanguineo = tipoSanguineo;
        Rh = rh;
        this.situacao = situacao;
    }
    
    public long getCodigo() {
        return codigo;
    }
    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }
    public String getContato() {
        return contato;
    }
    public void setContato(String contato) {
        this.contato = contato;
    }
    public TipoSanguineo getTipoSanguineo() {
        return tipoSanguineo;
    }
    public void setTipoSanguineo(TipoSanguineo tipoSanguineo) {
        this.tipoSanguineo = tipoSanguineo;
    }
    public RH getRh() {
        return Rh;
    }
    public void setRh(RH rh) {
        Rh = rh;
    }
    public Situacao getSituacao() {
        return situacao;
    }
    public void setSituacao(Situacao situacao) {
        this.situacao = situacao;
    }

    public boolean isTipoERhCorretos() {
        return tipoERhCorretos;
    }

    public void setTipoERhCorretos(boolean tipoERhCorretos) {
        this.tipoERhCorretos = tipoERhCorretos;
    }    
    
    @Override
    public String toString() {
        return "Código do Doador: " + codigo + "\n" +
                "Nome: " + nome + "\n" +
                "CPF: " + cpf + "\n" +
                "Contato: " + contato + "\n" +
                "Tipo Sanguíneo: " + tipoSanguineo + " " + Rh + "\n" +
                "Tipo e RH Corretos: " + (tipoERhCorretos ? "Sim" : "Não") + "\n" +
                "Situação: " + situacao;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (codigo ^ (codigo >>> 32));
        result = prime * result + ((nome == null) ? 0 : nome.hashCode());
        result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
        result = prime * result + ((contato == null) ? 0 : contato.hashCode());
        result = prime * result + (tipoERhCorretos ? 1231 : 1237);
        result = prime * result + ((tipoSanguineo == null) ? 0 : tipoSanguineo.hashCode());
        result = prime * result + ((Rh == null) ? 0 : Rh.hashCode());
        result = prime * result + ((situacao == null) ? 0 : situacao.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Doador other = (Doador) obj;
        if (codigo != other.codigo)
            return false;
        if (nome == null) {
            if (other.nome != null)
                return false;
        } else if (!nome.equals(other.nome))
            return false;
        if (cpf == null) {
            if (other.cpf != null)
                return false;
        } else if (!cpf.equals(other.cpf))
            return false;
        if (contato == null) {
            if (other.contato != null)
                return false;
        } else if (!contato.equals(other.contato))
            return false;
        if (tipoERhCorretos != other.tipoERhCorretos)
            return false;
        if (tipoSanguineo != other.tipoSanguineo)
            return false;
        if (Rh != other.Rh)
            return false;
        if (situacao != other.situacao)
            return false;
        return true;
    }
}
