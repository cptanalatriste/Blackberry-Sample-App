/*
 * ColoredCheckboxField.java
 *
 * © Research In Motion Limited, 2006
 * Confidential and proprietary.
 */

package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;


public class ColoredCheckboxField extends CheckboxField implements ColoredField
{
    private ColorTable _colorTable;
    
    
    public ColoredCheckboxField() 
    {
        this( null, false );
    }
    
    public ColoredCheckboxField( String label, boolean checked ) 
    {
        this( label, checked, 0 );
    }

    public ColoredCheckboxField( String label, boolean checked, long style ) 
    {
        this( label, checked, style, null );
    }

    public ColoredCheckboxField( String label, boolean checked, long style, LongIntHashtable colorTableData ) 
    {
        super( label, checked, style );
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
