package ui;

import core.dto.Path;
import core.dto.TaskNode;
import core.utils.FileUtils;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import sdk.javafx.suggestbar.StatementLabel;
import sdk.javafx.tagbar.TagBar;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Stream;

import static core.utils.FileUtils.removeFileExtension;

public class MainController {
    File[] listFile = new File[FileUtils.DEFAULT_PART];

    public static Stack<StatementLabel> stack = new Stack<>();

    private final FileChooser fileChooser = new FileChooser();
    private File file;
    private ArrayList<StatementLabel> saveLabel = new ArrayList<>();

    // Tên file mặc định nếu bạn chưa chọn file
    private final String DEFAULT_NAME = "Vnexpress.CLASSIFIED.ALL.txt";
    private String currentStatement;
    private TreeItem root;
    @FXML
    private BorderPane borderPane;

    @FXML
    private TagBar listKeyword;

    @FXML
    private VBox listResultVBox;

    @FXML
    private ScrollPane scrollPane;

    // Đã chọn file hay chưa
    private boolean isChoose = false;

//    private SuggestBar suggestBar;
    @FXML
    private void chooseFile() {
        isChoose = true;
        // Khởi tạo để nhảy đúng vào thư mục code
        fileChooser.setInitialDirectory(new File(Path.pathResource));
        file = fileChooser.showOpenDialog(Main.primaryStage);

        // Tối ưu hóa, xử lý song song
        // Chia file thành nhiều phần để xử lý cho nhanh

        // Chặn lỗi nếu người dùng mở rồi tắt luôn
        if (file != null) {
            if (!FileUtils.hasAllPart(file.getPath())) {
                FileUtils.splitFile(file.getPath());
            }
            else {
                FileUtils.isFinished = true;
            }
        }


    }

    @FXML
    private void search() {
        // Thêm đường dẫn mặc định
        if (!isChoose || file == null) file = new File(Path.pathResource + DEFAULT_NAME);
//        System.out.println(FileUtils.hasAllPart(file.getPath()));
        if(FileUtils.hasAllPart(file.getPath())) {
            FileUtils.isFinished = true;
        }
        if(FileUtils.isFinished) {
            // Hàm này thực hiện việc việc tìm kiếm khi người dùng nhập vào các từ khóa

            // Làm mới dữ liệu trong VBox trước khi đẩy vào
            for (StatementLabel label : saveLabel)
                this.listResultVBox.getChildren().remove(label);

            // Todo: Kiểm tra xem đã chọn file chưa mới chạy đoan code bên dưới
            // TODO: Kiểm tra xem đã nhập keyword chưa mới chạy đoạn code bên dưới
            // TODO: Kiểm tra xem chia file đã hoàn thành chưa mới chạy đoạn code bên dưới

            // Lấy ra list các keyword
            ObservableList<String> listKeyword = this.listKeyword.getListKeyword();


            for (int i = 0; i < FileUtils.DEFAULT_PART; i++) {
                listFile[i] = new File(Path.pathResource + "output" + File.separator +
                        removeFileExtension(file.getName()) + (i + 1) + ".txt");
                int finalI = i;
                Platform.runLater(() -> {
                    // Nếu file có tồn tại và list keyword không trống
                    if (listFile[finalI].exists() && !listKeyword.isEmpty()) {
                        String filePath = listFile[finalI].getPath();
                        Stream<String> lines;
                        try {
                            lines = Files.lines(Paths.get(filePath));
                            // Duyệt để xử lý từng dòng code
                            lines.forEach(l -> {
                                // Tránh các lỗi về final
                                currentStatement = l;
                                // chuyển văn bản thành chữ thường
                                currentStatement = currentStatement.toLowerCase();
                                // Nếu chuỗi l chứa tất cả các keyword
                                if (isContainAllKeyword(currentStatement, listKeyword)) {
                                    // Tạo một label chứa dữ liệu
                                    StatementLabel label = null;
                                    label = new StatementLabel(l);

                                    // Lưu lại lựa chọn trước đó

                                    // Lưu lại nó vào một ArrayList để hiệu quả cho việc remove
                                    saveLabel.add(label);

                                    this.listResultVBox.getChildren().add(label);
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        }
    }

    // Kiểm tra xem một chuỗi có chứa tất cả các keyword hay không ?
    private boolean isContainAllKeyword (String l, ObservableList<String> listKeyword) {
        boolean hasAllKeywords = true;
        for (String s : listKeyword) {
            if (!l.contains(s)) {
                hasAllKeywords = false;
                break;
            }
        }
        return hasAllKeywords;
    }

    // Khởi tạo và đồng bộ TreeNode
    private void backupTree() {
        // khôi phục tree tại đây

        // Tạo cây với node root
        root = new TreeItem(
                new TaskNode("thị trường cổ phiếu", new int[]{1, 1, 1, 1})
        );
        root.setExpanded(true);

        TreeItem nodeChild1 = new TreeItem(
                new TaskNode("cổ phiếu tăng", new int[]{1, 1, 1})
        );

        TreeItem nodeChild2 = new TreeItem(
                new TaskNode("cổ phiếu giảm", new int[]{1, 1, 1})
        );

        root.getChildren().addAll(nodeChild1, nodeChild2);
        // Tới đây ta sẽ có cây code với hai node con
    }

    public TreeItem getRoot() {
        return root;
    }

    public void setRoot(TreeItem root) {
        this.root = root;
    }
}
