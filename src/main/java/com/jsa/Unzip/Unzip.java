package com.jsa.Unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzip {

    String pathToZipfile;
    String outputFolder;
    public String getPathToZipfile() {
        return pathToZipfile;
    }

    public void setPathToZipfile(String pathToZipfile) {
        this.pathToZipfile = pathToZipfile;
    }

    public String getOutputFolder() {
        return outputFolder;
    }

    public void setOutputFolder(String outputFolder) {
        this.outputFolder = outputFolder;
    }

    public Unzip(String pathToZipfile, String outputFolder){
        this.pathToZipfile = pathToZipfile;
        this.outputFolder  = outputFolder;
    }
    public Unzip(){
    }
    public void Unzip(){
        String token = UUID.randomUUID().toString();
        final String OUTPUT_FOLDER = outputFolder+"/"+token;
        String FILE_PATH = pathToZipfile;

        // Tạo thư mục Output nếu nó không tồn tại.
//        File folder = new File(OUTPUT_FOLDER);
//        if (!folder.exists()) {
//            folder.mkdirs();
//        }
        // Tạo một buffer (Bộ đệm).
        byte[] buffer = new byte[1024];

        ZipInputStream zipIs = null;
        try {
            // Tạo đối tượng ZipInputStream để đọc file từ 1 đường dẫn (path).
            zipIs = new ZipInputStream(new FileInputStream(FILE_PATH));

            ZipEntry entry = null;
            // Duyệt từng Entry (Từ trên xuống dưới cho tới hết)
            while ((entry = zipIs.getNextEntry()) != null) {
                String entryName = entry.getName();
                String outFileName = OUTPUT_FOLDER + entryName;
                System.out.println("Unzip: " + outFileName);

                if (entry.isDirectory()) {
                    // Tạo các thư mục.
                    new File(outFileName).mkdirs();
                } else {
                    // Tạo một Stream để ghi dữ liệu vào file.
                    FileOutputStream fos = new FileOutputStream(outFileName);

                    int len;
                    // Đọc dữ liệu trên Entry hiện tại.
                    while ((len = zipIs.read(buffer)) > 0) {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                zipIs.close();
            } catch (Exception e) {
            }
        }
    }
}
