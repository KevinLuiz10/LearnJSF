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

@Named("cadastroBean")
@ViewScoped
public class CadastroBean implements Serializable {
    private static final long serialVersionUID = 1L;

    //Classe Pessoa para o cadastro de pessoa
    private Pessoa cadastrarPessoa;

    // Propriedade para armazenar as mensagens de erro
    private String errorMessage;

    @Inject
    private PessoaService pessoaService;

    @PostConstruct
    public void init() {
        if (cadastrarPessoa == null) {
            cadastrarPessoa = new Pessoa();
        }
    }

    public void confirmar(){
        pessoaService.cadastrar(cadastrarPessoa);
        cadastrarPessoa = new Pessoa();
    }

    public void limpar() {
        cadastrarPessoa.setNome(null);
        cadastrarPessoa.setIdade(null);
        cadastrarPessoa.setDataNascimento(null);
        errorMessage = null;
    }

    public void validarCampos() {
        List<String> erros = new ArrayList<>();

        System.out.println("Nome: " + cadastrarPessoa.getNome());
        System.out.println("Idade: " + cadastrarPessoa.getIdade());

        if (cadastrarPessoa.getNome() == null || cadastrarPessoa.getNome().trim().isEmpty()) {
            erros.add("Nome não informado.");
        }

        if (cadastrarPessoa.getIdade() == null) {
            erros.add("Idade não informada.");
        }

        if (cadastrarPessoa.getDataNascimento() == null) {
            erros.add("Data de nascimento não informada.");
        }

        if (cadastrarPessoa.getTipoDocumento() == null || cadastrarPessoa.getTipoDocumento().trim().isEmpty()) {
            erros.add("Tipo de documento não informado.");
        } else {
            if ("CPF".equals(cadastrarPessoa.getTipoDocumento())) {
                if (cadastrarPessoa.getNumeroCPF() == null || cadastrarPessoa.getNumeroCPF().trim().isEmpty() ||
                        cadastrarPessoa.getNumeroCPF().trim().length() < 11) {
                    erros.add("CPF não informado ou incompleto (deve conter 11 dígitos).");
                }
            } else if ("CNPJ".equals(cadastrarPessoa.getTipoDocumento())) {
                if (cadastrarPessoa.getNumeroCNPJ() == null || cadastrarPessoa.getNumeroCNPJ().trim().isEmpty() ||
                        cadastrarPessoa.getNumeroCNPJ().trim().length() < 14) {
                    erros.add("CNPJ não informado ou incompleto (deve conter 14 dígitos).");
                }
            }
        }

        if (!erros.isEmpty()) {
            errorMessage = String.join("<br/>", erros);
            PrimeFaces.current().executeScript("PF('errorDialog').show();");
        } else {
            PrimeFaces.current().executeScript("PF('confirmDialog').show();");
        }

    }


    public void setCadastrarPessoa(Pessoa cadastrarPessoa) {
        this.cadastrarPessoa = cadastrarPessoa;
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
    public Pessoa getCadastrarPessoa() {
        return cadastrarPessoa;
    }
}
