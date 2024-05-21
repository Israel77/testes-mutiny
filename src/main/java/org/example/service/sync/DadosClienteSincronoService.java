package org.example.service.sync;

import java.time.Duration;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.example.integration.ConsumidorCurio;
import org.example.integration.CurioConsumoException;
import org.example.integration.dto.requisicao.DadosRequisicaoObterCidadeCliente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterDadosCliente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterSaldoContaCorrente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterUltimasTransacoes;
import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;
import io.netty.handler.timeout.TimeoutException;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import static org.example.util.Utilidades.obterGeneroPorCodigo;

@RequestScoped
public class DadosClienteSincronoService {

    @Inject
    @RestClient
    ConsumidorCurio consumidorCurio;

    RequisicaoAgregadorDadosCliente requisicao;
    RespostaAgregadorDadosCliente resposta;


    public RespostaAgregadorDadosCliente obterDadosCliente(
            RequisicaoAgregadorDadosCliente requisicao) {

        this.requisicao = requisicao;
        resposta = new RespostaAgregadorDadosCliente();

        buscarDadosCliente();
        buscarCidadeCliente();
        buscarSaldoContaCorrente();
        buscarUltimasTransacoes();


        return resposta;
    }

    private void buscarDadosCliente() {
        final Duration TIMEOUT_OPERACAO = Duration.ofSeconds(5);

        DadosRequisicaoObterDadosCliente requisicaoCurio = new DadosRequisicaoObterDadosCliente();
        requisicaoCurio.setCodigoCliente(requisicao.codigoCliente());

        try {
            var respostaCurio = consumidorCurio.obterDadosCliente(requisicaoCurio).await()
                    .atMost(TIMEOUT_OPERACAO);

            resposta.setDataNascimentoCliente(respostaCurio.getDataNascimentoCliente());
            resposta.setTextoNomeCliente(respostaCurio.getTextoNomeCliente());
            resposta.setTextoGeneroCliente(
                    obterGeneroPorCodigo(respostaCurio.getCodigoGeneroCliente()));
        } catch (CurioConsumoException e) {
            Log.error("Erro ao obter dados do cliente: " + requisicao.codigoCliente());
        } catch (TimeoutException e) {
            Log.error("Timeout na operação: op123450");
        }
    }

    private void buscarCidadeCliente() {
        final Duration TIMEOUT_OPERACAO = Duration.ofSeconds(5);

        DadosRequisicaoObterCidadeCliente requisicaoCurio = new DadosRequisicaoObterCidadeCliente();
        requisicaoCurio.setCodigoCliente(requisicao.codigoCliente());

        try {
            var respostaCurio = consumidorCurio.obterCidadeCliente(requisicaoCurio).await()
                    .atMost(TIMEOUT_OPERACAO);

            resposta.setTextoNomeCidade(respostaCurio.getTextoNomeCidade());
            resposta.setTextoSiglaEstado(respostaCurio.getTextoSiglaEstado());
            resposta.setCodigoEnderecoPostal(respostaCurio.getCodigoEnderecoPostal());
        } catch (CurioConsumoException e) {
            Log.error("Erro ao obter cidade do cliente: " + requisicao.codigoCliente());
        } catch (TimeoutException e) {
            Log.error("Timeout na operação: op123451");
        }
    }

    private void buscarSaldoContaCorrente() {
        final Duration TIMEOUT_OPERACAO = Duration.ofSeconds(5);

        var requisicaoCurio = new DadosRequisicaoObterSaldoContaCorrente();
        requisicaoCurio.setCodigoCliente(requisicao.codigoCliente());

        try {
            var respostaCurio = consumidorCurio.obterSaldoContaCorrenteCliente(requisicaoCurio)
                    .await().atMost(TIMEOUT_OPERACAO);

            resposta.setSaldoAtualContaCorrente(respostaCurio.getSaldoAtualContaCorrente());
        } catch (CurioConsumoException e) {
            Log.error("Erro ao obter saldo da conta corrente do cliente: "
                    + requisicao.codigoCliente());
        } catch (TimeoutException e) {
            Log.error("Timeout na operação: op123452");
        }
    }

    private void buscarUltimasTransacoes() {
        final Duration TIMEOUT_OPERACAO = Duration.ofSeconds(5);

        var requisicaoCurio = new DadosRequisicaoObterUltimasTransacoes();
        requisicaoCurio.setCodigoCliente(requisicao.codigoCliente());

        try {
            var respostaCurio = consumidorCurio.obterUltimasTransacoesCliente(requisicaoCurio)
                    .await().atMost(TIMEOUT_OPERACAO);

            resposta.setListaTransacoes(respostaCurio.getListaTransacoes());
        } catch (CurioConsumoException e) {
            Log.error("Erro ao obter últimas transações do cliente: " + requisicao.codigoCliente());
        } catch (TimeoutException e) {
            Log.error("Timeout na operação: op123453");
        }
    }
}
