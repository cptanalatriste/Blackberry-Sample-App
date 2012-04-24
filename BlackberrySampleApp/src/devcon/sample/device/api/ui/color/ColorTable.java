package devcon.sample.device.api.ui.color;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.*;
import net.rim.device.api.util.*;


public class ColorTable implements ColoredFieldConstants
{
    private static final LongIntHashtable _emptyColorTableData = new LongIntHashtable();

    private LongIntHashtable _colorTableData = _emptyColorTableData;
    private ColoredField _coloredField;

    private XYRect _focusRect = new XYRect();


    public ColorTable( ColoredField field, LongIntHashtable colorTableData ) 
    {
        if( !( field instanceof Field ) ) {
            throw new IllegalArgumentException();
        }
        
        _coloredField = field;
        setColorTableData( colorTableData );
    }
    
    public void setColorTableData( LongIntHashtable colorTableData )
    {
        if( colorTableData == null ) {
            return;
        }
        _colorTableData = colorTableData;
    }
    
    public boolean setGlobalAlpha( Graphics g, long colorKey )
    {
        int alpha = _colorTableData.get( colorKey );
        if( alpha == -1 ) {
            return false;
        }
        g.setGlobalAlpha( alpha );
        return true;
    }
    
    public boolean setColor( Graphics g, long colorKey )
    {
        int color = _colorTableData.get( colorKey );
        if( color == -1 ) {
            return false;
        }
        g.setColor( color );
        return true;
    }
    
    public boolean setBackgroundColor( Graphics g, long colorKey )
    {
        int color = _colorTableData.get( colorKey );
        if( color == -1 ) {
            return false;
        }
        g.setBackgroundColor( color );
        return true;
    }
    
    public void paint( Graphics g ) 
    {
        int oldColor = g.getColor();
        int oldGlobalAlpha = g.getGlobalAlpha();
        try {
            
            setGlobalAlpha( g, ALPHA_FOREGROUND );
            setColor( g, g.isDrawingStyleSet( Graphics.DRAWSTYLE_FOCUS ) ? COLOR_FOREGROUND_FOCUS : COLOR_FOREGROUND );
            
            _coloredField.superPaint( g );
        
        } finally {
            g.setColor( oldColor );
            g.setGlobalAlpha( oldGlobalAlpha );
        }
    }
    
    public void drawFocus( Graphics g, boolean on )
    {
        ((Field) _coloredField).getFocusRect( _focusRect );

        boolean notEmpty = g.pushContext( _focusRect.x, _focusRect.y, _focusRect.width, _focusRect.height, 0, 0 );
        if( notEmpty ) {

            int oldBackgroundColor = g.getBackgroundColor();
            try {
                    
                if( on ) {
                    setBackgroundColor( g, COLOR_FOCUS );
                    g.clear();
                }
                 
                paint( g );
        
            } finally {
                g.setBackgroundColor( oldBackgroundColor );
            }
        }
        
        g.popContext();
        
    }
        
}
