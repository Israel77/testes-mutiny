package org.example.service.async;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterDadosCliente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.netty.util.Timeout;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.TimeoutException;
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

    public Uni<RespostaAgregadorDadosCliente> obterDadosCliente(
            RequisicaoAgregadorDadosCliente requisicao) {

        var requisicaoDadosCliente = new DadosRequisicaoObterDadosCliente();
        requisicaoDadosCliente.setCodigoCliente(requisicao.codigoCliente());

        return consumidorCurio
                .obterDadosCliente(requisicaoDadosCliente)
                .map(dadosCliente -> {
                    var resposta = new RespostaAgregadorDadosCliente();

                    resposta.setTextoNomeCliente(dadosCliente.getTextoNomeCliente());
                    resposta.setDataNascimentoCliente(dadosCliente.getDataNascimentoCliente());
                    resposta.setTextoGeneroCliente(obterGeneroPorCodigo(dadosCliente.getCodigoGeneroCliente()));

                    return resposta;
                });
    }

}
