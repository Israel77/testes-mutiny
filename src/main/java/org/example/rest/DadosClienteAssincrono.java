package org.example.rest;

import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;
import org.example.service.async.DadosClienteAssincronoService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/dados-clientes-assincrono")
public class DadosClienteAssincrono {

    @Inject
    DadosClienteAssincronoService dadosClienteService;

    @POST
    public Uni<RespostaAgregadorDadosCliente> obterDadosCliente(
            RequisicaoAgregadorDadosCliente requisicao) {
        return dadosClienteService.obterDadosCliente(requisicao);
    }
}
