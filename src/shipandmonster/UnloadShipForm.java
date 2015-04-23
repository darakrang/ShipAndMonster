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
public class UnloadShipForm extends JDialog implements ActionListener, ListSelectionListener {

    JList list;
    JButton buttonUpdate;
    JButton buttonCancel;

    JPanel panelButton;
    JPanel panelList;
    JScrollPane scrollPaneList;

    public static final String commandUnload = "Unload";
    public static final String commandCancel = "Cancel";

    ArrayList<String> listData;
    private ArrayList<CargoShip> arrayListUnloadShip;
    private ArrayList<Dock> arrayListUnloadDock;
    private ArrayList<Cargo> arrayListUnloadCargo;
    private Port unloadPort = new Port();

    public UnloadShipForm() {

        panelButton = new JPanel();
        panelList = new JPanel();
        scrollPaneList = new JScrollPane(panelList);

        list = new JList();
        list.addListSelectionListener(this);

        buttonUpdate = new JButton(commandUnload);
        buttonUpdate.addActionListener(this);

        buttonCancel = new JButton(commandCancel);
        buttonCancel.addActionListener(this);

        listData = new ArrayList<String>();

        makeDialog();
    }

    public void getArrayListUnloadShipsAndDock(Port port, ArrayList<CargoShip> arrayListShip) {
        int index = 0, startIndex = 0;
        CargoShip unloadShip;
//        Dock unloadDock;

        System.out.println("Please choose a safety ship to unload:");
        for (Dock dock : port.getArrayListDock()) {
            if (dock.getSymbol() == '$') {
                continue;
            }
            for (index = startIndex; index < arrayListShip.size(); index++) {
                unloadShip = arrayListShip.get(index);
                if (unloadShip.getCargo() != null
                        && unloadShip.getLatitude() == dock.getLatitude()
                        && unloadShip.getLongitude() == dock.getLongitude()
                        && !arrayListUnloadShip.contains(unloadShip)) {
                    unloadPort.getArrayListDock().add(dock);
                    arrayListUnloadShip.add(unloadShip);

                    startIndex = index + 1;
                    break;
                }
            }
        }
    }

    public void ShowDiaglog(ArrayList<Dock> dockList, ArrayList<CargoShip> shipList, ArrayList<Cargo> cargoList) {
        //get and fill data to Unload Dock and Ship
        arrayListUnloadDock = dockList;
        arrayListUnloadShip = shipList;
        arrayListUnloadCargo = cargoList;
        
        loadList(arrayListUnloadShip);

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

        //List Pane Layout
        panelList.setLayout(new GridLayout(1, 1));
        panelList.add(list);
        this.add(scrollPaneList, BorderLayout.CENTER);

    }

    public void loadList(ArrayList<CargoShip> shipList) {
        DefaultListModel<String> model = new DefaultListModel<String>();
        for (int i = 0; i < shipList.size(); i++) {
            model.addElement(shipList.get(i).getName());
        }
        list.setModel(model);
        list.setSelectedIndex(0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand() == commandUnload) {
            int index = list.getSelectedIndex();
            CargoShip unloadShip;
            Dock unloadDock;
            unloadShip = arrayListUnloadShip.get(index);
            unloadDock = arrayListUnloadDock.get(index);

            unloadDock.setSymbol('$');

            arrayListUnloadCargo.add(unloadShip.getCargo());
            unloadShip.setCargo(null);
            this.dispose();
        } else if (e.getActionCommand() == commandCancel) {
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
