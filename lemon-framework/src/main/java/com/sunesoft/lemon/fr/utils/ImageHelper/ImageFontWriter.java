package com.sunesoft.lemon.fr.utils.ImageHelper;

//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//import com.sun.image.codec.jpeg.JPEGImageDecoder;
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.geom.Rectangle2D;
//import java.awt.image.BufferedImage;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.List;

/**
 * Created by zhaowy on 2017/3/15.
 */
public class ImageFontWriter {
//    private JPanel jPanel;
//
//    public static void jpgWrite(String inPath, String outPath, List<ImagePlaceholder> placeholders) throws IOException {
//
//        String imageFile = inPath;
//        String tmpFile = imageFile.toLowerCase();
//        String outputFile = outPath;
//        if (tmpFile.endsWith(".jpeg") || tmpFile.endsWith(".jpg")) {
//            InputStream imageIn = new FileInputStream(new File(imageFile));
//            JPEGImageDecoder decoder = JPEGCodec.createJPEGDecoder(imageIn);
//            BufferedImage image = decoder.decodeAsBufferedImage();
//            JPEGEncodeParam param = (JPEGEncodeParam) decoder.getJPEGDecodeParam();
//
//            param.setQuality(1.0F, true);
//            Graphics g = image.getGraphics();
//            decoder.setJPEGDecodeParam(param);
//            for (ImagePlaceholder placeholder : placeholders) {
//
//                g.setColor(placeholder.getColor());
//                Font font= placeholder.getFont();
//                System.out.println("字体大小"+font.getSize());
//                g.setFont(placeholder.getFont());
//                FontMetrics metrics = new FontMetrics(font){};
//                String text =placeholder.getText();
//                Rectangle2D bounds = metrics.getStringBounds(text, null);
//                int textWidth = (int) bounds.getWidth();
//                int textHeight = (int) bounds.getHeight();
//                int lineFontSize = placeholder.getWidth()/font.getSize();
//
//                if(textWidth > placeholder.getWidth())//换行
//                {
//                    Integer lineCount = textWidth / placeholder.getWidth()+1;//行数计算
//                    System.out.println("文字共"+lineCount+"行");
//                    Integer indexSign = 0;
//                    for(int i =0;i<lineCount;i++)
//                    {
//                        Integer endIndex=lineFontSize*(i+1);
//                        if(endIndex>text.length())
//                            endIndex=text.length();
//                        if(indexSign==text.length()) break;
//                        String tmpText = text.substring(indexSign,endIndex);
//                        indexSign= endIndex;
//                        System.out.println(tmpText);
//                        g.drawString(tmpText, placeholder.getX(), placeholder.getY()+i*font.getSize()+10);
//
//                    }
//                }
//                else {//一行
//                    g.drawString(placeholder.getText(), placeholder.getX(), placeholder.getY());
//                }
//                    int left = 0;
//                int top = textHeight;
//                //int width= placeholder.getSize();
//                //int height =
//                //System.out.println(textWidth);
//               // System.out.println(textHeight);
//                //g.drawString(placeholder.getText(), left, top);
//                //g.fillRect(0, 0, 20, 100); // 画一个矩形
//            }
//            OutputStream output = new FileOutputStream(outputFile);
//            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(output);
//            encoder.encode(image);
//            imageIn.close();
//            output.close();
//        }
//    }
//
//    public static void main(String[] args){
////        String imagePath="C:\\Users\\zhaowy\\Desktop\\1.jpg";
//        String imagePath = "/images/dq_lc03.jpg";
//        String outPath ="C:\\Users\\zhaowy\\Desktop\\new1.jpg";
//        List<ImagePlaceholder>placeholders = new ArrayList<>();
//        ImagePlaceholder placeholder1 = new ImagePlaceholder(
//                165,
//                237,
//                "    一二三四五六七八九十一二三12345667889933423\"4\"四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十一二三四五六七八九十",
//                Color.red,
//                new Font("微软雅黑",Font.LAYOUT_RIGHT_TO_LEFT, 18),
//                320);
//        placeholders.add(placeholder1);
//        ImagePlaceholder placeholder2 = new ImagePlaceholder(
//                271,
//                523,
//                "授予单位一二三四五六七八九十",
//                Color.green,
//                new Font("宋体", Font.CENTER_BASELINE, 18),
//                600);
//        placeholders.add(placeholder2);
//        try {
//            jpgWrite(imagePath,outPath,placeholders);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

}
