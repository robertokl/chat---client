package br.com.robertokl.chat.client.actions;

import javax.swing.JTextArea;

import br.com.robertokl.chat.client.view.MainScreen;

public class SendMessageAction extends ClientAction {

    protected void execute(MainScreen screen) {
	JTextArea txtOut = screen.getTxtOut();
	txtOut.setText(txtOut.getText() + "\n" + super.params[0] + ": " + super.params[1]);
    }

}
