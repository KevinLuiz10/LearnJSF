package br.com.tcs.treinamento.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

public class Pessoa {

    private Long id;

    private String nome;

    //Idade da pessoa
    private Integer idade;

    //Data de nascimento | Utiliza apenas a data, sem o horário
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    private String tipoDocumento;

    // Número do CPF (caso tipoDocumento seja CPF)
    private String numeroCPF;

    // Número do CNPJ (caso tipoDocumento seja CNPJ)
    private String numeroCNPJ;


    public Pessoa() {
        this.id = null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroCPF() {
        return numeroCPF;
    }

    public void setNumeroCPF(String numeroCPF) {
        this.numeroCPF = numeroCPF;
    }

    public String getNumeroCNPJ() {
        return numeroCNPJ;
    }

    public void setNumeroCNPJ(String numeroCNPJ) {
        this.numeroCNPJ = numeroCNPJ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pessoa pessoa = (Pessoa) o;
        return Objects.equals(id, pessoa.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    // Método toString opcional para facilitar a depuração
    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", idade=" + idade + '\'' +
                ", data de nascimento=" + dataNascimento +
                '}';
    }

    public Pessoa copy(){
        Pessoa pessoa = new Pessoa();
        pessoa.setId(this.id);
        pessoa.setNome(this.nome);
        pessoa.setIdade(this.idade);
        pessoa.setDataNascimento(this.dataNascimento);
        pessoa.setTipoDocumento(this.tipoDocumento);
        pessoa.setNumeroCPF(this.numeroCPF);
        pessoa.setNumeroCNPJ(this.numeroCNPJ);
        return pessoa;
    }
}
