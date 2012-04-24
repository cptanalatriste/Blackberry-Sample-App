/*
 * TwoColumnFieldManager.java
 *
 * © Research In Motion Limited, 2006
 * Confidential and proprietary.
 */

package devcom.sample.device.api.ui.container;

import net.rim.device.api.ui.*;


public class TwoColumnFieldManager extends Manager
{
    private static final int SYSTEM_STYLE_SHIFT = 32;
    
    private static final int MAX_EXTENT = Integer.MAX_VALUE >> 1;  // Copied from VerticalFieldManager

    
    protected int _maxLeftColumnWidth;
    protected int _leftColumnWidth;
    
    
    public TwoColumnFieldManager( int maxLeftColumnWidth ) 
    {
        this( maxLeftColumnWidth, 0 );
    }

    public TwoColumnFieldManager( int maxLeftColumnWidth, long style ) 
    {
        super( style );
        _maxLeftColumnWidth = maxLeftColumnWidth;
    }

    public void sublayout( int width, int height )
    {
        int layoutHeight = isStyle( Manager.VERTICAL_SCROLL ) ? MAX_EXTENT : height;
        int layoutWidth = width;

        int maxLeftFieldWidth = 0;
        
        int numFields = getFieldCount();
        for( int i = 0; i < numFields; i++ ) {
            Field currentField = getField( i );
            if( currentField instanceof TwoColumnField ) {
               int currentLeftFieldWidth = ((TwoColumnField) currentField).layoutLeft( layoutWidth, layoutHeight );
               maxLeftFieldWidth = Math.max( currentLeftFieldWidth, maxLeftFieldWidth );
           }
        }
        
        
        int maxWidth = 0;
        
        int currentY = 0;
        int previousMarginBottom = 0;
        
        _leftColumnWidth = Math.min( maxLeftFieldWidth, _maxLeftColumnWidth );
        for( int i = 0; i < numFields; i++ ) {
            
            Field currentField = getField( i );
            if( currentField instanceof TwoColumnField ) {
               ((TwoColumnField) currentField).setLeftColumnWidth( _leftColumnWidth );
            }

            int marginLeft   = currentField.getMarginLeft();
            int marginRight  = currentField.getMarginRight();
            int marginTop    = currentField.getMarginTop();
            int marginBottom = currentField.getMarginBottom();

            currentY += Math.max( marginTop, previousMarginBottom );
            
            int availableChildWidth = layoutWidth - marginLeft - marginRight;
            int availableChildHeight = layoutHeight - currentY - currentField.getMarginBottom();
            
            layoutChild( currentField, availableChildWidth, availableChildHeight );
            
            maxWidth = Math.max( maxWidth, marginLeft + currentField.getWidth() + marginRight );
            currentY += currentField.getHeight();

            previousMarginBottom = marginBottom;
        }
    
        currentY += previousMarginBottom;

        int actualWidth = isStyle( USE_ALL_WIDTH ) ? layoutWidth : maxWidth;
        int actualHeight = currentY;

        // Now set the field positions
        currentY = 0;
        previousMarginBottom = 0;
        
        for( int i = 0; i < numFields; i++ ) {
        
            Field currentField = getField( i );
            currentY += Math.max( currentField.getMarginTop(), previousMarginBottom );
            
            int marginLeft  = currentField.getMarginLeft();
            int marginRight = currentField.getMarginRight();
            
            int childX;
            switch( (int)( ( currentField.getStyle() & FIELD_HALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
                case (int)( FIELD_RIGHT >> SYSTEM_STYLE_SHIFT ):
                    childX = actualWidth - currentField.getWidth() - marginRight;
                    break;
                case (int)( FIELD_HCENTER >> SYSTEM_STYLE_SHIFT ):
                    childX = marginLeft + ( actualWidth - marginLeft - currentField.getWidth() - marginRight ) / 2;
                    break;
                default:
                    childX = marginLeft;
                    break;
            }
            
            // The vertical margins can overlap
            setPositionChild( currentField, childX, currentY );
            
            currentY += currentField.getHeight();
            previousMarginBottom = currentField.getMarginBottom();

        }
        
        setVirtualExtent( actualWidth, actualHeight );
        setExtent( Math.min( actualWidth, width ), Math.min( actualHeight, height ) );
    }
    
}
