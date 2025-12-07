package com.pece.agencia.api.veiculo;

public class PlataformaLocacaoVeiculoOfflineException extends Exception {
    public PlataformaLocacaoVeiculoOfflineException(Throwable cause) {
        super("Erro de conectividade com a plataforma de locacao de veiculo", cause);
    }
}
