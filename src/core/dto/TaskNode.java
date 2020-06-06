package core.dto;


/**
 * Class này có là nội dung của TreeItem
 *
 * @author  Doãn Phụng OOP-2020
 */
public class TaskNode {
    private int [] weight ;
    private String tag;
    public TaskNode (String tag, int [] weight){
        this.tag = tag;
        this.weight =weight;
    }

    public int[] getWeight() {
        return weight;
    }

    public void setWeight(int[] weight) {
        this.weight = weight;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
