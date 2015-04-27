package shipandmonster;

/**Contains useful constant definitions used in the GUI menus
 *
 * @author Hunter
 */
public class MenuLibrary
{
    //menu titles
    public static final String labelFile = "File";
    public static final String labelShip = "Ship";
    public static final String labelPort = "Port";
    public static final String labelSeaMonster = "Sea Monster";
    public static final String labelAbout = "About";
    
    //file menu
    public static final String commandOpen = "Open";
    public static final String commandClose = "Close";
    public static final String commandSnapshot = "Snap Shot";
    public static final String commandExit = "Exit";
    
    //ship menu
    public static final String commandGenerateShips = "Generate Ships";
    public static final String commandUpdateShips = "Update Ships";
    public static final String commandDisplayShips = "Display All Ships";
    public static final String commandRemoveShips = "Remove All Ships";
    
    //port menu
    public static final String commandUnloadShip = "Unload Ship";
    public static final String commandUpdateDock = "Update Dock";
    public static final String commandDisplayDocks = "Display All Docks";
    public static final String commandDisplayCargos = "Display All Cargos";
    
    //sea monster menu
    public static final String commandGenerateMonsters = "Generate Monsters";
    public static final String commandUpdateMonsters = "Update Monsters";
    public static final String commandDisplayMonsters = "Display All Monsters";
    public static final String commandRemoveMonsters = "Remove All Monsters";
    public static final String commandSummonGodzilla = "Summon Godzilla";
    
    //about menu
    private static String nameOne = "Cyril Lutterodt";
    private static String idOne = "1001016204";
    private static String nameTwo = "Dara Krang";
    private static String idTwo = "1000910872";
    private static String nameThree = "Hunter Reynolds";
    private static String idThree = "1000999648";
    public static final String commandAbout = String.format("The Three Genii\nCSE 1325-002\nApril 28, 2015\n\nName: %s\nID: %s\n\nName: %s\nID: %s\n\nName: %s\nID: %s", nameOne, idOne, nameTwo, idTwo, nameThree, idThree);
    //Genii = plural of Genius
    
    //buttons
    public static final String commandStart = "Begin Hunting";
    public static final String commandStop = "Stop Hunting";
    public static final String command3D = "Launch 3D";
    
    //IMPORTANT: file paths should be changed and verified for the demonstration!!!
    public static final String iconPath = "C:\\UTA\\1325\\GitHub\\ShipAndMonster\\images\\";
    
    
    public static final int ICON_SIZE = 15; //15x15 icons
    public static final int MAP_ORIGIN_X = 0;
    public static final int MAP_ORIGIN_Y = 23;
}
