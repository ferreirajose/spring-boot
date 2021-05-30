package com.jose.cursomc.domain.enums;

public enum EstadoPagamento {
    PENDENTE(1, "Pendente"),
    QUITADO(2, "Quitado"),
    CANCELAD(3, "Cancelado");


    private int code;
    private String descricao;

    private EstadoPagamento(int code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public int getCode() {
        return code;
    }

    public void setCod(int code) {
        this.code = code;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public static EstadoPagamento toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (EstadoPagamento x : EstadoPagamento.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("O parametro informado é inválido: " + code);

    }

}
