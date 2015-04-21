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

/**
 *
 * @author Dara
 */
public class UpdateDockForm extends JDialog implements ActionListener {
    
    JButton buttonUpdate;
    JButton buttonCancel;
    
    JPanel panelButton;
    JPanel panelTextField;
    
    JPanel labelPanel;
    JPanel fieldPanel;
    
    private JLabel lblName = new JLabel("Name");
    private JLabel lblSection = new JLabel("Section");
    private JLabel lblNumber = new JLabel("Number");
    private JLabel lblLength = new JLabel("Length");
    private JLabel lblDepth = new JLabel("Depth");
    private JLabel lblWidth = new JLabel("Width");
    private JLabel lblLongitude = new JLabel("Logitude");
    private JLabel lblLatitude = new JLabel("Latitude");
    
    private JTextField txtName;
    private JTextField txtSection;
    private JTextField txtNumber;
    private JTextField txtLenght;
    private JTextField txtDepth;
    private JTextField txtWidth;
    private JTextField txtLongitude;
    private JTextField txtLatitude;
    
    public static final String commandUpdate = "Update";
    public static final String commandCancel = "Cancel";
    

    
    private Dock dockData;
    private ArrayList<Dock> arrayListDock;
    private int indexDockData;
    
    public UpdateDockForm() {
        
        panelButton = new JPanel();
        panelTextField = new JPanel();

        //10 members of CargoShip
        labelPanel = new JPanel(new GridLayout(10, 1));
        fieldPanel = new JPanel(new GridLayout(10, 1));
        panelTextField.add(labelPanel, BorderLayout.WEST);
        panelTextField.add(fieldPanel, BorderLayout.CENTER);

        //text fied
        txtName = new JTextField();
        txtSection = new JTextField();
        txtNumber = new JTextField();
        txtLenght = new JTextField();
        txtDepth = new JTextField();
        txtWidth = new JTextField();
        txtLongitude = new JTextField();
        txtLatitude = new JTextField();
        
        buttonUpdate = new JButton(commandUpdate);
        buttonUpdate.addActionListener(this);
        
        buttonCancel = new JButton(commandCancel);
        buttonCancel.addActionListener(this);
                
        makeDialog();
    }
    
    public void ShowDiaglog(int indexDock, ArrayList<Dock> dockList) {
        this.dockData = dockList.get(indexDock);
        this.arrayListDock = dockList;
        this.indexDockData = indexDock;
        
        loadFormData(dockList.get(indexDock));
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
        panelTextField.setLayout(new GridLayout(9, 2));
        panelTextField.add(this.lblName);
        panelTextField.add(this.txtName);
        panelTextField.add(this.lblSection);
        panelTextField.add(this.txtSection);
        panelTextField.add(this.lblNumber);
        panelTextField.add(this.txtNumber);
        panelTextField.add(this.lblLength);
        panelTextField.add(this.txtLenght);
        panelTextField.add(this.lblDepth);
        panelTextField.add(this.txtDepth);
        panelTextField.add(this.lblWidth);
        panelTextField.add(this.txtWidth);
        panelTextField.add(this.lblLongitude);
        panelTextField.add(this.txtLongitude);
        panelTextField.add(this.lblLatitude);
        panelTextField.add(this.txtLatitude);
        
        this.add(panelTextField, BorderLayout.CENTER);
        
    }
    
    public void loadFormData(Dock dock) {
        
        this.txtName.setText(dock.getName());
        this.txtSection.setText(String.valueOf(dock.getSection()));
        this.txtNumber.setText(String.valueOf(dock.getNumber()));
        this.txtLenght.setText(String.valueOf(dock.getLength()));
        this.txtDepth.setText(String.valueOf(dock.getDepth()));
        this.txtWidth.setText(String.valueOf(dock.getWidth()));
        this.txtLongitude.setText(String.valueOf(dock.getLongitude()));
        this.txtLatitude.setText(String.valueOf(dock.getLatitude()));
        
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == commandUpdate) {
            dockData.setName(this.txtName.getText());
            dockData.setSection(this.txtSection.getText().charAt(0));
            dockData.setNumber(Integer.valueOf(this.txtNumber.getText()));
            dockData.setLength(Double.valueOf(this.txtLenght.getText()));
            dockData.setDepth(Double.valueOf(this.txtDepth.getText()));
            dockData.setWidth(Double.valueOf(this.txtWidth.getText()));
            dockData.setLongitude(Double.valueOf(this.txtLongitude.getText()));
            dockData.setLatitude(Double.valueOf(this.txtLatitude.getText()));
            UpdateDockListForm frm = new UpdateDockListForm();
            frm.ShowDialog(this.arrayListDock);
            this.dispose();
        } else if (e.getActionCommand() == commandCancel) {
            UpdateDockListForm frm = new UpdateDockListForm();
            frm.ShowDialog(this.arrayListDock);
            this.dispose();
        }        
    }
    
}