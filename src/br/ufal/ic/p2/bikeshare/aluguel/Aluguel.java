package br.ufal.ic.p2.bikeshare.aluguel;

import br.ufal.ic.p2.bikeshare.exception.aluguel.AluguelAindaEmAndamentoException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataInvalidaException;
import br.ufal.ic.p2.bikeshare.exception.aluguel.DataNulaException;
import br.ufal.ic.p2.bikeshare.exception.validacao.AtributoNaoExisteException;
import br.ufal.ic.p2.bikeshare.util.Validador;

import java.io.Serializable;
import java.time.DateTimeException;
import java.time.LocalDateTime;
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

    public Aluguel(String id, String usuarioId, String bicicletaId,
                   String estacaoOrigemId, String data) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.bicicletaId = bicicletaId;
        this.estacaoOrigemId = estacaoOrigemId;
        this.inicio = validarData(data);
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

    public String getAtributo(String atributo) {
        if (atributo == null) {
            throw new AtributoNaoExisteException();
        }
        switch (atributo) {
            case "estado":
                return "emAndamento";
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
            case "fim":
            case "estacaoDestino":
                throw new AluguelAindaEmAndamentoException();
            default:
                throw new AtributoNaoExisteException();
        }
    }
}
