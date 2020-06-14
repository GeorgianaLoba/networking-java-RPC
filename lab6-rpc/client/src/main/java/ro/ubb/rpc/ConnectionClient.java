package ro.ubb.rpc;


import ro.ubb.rpc.domain.exceptions.ConnectionException;
import ro.ubb.rpc.message.Message;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by radu.
 */
public class ConnectionClient {
    public Message sendAndReceive(Message request) {
        try (var socket = new Socket(Message.HOST, Message.PORT);
             var is = socket.getInputStream();
             var os = socket.getOutputStream())
        {
            request.writeTo(os);
            Message response = new Message();
            response.readFrom(is);
            System.out.println(response);
            return response;
        } catch (IOException e) {
            throw new ConnectionException("error connection to server " + e.getMessage(), e);
        }
    }
}