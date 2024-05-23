package org.example.service.async.operacoes;

import java.time.Duration;

import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterCidadeCliente;
import org.example.integration.dto.resposta.DadosRespostaObterCidadeCliente;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

import io.quarkus.logging.Log;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class ObterCidadeClienteService
        implements
        ConsumidorOperacao<RequisicaoAgregadorDadosCliente, RespostaAgregadorDadosCliente, DadosRespostaObterCidadeCliente> {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    @Override
    public Uni<DadosRespostaObterCidadeCliente> tratarRequisicao(RequisicaoAgregadorDadosCliente requisicao) {
        var requisicaoCidadeCliente = new DadosRequisicaoObterCidadeCliente();
        requisicaoCidadeCliente.setCodigoCliente(requisicao.codigoCliente());

        return consumidorCurio
                .obterCidadeCliente(requisicaoCidadeCliente)
                // Tratar timeout
                .ifNoItem().after(Duration.ofSeconds(5))
                .recoverWithItem(() -> {
                    Log.error("Timeout na operação: op123451");
                    return new DadosRespostaObterCidadeCliente();
                })
                // Tratar exceção lançada pelo Curio
                .onFailure(CurioConsumoException.class)
                .recoverWithItem(e -> {
                    Log.error("Erro ao obter dados do cliente: " + requisicao.codigoCliente());
                    return new DadosRespostaObterCidadeCliente();
                });
    }

    @Override
    public void inserirDados(RespostaAgregadorDadosCliente resposta,
            DadosRespostaObterCidadeCliente dadosCidadeCliente) {

        resposta.setTextoNomeCidade(dadosCidadeCliente.getTextoNomeCidade());
        resposta.setTextoSiglaEstado(dadosCidadeCliente.getTextoSiglaEstado());
        resposta.setCodigoEnderecoPostal(dadosCidadeCliente.getCodigoEnderecoPostal());
    }

}
