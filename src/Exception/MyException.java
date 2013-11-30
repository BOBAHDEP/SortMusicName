package Exception;

public class MyException extends Exception {
    private String detail;
    public MyException(String name) {
        this.detail = name;
    }
    public String toString() {
        return "MyException["+detail+"]";
    }
}
