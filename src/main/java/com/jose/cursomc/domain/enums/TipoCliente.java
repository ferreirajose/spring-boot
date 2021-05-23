package com.jose.cursomc.domain.enums;

public enum TipoCliente {
    PF(1, "Pessoa Física"),
    PJ(2, "Pessoa Jurídica");

    private int code;
    private String descricao;

    private TipoCliente(int code, String descricao) {
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


    public static TipoCliente toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (TipoCliente x : TipoCliente.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("O parametro informado é inválido: " + code);

    }
    

}
