package org.example.rest;

import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;
import org.example.service.sync.DadosClienteSincronoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@ApplicationScoped
@Path("/dados-clientes-sincrono")
public class DadosClienteSincrono {

    @Inject
    @RequestScoped
    DadosClienteSincronoService dadosClienteService;

    @POST
    public RespostaAgregadorDadosCliente obterDadosCliente(
            RequisicaoAgregadorDadosCliente requisicao) {
        return dadosClienteService.obterDadosCliente(requisicao);
    }
}
