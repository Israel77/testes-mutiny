package org.example.service.async;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterCidadeCliente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterDadosCliente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import static org.example.util.Utilidades.obterGeneroPorCodigo;

import java.time.Duration;

@RequestScoped
public class DadosClienteAssincronoService {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    public Uni<RespostaAgregadorDadosCliente> tratarRequisicao(
            RequisicaoAgregadorDadosCliente requisicao) {

        var requisicaoDadosCliente = new DadosRequisicaoObterDadosCliente();
        requisicaoDadosCliente.setCodigoCliente(requisicao.codigoCliente());

        var dadosClienteUni = consumidorCurio
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

        var requisicaoCidadeCliente = new DadosRequisicaoObterCidadeCliente();
        requisicaoCidadeCliente.setCodigoCliente(requisicao.codigoCliente());

        var dadosCidadeClienteUni = consumidorCurio
                .obterCidadeCliente(requisicaoCidadeCliente)
                // Tratar timeout
                .ifNoItem().after(Duration.ofSeconds(5))
                .recoverWithItem(() -> {
                    Log.error("Timeout na operação: op123451");
                    return null;
                })
                // Tratar exceção lançada pelo Curio
                .onFailure(CurioConsumoException.class)
                .recoverWithItem(e -> {
                    Log.error("Erro ao obter dados do cliente: " + requisicao.codigoCliente());
                    return null;
                });

        return Uni.combine().all().unis(dadosClienteUni, dadosCidadeClienteUni).asTuple()
                .map(tuple -> {
                    var resposta = new RespostaAgregadorDadosCliente();

                    var respostaDadosCliente = tuple.getItem1();
                    var respostaCidadeCliente = tuple.getItem2();

                    if (respostaDadosCliente != null) {
                        resposta.setTextoNomeCliente(respostaDadosCliente.getTextoNomeCliente());
                        resposta.setDataNascimentoCliente(respostaDadosCliente.getDataNascimentoCliente());
                        resposta.setTextoGeneroCliente(
                                obterGeneroPorCodigo(respostaDadosCliente.getCodigoGeneroCliente()));
                    }

                    if (respostaCidadeCliente != null) {
                        resposta.setTextoNomeCidade(respostaCidadeCliente.getTextoNomeCidade());
                        resposta.setTextoSiglaEstado(respostaCidadeCliente.getTextoSiglaEstado());
                        resposta.setCodigoEnderecoPostal(respostaCidadeCliente.getCodigoEnderecoPostal());
                    }

                    return resposta;
                });
    }

}
