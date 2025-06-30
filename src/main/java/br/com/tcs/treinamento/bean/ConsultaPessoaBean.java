package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.service.PessoaService;
import org.primefaces.PrimeFaces;

import javax.annotation.PostConstruct;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
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

    /*
    public List<String> validarCampos() {
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
            return erros;
            //errorMessage = String.join("<br/>", erros);
            //PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            return null;
            //PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }

    }
    */

    public String confirmar(){
        pessoaService.cadastrar(pessoaSelecionada);
        pessoas = pessoaService.listar();
        return "consultaPessoas?faces-redirect=true";
    }

    public void salvar() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (pessoaSelecionada.getNome() == null || pessoaSelecionada.getNome().trim().isEmpty()) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Nome não informado."));
            context.validationFailed();
            return;
        }

        if (pessoaSelecionada.getIdade() == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Idade não informada."));
            context.validationFailed();
            return;
        }

        if (pessoaSelecionada.getDataNascimento() == null) {
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Erro", "Data de nascimento não informada."));
            context.validationFailed();
            return;
        }

        // Se chegou aqui, está tudo ok:
        confirmar();

        context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso", "Pessoa atualizada."));
    }

    public void prepararEdicao(Pessoa pessoa){
        //return "alterar?faces-redirect=true&pessoaId=" + pessoa.getId() + "&tpManutencao=true";
        this.pessoaSelecionada = pessoa;
        System.out.println("Entrou em preparar edicao");
    }

    public void prepararExclusao(Pessoa pessoa){
        pessoaSelecionada = pessoa;
        PrimeFaces.current().executeScript("PF('confirmRemoveDialog').show();");
    }

    public String confirmarExclusao(){
        pessoaService.removeById(pessoaSelecionada.getId());
        return "consultaPessoas?faces-redirect=true";
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
