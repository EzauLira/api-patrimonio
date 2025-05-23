package br.com.carismapatrimonial.patrimonio.damain.command.Camera;

import br.com.carismapatrimonial.patrimonio.port.input.camera.ICamera;
import com.github.sarxos.webcam.Webcam;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.image.BufferedImage;

public class CameraCommand implements ICamera {

    @Autowired
    VideoCapture videoCapture;

    @Autowired
    Webcam webcam;

    @Override
    public BufferedImage captureFrame() {
        if (webcam != null && webcam.isOpen()) {
            return webcam.getImage();
        }
        return null;
    }

//    @Override
//    public BufferedImage captureFrame() {
//        Mat frame = new Mat();
//        if (videoCapture.read(frame)) {
//            Mat converted = new Mat();
//            Imgproc.cvtColor(frame, converted, Imgproc.COLOR_BGR2RGB);
//            byte[] data = new byte[converted.rows() * converted.cols() * (int)converted.elemSize()];
//            converted.get(0, 0, data);
//            BufferedImage image = new BufferedImage(converted.cols(), converted.rows(), BufferedImage.TYPE_3BYTE_BGR);
//            image.getRaster().setDataElements(0, 0, converted.cols(), converted.rows(), data);
//            return image;
//        }
//        return null;
//    }
}
