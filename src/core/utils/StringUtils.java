package core.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class StringUtils {
	private static String error;
	public static boolean checkInputString(String s) {
		while (true) {
		    //Nhap chuoi
//		    Scanner scanner = new Scanner(System.in);
//		    System.out.println("Nhập vào chuỗi bất có số từ bằng số lượng số 1: ");
//		    s = scanner.nextLine();
//			System.out.println(countWord(s));
//		    System.out.println(countNumber1And0(s));
//		    System.out.println(checkNumberInput(s));
		    
		    //Thong bao neu nhap loi
		    //Nhap so khac ngoai 0 va 1
		    if (!checkNumberInput(s)) {
		    	error = "Bạn chưa nhập trọng số cho mỗi từ";
		    	Alert alert = new Alert(Alert.AlertType.CONFIRMATION, error + ". Mời bạn nhập lại", ButtonType.OK);
				alert.showAndWait();
				return false;
			}
		    //Số lượng từ không = số lượng số 1
		    else if (countWord(s) != countNumber1And0(s) ) {
				error = "Số lượng trọng số và từ không giống nhau";
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION, error + ". Mời bạn nhập lại", ButtonType.OK);
				alert.showAndWait();
				return false;
			} else {
		    	return true;
			}


		    
	    }
	    
	    
	}

	// Phải gọi hàm check trước hàm này
	public static String getString(String s) {
		int a = s.indexOf('0');
		int b = s.indexOf('1');
		int index = (a == -1) ? b :((b == -1) ? a : Math.min(a, b));
		return s.substring(0, index).trim();
	}
	
	//Kiem tra chuoi chi co so 0 va 1 hay khong
	//Tra ve true neu chi toan 0 va 1
	private static boolean checkNumberInput(String s) {

		int[] intArr = creatIntArray(s);
		for (int value : intArr) {
			if ((value == 1) || (value == 0))
				return true;
		}
		return false;
	}
	
	//Dem so tu trong chuoi
	private static int countWord(String s) {
		int count = 0;
	    if (s == null || s.isEmpty()) {
	        return 0;
	      }

	    String[] words = s.split("\\s+");
		for (String word : words) {
			if (!isNumeric(word))
				count++;
		}
	    return count;
	}
	
	//Kiem tra mot chuoi co phai la so khong
	private static boolean isNumeric(String str)
	{
	    for (char c : str.toCharArray())
	    {
	        if (!Character.isDigit(c)) return false;
	    }
	    return true;
	}
	
	//Dem so 1 và 0 trong chuoi
	private static int countNumber1And0(String s) {
		int count = 0;
	   int[] intArr = creatIntArray(s);
	   int len = intArr.length;
		for (int value : intArr) {

			if (value == 1 || value == 0)
				count += 1;
		}
	    return count;
	}

	//Create Int array from String array
	private static int[] creatIntArray(String s) {
	    String words[] = s.split(" ");
	    int len = words.length - countWord(s);

	    int[] intArr = new int[len];
	    for (int i = 0; i < len; i++) {
	    	if (isNumeric(words[i])) {
		    	String num = words[i];
		        intArr[i] = Integer.parseInt(num);
	    	}
	       }
	    return intArr;
	}
	
//    public static void main(String[] args) {
//    	checkInputString();
//    }

}
