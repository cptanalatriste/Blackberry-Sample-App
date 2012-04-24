/*
 * AlignmentStyleBitFieldManager.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2009-2009
 */

package devcom.sample.device.api.ui.container;

import net.rim.device.api.ui.*;


public class AlignmentStyleBitFieldManager extends Manager
{

    public AlignmentStyleBitFieldManager( long style )
    {
        super( style  );
    }

    protected void sublayout( int width, int height )
    {
        int numFields = getFieldCount();
        for( int i = 0; i < numFields; i++ ) {

            Field currentField = super.getField( i );
            layoutChild( currentField, width, height );

            int newX = -1;
            int newY = -1;
            long fieldStyle = currentField.getStyle();

            int preferredHeight = currentField.getHeight() + getMarginBottom() + getMarginTop();
            int preferredWidth = currentField.getWidth() + getMarginLeft() + getMarginRight();

            if( ( fieldStyle & Field.FIELD_VCENTER ) == Field.FIELD_VCENTER ) {
                newY = ( height - preferredHeight ) / 2;
            } else if( ( fieldStyle & Field.FIELD_BOTTOM ) == Field.FIELD_BOTTOM ) {
                newY = height - preferredHeight - getMarginBottom();
            } else  {
                newY = getMarginTop();
            }

            if( ( fieldStyle & Field.FIELD_HCENTER ) == Field.FIELD_HCENTER ) {
                newX = ( width - preferredWidth ) / 2;
            } else if( ( fieldStyle & Field.FIELD_RIGHT ) == Field.FIELD_RIGHT ) {
                newX = width - preferredWidth + getMarginRight();
            } else {
                newX = getMarginLeft();
            }

            setPositionChild( currentField, newX, newY );
        }

        setExtent( width, height );
    }
}



























