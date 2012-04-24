/*
 * ColoredRadioButtonField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2008-2008
 */

package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;


public class ColoredRadioButtonField extends RadioButtonField implements ColoredField
{
    private ColorTable _colorTable;
    
    
    public ColoredRadioButtonField() 
    {
        this( null );
    }
    
    public ColoredRadioButtonField( String label )
    {
        this( label, null, false );
    }
    
    public ColoredRadioButtonField( String label, RadioButtonGroup group, boolean selected ) 
    {
        this( label, group, selected, 0 );
    }

    public ColoredRadioButtonField( String label, RadioButtonGroup group, boolean selected, long style ) 
    {
        this( label, group, selected, style, null );
    }

    public ColoredRadioButtonField( String label, RadioButtonGroup group, boolean selected, long style, LongIntHashtable colorTableData ) 
    {
        super( label, group, selected, style );
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
