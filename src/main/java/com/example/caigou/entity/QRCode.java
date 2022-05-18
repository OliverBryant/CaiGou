package com.example.caigou.entity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;
import java.util.Hashtable;

public class QRCode {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";

    private static final int QRCODE_SIZE = 300;

    private static final int WIDTH = 60;
    private static final int HEIGHT = 60;

    private static BufferedImage createImage(String content, String imgPath, boolean needCompress) throws Exception {

        Hashtable hashtable = new Hashtable();
        hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hashtable.put(EncodeHintType.CHARACTER_SET, CHARSET);
        hashtable.put(EncodeHintType.MARGIN, 1);

        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hashtable);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                image.setRGB(i, j, bitMatrix.get(i, j) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }

        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }

        QRCode.insertImage(image, imgPath, needCompress);
        return image;

    }

    private static void insertImage(BufferedImage source, String imgPath, boolean needCompress) throws Exception {
        File file = new File(imgPath);
        if (!file.exists()) {
            System.err.println("" + imgPath + "Can't find the file!");
            return;
        }

        Image src = ImageIO.read(new File(imgPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }

        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src,x,y,width,height,null);
        Shape shape = new RoundRectangle2D.Float(x,y,width,height,6,6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    public static void encode(String content, String imgPath, String destPath, boolean needCompress) throws Exception{
        BufferedImage image = QRCode.createImage(content,imgPath,needCompress);
        mkdirs(destPath);
        ImageIO.write(image,FORMAT_NAME,new File(destPath));
    }

    public static BufferedImage encode(String content, String imgPath, boolean needCompress) throws Exception{
        BufferedImage image = QRCode.createImage(content,imgPath,needCompress);
        return image;
    }

    private static void mkdirs(String destPath) {
        File file = new File(destPath);
        if(!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    public static void encode (String content, String imgPath, OutputStream output, boolean needCompress) throws Exception{
        BufferedImage image = QRCode.createImage(content,imgPath,needCompress);
        ImageIO.write(image,FORMAT_NAME,output);
    }

    public static void encode(String content, OutputStream output) throws Exception{
        QRCode.encode(content,null,output,false);
    }
}
