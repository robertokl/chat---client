package br.com.robertokl.chat.client.actions;

import br.com.robertokl.chat.client.view.MainScreen;

public class MuteAction extends ClientAction {

    protected void execute(MainScreen screen) {
	screen.showErrorDialog("Você está mudo!");
    }

}
