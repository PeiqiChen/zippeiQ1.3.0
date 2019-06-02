package process_unit;

public class ParameterException extends Exception {
    String msg;
    public ParameterException(String msg){
        super(msg);
    }
}
