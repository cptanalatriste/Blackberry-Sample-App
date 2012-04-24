//#preprocess
/*
 * CustomButtonField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2010-2010
 */

package devcon.sample.device.api.ui.component;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;

import net.rim.device.api.ui.component.*;
import net.rim.device.api.ui.decor.*;

/**
 * Implements all the stuff we don't want to do each time we need a new button
 */
public class CustomButtonField extends Field
{
    private XYRect _drawFocusTempRect = new XYRect();
    
    private int _normalColor;
    private int _focusColor;
    private int _activeColor;
    
    private Border _normalBorder;
    private Border _focusBorder;
    private Border _activeBorder;
    
    private Background _normalBackground;
    private Background _focusBackground;
    private Background _activeBackground;
    
    private String _text;
    
    private boolean _active;
    private boolean _focus;
    
    private XYRect _contentRect;
    private XYRect _borderRect;
    private XYRect _backgroundRect;
    
    public CustomButtonField( String text, Border normalBorder, Border focusBorder, Border activeBorder, int normalColor, int focusColor, int activeColor, long style )
    {        
        super( Field.FOCUSABLE | style );
        
        _text = text;
        
        _normalColor = normalColor;
        _focusColor = focusColor;
        _activeColor = activeColor;
        
        _normalBorder = normalBorder;
        _focusBorder = focusBorder;
        _activeBorder = activeBorder;
        
        _normalBackground = _normalBorder.getBackground();
        _focusBackground = _focusBorder.getBackground();
        _activeBackground = _activeBorder.getBackground();
        
        _borderRect = new XYRect();
        _contentRect = new XYRect();
        _backgroundRect = new XYRect();
    }
        
    public void setText(String text)
    {
        _text = text;
        updateLayout();
    }
    
        
    public String getText()
    {
        return _text;
    }
    
    public int getPreferredWidth()
    {
        if( isStyle( USE_ALL_WIDTH ) ) {
            return Integer.MAX_VALUE;
        } else {
            return _normalBorder.getLeft() + getFont().getBounds( _text ) + _normalBorder.getRight();
        }
    }
    
    public int getPreferredHeight()
    {
        return _normalBorder.getTop() + getFont().getHeight() + _normalBorder.getBottom();
    }
        
    protected void layout( int width, int height )
    {
        setExtent( Math.min( width, getPreferredWidth() ), Math.min( height, getPreferredHeight() ) );
        
        int borderLeft    = _normalBorder.getLeft();
        int borderRight   = _normalBorder.getRight();
        int borderTop     = _normalBorder.getTop();
        int borderBottom  = _normalBorder.getBottom();
        
        int borderWidth   = borderLeft + borderRight;
        int borderHeight  = borderTop  + borderBottom;
        
        getContentRect( _contentRect );
        _borderRect.set( 0, 0, getWidth(), getHeight() );
        _backgroundRect.set( borderLeft, borderTop, _borderRect.width - borderWidth, _borderRect.height - borderHeight );
    }
    
    
    /* Painting */
    
    protected void paint( Graphics g )
    {
        int oldColour = g.getColor();
        try {
            
            g.setColor( getTextColor() );
            g.drawText( _text, 0, _backgroundRect.y, DrawStyle.HCENTER, _contentRect.width ); 

        } finally {
            g.setColor( oldColour );
        }
    }
    
    private int getTextColor()
    {
        if( _active ) {
            return _activeColor;
        } else if( _focus ) {
            return _focusColor;
        } else {
            return _normalColor;
        }
    }
            
    protected void paintBackground( Graphics g )
    {
        Border currentBorder = getCurrentBorder();
        Background currentBackground = getCurrentBackground();
        
        currentBorder.paint( g, _borderRect );
        currentBackground.draw( g, _backgroundRect );
    }
    
    private Border getCurrentBorder()
    {
        if( _active ) {
            return _activeBorder;
        } else if( _focus ) {
            return _focusBorder;
        } else {
            return _normalBorder;
        }
    }
    
    private Background getCurrentBackground()
    {
        if( _active ) {
            return _activeBackground;
        } else if( _focus ) {
            return _focusBackground;
        } else {
            return _normalBackground;
        }
    }
    
    protected void drawFocus( Graphics g, boolean on )
    {
        // The paint() method looks after drawing the focus for us
    }
    
    
    /* Handle Focus and Press changes */
    
    protected void onFocus( int direction )
    {
        _focus = true;
        invalidate();
        super.onFocus( direction );
    }
        
    protected void onUnfocus()
    {
        super.onUnfocus();
        if( _active || _focus ) {
            _focus = false;
            _active = false;
            invalidate();
        }
    }
    
    
    /* All the ways the Button can be clicked */
    
    
    // Release Active State
    protected boolean keyUp(int keycode, int time)
    {
        if( Keypad.map( Keypad.key( keycode ), Keypad.status( keycode ) ) == Characters.ENTER ) {
            _active = false;
            invalidate();
            return true;
        }

        return false;
    }
    
    // Enable Active State
    protected boolean keyDown(int keycode, int time)
    {
        if( Keypad.map( Keypad.key( keycode ), Keypad.status( keycode ) ) == Characters.ENTER ) {
            _active = true;
            invalidate();
        }

        return super.keyDown( keycode, time );
    }

    // Invoke on ENTER
    protected boolean keyChar( char character, int status, int time ) 
    {
        if( character == Characters.ENTER ) {
            clickButton();
            return true;
        }
        return super.keyChar( character, status, time );
    }
    
    protected boolean navigationClick(int status, int time) {
        _active = true;
        invalidate();
        return super.navigationClick( status, time );
    }
    
    protected boolean navigationUnclick(int status, int time) {
        _active = false;
        invalidate();
        clickButton(); 
        return true;
    }
    
    protected boolean invokeAction( int action ) 
    {
        switch( action ) {
            case ACTION_INVOKE: {
                clickButton(); 
                return true;
            }
        }
        return super.invokeAction( action );
    }    

    public void setDirty( boolean dirty ) { }
     
    public void setMuddy( boolean muddy ) { }
         
         
    /* A public way to click this button  */
    
    public void clickButton(){
        fieldChangeNotify( 0 );
    }
    

//#ifndef VER_4.6.1 | VER_4.6.0 | VER_4.5.0 | VER_4.2.1 | VER_4.2.0

    protected boolean touchEvent(TouchEvent message)
    {
        boolean isOutOfBounds = touchEventOutOfBounds( message );
        switch(message.getEvent()) {
            case TouchEvent.DOWN:
                if( !isOutOfBounds ) {
                    if( !_active ) {
                        _active = true;
                        invalidate();
                    }
                    return true;
                }
                return false;
            case TouchEvent.UNCLICK:
                if( _active ) {
                    _active = false;
                    invalidate();
                }
                
                if( !isOutOfBounds ) {
                    clickButton();
                    return true;
                }
            case TouchEvent.UP:
                if( _active ) {
                    _active = false;
                    invalidate();
                }
            
            default : 
                return false;
        }
    }
    
    private boolean touchEventOutOfBounds( TouchEvent message )
    {
        int x = message.getX( 1 );
        int y = message.getY( 1 );
        return ( x < 0 || y < 0 || x > getWidth() || y > getHeight() );
    }
//#endif

    
}

