package br.com.robertokl.chat.client.view;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import br.com.robertokl.chat.client.handlers.ServerComunicationHandler;
import br.com.robertokl.chat.commoms.constants.Status;

public class MainScreen extends JFrame {
    
    // @jve:decl-index=0:
    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JTextArea txtOut = null;
    private JList listConnecteds = null;
    private JComboBox comboStatus = null;
    private JTextField txtIn = null;
    private JButton buttonSend = null;
    private static final ServerComunicationHandler SERVER_COMUNICATION_HANDLER = new ServerComunicationHandler();  //  @jve:decl-index=0:
    /**
     * This is the default constructor
     */
    public MainScreen() {
	super();
	initialize();
	try {
	    String host = JOptionPane.showInputDialog("Digite o host");
	    String port = JOptionPane.showInputDialog("Digite a porta");
	    String name = JOptionPane.showInputDialog("Digite um apelido");
	    while(!name.matches("^[a-zA-Z ]+$")){
		JOptionPane.showMessageDialog(null, "Digite um apelido válido.", "Erro", JOptionPane.ERROR_MESSAGE);
		name = JOptionPane.showInputDialog("Digite um apelido");
	    }
	    ServerComunicationHandler.SCREEN = this;
	    SERVER_COMUNICATION_HANDLER.connect(host, port, name);
	} catch (Exception e) {
	    JOptionPane.showMessageDialog(this, "Não foi possível se conectar com o servidor.\n\n"
		    + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	}
    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
	this.setSize(756, 323);
	this.setContentPane(getJContentPane());
	this.setTitle("Chat");
	this.setVisible(true);
	this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
	if (jContentPane == null) {
	    jContentPane = new JPanel();
	    jContentPane.setLayout(null);
	    jContentPane.add(getTxtOut(), null);
	    jContentPane.add(getListConnecteds(), null);
	    jContentPane.add(getComboStatus(), null);
	    jContentPane.add(getTxtIn(), null);
	    jContentPane.add(getButtonSend(), null);
	}
	return jContentPane;
    }

    /**
     * This method initializes txtOut
     * 
     * @return javax.swing.JTextArea
     */
    public synchronized JTextArea getTxtOut() {
	if (txtOut == null) {
	    txtOut = new JTextArea();
	    txtOut.setBounds(new Rectangle(5, 4, 541, 242));
	    txtOut.setEditable(false);
	}
	return txtOut;
    }

    /**
     * This method initializes listConnecteds
     * 
     * @return javax.swing.JList
     */
    public synchronized JList getListConnecteds() {
	if (listConnecteds == null) {
	    DefaultListModel model = new DefaultListModel();
	    listConnecteds = new JList(model);
	    listConnecteds.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	    listConnecteds.setBounds(new Rectangle(556, 4, 179, 242));
	    listConnecteds.addListSelectionListener(new ListSelectionListener() {
		public void valueChanged(ListSelectionEvent e) {
		    if (listConnecteds.getSelectedValue() == null) return;
		    getTxtIn().setText("/pm " + listConnecteds.getSelectedValue().toString().trim() + " ");
		    getTxtIn().requestFocus();
		}
	    });
	}
	return listConnecteds;
    }

    /**
     * This method initializes comboStatus
     * 
     * @return javax.swing.JComboBox
     */
    private JComboBox getComboStatus() {
	if (comboStatus == null) {
	    comboStatus = new JComboBox();
	    comboStatus.setBounds(new Rectangle(5, 253, 137, 28));
	    for (int i = 0; i < Status.values().length; i++) {
		comboStatus.addItem(Status.values()[i].getStatus());
	    }
	    comboStatus.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent event) {
		    try {
			SERVER_COMUNICATION_HANDLER.changedStatus(comboStatus.getSelectedItem()
				.toString());
		    } catch (Exception e) {
			showErrorDialog(e);
		    }
		}
	    });
	}
	return comboStatus;
    }

    /**
     * This method initializes txtIn
     * 
     * @return javax.swing.JTextField
     */
    private JTextField getTxtIn() {
	if (txtIn == null) {
	    txtIn = new JTextField();
	    txtIn.setBounds(new Rectangle(145, 253, 402, 26));
	    txtIn.addActionListener(new SendActionEventListener());
	}
	return txtIn;
    }

    /**
     * This method initializes buttonSend
     * 
     * @return javax.swing.JButton
     */
    private JButton getButtonSend() {
	if (buttonSend == null) {
	    buttonSend = new JButton();
	    buttonSend.setBounds(new Rectangle(557, 253, 176, 23));
	    buttonSend.setText("Enviar");
	    buttonSend.addActionListener(new SendActionEventListener());
	}
	return buttonSend;
    }

    public static void main(String[] args) {
	new MainScreen();
    }

    private void showErrorDialog(Exception e) {
	JOptionPane.showMessageDialog(null, "Erro ao enviar mensagem para o servidor:\n\n"
		+ e.getMessage());
    }
    
    private class SendActionEventListener implements ActionListener {
	public void actionPerformed(ActionEvent event) {
	    if (getTxtIn().getText() == null || getTxtIn().getText().equals("")) return;
	    try {
		SERVER_COMUNICATION_HANDLER.sendMessage(getTxtIn().getText());
		getTxtIn().setText("");
	    } catch (Exception e) {
		showErrorDialog(e);
	    }
	}
    }
} // @jve:decl-index=0:visual-constraint="10,-38"
