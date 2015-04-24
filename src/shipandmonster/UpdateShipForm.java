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
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author Dara
 */
public class UpdateShipForm extends JDialog implements ActionListener {
    
    JButton buttonUpdate;
    JButton buttonCancel;
    
    JPanel panelButton;
    JPanel panelTextField;
    
    JPanel labelPanel;
    JPanel fieldPanel;
    
    private JLabel lblName = new JLabel("Name");
    private JLabel lblCountry = new JLabel("Country");
    private JLabel lblTransponder = new JLabel("Transponder");
    private JLabel lblCapacity = new JLabel("Capacity");
    private JLabel lblLength = new JLabel("Length");
    private JLabel lblDraft = new JLabel("Draft");
    private JLabel lblBeam = new JLabel("Beam");
    private JLabel lblColumn = new JLabel("Column");
    private JLabel lblRow = new JLabel("Row");
    private JLabel lblCargo = new JLabel("Cargo");
    
    private JTextField txtName;
    private JTextField txtCountry;
    private JTextField txtTransponder;
    private JTextField txtCapacity;
    private JTextField txtLenght;
    private JTextField txtDraft;
    private JTextField txtBeam;
    private JTextField txtColumn;
    private JTextField txtRow;
    private JButton btnCargo = new JButton("Load Cargo");
    
    public static final String commandUpdate = "Update";
    public static final String commandCancel = "Cancel";
    

    
    private CargoShip shipData;
    private ArrayList<CargoShip> arrayListShip;
    private Cargo cargoData;
    private int indexShipData;
    private JDialog parent;
    public UpdateShipForm() {
        
        panelButton = new JPanel();
        panelTextField = new JPanel();

        //10 members of CargoShip
        labelPanel = new JPanel(new GridLayout(10, 1));
        fieldPanel = new JPanel(new GridLayout(10, 1));
        panelTextField.add(labelPanel, BorderLayout.WEST);
        panelTextField.add(fieldPanel, BorderLayout.CENTER);

        //text fied
        txtName = new JTextField();
        txtCountry = new JTextField();
        txtTransponder = new JTextField();
        txtCapacity = new JTextField();
        txtLenght = new JTextField();
        txtDraft = new JTextField();
        txtBeam = new JTextField();
        txtColumn = new JTextField();
        txtRow = new JTextField();

        btnCargo.addActionListener(this);
        
        buttonUpdate = new JButton(commandUpdate);
        buttonUpdate.addActionListener(this);
        
        buttonCancel = new JButton(commandCancel);
        buttonCancel.addActionListener(this);
                
        makeDialog();
    }
    
    public void ShowDiaglog(int indexShip, ArrayList<CargoShip> shipList, JDialog parent) {
        this.shipData = shipList.get(indexShip);
        this.arrayListShip = shipList;
        this.indexShipData = indexShip;
        this.cargoData = this.shipData.getCargo();
        this.parent = parent;
        
        loadFormData(shipList.get(indexShip));
        this.setSize(new Dimension(300, 300));
        Utilities.centerOnScreen(this, true);
        this.setVisible(true);
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
        panelTextField.setLayout(new GridLayout(11, 2));
        panelTextField.add(this.lblName);
        panelTextField.add(this.txtName);
        panelTextField.add(this.lblCountry);
        panelTextField.add(this.txtCountry);
        panelTextField.add(this.lblTransponder);
        panelTextField.add(this.txtTransponder);
        panelTextField.add(this.lblCapacity);
        panelTextField.add(this.txtCapacity);
        panelTextField.add(this.lblLength);
        panelTextField.add(this.txtLenght);
        panelTextField.add(this.lblDraft);
        panelTextField.add(this.txtDraft);
        panelTextField.add(this.lblBeam);
        panelTextField.add(this.txtBeam);
        panelTextField.add(this.lblColumn);
        panelTextField.add(this.txtColumn);
        panelTextField.add(this.lblRow);
        panelTextField.add(this.txtRow);
        panelTextField.add(this.lblCargo);
        panelTextField.add(this.btnCargo);
        
        this.add(panelTextField, BorderLayout.CENTER);
        
    }
    
    public void loadFormData(CargoShip ship) {
        
        this.txtName.setText(ship.getName());
        this.txtCountry.setText(ship.getCountry());
        this.txtTransponder.setText(String.valueOf(ship.getTransponder()));
        this.txtCapacity.setText(String.valueOf(ship.getCargoCapacity()));
        this.txtLenght.setText(String.valueOf(ship.getLength()));
        this.txtDraft.setText(String.valueOf(ship.getDraft()));
        this.txtBeam.setText(String.valueOf(ship.getBeam()));
        this.txtColumn.setText(String.valueOf(MapConverter.lon2col(ship.getLongitude())));
        this.txtRow.setText(String.valueOf(MapConverter.lat2row(ship.getLatitude())));
        
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == commandUpdate) {
            shipData.setName(this.txtName.getText());
            shipData.setCountry(this.txtCountry.getText());
            shipData.setTransponder(Long.valueOf(this.txtTransponder.getText()));
            shipData.setCargoCapacity(Double.valueOf(this.txtCapacity.getText()));
            shipData.setLength(Double.valueOf(this.txtLenght.getText()));
            shipData.setDraft(Double.valueOf(this.txtDraft.getText()));
            shipData.setBeam(Double.valueOf(this.txtBeam.getText()));
            shipData.setLongitude(MapConverter.col2lon(Integer.valueOf(this.txtColumn.getText())));
            shipData.setLatitude(MapConverter.row2lat(Integer.valueOf(this.txtRow.getText())));
            parent.setVisible(true);
            this.dispose();
        } else if (e.getActionCommand() == commandCancel) {
            UpdateShipListForm frm = new UpdateShipListForm();
            frm.ShowDialog(this.arrayListShip);
            this.dispose();
        } else if (e.getActionCommand() == "Load Cargo") {
            UpdateCargoForm frm = new UpdateCargoForm();
            frm.ShowDiaglog(this.cargoData,this.indexShipData,this.arrayListShip,this);
            this.dispose();
        }
        
    }
    
}
