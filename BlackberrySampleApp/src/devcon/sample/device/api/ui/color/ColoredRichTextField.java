/*
 * ColoredRichTextField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2009-2009
 */

package devcon.sample.device.api.ui.color;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;


public class ColoredRichTextField extends RichTextField implements ColoredField
{
    private ColorTable _colorTable;
    

    public ColoredRichTextField() 
    {
        this( null );
    }

    public ColoredRichTextField( String text ) 
    {
        this( text, 0 );
    }

    public ColoredRichTextField( String text, long style ) 
    {
        this( text, null, null, null, style );
    }

    public ColoredRichTextField( String text, int[] offsets, byte[] attributes, Font[] fonts, long style ) 
    {
        this( text, offsets, attributes, fonts, null, style );
    }

    public ColoredRichTextField( String text, int[] offsets, byte[] attributes, Font[] fonts, Object[] cookies, long style ) 
    {
        this( text, offsets, attributes, fonts, cookies, style, null );
    }
    
    public ColoredRichTextField( String text, int[] offsets, byte[] attributes, Font[] fonts, Object[] cookies, long style, LongIntHashtable colorTableData ) 
    {
        super( text, offsets, attributes, fonts, cookies, style );
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
