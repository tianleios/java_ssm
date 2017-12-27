package hello.impl;

import hello.MessageService;

/**
 * Created by tianlei on 2017/十二月/23.
 */
public class MessageServiceImpl implements MessageService {

    @Override
    public String getMessage() {
        return "MessageService";
    }
}
