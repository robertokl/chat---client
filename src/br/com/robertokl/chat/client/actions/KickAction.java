package br.com.robertokl.chat.client.actions;

import br.com.robertokl.chat.client.view.MainScreen;

public class KickAction extends ClientAction {

    protected void execute(MainScreen screen) {
	screen.showErrorDialog("Você foi expulso do servidor!");
	System.exit(0);
    }

}
