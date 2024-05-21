package org.example.integration.dto.resposta;

public class DadosRespostaObterCidadeCliente {
    private String textoNomeCidade;
    private String textoSiglaEstado;
    private String codigoEnderecoPostal;

    public DadosRespostaObterCidadeCliente() {}

    public String getTextoNomeCidade() {
        return textoNomeCidade;
    }

    public void setTextoNomeCidade(String textoNomeCidade) {
        this.textoNomeCidade = textoNomeCidade;
    }

    public String getTextoSiglaEstado() {
        return textoSiglaEstado;
    }

    public void setTextoSiglaEstado(String textoSiglaEstado) {
        this.textoSiglaEstado = textoSiglaEstado;
    }

    public String getCodigoEnderecoPostal() {
        return codigoEnderecoPostal;
    }

    public void setCodigoEnderecoPostal(String numeroCodigoPostal) {
        this.codigoEnderecoPostal = numeroCodigoPostal;
    }
}
