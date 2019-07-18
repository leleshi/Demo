package lele.shi.demo.exception;

import lombok.Data;

/**
 * @Description
 * @Author lele.shi
 * @Date 2019-07-16 15:41
 **/
@Data
public class CheckException extends RuntimeException {

    private int code;

    public CheckException(){}

    public CheckException(String message){
        super(message);
    }

    public CheckException(int code, String message){
        super(message);
        this.code = code;
    }

}
