package br.com.robertokl.chat.client.actions;

import br.com.robertokl.chat.client.view.MainScreen;

public class ErrorAction extends ClientAction {

    protected void execute(MainScreen screen) {
	screen.showErrorDialog(super.params[0]);
    }

}
