package org.example.service.async.operacoes;

import java.time.Duration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterSaldoContaCorrente;
import org.example.integration.dto.resposta.DadosRespostaObterSaldoContaCorrente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ObterSaldoContaService
        implements
        ConsumidorOperacao<RequisicaoAgregadorDadosCliente, RespostaAgregadorDadosCliente, DadosRespostaObterSaldoContaCorrente> {
    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    @Override
    public Uni<DadosRespostaObterSaldoContaCorrente> tratarRequisicao(
            RequisicaoAgregadorDadosCliente requisicao) {
        var requisicaoObterSaldoConta = new DadosRequisicaoObterSaldoContaCorrente();
        requisicaoObterSaldoConta.setCodigoCliente(requisicao.codigoCliente());

        return consumidorCurio
                .obterSaldoContaCorrenteCliente(requisicaoObterSaldoConta)
                // Tratar timeout
                .ifNoItem().after(Duration.ofSeconds(5))
                .recoverWithItem(() -> {
                    Log.error("Timeout na operação: op123452");
                    return new DadosRespostaObterSaldoContaCorrente();
                })
                // Tratar exceção lançada pelo Curio
                .onFailure(CurioConsumoException.class)
                .recoverWithItem(e -> {
                    Log.error("Erro ao obter dados do cliente: " + requisicao.codigoCliente());
                    return new DadosRespostaObterSaldoContaCorrente();
                });
    }

    @Override
    public void inserirDados(RespostaAgregadorDadosCliente resposta,
            DadosRespostaObterSaldoContaCorrente dadosSaldoConta) {

        resposta.setSaldoAtualContaCorrente(dadosSaldoConta.getSaldoAtualContaCorrente());
    }
}
