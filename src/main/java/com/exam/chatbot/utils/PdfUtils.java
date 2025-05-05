package com.exam.chatbot.utils;


import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class PdfUtils {
//    public static String extractText(InputStream input) throws IOException {
//        try (PDDocument document = PDDocument.load(input)) {
//            PDFTextStripper stripper = new PDFTextStripper();
//            return stripper.getText(document);
//        }
//    }
//
//    public static Map<Integer, String> extractImages(InputStream input, String outputFolder) throws IOException {
//        Map<Integer, String> imageMap = new HashMap<>();
//        try (PDDocument document = PDDocument.load(input)) {
//            PDFRenderer renderer = new PDFRenderer(document);
//            for (int i = 0; i < document.getNumberOfPages(); i++) {
//                BufferedImage image = renderer.renderImageWithDPI(i, 150);
//                String filename = "page" + i + "_img0.png";
//                File outputFile = new File(outputFolder, filename);
//                ImageIO.write(image, "png", outputFile);
//                imageMap.put(i, filename);
//            }
//        }
//        return imageMap;
//    }
}
