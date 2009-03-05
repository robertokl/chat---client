package br.com.robertokl.chat.client.actions;

import java.net.Socket;

import javax.swing.DefaultListModel;

import br.com.robertokl.chat.client.handlers.ServerComunicationHandler;
import br.com.robertokl.chat.client.view.MainScreen;
import br.com.robertokl.chat.commoms.actions.Action;

public abstract class ClientAction implements Action {
    
    protected String[] params;
    protected Socket client;

    public void setClient(Socket client) {
	this.client = client;
    }

    public void setParams(String[] params) {
	this.params = params;
    }
    
    public void execute() throws Exception {
	execute(ServerComunicationHandler.SCREEN);
    }

    protected abstract void execute(MainScreen screen);

    protected void updateConnectedList(MainScreen screen) {
        DefaultListModel model = (DefaultListModel) screen.getListConnecteds().getModel();
        model.clear();
        for (String param : this.params) {
            model.addElement(param);
        }
    }

}
