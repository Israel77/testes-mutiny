package org.example.rest.dto;

public record RequisicaoAgregadorDadosCliente(int codigoCliente) {
    public static class EntradaDTOBuilder {
        private int codigoCliente;

        public EntradaDTOBuilder codigoCliente(int codigoCliente) {
            this.codigoCliente = codigoCliente;
            return this;
        }

        public RequisicaoAgregadorDadosCliente build() {
            return new RequisicaoAgregadorDadosCliente(codigoCliente);
        }
    }

    public static EntradaDTOBuilder builder() {
        return new EntradaDTOBuilder();
    }
}
