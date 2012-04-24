/*
 * ColoredLabelField.java
 *
 * © Research In Motion Limited, 2006
 * Confidential and proprietary.
 */

package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;


public class ColoredLabelField extends LabelField implements ColoredField
{
    private ColorTable _colorTable;
    

    public ColoredLabelField( String text ) 
    {
        this( text, 0 );
    }

    public ColoredLabelField( String text, long style ) 
    {
        this( text, style, null );
    }
    
    public ColoredLabelField( String text, long style, LongIntHashtable colorTableData ) 
    {
        super( text, style );
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
