package eney.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.stereotype.Component;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

@Component
public class StompHandler extends ChannelInterceptorAdapter {

//    @Autowired
//    private PaymentController paymentController;
//    @Autowired
//    private final SimpMessagingTemplate template;

    private static final List<StompHeaderAccessor> sessionList = new ArrayList<StompHeaderAccessor>();

//    @Autowired
//    public StompHandler(SimpMessagingTemplate template) {
//        this.template = template;
//    }


    @Override
    public void postSend(Message message, MessageChannel channel, boolean sent) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        String sessionId = accessor.getSessionId();
        System.out.println("##########");
        System.out.println(accessor.getCommand());
        System.out.println("##########");
        switch (accessor.getCommand()) {
            case CONNECT:
                System.out.println("커넥트");
//                paymentController.setSocketSession(sessionId);
//                sessionList.add(accessor);
                System.out.println(sessionId);
//                template.convertAndSend("/queue/res", sessionId);
                // 유저가 Websocket으로 connect()를 한 뒤 호출됨
                break;
            case DISCONNECT:
                System.out.println("디스커넥트");
                System.out.println(sessionId);
                // 유저가 Websocket으로 disconnect() 를 한 뒤 호출됨 or 세션이 끊어졌을 때 발생함(페이지 이동~ 브라우저 닫기 등)
                break;
            default:
                break;
        }

    }
}