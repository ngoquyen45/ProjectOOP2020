package core.suggest;

import core.dto.Path;
import core.extend.HashMap2D;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;
/**
 * Class này có chức năng chính là đưa ra đề nghị cho câu văn
 * dựa vào một list tag trong file read1.txt
 *
 * @author  Trịnh Tuấn OOP-2020
 */

public class NormalSuggester implements Suggester{
    private final HashMap2D tags = new HashMap2D();

    // Hàm khởi tạo giúp khởi tạo ra mảng tags từ file read1.txt
    public NormalSuggester() {
        String filePath = Path.pathResource + "read1.txt";
        Stream<String> lines;
        try {
            lines = Files.lines(Paths.get(filePath));
            // Duyệt để xử lý từng dòng code
            lines.forEach(l -> {
                StringBuilder sb = new StringBuilder();
                ArrayList<String> s1 = new ArrayList<>();
                ArrayList<String> s2 = new ArrayList<>();
                // Các thành phần trên một dòng
                String[] words = l.split(" ");
                for (String word : words) {
                    if ((word.equals("1")) || (word.equals("0"))) {
                        s2.add(word);
                    } else {
                        s1.add(word);
                        sb.append(word).append(" ");
                    }
                }
                String str = sb.substring(0, sb.length() - 1);
                tags.addKey(str);
                for (int j = 0; j < s1.size(); j++) {
                    tags.addValue(str, s1.get(j), Integer.parseInt(s2.get(j)));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> suggest(String sentence) {
        // Tắt hoa và thường
        sentence = sentence.toLowerCase();
        ArrayList<String> result = new ArrayList<>();
        for (String key1 : tags.getOuter().keySet()) {
            HashMap<String, Integer> inner = tags.getOuter().get(key1);

            // Kiểm tra xem tất cả các key2 có trong số bằng 1, có nằm trong trong câu không ?
            boolean check = true;
            for (String key2 : inner.keySet()) {
                if (inner.get(key2) == 1) {
                    if (!sentence.contains(key2)) {
                        check = false;
                        break;
                    }
                }
            }

            // Nếu chứa tất cả các keyword bằng 1
            if (check) result.add(key1);
        }
        return result;
    }

}

