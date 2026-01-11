package database.model;

public class TB_REPLICACAO_PROCESSO_TABELA {

    private long id;
    private long processo_id;
    private String tabela_origem;
    private String tabela_destino;
    private int ordem;
    private boolean habilitado;
    private String ds_where;

    public TB_REPLICACAO_PROCESSO_TABELA() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getProcesso_id() {
        return processo_id;
    }

    public void setProcesso_id(long processo_id) {
        this.processo_id = processo_id;
    }

    public String getTabela_origem() {
        return tabela_origem;
    }

    public void setTabela_origem(String tabela_origem) {
        this.tabela_origem = tabela_origem;
    }

    public String getTabela_destino() {
        return tabela_destino;
    }

    public void setTabela_destino(String tabela_destino) {
        this.tabela_destino = tabela_destino;
    }

    public int getOrdem() {
        return ordem;
    }

    public void setOrdem(int ordem) {
        this.ordem = ordem;
    }

    public boolean isHabilitado() {
        return habilitado;
    }

    public void setHabilitado(boolean habilitado) {
        this.habilitado = habilitado;
    }

    public String getDs_where() {
        return ds_where;
    }

    public void setDs_where(String ds_where) {
        this.ds_where = ds_where;
    }
}
