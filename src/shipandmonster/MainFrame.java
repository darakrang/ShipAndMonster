/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package shipandmonster;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

/**
 *
 * @author beckerxx
 */
public class MainFrame extends JFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu menuFile;

    JMenu menuShip;
    JMenuItem menuitemGenerateShips;
    JMenuItem menuitemUpdateShips;
    JMenuItem menuitemDisplayAllShips;
    JMenuItem menuitemRemoveAllShips;

    JMenu menuPort;
    JMenuItem menuitemUnloadShip;
    JMenuItem menuitemUpdateDock;
    JMenuItem menuitemDisplayAllCargos;
    JMenuItem menuitemDisplayAllDocks;

    JMenu menuMonster;
    JMenuItem menuitemGenerateMonsters;
    JMenuItem menuitemUpdateMonsters;
    JMenuItem menuitemDisplayAllMonsters;
    JMenuItem menuitemRemoveAllMonsters;
    JMenuItem menuitemSummonGodzilla;

    JMenuItem menuitemExit;
    JMenuItem menuitemBoo;
    JMenuItem menuitemAbout;

    JTextArea messageboxStatus;

    public final static String labelFile = "File";
    public final static String commandShip = "Ship";
    public final static String commandGenerateShip = "Generate Ships";
    public final static String commandUpdateShip = "Update Ships";
    public final static String commandDisplayAllShips = "Display all Ships";
    public final static String commandRemoveallShips = "Remove all Ships";
    public final static String commandPort = "Port";
    public final static String commandUnloadShip = "Unload Ship";
    public final static String commandUpdateDock = "Update Dock";
    public final static String commandDisplayAllDocks = "Display All Docks";
    public final static String commandDisplayAllCargos = "Display All Cargos";
    public final static String commandMonster = "Sea Monster";
    public final static String commandGenerateMonsters = "Generate Monsters";
    public final static String commandUpdateMonsters = "Update Monsters";
    public final static String commandDisplayallMonsters = "Display all Monsters";
    public final static String commandRemoveAllMonsters = "Remove all Monsters";
    public final static String commandSummonGodzilla = "Summon Godzilla";
    public final static String commandExit = "Exit";
    public final static String commandBoo = "Boo!";
    public final static String commandAbout = "About";

    public MainFrame() {
        //Set up my Menus
        menuBar = new JMenuBar();

        menuFile = new JMenu(labelFile);

        menuitemExit = new JMenuItem(commandExit);
        menuitemExit.addActionListener(this);
        menuFile.add(menuitemExit);

        //Ship Menu and its menu item
        menuShip = new JMenu(commandShip);
        menuShip.addActionListener(this);

        menuitemGenerateShips = new JMenuItem(commandGenerateShip);
        menuitemGenerateShips.addActionListener(this);
        menuShip.add(menuitemGenerateShips);

        menuitemUpdateShips = new JMenuItem(commandUpdateShip);
        menuitemUpdateShips.addActionListener(this);
        menuShip.add(menuitemUpdateShips);

        menuitemDisplayAllShips = new JMenuItem(commandDisplayAllShips);
        menuitemDisplayAllShips.addActionListener(this);
        menuShip.add(menuitemDisplayAllShips);

        menuitemRemoveAllShips = new JMenuItem(commandRemoveallShips);
        menuitemRemoveAllShips.addActionListener(this);
        menuShip.add(menuitemRemoveAllShips);

        //Port Menu and its menu item
        menuPort = new JMenu(commandPort);
        menuPort.addActionListener(this);
        
        menuitemUnloadShip = new JMenuItem(commandUnloadShip);
        menuitemUnloadShip.addActionListener(this);
        menuPort.add(menuitemUnloadShip);
        
        menuitemUpdateDock = new JMenuItem(commandUpdateDock);
        menuitemUpdateDock.addActionListener(this);
        menuPort.add(menuitemUpdateDock);
        
        menuitemDisplayAllDocks = new JMenuItem(commandDisplayAllDocks);
        menuitemDisplayAllDocks.addActionListener(this);
        menuPort.add(menuitemDisplayAllDocks);
        
        menuitemDisplayAllCargos = new JMenuItem(commandDisplayAllCargos);
        menuitemDisplayAllCargos.addActionListener(this);
        menuPort.add(menuitemDisplayAllCargos);
        
        //Monster Menu and its menu item
        menuMonster = new JMenu(commandMonster);
        menuMonster.addActionListener(this);
        
        menuitemGenerateMonsters = new JMenuItem(commandGenerateMonsters);
        menuitemGenerateMonsters.addActionListener(this);
        menuMonster.add(menuitemGenerateMonsters);
        
        menuitemUpdateMonsters = new JMenuItem(commandUpdateMonsters);
        menuitemUpdateMonsters.addActionListener(this);
        menuMonster.add(menuitemUpdateMonsters);
        
        menuitemDisplayAllMonsters = new JMenuItem(commandDisplayallMonsters);
        menuitemDisplayAllMonsters.addActionListener(this);
        menuMonster.add(menuitemDisplayAllMonsters);
        
        menuitemRemoveAllMonsters = new JMenuItem(commandRemoveAllMonsters);
        menuitemRemoveAllMonsters.addActionListener(this);
        menuMonster.add(menuitemRemoveAllMonsters);
        
        menuitemSummonGodzilla = new JMenuItem(commandSummonGodzilla);
        menuitemSummonGodzilla.addActionListener(this);
        menuMonster.add(menuitemSummonGodzilla);
        
        //=====================

        menuitemAbout = new JMenuItem(commandAbout);
        menuitemAbout.addActionListener(this);

        menuBar.add(menuFile);
        menuBar.add(menuShip);
        menuBar.add(menuPort);
        menuBar.add(menuMonster);
        menuBar.add(menuitemAbout);
        this.setJMenuBar(menuBar);

        //Create Workspace
        BorderLayout borderLayout = new BorderLayout();
        this.setLayout(borderLayout);

        messageboxStatus = new JTextArea("Status Box!");
        messageboxStatus.setEditable(false);
        this.add(messageboxStatus, BorderLayout.SOUTH);

        Dimension d = new Dimension(800, 600);
        this.setSize(d);
        this.setVisible(true);

    }

//    public void createMenuItem()
    public void actionPerformed(ActionEvent e) {
        //System.out.println("I've been clicked!");

        String command = e.getActionCommand();

        switch (command) {
            case commandExit:
                System.exit(0);
                break;
            case commandBoo:
                System.out.println("Go for the eyes!");
                messageboxStatus.append("Go for the eyes, Boo! Go for the eyes!\n");
                break;
            case commandAbout:
                JOptionPane.showMessageDialog(this, "Minsc the Ranger at your service!");
                break;
        }

    }

}
