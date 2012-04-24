/*
 * JustifiedHorizontalFieldManager.java
 *
 * © Research In Motion Limited, 2006
 * Confidential and proprietary.
 */

package devcom.sample.device.api.ui.container;

import net.rim.device.api.ui.*;
import net.rim.device.api.ui.component.*;
import net.rim.device.api.util.*;

import devcon.sample.device.api.ui.*;

public class JustifiedHorizontalFieldManager extends Manager
{
    private static final int SYSTEM_STYLE_SHIFT = 32;
    
    protected Field _leftField;
    protected Field _centerField;
    protected Field _rightField;
    
    protected XYEdges _leftFieldMargins = new XYEdges();
    protected XYEdges _centerFieldMargins = new XYEdges();
    protected XYEdges _rightFieldMargins = new XYEdges();

    
    public JustifiedHorizontalFieldManager( long style )
    {
        super( style );
    }

    public JustifiedHorizontalFieldManager( Field leftField, Field centerField, Field rightField )
    {
        this( leftField, centerField, rightField, Field.USE_ALL_WIDTH );
    }

    public JustifiedHorizontalFieldManager( Field leftField, Field centerField, Field rightField, long style )
    {
        super( style );
        
        _leftField   = ( leftField   != null ) ? leftField   : new NullField( Field.NON_FOCUSABLE );
        _centerField = ( centerField != null ) ? centerField : new NullField( Field.NON_FOCUSABLE );
        _rightField  = ( rightField  != null ) ? rightField  : new NullField( Field.NON_FOCUSABLE );
        
        add( _leftField );
        add( _centerField );
        add( _rightField );
    }
    
    public int getPreferredWidth()
    {
        return   _leftField.getPreferredWidth() + _centerField.getPreferredWidth() + _rightField.getPreferredWidth() 
               + FieldDimensionUtilities.getBorderAndPaddingWidth( this );
    }
    
    public int getPreferredHeight()
    {
        return   Math.max( Math.max( _leftField.getPreferredHeight(), _centerField.getPreferredHeight() ), _rightField.getPreferredHeight() ) 
               + FieldDimensionUtilities.getBorderAndPaddingHeight( this );
    }
    
    protected void sublayout( int width, int height )
    {
        int maxHeight = 0;

        _leftField.getMargin(   _leftFieldMargins   );
        _centerField.getMargin( _centerFieldMargins );
        _rightField.getMargin(  _rightFieldMargins  );
        
        int leftCenterMargin  = Math.max( _leftFieldMargins.right,   _centerFieldMargins.left );
        int rightCenterMargin = Math.max( _centerFieldMargins.right, _rightFieldMargins.left  );
        
        int availableWidth = width - ( _leftFieldMargins.left + leftCenterMargin + rightCenterMargin + _rightFieldMargins.right );

        layoutChild( _leftField, availableWidth, height - _leftFieldMargins.top - _leftFieldMargins.bottom );
        maxHeight = Math.max( maxHeight, _leftFieldMargins.top + _leftField.getHeight() + _leftFieldMargins.bottom );
        availableWidth -= _leftField.getWidth();
        
        layoutChild( _rightField, availableWidth, height - _rightFieldMargins.top - _rightFieldMargins.bottom );
        maxHeight = Math.max( maxHeight, _rightFieldMargins.top + _rightField.getHeight() + _rightFieldMargins.bottom );
        availableWidth -= _rightField.getWidth();

        layoutChild( _centerField, availableWidth, height - _centerFieldMargins.top - _centerFieldMargins.bottom );
        maxHeight = Math.max( maxHeight, _centerFieldMargins.top + _centerField.getHeight() + _centerFieldMargins.bottom );
        availableWidth -= _centerField.getWidth();

        if( !isStyle( Field.USE_ALL_HEIGHT ) ) {
            height = maxHeight;
        }
        if( !isStyle( Field.USE_ALL_WIDTH ) ) {
            width -= availableWidth;
        }

        int xLeft  = _leftFieldMargins.left;
        int xRight = width - _rightField.getWidth() - _rightFieldMargins.right;
        int xCenter;
        int xCenterMin = xLeft + _leftField.getWidth() + leftCenterMargin;
        int xCenterMax = xRight - _centerField.getWidth() - rightCenterMargin; 
        
        switch( (int)( ( _centerField.getStyle() & FIELD_HALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_RIGHT >> SYSTEM_STYLE_SHIFT ):
                xCenter = xCenterMax; 
                break;
            case (int)( FIELD_HCENTER >> SYSTEM_STYLE_SHIFT ):
                xCenter = MathUtilities.clamp( xCenterMin, ( width - _centerField.getWidth() ) / 2, xCenterMax ) ;
                break;
            default: // left
                xCenter = xCenterMin;
                break;
        }

        setPositionChild( _leftField,   xLeft,   getFieldY( _leftField,   height, _leftFieldMargins   ) );
        setPositionChild( _centerField, xCenter, getFieldY( _centerField, height, _centerFieldMargins ) );
        setPositionChild( _rightField,  xRight,  getFieldY( _rightField,  height, _rightFieldMargins  ) );
                          
        setExtent( width, height );
    }

    private int getFieldY( Field field, int height, XYEdges margins )
    {
        switch( (int)( ( field.getStyle() & FIELD_VALIGN_MASK ) >> SYSTEM_STYLE_SHIFT ) ) {
            case (int)( FIELD_BOTTOM >> SYSTEM_STYLE_SHIFT ):
                return height - field.getHeight() - margins.bottom; 
            case (int)( FIELD_VCENTER >> SYSTEM_STYLE_SHIFT ):
                return margins.top + ( height - margins.top - field.getHeight() - margins.bottom ) / 2;
            default:
                return margins.top;
        }
    }

    public Field getLeftField()
    {
        return _leftField;
    }
    
    public Field getCenterField()
    {
        return _centerField;
    }
    
    public Field getRightField()
    {
        return _rightField;
    }
    
    public void replace( Field oldField, Field newField )
    {
        if( oldField == newField ) {
            // Nothing to do
            return;
        }
        
        if( oldField == _leftField ) {
            _leftField = newField;
        } else if( oldField == _centerField ) {
            _centerField = newField;
        } else if( oldField == _rightField ) {
            _rightField = newField;
        } else {
            throw new IllegalArgumentException();
        }
        
        add( newField );
        delete( oldField );
    }
}    

