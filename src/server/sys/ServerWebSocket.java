package server.sys;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import data.EmotivDataTest;
import util.ConstantsTest;

@ServerEndpoint(value = ConstantsTest.ENDPOINT)
public class ServerWebSocket {
  private EmotivDataTest emotivData = new EmotivDataTest();

  // Using a SET to ensure uniqueness
  static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());

  @OnOpen
  public void onOpen(Session session) {
    clients.add(session);
  }

  @OnMessage
  public void onMesage(String emotivDataString, Session session) throws IOException {
    emotivData.toJson(emotivDataString);
  }

  @OnClose
  public void onClose() throws IOException {

  }
}
