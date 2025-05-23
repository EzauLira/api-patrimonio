package br.com.carismapatrimonial.patrimonio.port.input.camera;

import java.awt.image.BufferedImage;

public interface IQRCode {

    String decodeQRCode(BufferedImage image);
}
