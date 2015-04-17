/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipandmonster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import static shipandmonster.UpdateShipForm.commandCancel;
import static shipandmonster.UpdateShipForm.commandUpdate;

/**
 *
 * @author Dara
 */
public class UpdateCargoForm extends JDialog implements ActionListener {

    JButton buttonUpdate;
    JButton buttonCancel;
    
    JPanel panelButton;
    JPanel panelTextField;
    
    JPanel labelPanel;
    JPanel fieldPanel;
    
    private JLabel lblTonnage = new JLabel("Tonnage");
    private JLabel lblDiscription = new JLabel("Discription");
    
    private JTextField txtTonnage;
    private JTextField txtDiscription;
    
    public static final String commandUpdate = "Update";
    public static final String commandCancel = "Cancel";
    
    private Cargo cargoData;
    private ArrayList<CargoShip> arrayListShipData;
    private int indexShipData;
    
    public UpdateCargoForm() {
        
        panelButton = new JPanel();
        panelTextField = new JPanel();

        //10 members of CargoShip
        labelPanel = new JPanel(new GridLayout(10, 1));
        fieldPanel = new JPanel(new GridLayout(10, 1));
        panelTextField.add(labelPanel, BorderLayout.WEST);
        panelTextField.add(fieldPanel, BorderLayout.CENTER);

        //text fied
        txtTonnage = new JTextField();
        txtDiscription = new JTextField();
        
        
        buttonUpdate = new JButton(commandUpdate);
        buttonUpdate.addActionListener(this);
        
        buttonCancel = new JButton(commandCancel);
        buttonCancel.addActionListener(this);
                
        makeDialog();
    }
    
    public void makeDialog() {
        //Dialog Layout
        this.setLayout(new BorderLayout());

        //Button Pane Layout
        panelButton.setLayout(new GridLayout(1, 2));
        panelButton.add(buttonUpdate);
        panelButton.add(buttonCancel);
        this.add(panelButton, BorderLayout.SOUTH);

        //Text Field Pane Layout
        panelTextField.setLayout(new GridLayout(3, 2));
        panelTextField.add(this.lblTonnage);
        panelTextField.add(this.txtTonnage);
        panelTextField.add(this.lblDiscription);
        panelTextField.add(this.txtDiscription);
        
        this.add(panelTextField, BorderLayout.CENTER);
        
    }
    
    public void ShowDiaglog(Cargo cargo, int indexShip, ArrayList<CargoShip> arrayListShip) {
        this.arrayListShipData = arrayListShip;
        this.indexShipData = indexShip;
        this.cargoData = cargo;
        
        loadFormData(this.cargoData);
        this.setSize(new Dimension(300, 150));
        Utilities.centerOnScreen(this, true);
        this.setVisible(true);
    }
    
    public void loadFormData(Cargo cargo) {
        
        this.txtTonnage.setText(String.valueOf(cargo.getTonnage()));
        this.txtDiscription.setText(cargo.getDescription());
        
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand() == commandUpdate) {
            cargoData.setTonnage(Double.valueOf(this.txtTonnage.getText()));
            cargoData.setDescription(this.txtDiscription.getText());
            
            this.dispose();
        } else if (e.getActionCommand() == commandCancel) {
            this.dispose();
        } 
        UpdateShipForm frm = new UpdateShipForm();
        frm.ShowDiaglog(this.indexShipData,this.arrayListShipData);
    }
    
}
