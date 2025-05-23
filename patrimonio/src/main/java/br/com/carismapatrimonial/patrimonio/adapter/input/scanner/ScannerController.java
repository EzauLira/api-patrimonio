package br.com.carismapatrimonial.patrimonio.adapter.input.scanner;

import br.com.carismapatrimonial.patrimonio.port.input.camera.ICamera;
import br.com.carismapatrimonial.patrimonio.port.input.camera.IQRCode;
import org.springframework.beans.factory.annotation.Autowired;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class ScannerController implements IScannerController {

    @Autowired
    ICamera iCameraService;

    @Autowired
    IQRCode iqrCodeService;


    public void abrirScanner(JFrame frame, JLabel label) {
        new Thread(() -> {
            while (true) {
                BufferedImage image = iCameraService.captureFrame();
                if (image != null) {
                    label.setIcon(new ImageIcon(image));
                    String decoded = iqrCodeService.decodeQRCode(image);
                    if (decoded != null) {
                        System.out.println("Código lido: " + decoded);

                        // CHAMA O ENDPOINT JÁ EXISTENTE!
                        // Por exemplo:
                        enviarQRCodeParaApi(decoded);

                        JOptionPane.showMessageDialog(frame, "QR Code: " + decoded);
                        break;
                    }
                }
            }
        }).start();
    }

    private void enviarQRCodeParaApi(String codigo) {
        // Aqui você chama seu endpoint REST com o código lido
        // usando HttpClient, RestTemplate, WebClient, etc.
    }

}
