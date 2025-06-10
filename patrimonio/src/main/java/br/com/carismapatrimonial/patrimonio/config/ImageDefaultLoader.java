package br.com.carismapatrimonial.patrimonio.config;

import java.util.Base64;

public class ImageDefaultLoader {

    /**
     * Retorna uma imagem PNG padrão em formato byte[]
     * @return byte[] - Imagem padrão em bytes
     */
    public static byte[] getDefaultProductImageBytes() {
        String base64Default = "iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mP8z8BQDwAEhQGAHESMIAAAAABJRU5ErkJggg==";
        return Base64.getDecoder().decode(base64Default);
    }
}
