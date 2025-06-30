package br.com.tcs.treinamento.bean;

import br.com.tcs.treinamento.entity.Pessoa;
import br.com.tcs.treinamento.service.PessoaService;
import org.primefaces.PrimeFaces;


import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;


@Named("atualizaCadastroBean")
@ViewScoped
public class AtualizaCadastroBean implements Serializable {

    private Pessoa pessoaSelecionada;
    private Long pessoaId;
    private Boolean tpManutencao;
    private String errorMessage;

    @Inject
    private PessoaService pessoaService;


    public void prepararEdicao(Pessoa pessoa){
        this.pessoaSelecionada = pessoa;
    }

    public void salvarAlteracoes() {
        // Aqui você pode atualizar a lista, salvar no banco, etc.
        // Neste exemplo, como o objeto é por referência, já está alterado
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage("Alterações salvas com sucesso"));
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

    // getters/setters

    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }

    public Boolean getTpManutencao() {
        return tpManutencao;
    }

    public void setTpManutencao(Boolean tpManutencao) {
        this.tpManutencao = tpManutencao;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public PessoaService getPessoaService() {
        return pessoaService;
    }

    public void setPessoaService(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }
}