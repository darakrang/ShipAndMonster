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
public class UpdateMonsterForm extends JDialog implements ActionListener {
    
    JButton buttonUpdate;
    JButton buttonCancel;
    
    JPanel panelButton;
    JPanel panelTextField;
    
    JPanel labelPanel;
    JPanel fieldPanel;
    
    private JLabel lblLabel = new JLabel("Label");
    private JLabel lblColumn = new JLabel("Column");
    private JLabel lblRow = new JLabel("Row");
    
    private JTextField txtLabel;
    private JTextField txtColumn;
    private JTextField txtRow;
    
    public static final String commandUpdate = "Update";
    public static final String commandCancel = "Cancel";
    
    private SeaMonster monsterData;
    private ArrayList<SeaMonster> arrayListMonster;
    private Cargo cargoData;
    private int indexShipData;
    
    private JDialog parent;
    
    public UpdateMonsterForm() {
        
        panelButton = new JPanel();
        panelTextField = new JPanel();

        //10 members of CargoShip
        labelPanel = new JPanel(new GridLayout(3, 1));
        fieldPanel = new JPanel(new GridLayout(3, 1));
        panelTextField.add(labelPanel, BorderLayout.WEST);
        panelTextField.add(fieldPanel, BorderLayout.CENTER);

        //text fied
        txtLabel = new JTextField();
        txtColumn = new JTextField();
        txtRow = new JTextField();
        
        buttonUpdate = new JButton(commandUpdate);
        buttonUpdate.addActionListener(this);
        
        buttonCancel = new JButton(commandCancel);
        buttonCancel.addActionListener(this);
                
        makeDialog();
    }
    
    public void ShowDiaglog(int indexMonster, ArrayList<SeaMonster> monsterList, JDialog parent) {
        this.monsterData = monsterList.get(indexMonster);
        this.arrayListMonster = monsterList;
        this.indexShipData = indexMonster;
        this.parent = parent;
        
        loadFormData(monsterList.get(indexMonster));
        this.setSize(new Dimension(300, 150));
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
        panelTextField.setLayout(new GridLayout(4, 2));
        panelTextField.add(this.lblLabel);
        panelTextField.add(this.txtLabel);
        panelTextField.add(this.lblColumn);
        panelTextField.add(this.txtColumn);
        panelTextField.add(this.lblRow);
        panelTextField.add(this.txtRow);
        
        this.add(panelTextField, BorderLayout.CENTER);
        
    }
    
    public void loadFormData(SeaMonster monster) {        
        this.txtLabel.setText(monster.getLabel());
        this.txtColumn.setText(String.valueOf(monster.getPosition().getColumn()));
        this.txtRow.setText(String.valueOf(monster.getPosition().getRow()));        
    }
        
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == commandUpdate) {
            monsterData.setLabel(this.txtLabel.getText());
            monsterData.getPosition().setLongitude(Double.valueOf(MapConverter.col2lon(Integer.valueOf(this.txtColumn.getText()))));
            monsterData.getPosition().setLatitude(Double.valueOf(MapConverter.row2lat(Integer.valueOf(this.txtRow.getText()))));
            monsterData.getPosition().setColumn(Integer.valueOf(this.txtColumn.getText()));
            monsterData.getPosition().setRow(Integer.valueOf(this.txtRow.getText()));
            this.parent.setVisible(true);
            this.dispose();
        } else if (e.getActionCommand() == commandCancel) {
            UpdateMonsterListForm frm = new UpdateMonsterListForm();
            frm.ShowDialog(this.arrayListMonster);
            this.dispose();
        } 
    }
    
}