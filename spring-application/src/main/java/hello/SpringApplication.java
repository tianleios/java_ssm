package hello;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by tianlei on 2017/十二月/23.
 */

@Configuration
@ComponentScan
public class SpringApplication {

    @Bean
    MessageService mockMessageService() {

        return new MessageService() {

            @Override
            public String getMessage() {
                return "Hello World!";
            }

        };

    }
    public static void main(String[] args) {

//      AnnotationConfigApplicationContext ctx =  new  AnnotationConfigApplicationContext(SpringApplication.class);
//      MessagePrinter messagePrinter = ctx.getBean(MessagePrinter.class);
//      messagePrinter.printMessage();
//        XmlBeanFactory
     ClassPathXmlApplicationContext classPathXmlApplicationContext = new    ClassPathXmlApplicationContext("applicationContext.xml");
        MessagePrinter messagePrinter = (MessagePrinter)classPathXmlApplicationContext.getBean("messagePrinter");
        messagePrinter.printMessage();
    }
}
