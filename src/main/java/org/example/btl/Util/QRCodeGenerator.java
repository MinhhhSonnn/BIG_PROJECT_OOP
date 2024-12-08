package org.example.btl.Util;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Files;

public class QRCodeGenerator {


  // dữ lieu se duoc ma hoa thanh data, noi luu anh qr, chieu dai, rong
  public static void generateQRCode(String data, String filePath, int width, int height)
      throws WriterException, IOException {
    // tạo thư mục neu chua tồn tại
    Path parentPath = FileSystems.getDefault().getPath(filePath).getParent();
    if (parentPath != null && !Files.exists(parentPath)) {
      Files.createDirectories(parentPath);
    }

    // tạo ma qr
    QRCodeWriter qrCodeWriter = new QRCodeWriter();
    BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height);

    // ghi ma qr ra tep png
    Path path = FileSystems.getDefault().getPath(filePath);
    MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
  }
}
