package com.jose.cursomc.resources.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

public class URL {

    public static String decodeParam(String s) {
        try {
           return URLDecoder.decode(s, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return "Não foi possivel decodificar paramteto";
        }
    }

    public static  List<Integer> decodeIntList(String s) {
        String [] vet = s.split(",");
        List<Integer> list = new ArrayList<>();

        for (int i = 0; i < vet.length; i++) {  
            list.add(Integer.parseInt(vet[i]));
        }

        return list;

        /** 
         * Utilizando lamba calculos, ou conceito de programação funcional
        */
       // return Arrays.asList(s.split(",")).stream().map(item -> Integer.parseInt(item)).collect(Collectors.toList());
    }
}
