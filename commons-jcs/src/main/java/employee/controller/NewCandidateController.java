package employee.controller;

import employee.events.Event;
import employee.model.Model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by luisburgos on 14/09/15.
 */
public class NewCandidateController extends Controller {

    private JFrame mainFrame;
    private JPanel panelButtons, panelFields;
    private JButton buttonAdd, buttonCancel;
    private JLabel nameLabel, lastnameLabel, partyLabel;
    private JTextField nameTextField, lastnameTextField, partyTextField;

    public NewCandidateController(Model model, Event event) {
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

        panelFields   = new JPanel();

        nameLabel     = new JLabel("Name: ");
        nameTextField = new JTextField(10);

        lastnameLabel     = new JLabel("Last Name: ");
        lastnameTextField = new JTextField(10);

        partyLabel     = new JLabel("Party: ");
        partyTextField = new JTextField(10);

        panelFields.setLayout(new BoxLayout(panelFields, BoxLayout.Y_AXIS));
        panelFields.add(nameLabel);
        panelFields.add(nameTextField);
        panelFields.add(lastnameLabel);
        panelFields.add(lastnameTextField);
        panelFields.add(partyLabel);
        panelFields.add(partyTextField);

        panelButtons = new JPanel();
        buttonAdd    = new JButton("Agregar");
        buttonCancel = new JButton("Cancelar");

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

    @Override
    public void handleEvent(Event event, Object... params) {
        //mModel.callFunctionByName(Votations.class, Candidate.class, "addCandidate", params);
    }

    public void update(Event event) {
        //Nothing...
    }

}
