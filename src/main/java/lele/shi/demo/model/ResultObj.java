package lele.shi.demo.model;

import lombok.Data;

/**
 * @Description 响应对象
 * @Author lele.shi
 * @Date 2019-07-16 15:59
 **/
@Data
public class ResultObj {
    private int code;
    private String errMsg;
    private Object data;

    public static ResultObj successResultObj(Object data){
        ResultObj resultObj = new ResultObj();
        resultObj.setCode(0);
        resultObj.setData(data);
        return resultObj;
    }

    public static ResultObj errResultObj(int code, String errMsg){
        ResultObj resultObj = new ResultObj();
        resultObj.setCode(1);
        resultObj.setErrMsg(errMsg);
        return resultObj;
    }
}
