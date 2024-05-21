package org.example.service.async.operacoes;

import java.time.Duration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterDadosCliente;
import org.example.integration.dto.resposta.DadosRespostaObterDadosCliente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import static org.example.util.Utilidades.obterGeneroPorCodigo;

@RequestScoped
public class ObterDadosClienteService {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    public Uni<DadosRespostaObterDadosCliente> tratarRequisicao(RequisicaoAgregadorDadosCliente requisicao) {

        var requisicaoDadosCliente = new DadosRequisicaoObterDadosCliente();
        requisicaoDadosCliente.setCodigoCliente(requisicao.codigoCliente());

        return consumidorCurio
                .obterDadosCliente(requisicaoDadosCliente)
                // Tratar timeout
                .ifNoItem().after(Duration.ofSeconds(5))
                .recoverWithItem(() -> {
                    Log.error("Timeout na operação: op123450");
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
            DadosRespostaObterDadosCliente dadosCliente) {

        if (dadosCliente == null)
            return;

        resposta.setTextoNomeCliente(dadosCliente.getTextoNomeCliente());
        resposta.setDataNascimentoCliente(dadosCliente.getDataNascimentoCliente());
        resposta.setTextoGeneroCliente(
                obterGeneroPorCodigo(dadosCliente.getCodigoGeneroCliente()));
    }
}
