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
    private static String idOne = "??????????";
    private static String nameTwo = "Dara Krang";
    private static String idTwo = "1000910872";
    private static String nameThree = "Hunter Reynolds";
    private static String idThree = "1000999648";
    public static final String commandAbout = String.format("The Three Assassins\nCSE 1325-002\nApril 28, 2015\n\tName: %s\n\tID: %s", nameOne, idOne, nameTwo, idTwo, nameThree, idThree);
    
    //IMPORTANT: file paths should be changed and verified for the demonstration!!!
    public static final String iconPath = "C:\\Users\\Hunter\\Documents\\UTA Files\\Spring2015 (Freshman)\\CSE 1325 (Object-Oriented Programming)\\Semester Project\\Icons";
}
