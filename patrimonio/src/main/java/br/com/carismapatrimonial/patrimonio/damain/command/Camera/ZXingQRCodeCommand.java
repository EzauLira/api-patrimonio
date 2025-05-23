package br.com.carismapatrimonial.patrimonio.damain.command.Camera;

import br.com.carismapatrimonial.patrimonio.port.input.camera.IQRCode;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import java.awt.image.BufferedImage;

public class ZXingQRCodeCommand implements IQRCode {

    @Override
    public String decodeQRCode(BufferedImage image) {
        if (image == null) return null;
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        try {
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (NotFoundException e) {
            return null;
        }
    }
}
