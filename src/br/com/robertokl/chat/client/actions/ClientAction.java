package br.com.robertokl.chat.client.actions;

import java.net.Socket;

import javax.swing.DefaultListModel;

import br.com.robertokl.chat.client.handlers.ServerComunicationHandler;
import br.com.robertokl.chat.client.view.MainScreen;
import br.com.robertokl.chat.commoms.actions.Action;

/**
 * @author  klein
 */
public abstract class ClientAction implements Action {
    
    /**
	 * @uml.property  name="params" multiplicity="(0 -1)" dimension="1"
	 */
    protected String[] params;
    /**
	 * @uml.property  name="client"
	 */
    protected Socket client;

    /**
	 * @param  client
	 * @uml.property  name="client"
	 */
    public void setClient(Socket client) {
	this.client = client;
    }

    /**
	 * @param  params
	 * @uml.property  name="params"
	 */
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
