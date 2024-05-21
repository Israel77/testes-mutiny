package org.example.service.async.operacoes;

import java.time.Duration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterUltimasTransacoes;
import org.example.integration.dto.resposta.DadosRespostaObterUltimasTransacoes;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ObterUltimasTransacoesService {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    public Uni<DadosRespostaObterUltimasTransacoes> tratarRequisicao(
            RequisicaoAgregadorDadosCliente requisicao) {

        var requisicaoObterUltimasTransacoes = new DadosRequisicaoObterUltimasTransacoes();
        requisicaoObterUltimasTransacoes.setCodigoCliente(requisicao.codigoCliente());

        return consumidorCurio
                .obterUltimasTransacoesCliente(requisicaoObterUltimasTransacoes)
                // Tratar timeout
                .ifNoItem().after(Duration.ofSeconds(5))
                .recoverWithItem(() -> {
                    Log.error("Timeout na operação: op123452");
                    return null;
                })
                // Tratar exceção lançada pelo Curio
                .onFailure(CurioConsumoException.class)
                .recoverWithItem(e -> {
                    Log.error("Erro ao obter dados do cliente: " + requisicao.codigoCliente());
                    return null;
                });
    }

    public void inserirDados(RespostaAgregadorDadosCliente resposta,
            DadosRespostaObterUltimasTransacoes dadosUltimasTransacoes) {
        if (dadosUltimasTransacoes == null)
            return;

        resposta.setListaTransacoes(dadosUltimasTransacoes.getListaTransacoes());
    }
}
