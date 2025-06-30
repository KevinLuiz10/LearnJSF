package br.com.tcs.treinamento.service;

import br.com.tcs.treinamento.dao.PessoaDAO;
import br.com.tcs.treinamento.entity.Pessoa;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.List;

@ManagedBean(name="pessoaService")
@ApplicationScoped
public class PessoaService implements Serializable {
    private static final long serialVersionUID = 1L;

    private PessoaDAO pessoaDAO = PessoaDAO.getPessoaDAOInstance();

    public List<Pessoa> listar() {
        return pessoaDAO.getPessoas();
    }

    public Pessoa getById(Long id){
        return pessoaDAO.getById(id);
    }

    public void cadastrar(Pessoa pessoa){
        if (pessoa.getId() == null) {
            pessoa.setId(gerarNovoId());
        }

        pessoaDAO.cadastrar(pessoa);
    }

    public boolean removeById(Long id){
        return pessoaDAO.removeById(id);
    }

    private Long gerarNovoId() {
        long maxId = listar().stream()
                .mapToLong(p -> p.getId() != null ? p.getId() : 0L)
                .max()
                .orElse(0L);
        return maxId + 1;
    }

}
