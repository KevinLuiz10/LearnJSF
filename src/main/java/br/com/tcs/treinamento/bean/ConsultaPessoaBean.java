package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.service.PessoaService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Named("consultaPessoaBean")
@ViewScoped
public class ConsultaPessoaBean implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<Pessoa> pessoas;
    private Pessoa pessoaSelecionada;
    private String errorMessage;
    private Long pessoaId;
    private Boolean tpManutencao;

    @Inject
    private PessoaService pessoaService;

    @PostConstruct
    public void init(){
        // Recupera parâmetro "pessoaId" da URL
        Map<String, String> params = FacesContext.getCurrentInstance()
                .getExternalContext()
                .getRequestParameterMap();
        String idParam = params.get("pessoaId");
        if (idParam != null && !idParam.trim().isEmpty()) {
            try {
                pessoaId = Long.valueOf(idParam);
                pessoaSelecionada = pessoaService.getById(pessoaId);
            } catch (NumberFormatException e) {
                errorMessage = "ID inválido da pessoa.";
            }
        }
        // Recupera o parâmetro tpManutencao; se não existir, assume um valor padrão (por exemplo, true para edição)
        String tpParam = params.get("tpManutencao");
        if (tpParam != null && !tpParam.trim().isEmpty()) {
            setTpManutencao(Boolean.valueOf(tpParam));
        } else {
            setTpManutencao(true);
        }
        pessoas = pessoaService.listar();
    }

    private Pessoa mapPessoaEntity() {
        Pessoa pessoa = new Pessoa();
        pessoa.setId(pessoaSelecionada.getId());
        pessoa.setNome(pessoaSelecionada.getNome());
        pessoa.setIdade(pessoaSelecionada.getIdade());
        pessoa.setDataNascimento(pessoaSelecionada.getDataNascimento());
        return pessoa;
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        if (pessoaSelecionada.getNome() == null || pessoaSelecionada.getNome().trim().isEmpty()) {
            erros.add("Nome não informado.");
        }

        if (pessoaSelecionada.getIdade() == null) {
            erros.add("Idade não informada.");
        }

        if (pessoaSelecionada.getDataNascimento() == null) {
            erros.add("Data de nascimento não informada.");
        }

        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }

    }

    public String confirmar(){
        pessoaService.cadastrar(pessoaSelecionada);
        return "consultaPessoas?faces-redirect=true";
    }

    public String prepararEdicao(Pessoa pessoa){
        return "alterar?faces-redirect=true&pessoaId=" + pessoa.getId() + "&tpManutencao=true";
    }


    public List<Pessoa> getPessoas() {
        return pessoas;
    }
    public void setPessoas(List<Pessoa> pessoas) {
        this.pessoas = pessoas;
    }
    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }
    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }
    public String getErrorMessage() {
        return errorMessage;
    }
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    public Long getPessoaId() {
        return pessoaId;
    }
    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }
    public PessoaService getPessoaService() {
        return pessoaService;
    }
    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
    public Boolean getTpManutencao() {
        return tpManutencao;
    }
    public void setTpManutencao(Boolean tpManutencao) {
        this.tpManutencao = tpManutencao;
    }
}
