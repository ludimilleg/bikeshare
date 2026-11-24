package br.ufal.ic.p2.bikeshare.usuario;

import br.ufal.ic.p2.bikeshare.exception.usuario.EmailInvalidoException;
import br.ufal.ic.p2.bikeshare.exception.usuario.EmailNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.NomeNuloException;
import br.ufal.ic.p2.bikeshare.exception.usuario.SenhaNulaException;
import br.ufal.ic.p2.bikeshare.exception.usuario.UsuarioNaoEhPessoaFisicaException;
import br.ufal.ic.p2.bikeshare.exception.usuario.UsuarioNaoEhPessoaJuridicaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.pagamento.MetodoPagamento;
import br.ufal.ic.p2.bikeshare.pagamento.PagamentoSaldoPreCarregado;
import br.ufal.ic.p2.bikeshare.pagamento.SemMetodoPagamento;
import br.ufal.ic.p2.bikeshare.exception.aluguel.MetodoPagamentoNaoCadastradoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.SaldoInsuficienteException;
import br.ufal.ic.p2.bikeshare.plano.FabricaPlano;
import br.ufal.ic.p2.bikeshare.plano.Plano;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.regex.Pattern;

public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 4910641543355373962L;

    private static final Pattern PADRAO_EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+$");

    private final String id;
    private String nome;
    private String email;
    private String senha;
    private Plano plano;
    private MetodoPagamento metodoPagamento;
    private PagamentoSaldoPreCarregado saldoPreCarregado;
    private final EstadoUsuario estado;
    private String aluguelAtivoId;

    protected Usuario(String id, String nome, String email, String senha, Plano plano) {
        this.id = id;
        this.nome = Validador.exigirTexto(nome, NomeNuloException::new);
        this.email = validarEmail(email);
        this.senha = Validador.exigirTexto(senha, SenhaNulaException::new);
        this.plano = plano;
        this.metodoPagamento = new SemMetodoPagamento();
        this.estado = EstadoUsuario.ATIVO;
    }

    static String validarEmail(String email) {
        Validador.exigirTexto(email, EmailNuloException::new);
        return Validador.exigirFormato(email, PADRAO_EMAIL, EmailInvalidoException::new);
    }

    public void alterarNome(String nome) {
        this.nome = Validador.exigirTexto(nome, NomeNuloException::new);
    }

    public void alterarSenha(String senha) {
        this.senha = Validador.exigirTexto(senha, SenhaNulaException::new);
    }

    public void alterarPlano(String nome) {
        if (!plano.getNome().equals(nome)) {
            plano = FabricaPlano.criarComMensalidadePadrao(nome);
        }
    }

    public void alterarMetodoPagamento(MetodoPagamento metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public void ativarSaldoPreCarregado() {
        if (saldoPreCarregado == null) {
            saldoPreCarregado = new PagamentoSaldoPreCarregado();
        }
        metodoPagamento = saldoPreCarregado;
    }

    public void adicionarSaldo(String valor) {
        metodoPagamento.adicionarSaldo(valor);
    }

    public String getAluguelAtivoId() {
        return aluguelAtivoId;
    }

    public void iniciarAluguel(String aluguelId) {
        this.aluguelAtivoId = aluguelId;
    }

    public void finalizarAluguel() {
        this.aluguelAtivoId = null;
    }

    public void validarMetodoPagamentoParaRetirada() {
        String nome = metodoPagamento.getNome();
        if ("nenhum".equals(nome)) {
            throw new MetodoPagamentoNaoCadastradoException();
        }
        if ("saldoPreCarregado".equals(nome)
                && !((PagamentoSaldoPreCarregado) metodoPagamento).possuiSaldo()) {
            throw new SaldoInsuficienteException();
        }
    }

    void alterarEmailValidado(String email) {
        this.email = email;
    }

    public abstract TipoUsuario getTipo();

    public String getCpf() {
        throw new UsuarioNaoEhPessoaFisicaException();
    }

    public String getCnpj() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getRazaoSocial() {
        throw new UsuarioNaoEhPessoaJuridicaException();
    }

    public String getAtributo(String atributo) {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "nome":
                return nome;
            case "email":
                return email;
            case "senha":
                return senha;
            case "tipo":
                return getTipo().getNome();
            case "estado":
                return estado.getNome();
            case "plano":
                return plano.getNome();
            case "mensalidade":
                return Dinheiro.formatar(plano.getMensalidade());
            case "aluguelAtivo":
                return aluguelAtivoId == null ? "nenhum" : aluguelAtivoId;
            case "totalPendencias":
                return Dinheiro.formatar(BigDecimal.ZERO);
            case "metodoPagamento":
                return metodoPagamento.getNome();
            case "cpf":
                return getCpf();
            case "cnpj":
                return getCnpj();
            case "razaoSocial":
                return getRazaoSocial();
            case "numeroCartao":
            case "validadeCartao":
            case "chavePix":
            case "saldo":
                return metodoPagamento.getAtributo(atributo);
            default:
                throw new AtributoNaoExisteException();
        }
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}