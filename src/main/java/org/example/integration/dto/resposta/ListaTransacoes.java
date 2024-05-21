package org.example.integration.dto.resposta;

import java.math.BigDecimal;

public class ListaTransacoes {
    private String codigoTransacao;
    private BigDecimal valorTransacao;
    private String dataTransacao;

    public ListaTransacoes() {}

    public String getCodigoTransacao() {
        return codigoTransacao;
    }

    public void setCodigoTransacao(String numeroTransacao) {
        this.codigoTransacao = numeroTransacao;
    }

    public BigDecimal getValorTransacao() {
        return valorTransacao;
    }

    public void setValorTransacao(BigDecimal valorTransacao) {
        this.valorTransacao = valorTransacao;
    }

    public String getDataTransacao() {
        return dataTransacao;
    }

    public void setDataTransacao(String dataTransacao) {
        this.dataTransacao = dataTransacao;
    }
}

