package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by tianlei on 2017/十二月/23.
 */
@Component
public class MessagePrinter {

    final private MessageService messageService;

    @Autowired
    public MessagePrinter(MessageService messageService) {
        this.messageService = messageService;
    }

    public void printMessage() {
        System.out.println(this.messageService.getMessage());
    }

}
