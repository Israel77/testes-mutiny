package org.example.integration;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;
import org.example.integration.dto.requisicao.DadosRequisicaoObterCidadeCliente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterDadosCliente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterSaldoContaCorrente;
import org.example.integration.dto.requisicao.DadosRequisicaoObterUltimasTransacoes;
import org.example.integration.dto.resposta.DadosRespostaObterCidadeCliente;
import org.example.integration.dto.resposta.DadosRespostaObterDadosCliente;
import org.example.integration.dto.resposta.DadosRespostaObterSaldoContaCorrente;
import org.example.integration.dto.resposta.DadosRespostaObterUltimasTransacoes;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@RegisterRestClient(configKey = "curio-host")
@ApplicationScoped
public interface ConsumidorCurio {

        // Operação para obter dados do cliente
        @Path("/op123450")
        @POST
        Uni<DadosRespostaObterDadosCliente> obterDadosCliente(
                        DadosRequisicaoObterDadosCliente requisicao);

        // Operação para obter cidade do cliente
        @Path("/op123451")
        @POST
        Uni<DadosRespostaObterCidadeCliente> obterCidadeCliente(
                        DadosRequisicaoObterCidadeCliente requisicao);

        // Operação para obter saldo da conta corrente do cliente
        @Path("/op123452")
        @POST
        Uni<DadosRespostaObterSaldoContaCorrente> obterSaldoContaCorrenteCliente(
                        DadosRequisicaoObterSaldoContaCorrente requisicao);


        // Operação para obter últimas transações do cliente
        @Path("/op123453")
        @POST
        Uni<DadosRespostaObterUltimasTransacoes> obterUltimasTransacoesCliente(
                        DadosRequisicaoObterUltimasTransacoes requisicao);
}
