package shipandmonster;

import java.awt.BorderLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**Represents a visual and data depiction
 *
 * @author Hunter
 */
public class MapTile extends JLabel
{
    private Position position;
    private char symbol;
    private String description;
    
    public MapTile(int col, int row)
    {
        super();
        setLayout(new BorderLayout());
        setBounds(MenuLibrary.ICON_SIZE*col + MenuLibrary.MAP_ORIGIN_X, MenuLibrary.ICON_SIZE*row + MenuLibrary.MAP_ORIGIN_Y, MenuLibrary.ICON_SIZE, MenuLibrary.ICON_SIZE);
        
        position = new Position(col, row);
        symbol = '.'; //set to water by default
        description = "Ocean";
    }
    
    public Position getPosition()
    {
        return position;
    }
    
    public void setPosition(Position p)
    {
        position = p;
    }
    
    public char getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(char s)
    {
        this.symbol = s;
        
        //update description as well
        if (symbol == '.')
        {
            description = "Ocean";
        }
        else if (symbol == '*')
        {
            description = "Land";
        }
        else if (symbol == 'D')
        {
            description = "Dock";
        }
        else if (symbol == 'C')
        {
            description = "Crane";
        }
        else if (symbol == 'P')
        {
            description = "Pier";
        }
        else if (symbol == 'S')
        {
            description = "Cargo Ship";
        }
        else if (symbol == 'B')
        {
            description = "Container Ship";
        }
        else if (symbol == 'T')
        {
            description = "Oil Tanker";
        }
        else if (symbol == '$')
        {
            description = "Docked Ship";
        }
        else if (symbol == 'X')
        {
            description = "Unsafe Ship(s)";
        }
        else if (symbol == 'G')
        {
            description = "Godzilla";
        }
        else if (symbol == 'K')
        {
            description = "\"Hello, beastie!\" ~Jack Sparrow";
        }
        else if (symbol == 'L')
        {
            description = "Leviathan";
        }
        else if (symbol == 's')
        {
            //CAREFUL! Lowercase 's' is the sea serpent, upper-case 'S' is a cargo ship
            description = "Sea Serpent";
        }
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String d)
    {
        description = d;
    }
}
