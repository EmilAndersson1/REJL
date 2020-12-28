package model;

public class Code {

    public Code() {
    }

    public String code = "";

    public String error = "no error";

    public String state = "";

    @Override
    public String toString() {
        return "Code{" +
                "code='" + code + '\'' +
                ", error='" + error + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
