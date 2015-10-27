package employee.controller;

import employee.events.Event;
import employee.misc.StringRes;
import employee.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by luisburgos on 26/10/15.
 */
public class AddEmployeeController extends Controller {

    private JFrame mainFrame;
    private JPanel panelButtons, panelFields;
    private JButton buttonAdd, buttonCancel;
    private JLabel labelName, labelAddress;
    private JTextField nameTextField, lastnameTextField;

    public AddEmployeeController(Model model, Event event) {
        super(model, event);
        prepareGUI();
    }

    private void prepareGUI() {
        mainFrame = new JFrame();
        mainFrame.setSize(450, 200);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent) {
                mainFrame.dispose();
            }
        });

        panelFields = new JPanel();

        labelName = new JLabel(StringRes.LABEL_NAME);
        nameTextField = new JTextField(10);
        labelAddress = new JLabel(StringRes.LABEL_ADDRESS);
        lastnameTextField = new JTextField(10);

        panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
        panelFields.add(labelName);
        panelFields.add(nameTextField);
        panelFields.add(labelAddress);
        panelFields.add(lastnameTextField);

        panelButtons = new JPanel();
        buttonAdd = new JButton(StringRes.BTN_ADD);
        buttonCancel = new JButton(StringRes.BTN_CANCEL);

        buttonAdd.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                /*Candidate candidate = new Candidate();
                candidate.setName(nameTextField.getText());
                candidate.setLastname(lastnameTextField.getText());
                candidate.setParty(partyTextField.getText());
                candidate.setVotes(0);
                handleEvent(new NewCandidate(), candidate);*/
            }
        });

        buttonCancel.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                mainFrame.dispose();
            }
        });

        panelButtons.setLayout(new BoxLayout(panelButtons, BoxLayout.X_AXIS));
        panelButtons.add(buttonAdd);
        panelButtons.add(buttonCancel);

        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        mainFrame.add(panelFields);
        mainFrame.add(panelButtons);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    public void handleEvent(Event event, Object... params) {
        //mModel.callFunctionByName(Votations.class, Candidate.class, "addCandidate", params);
    }

    public void update(Event event) {

    }

}
