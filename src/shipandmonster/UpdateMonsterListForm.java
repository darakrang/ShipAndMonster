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
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author Dara
 */
public class UpdateMonsterListForm extends JDialog implements ActionListener,ListSelectionListener
{
    JList list;
    JButton buttonUpdate;
    JButton buttonCancel;
    
    JPanel panelButton;
    JPanel panelList;
    JScrollPane scrollPaneList;
    
    
    public static final String commandUpdate="Update";
    public static final String commandCancel="Cancel";
    
    ArrayList<String> listData;
    
    private ArrayList<SeaMonster> arrayListMonster;    
    
    public UpdateMonsterListForm()
    {
        
        panelButton=new JPanel();
        panelList=new JPanel();
        scrollPaneList = new JScrollPane(panelList);
        
        list=new JList();
        list.addListSelectionListener(this);
        
        buttonUpdate=new JButton(commandUpdate);
        buttonUpdate.addActionListener(this);
        
        buttonCancel=new JButton(commandCancel);
        buttonCancel.addActionListener(this);

        listData=new ArrayList<String>();
        
        makeDialog();
    }
    
    public void ShowDialog(ArrayList<SeaMonster> monsterList) {
        arrayListMonster = monsterList;
        
        loadList(monsterList);
        
        this.setSize(new Dimension(300,300));
        Utilities.centerOnScreen(this, true);
        this.setVisible(true);
    }
    
    public void makeDialog()
    {
        //Dialog Layout
        this.setLayout(new BorderLayout());
        
        //Button Pane Layout
        panelButton.setLayout(new GridLayout(1,2));
        panelButton.add(buttonUpdate);
        panelButton.add(buttonCancel);
        this.add(panelButton,BorderLayout.SOUTH);
        
        //List Pane Layout
        panelList.setLayout(new GridLayout(1,1));
        panelList.add(list);
        this.add(scrollPaneList,BorderLayout.CENTER);
        
        
    }

    public void loadList(ArrayList<SeaMonster> monsterList)
    {
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (int i = 0; i < monsterList.size(); i++) {
            model.addElement(monsterList.get(i).getLabel());
        }
        list.setModel(model);
        list.setSelectedIndex(0);
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
       
        if (e.getActionCommand()==commandUpdate)
        {
            int index=list.getSelectedIndex();            
            UpdateMonsterForm frm = new UpdateMonsterForm();
            frm.ShowDiaglog(index, arrayListMonster, this);
            this.setVisible(false);
        }
        else if (e.getActionCommand()==commandCancel)
        {
            this.dispose();
        }
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
       // JList source=(JList) e.getSource();
       // int index=e.getFirstIndex();
      // System.out.println(index);
      // System.out.println(listData[index]);
        
    }
}