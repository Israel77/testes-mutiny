package org.example.integration.dto.resposta;

import java.math.BigDecimal;

public class DadosRespostaObterSaldoContaCorrente {
    private BigDecimal saldoAtualContaCorrente;

    public DadosRespostaObterSaldoContaCorrente() {}

    public BigDecimal getSaldoAtualContaCorrente() {
        return saldoAtualContaCorrente;
    }

    public void setSaldoAtualContaCorrente(BigDecimal saldoAtualContaCorrente) {
        this.saldoAtualContaCorrente = saldoAtualContaCorrente;
    }

}
