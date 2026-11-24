package br.ufal.ic.p2.bikeshare.aluguel;

import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelAindaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataNulaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelNaoEstaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataDevolucaoAnteriorException;
import br.ufal.ic.p2.bikeshare.util.Validador;
import br.ufal.ic.p2.bikeshare.util.Dinheiro;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Aluguel implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Pattern PADRAO_DATA =
            Pattern.compile("^([0-9]{1,2})/([0-9]{1,2})/([0-9]{4}) ([0-9]{1,2}):([0-9]{2})$");

    private final String id;
    private final String usuarioId;
    private final String bicicletaId;
    private final String estacaoOrigemId;
    private final LocalDateTime inicio;
    private String estado = "emAndamento";
    private LocalDateTime fim;
    private String estacaoDestinoId;
    private BigDecimal valorCobrado = BigDecimal.ZERO;

    public Aluguel(String id, String usuarioId, String bicicletaId,
                   String estacaoOrigemId, String data) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.inicio = validarData(data);
    }

    public static void exigirDataValida(String data) {
        validarData(data);
    }

    public static LocalDateTime lerData(String data) {
        return validarData(data);
    }

    private static LocalDateTime validarData(String data) {
        Validador.exigirTexto(data, DataNulaException::new);
        Matcher dados = PADRAO_DATA.matcher(data.trim());
        if (!dados.matches()) {
            throw new DataInvalidaException();
        }
        try {
            int dia = Integer.parseInt(dados.group(1));
            int mes = Integer.parseInt(dados.group(2));
            int ano = Integer.parseInt(dados.group(3));
            int hora = Integer.parseInt(dados.group(4));
            int minuto = Integer.parseInt(dados.group(5));
            return LocalDateTime.of(ano, mes, dia, hora, minuto);
        } catch (DateTimeException | NumberFormatException e) {
            throw new DataInvalidaException();
        }
    }

    public String getId() {
        return id;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public String getBicicletaId() {
        return bicicletaId;
    }

    public String getEstacaoOrigemId() {
        return estacaoOrigemId;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getInicio() {
        return inicio;
    }

    public void finalizar(String estacaoDestinoId, LocalDateTime fim, BigDecimal valor) {
        validarAndamento();
        if (fim.isBefore(inicio)) {
            throw new DataDevolucaoAnteriorException();
        }
        this.estado = "finalizado";
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
        this.valorCobrado = valor;
    }

    public void cancelar(String estacaoDestinoId, LocalDateTime fim) {
        validarAndamento();
        if (fim.isBefore(inicio)) {
            throw new DataDevolucaoAnteriorException();
        }
        this.estado = "cancelado";
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
    }

    public void expirar(String estacaoDestinoId, LocalDateTime fim) {
        validarAndamento();
        if (fim.isBefore(inicio)) {
            throw new DataDevolucaoAnteriorException();
        }
        this.estado = "expirado";
        this.estacaoDestinoId = estacaoDestinoId;
        this.fim = fim;
    }

    private void validarAndamento() {
        if (!"emAndamento".equals(estado)) {
            throw new AluguelNaoEstaEmAndamentoException();
        }
    }

    private static String formatarData(LocalDateTime data) {
        return data.getDayOfMonth() + "/" + data.getMonthValue() + "/"
                + data.getYear() + " " + data.getHour() + ":"
                + String.format("%02d", data.getMinute());
    }

    public String getAtributo(String atributo) {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return estado;
            case "usuario":
                return usuarioId;
            case "bicicleta":
                return bicicletaId;
            case "estacaoOrigem":
                return estacaoOrigemId;
            case "inicio":
                return inicio.getDayOfMonth() + "/" + inicio.getMonthValue() + "/"
                        + inicio.getYear() + " " + inicio.getHour() + ":" 
                        + String.format("%02d", inicio.getMinute());
            case "valorCobrado":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEmAndamentoException();
                }
                return Dinheiro.formatar(valorCobrado);
            case "fim":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEmAndamentoException();
                }
                return formatarData(fim);
            case "estacaoDestino":
                if ("emAndamento".equals(estado)) {
                    throw new AluguelAindaEmAndamentoException();
                }
                return estacaoDestinoId;
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
