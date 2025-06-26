package br.com.tcs.treinamento.dao;

import java.util.ArrayList;
import java.util.List;
import br.com.tcs.treinamento.entity.Pessoa;

public class PessoaDAO {
    private static PessoaDAO pessoaDAOInstance;
    private List<Pessoa> pessoas;

    private PessoaDAO(){
        pessoas = new ArrayList<Pessoa>();
    }

    public static PessoaDAO getPessoaDAOInstance() {
        if(pessoaDAOInstance==null){
            pessoaDAOInstance=new PessoaDAO();
        }
        return pessoaDAOInstance;
    }

    public Pessoa getById(Long id){
        for(Pessoa pessoa:pessoas){
            if(pessoa.getId()==id){
                return pessoa;
            }
        }
        return null;
    }

    public boolean cadastrar(Pessoa pessoa){
        for(int i=0; i<pessoas.size(); i++){
            if(pessoas.get(i).getId()==pessoa.getId()){
                pessoas.set(i, pessoa);
                return true;
            }
        }
        pessoas.add(pessoa);
        return true;
    }

    public boolean removeById(Long id){
        for(int i=0; i<pessoas.size(); i++){
            if(pessoas.get(i).getId()==id){
                pessoas.remove(i);
                return true;
            }
        }
        return false;
    }

    public List<Pessoa> getPessoas() {
        return pessoas;
    }
}
