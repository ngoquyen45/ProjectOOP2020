package core.suggest;

import core.dto.TaskNode;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;

/**
 * Class này có chức năng chính là đưa ra đề nghị cho câu văn
 * dựa vào dựa vào list đề nghị đã có trong 1 cây
 *
 * @author  Doãn Phụng OOP-2020
 */

public class TreeSuggest implements Suggester {
    private final TreeItem<TaskNode> root;
    private ArrayList<String> list;
    public TreeSuggest(TreeItem<TaskNode> root) {
        this.root = root;
        list = new ArrayList<>();
    }

    private void suggest(String statement, TreeItem<TaskNode> root) {

        // Bước 1: Kiểm tra xem node này có phải node lá không ?
        // Bước 2: Nếu là node lá thì xử lý
        // Bước 3: Nếu không là node lá thì tiếp tụ đệ quy đến khi xong thì thôi

        // Nếu node này không có con và nó cũng không phải node null thì nó là node lá
        if (root.getChildren().isEmpty()) {
            String tag = root.getValue().getTag();
            String[] words = tag.split(" ");
            int[] weights = root.getValue().getWeight();

            boolean isContainAll = true; // Chứa tất cả các trọng số 1
            for (int i = 0; i < weights.length; i++) {
                if (weights[i] == 1) {
                    // Nếu câu văn chứa một đoạn
                    if (!statement.contains(words[i].toLowerCase())) {
                        isContainAll = false;
                        break;
                    }

                }
            }
            if (isContainAll) list.add(tag);
        } else {
            for (TreeItem<TaskNode> child : root.getChildren()) {
                suggest(statement, child);
            }
        }
    }

    @Override
    public ArrayList<String> suggest(String statement) {
        suggest(statement,root);
        return list;
    }
}




