package br.com.robertokl.chat.client.actions;

import br.com.robertokl.chat.commoms.actions.Action;
import br.com.robertokl.chat.commoms.actions.ActionFactory;

public class ClientActionFactory extends ActionFactory {

    protected Action getClientLoginAction() {
	return new ClientLoginAction();
    }

    protected Action getSendMessageAction() {
	return new SendMessageAction();
    }

    protected String initialize(String data) {
	if (!data.startsWith("newActionToClient:")) {
	    throw new IllegalAccessError("Mensagem enviada errada ao cliente!");
	}
	data = data.replace("newActionToClient:", "");
	return data;
    }

    protected Action getStatusChangeAction() {
	return new StatusChangeAction();
    }

    protected Action getPrivateMessageAction() {
	return new SendMessageAction();
    }

    protected Action getErrorAction() {
	return new ErrorAction();
    }

    protected Action getAdminLoginAction() {
	return new StatusChangeAction();
    }

}
