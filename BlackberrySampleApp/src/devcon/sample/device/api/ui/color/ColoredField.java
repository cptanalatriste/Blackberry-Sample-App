package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.util.*;


public interface ColoredField extends ColoredFieldConstants
{
    public void superPaint( Graphics graphics );
    public void setColorTableData( LongIntHashtable colorTableData );
}
