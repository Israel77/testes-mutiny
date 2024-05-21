package org.example.service.async;

import java.util.List;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.dto.resposta.DadosRespostaObterCidadeCliente;
import org.example.integration.dto.resposta.DadosRespostaObterDadosCliente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;
import org.example.service.async.operacoes.ObterCidadeClienteService;
import org.example.service.async.operacoes.ObterDadosClienteService;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class DadosClienteAssincronoService {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    @Inject
    ObterCidadeClienteService obterCidadeClienteService;

    @Inject
    ObterDadosClienteService obterDadosClienteService;

    public Uni<RespostaAgregadorDadosCliente> tratarRequisicao(
            RequisicaoAgregadorDadosCliente requisicao) {

        List<Uni<?>> unis = List.of(
                obterDadosClienteService.tratarRequisicao(requisicao),
                obterCidadeClienteService.tratarRequisicao(requisicao));

        return Uni.combine().all().unis(unis)
                .with(listaDeRespostas -> {
                    var resposta = new RespostaAgregadorDadosCliente();

                    obterDadosClienteService.inserirDados(resposta,
                            (DadosRespostaObterDadosCliente) listaDeRespostas.get(0));

                    obterCidadeClienteService.inserirDados(resposta,
                            (DadosRespostaObterCidadeCliente) listaDeRespostas.get(1));

                    return resposta;
                });
    }

}
