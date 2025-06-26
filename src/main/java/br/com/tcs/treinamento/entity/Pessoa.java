package br.com.tcs.treinamento.entity;

import jakarta.persistence.*;

import java.util.Date;
import java.util.Objects;

public class Pessoa {
    private static Long ids=0L;

    private Long id;

    private String nome;

    //Idade da pessoa
    private Integer idade;

    //Data de nascimento | Utiliza apenas a data, sem o horário
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    public Pessoa() {
        id=++ids;
    }

    public Pessoa(String nome, int idade, Date dataNascimento) {
        this.nome = nome;
        this.idade = idade;
        this.dataNascimento = dataNascimento;
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
}
