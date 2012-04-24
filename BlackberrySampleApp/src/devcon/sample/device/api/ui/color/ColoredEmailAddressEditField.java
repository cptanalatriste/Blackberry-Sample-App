/*
 * ColoredEmailAddressEditField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2009-2009
 */


package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;

public class ColoredEmailAddressEditField extends EmailAddressEditField implements ColoredField
{
    private ColorTable _colorTable;

    
    public ColoredEmailAddressEditField( String label, String initialValue )
    {
        this( label, initialValue, DEFAULT_MAXCHARS );
    }
        
    public ColoredEmailAddressEditField( String label, String initialValue, int maxNumChars )
    {
        this( label, initialValue, maxNumChars, 0 );
    }
        
    public ColoredEmailAddressEditField( String label, String initialValue, int maxNumChars, long style )
    {
        this( label, initialValue, maxNumChars, style, null );
    }
        
    public ColoredEmailAddressEditField( String label, String initialValue, int maxNumChars, long style, LongIntHashtable colorTableData )
    {
        super( label, initialValue, maxNumChars, style );
        _colorTable = new ColorTable( this, colorTableData );
    }

        
    public void setColorTableData( LongIntHashtable colorTableData )
    {
        _colorTable.setColorTableData( colorTableData );
        invalidate();
    }
    

    protected void paint( Graphics g ) 
    {
        _colorTable.paint( g );
    }

    public void superPaint( Graphics g )
    {
        super.paint( g );
    }

    protected void drawFocus( Graphics g, boolean on )
    {
        _colorTable.drawFocus( g, on );
    }
    
}
