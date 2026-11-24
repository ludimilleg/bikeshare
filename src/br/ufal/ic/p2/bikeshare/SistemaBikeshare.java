package br.ufal.ic.p2.bikeshare;

import br.ufal.ic.p2.bikeshare.aluguel.RepositorioAlugueis;
import br.ufal.ic.p2.bikeshare.aluguel.ServicoAluguel;
import br.ufal.ic.p2.bikeshare.bicicleta.RepositorioBicicletas;
import br.ufal.ic.p2.bikeshare.estacao.RepositorioEstacoes;
import br.ufal.ic.p2.bikeshare.usuario.RepositorioUsuarios;
import br.ufal.ic.p2.bikeshare.usuario.ServicoUsuarios;

import java.io.Serializable;

public class SistemaBikeshare implements Serializable {

    private static final long serialVersionUID = 2656971213220932395L;

    private final RepositorioUsuarios usuarios = new RepositorioUsuarios();
    private final RepositorioEstacoes estacoes = new RepositorioEstacoes();
    private final RepositorioBicicletas bicicletas = new RepositorioBicicletas(estacoes);
    private final RepositorioAlugueis alugueis = new RepositorioAlugueis();
    private ServicoUsuarios servicoUsuarios;
    private ServicoAluguel servicoAluguel;

    private ServicoUsuarios getServicoUsuarios() {
        if (servicoUsuarios == null) {
            servicoUsuarios = new ServicoUsuarios(usuarios);
        }
        return servicoUsuarios;
    }

    private ServicoAluguel getServicoAluguel() {
        if (servicoAluguel == null) {
            servicoAluguel = new ServicoAluguel(
                    usuarios, estacoes, bicicletas, alugueis);
        }
        return servicoAluguel;
    }

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf) {
        return getServicoUsuarios().cadastrarPessoaFisica(nome, email, senha, tipo, cpf);
    }

    public String cadastrarPessoaFisica(String nome, String email, String senha,
                                        String tipo, String cpf, String plano, String mensalidade) {
        return getServicoUsuarios().cadastrarPessoaFisica(
                nome, email, senha, tipo, cpf, plano, mensalidade);
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha,
                                          String tipo, String cnpj, String razaoSocial) {
        return getServicoUsuarios().cadastrarPessoaJuridica(
                nome, email, senha, tipo, cnpj, razaoSocial);
    }

    public String cadastrarPessoaJuridica(String nome, String email, String senha, String tipo,
                                          String cnpj, String razaoSocial,
                                          String plano, String mensalidade) {
        return getServicoUsuarios().cadastrarPessoaJuridica(
                nome, email, senha, tipo, cnpj, razaoSocial, plano, mensalidade);
    }

    public String getAtributoUsuario(String usuario, String atributo) {
        return getServicoUsuarios().getAtributo(usuario, atributo);
    }

    public String getUsuarioPorEmail(String email) {
        return getServicoUsuarios().getIdPorEmail(email);
    }

    public void removerUsuario(String id) {
        getServicoUsuarios().remover(id);
    }

    public void alterarUsuario(String id, String atributo, String valor) {
        getServicoUsuarios().alterar(id, atributo, valor);
    }

    public void alterarUsuario(String id, String atributo, String metodo,
                               String numero, String validade) {
        getServicoUsuarios().alterarMetodoPagamento(
                id, atributo, metodo, numero, validade);
    }

    public void alterarUsuario(String id, String atributo, String metodo, String chave) {
        getServicoUsuarios().alterarMetodoPagamento(
                id, atributo, metodo, chave);
    }

    public String cadastrarEstacao(String nome, String endereco, String capacidade) {
        return estacoes.cadastrar(nome, endereco, capacidade).getId();
    }

    public String getVagasDisponiveis(String estacao) {
        return String.valueOf(bicicletas.getVagasDisponiveis(estacao));
    }

    public String getBicicletasDisponiveis(String estacao) {
        return String.valueOf(bicicletas.getBicicletasDisponiveis(estacao));
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo) {
        bicicletas.cadastrar(estacao, bicicleta, tipo);
    }

    public void adicionarBicicleta(String estacao, String bicicleta, String tipo, String nivelBateria) {
        bicicletas.cadastrar(estacao, bicicleta, tipo, nivelBateria);
    }

    public void removerBicicleta(String estacao, String bicicleta) {
        bicicletas.remover(estacao, bicicleta);
    }

    public String getAtributoBicicleta(String bicicleta, String atributo) {
        return bicicletas.getAtributo(bicicleta, atributo);
    }

    public String getEstadoBicicleta(String bicicleta) {
        return bicicletas.getEstado(bicicleta);
    }

    public String retirarBicicleta(String usuarioId, String estacaoId,
                                   String bicicletaId, String data) {
        return getServicoAluguel().retirar(
                usuarioId, estacaoId, bicicletaId, data);
    }

    public String getAtributoAluguel(String aluguelId, String atributo) {
        return getServicoAluguel().getAtributoAluguel(
                aluguelId, atributo);
    }

    public void devolverBicicleta(String aluguelId, String estacaoId, String data) {
        getServicoAluguel().devolver(
                aluguelId, estacaoId, data);
    }
}