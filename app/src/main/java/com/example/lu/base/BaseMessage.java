package com.example.lu.base;

/**
 * Created by lyw.
 *
 * @author: lyw
 * @package: com.ido.veryfitpro.base
 * @description: ${TODO}{ 类注释}
 * @date: 2018/7/16 0016
 * EventBus  发送消息顶层类
 */

public class BaseMessage<T> {
    public BaseMessage(int type, T data) {
        this.type = type;
        this.data = data;
    }

    public BaseMessage(int type) {
        this.type = type;
    }


//    public static final int EVENT_TYPE_SYN_HEALTH=1;
    /**

     * 消息类型
     */
    private int type;
    /**
     * 消息数据
     */
    private T data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseMessage{" +
                "type=" + type +
                ", data=" + data +
                '}';
    }
}
