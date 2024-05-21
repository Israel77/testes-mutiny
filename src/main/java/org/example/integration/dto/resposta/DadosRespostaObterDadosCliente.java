package org.example.integration.dto.resposta;

public class DadosRespostaObterDadosCliente {
    private String textoNomeCliente;
    private String dataNascimentoCliente;
    private int codigoGeneroCliente;

    public DadosRespostaObterDadosCliente() {}

    public String getTextoNomeCliente() {
        return textoNomeCliente;
    }

    public void setTextoNomeCliente(String textoNome) {
        this.textoNomeCliente = textoNome;
    }

    public String getDataNascimentoCliente() {
        return dataNascimentoCliente;
    }

    public void setDataNascimentoCliente(String textoDataNascimento) {
        this.dataNascimentoCliente = textoDataNascimento;
    }

    public int getCodigoGeneroCliente() {
        return codigoGeneroCliente;
    }

    public void setCodigoGeneroCliente(int textoSexo) {
        this.codigoGeneroCliente = textoSexo;
    }
}
