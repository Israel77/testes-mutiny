package org.example.service;

import org.example.rest.dto.RequisicaoAgregadorDadosCliente;
import org.example.rest.dto.RespostaAgregadorDadosCliente;

@FunctionalInterface
public interface ProvedorRespostaDadosCliente {
    public RespostaAgregadorDadosCliente obterDadosCliente(
            RequisicaoAgregadorDadosCliente requisicao);
}
