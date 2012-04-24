/*
 * TwoColumnField.java
 *
 * © Research In Motion Limited, 2006
 * Confidential and proprietary.
 */

package devcom.sample.device.api.ui.container;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.NullField;

public class TwoColumnField extends Manager
{
    private static final int SYSTEM_STYLE_SHIFT = 32;
    
    private Field _leftField;
    private Field _rightField;

    protected int _leftColumnWidth = -1;
    
    
    public TwoColumnField()
    {
        this( null, null );
    }
    
    public TwoColumnField( Field leftField, Field rightField ) 
    {
        this( leftField, rightField, 0 );
    }
    
    public TwoColumnField( Field leftField, Field rightField, long style ) 
    {
        super( style );

        _leftField  = ( leftField  != null ) ? leftField  : new NullField( NON_FOCUSABLE );
        _rightField = ( rightField != null ) ? rightField : new NullField( NON_FOCUSABLE );
        
        add( _leftField );
        add( _rightField );
    }

    public Field getLeftField()
    {
        return _leftField;
    }
    
    public Field getRightField()
    {
        return _rightField;
    }
    
    public void replace( Field oldField, Field newField )
    {
        super.replace( oldField, newField );
        
        if( oldField == _leftField ) {
            _leftField = newField;
        } else if( oldField == _rightField ) {
            _rightField = newField;
        }
    }

    /**
     * Lays out the the left field and then return the width that it would like to have.
     */
    public int layoutLeft( int width, int height )
    {
        int marginLeft   = _leftField.getMarginLeft();
        int marginRight  = _leftField.getMarginRight();
        int marginTop    = _leftField.getMarginTop();
        int marginBottom = _leftField.getMarginBottom();
         
        width = width - marginLeft - marginBottom;
        height = height - marginTop - marginBottom;
        
        layoutChild( _leftField, width, height );
            
        return _leftField.getWidth() + marginLeft + marginRight;
    }
    
    public void setLeftColumnWidth( int leftColumnWidth )
    {
        _leftColumnWidth = leftColumnWidth;
    }
    
    protected void sublayout( int width, int height )
    {
        if( _leftColumnWidth == -1 ) {
            throw new IllegalStateException();
        }

        // Calculate dimensions for the left field
        int leftMarginLeft    = _leftField.getMarginLeft();
        int leftMarginRight   = _leftField.getMarginRight();
        int leftMarginTop     = _leftField.getMarginTop();
        int leftMarginBottom  = _leftField.getMarginBottom();

        int leftWidth   = _leftColumnWidth - leftMarginLeft - leftMarginRight;
        int leftHeight  = height - leftMarginTop - leftMarginBottom;

        // Calculate dimensions for the right field
        int rightMarginLeft   = _rightField.getMarginLeft();
        int rightMarginRight  = _rightField.getMarginRight();
        int rightMarginTop    = _rightField.getMarginTop();
        int rightMarginBottom = _rightField.getMarginBottom();

        int rightWidth  = width - _leftColumnWidth - rightMarginLeft - rightMarginRight;
        int rightHeight = height - rightMarginTop - rightMarginBottom;
        

        // Lay out the fields
        layoutChild( _leftField, leftWidth, leftHeight );
        layoutChild( _rightField, rightWidth, rightHeight );
        
        
        int actualWidth = isStyle( USE_ALL_WIDTH ) ? width : ( _leftColumnWidth + rightMarginLeft + _rightField.getWidth() + rightMarginRight );
        int actualRightColumnWidth = actualWidth - _leftColumnWidth;
        
        int actualHeight = Math.max( 
            leftMarginTop + _leftField.getHeight() + leftMarginBottom, 
            rightMarginTop + _rightField.getHeight() + rightMarginBottom );
        
        
        // Calculate field positions
        int leftX;
        switch( (int)( ( _leftField.getStyle() & FIELD_HALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_RIGHT >> SYSTEM_STYLE_SHIFT ):
                leftX = _leftColumnWidth - _leftField.getWidth() - leftMarginRight;
                break;
            case (int)( FIELD_HCENTER >> SYSTEM_STYLE_SHIFT ):
                leftX = leftMarginLeft + ( _leftColumnWidth - leftMarginLeft - _leftField.getWidth() - leftMarginRight ) / 2;
                break;
            default:
                leftX = leftMarginLeft;
                break;
        }
        int leftY;
        switch( (int)( ( _leftField.getStyle() & FIELD_VALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_BOTTOM >> SYSTEM_STYLE_SHIFT ):
                leftY = actualHeight - _leftField.getHeight() - leftMarginBottom;
                break;
            case (int)( FIELD_VCENTER >> SYSTEM_STYLE_SHIFT ):
                leftY = leftMarginTop + ( actualHeight - leftMarginTop - _leftField.getHeight() - leftMarginBottom ) / 2;
                break;
            default:
                leftY = leftMarginTop;
                break;
        }
        setPositionChild( _leftField, leftX, leftY );
        
        int rightX;
        switch( (int)( ( _rightField.getStyle() & FIELD_HALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_RIGHT >> SYSTEM_STYLE_SHIFT ):
                rightX = actualWidth - _rightField.getWidth() - rightMarginRight;
                break;
            case (int)( FIELD_HCENTER >> SYSTEM_STYLE_SHIFT ):
                rightX = _leftColumnWidth + rightMarginLeft + ( actualRightColumnWidth - rightMarginLeft - _rightField.getWidth() - rightMarginRight ) / 2;
                break;
            default:
                rightX = _leftColumnWidth + rightMarginLeft;
                break;
        }
        int rightY;
        switch( (int)( ( _rightField.getStyle() & FIELD_VALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_BOTTOM >> SYSTEM_STYLE_SHIFT ):
                rightY = actualHeight - _rightField.getHeight() - rightMarginBottom;
                break;
            case (int)( FIELD_VCENTER >> SYSTEM_STYLE_SHIFT ):
                rightY = rightMarginTop + ( actualHeight - rightMarginTop - _rightField.getHeight() - rightMarginBottom ) / 2;
                break;
            default:
                rightY = rightMarginTop;
                break;
        }
        setPositionChild( _rightField, rightX, rightY );

        setExtent( actualWidth, actualHeight );
    }
    
}
