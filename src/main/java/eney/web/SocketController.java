package eney.web;

import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;


//@ServerEndpoint(value="/aaa")
@Profile("stomp")
@Controller
public class SocketController  {


//    private static final List<Session> sessionList=new ArrayList<Session>();;
//    public SocketController() {
//        // TODO Auto-generated constructor stub
//        System.out.println("웹소켓(서버) 객체생성");
//    }
//
//    @OnOpen
//    public void onOpen(Session session) {
//        System.out.println(session.getId());
//        try {
//            final Basic basic=session.getBasicRemote();
//            basic.sendText("Connection Established");
//        }catch (Exception e) {
//            // TODO: handle exception
//            System.out.println(e.getMessage());
//        }
//        sessionList.add(session);
//    }

    @MessageMapping("/test")
    public void join(WebSocketSession session, String msg) {
        System.out.println("받음 여기");
        System.out.println(session);
        System.out.println(msg);
//        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
//        template.convertAndSend("/subscribe/chat/room/" + message.getChatRoomId(), message);
    }



}
