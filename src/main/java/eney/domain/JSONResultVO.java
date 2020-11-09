package eney.domain;

public class JSONResultVO {

    private String result; // success, fail
    private String message; // if fail, set
    private Object data; // if success, set data

    public static JSONResultVO success(Object data) {
        return new JSONResultVO("success", null, data);
    }

    public static JSONResultVO success(Object data, String value) {
        return new JSONResultVO("success", value, data);
    }

    public static JSONResultVO fail(String message) {
        return new JSONResultVO("fail", message, null);
    }

    private JSONResultVO(String result, String message, Object data) {
        this.result = result;
        this.message = message;
        this.data = data;
    }

    public JSONResultVO() {
        super();
        // TODO Auto-generated constructor stub
    }


    public void setResult(String result) {
        this.result = result;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "JSONResult [result=" + result + ", message=" + message + ", data=" + data + "]";
    }

}