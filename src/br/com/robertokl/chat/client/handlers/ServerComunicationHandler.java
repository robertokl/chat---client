package br.com.robertokl.chat.client.handlers;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import br.com.robertokl.chat.client.actions.ClientActionFactory;
import br.com.robertokl.chat.client.view.MainScreen;
import br.com.robertokl.chat.commoms.actions.ActionFactory;
import br.com.robertokl.chat.commoms.constants.Actions;
import br.com.robertokl.chat.commoms.handlers.IncomingMessageListener;

public class ServerComunicationHandler {

    public static MainScreen SCREEN;
    private Socket socket;
    private final ActionFactory factory = new ClientActionFactory();
    private String serverAction = "newActionToServer:";

    public void connect(String host, String port, String name) throws Exception {
	this.socket = new Socket(host, Integer.valueOf(port));
	new Thread(new IncomingMessageListener(socket, factory)).start();
	sendAction(Actions.CLIENT_LOGIN, name);
    }

    private void sendAction(Actions action, String... parameters) throws Exception {
	StringBuffer a = new StringBuffer();
	a.append(serverAction + action.getAction());
	for (String param : parameters) {
	    a.append(";");
	    a.append(param);
	}
	send(a.toString());
    }

    private void send(String string) throws Exception {
	DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket
		.getOutputStream()));
	out.writeUTF(string);
	out.flush();
    }

    public void sendMessage(String text) throws Exception {
	if (!text.startsWith("/")) {
	    sendAction(Actions.BROADCAST_MESSAGE, text);
	} else {
	    sendCommandToServer(text);
	}
    }

    private void sendCommandToServer(String text) throws Exception {
	String action = text.split(" ")[0].substring(1);
	text = text.replace("/" + action + " ", "");
	if ("pm".equals(action)) {
	    String to = text.split(" ")[0];
	    text = text.replace(to + " ", to + ";");
	    sendAction(Actions.PRIVATE_MESSAGE, text);
	} else if ("exit".equals(action)) {
	    System.exit(0);
	} else {
	    Actions a = Actions.findAction(action);
	    if (a != null) {
		sendAction(a, text);
		return;
	    }
	    SCREEN.showErrorDialog("Comando não encontrado.");
	}
    }

    public void changedStatus(String status) throws Exception {
	sendAction(Actions.STATUS_CHANGE, status);
    }
}
