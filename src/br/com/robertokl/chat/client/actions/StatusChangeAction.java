package br.com.robertokl.chat.client.actions;

import br.com.robertokl.chat.client.view.MainScreen;

public class StatusChangeAction extends ClientAction {
    protected void execute(MainScreen screen) {
	super.updateConnectedList(screen);
    }
}
