package org.example.service.async.operacoes;

import io.smallrye.mutiny.Uni;

public interface ConsumidorOperacao<RequisicaoRest, RespostaRest, RespostaCurio> {

    /*
     * Gera o Uni correspondente ao consumo do curió
     */
    public Uni<RespostaCurio> tratarRequisicao(RequisicaoRest requisicaoRest);

    /*
     * Insere os dados na resposta (side effect) a partir do que foi obtido na
     * operação
     */
    public void inserirDados(RespostaRest respostaRest, RespostaCurio respostaCurio);
}