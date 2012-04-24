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
import net.rim.device.api.i18n.*;


public class ColoredDateField extends DateField implements ColoredField
{
    private ColorTable _colorTable;
    
    
    public ColoredDateField()
    {
        this( null, 0, DATE_TIME );
    }
    
    public ColoredDateField( String label, long date, long style ) 
    {
        super( label, date, style );
        _colorTable = new ColorTable( this, null );
    }
    
    public ColoredDateField( String label, long date, DateFormat format ) 
    {
        this( label, date, format, 0 );
    }

    public ColoredDateField( String label, long date, DateFormat format, long style ) 
    {
        this( label, date, format, style, null );
    }
    
    public ColoredDateField( String label, long date, long style, LongIntHashtable colorTableData ) 
    {
        super( label, date, style );
        _colorTable = new ColorTable( this, colorTableData );
    }

    public ColoredDateField( String label, long date, DateFormat format, long style, LongIntHashtable colorTableData ) 
    {
        super( label, date, format, style );
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
