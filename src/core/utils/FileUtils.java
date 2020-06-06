package core.utils;

import core.dto.Path;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class này dùng để chia file
 *
 * @author Lâm Sang OOP-2020
 */

public class FileUtils {
    public static boolean isFinished = false;
    // Mặc định chia làm 8 phần
    public static final int DEFAULT_PART = 8;

    // Chia file
    public static void splitFile(String path) {
        File file = new File(path);

        FileReader fileReader = null;
        BufferedReader br = null;

        try {
            fileReader = new FileReader(file);
            br = new BufferedReader(fileReader);

            StringBuilder builder = new StringBuilder();
            String line;

            int index = 0;

            List<StringBuilder> allGroup = new ArrayList<>();

            while ((line = br.readLine()) != null) {
                if (line.startsWith("NHÓM")) {
                    // ghi file cu neu co

                    if (index != 0)
                        allGroup.add(builder);

                    builder = new StringBuilder();
                    builder.append(line).append("\n");

                    index++;
                } else {
                    builder.append(line).append("\n");
                }
            }

            int div = allGroup.size() / DEFAULT_PART;

            for (int i = 0; i < DEFAULT_PART; i++) {
                int st = i * div;
                int ed = (i + 1) * div;

                StringBuilder builder2 = new StringBuilder();
                if (i == (DEFAULT_PART - 1)) {
                    for (int j = st; j < allGroup.size(); j++) {
                        builder2.append(allGroup.get(j).toString().trim()).append("\n\n");
                    }
                } else {
                    for (int j = st; j < ed; j++) {
                        builder2.append(allGroup.get(j).toString().trim()).append("\n\n");
                    }
                }

//                System.out.println("Ghi file " + (i + 1));
                File wFile = new File(Path.pathResource + "output" + File.separator +
                        removeFileExtension(file.getName()) + (i + 1) + ".txt");
                FileWriter fw;
                try {
                    fw = new FileWriter(wFile);
                    BufferedWriter bw = new BufferedWriter(fw);
                    bw.write(builder2.toString().trim());
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            isFinished = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }

                if (null != fileReader) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //
    public static String removeFileExtension(String filename) {
        return filename.replaceFirst("[.][^.]+$", "");
    }
//    public static void main(String[] args) {
////        (new FileSplitter()).split("D://abcd//TNCK.CLASSIFIED.ALL.txt");
//    }

    // Có tất cả các phần file
    public static boolean hasAllPart(String pathFile) {
        File file = new File(pathFile);
        for (int i = 0; i < FileUtils.DEFAULT_PART; i++) {
            if (!new File(Path.pathResource + "output" + File.separator +
                    removeFileExtension(file.getName()) + (i + 1) + ".txt").exists()) {
                return false;
            }
        }
        return true;
    }
}
