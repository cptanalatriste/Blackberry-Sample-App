//#preprocess
/*
 * RatingField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2009-2009
 */


package devcon.sample.device.api.ui.component;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.util.*;

public class RatingField extends Field
{
    private Bitmap _ratingGlyph;
    private Bitmap _baseGlyph;
    private Bitmap _ratingGlyphFocus;
    private Bitmap _baseGlyphFocus;

    private int _maxValue;
    private int _currentValue;
    
    private boolean _focused;
    private boolean _pressed;
    
    private int _width;
    private int _height;
    
    private int _maxGlyphWidth;
    private int _maxGlyphHeight;
    
    
    public RatingField( Bitmap ratingGlyph,      Bitmap baseGlyph,
                        Bitmap ratingGlyphFocus, Bitmap baseGlyphFocus,
                        int maxValue, int initialValue, long style )
    {
        super( Field.FOCUSABLE | Field.EDITABLE | style );
        
        if( maxValue < 1 || initialValue > maxValue ) {
            throw new IllegalArgumentException( "invalid value parameters" );
        }
        
        if( ratingGlyph == null || baseGlyph == null || ratingGlyphFocus == null || baseGlyphFocus == null ) {
            throw new IllegalArgumentException( "all glyphs are required" );
        } 
        
        _ratingGlyph      = ratingGlyph;
        _baseGlyph        = baseGlyph;
        _ratingGlyphFocus = ratingGlyphFocus;
        _baseGlyphFocus   = baseGlyphFocus;
        
        _maxGlyphWidth  = Math.max( Math.max( Math.max( ratingGlyph.getWidth(),  baseGlyph.getWidth() ),  ratingGlyphFocus.getWidth() ),  baseGlyphFocus.getWidth() );
        _maxGlyphHeight = Math.max( Math.max( Math.max( ratingGlyph.getHeight(), baseGlyph.getHeight() ), ratingGlyphFocus.getHeight() ), baseGlyphFocus.getHeight() );

        _maxValue = maxValue;  
        setValue( initialValue );
    }
    
    public void setValue( int newValue )
    {
        if( newValue < 0 || newValue > _maxValue ){
            throw new IllegalArgumentException();
        }
        _currentValue =  newValue;
        fieldChangeNotify( FieldChangeListener.PROGRAMMATIC );
        invalidate();
    }

    public int getValue()
    {
        return _currentValue;
    }

    public int getMaxValue()
    {
        return _maxValue;
    }
    
    public int getPreferredWidth() 
    {
        return _maxGlyphWidth * _maxValue;
    }

    public int getPreferredHeight() 
    {
        return _maxGlyphHeight;
    }

    protected void layout( int width, int height ) 
    {
        _width  = Math.min( getPreferredWidth(),  width  );
        _height = Math.min( getPreferredHeight(), height );

        setExtent( _width, _height );
    }
    
    protected void paint( Graphics g )
    {       
        Bitmap ratingGlyphToDraw = _focused ? _ratingGlyphFocus : _ratingGlyph;
        Bitmap baseGlyphToDraw   = _focused ? _baseGlyphFocus   : _baseGlyph;
        
        int i = 0;
        int x = 0;
        
        int ratingY = ( _height - ratingGlyphToDraw.getHeight() ) / 2;
        for( ; i < _currentValue; i++ ) {
            g.drawBitmap( x, ratingY, ratingGlyphToDraw.getWidth(), ratingGlyphToDraw.getHeight(), ratingGlyphToDraw, 0, 0 );
            x += _maxGlyphWidth;
        }
        
        int baseY = ( _height - baseGlyphToDraw.getHeight() ) / 2;
        for( ; i < _maxValue; i++ ) {
            g.drawBitmap( x, baseY, baseGlyphToDraw.getWidth(), baseGlyphToDraw.getHeight(), baseGlyphToDraw, 0, 0 );
            x += _maxGlyphWidth;
        }
    }
    
    protected void drawFocus( Graphics g, boolean on )
    {
        // The paint() method looks after drawing the focus for us
    }
    
    protected void onFocus( int direction )
    {
        _focused = true;
        invalidate();
        super.onFocus( direction );
    }
    
    protected void onUnfocus()
    {
        _focused = false;
        invalidate();
        super.onUnfocus();
    }
        
    protected boolean navigationMovement(int dx, int dy, int status, int time) 
    {
        if( _pressed ) {
            if( dx > 0 || dy > 0 ) {
                nextValue();
            } else {
                previousValue();
            }
            fieldChangeNotify( 0 );
            return true;
        }
        return super.navigationMovement( dx, dy, status, time);
    }

    private void nextValue()
    {
        if( _currentValue < _maxValue ) {
            _currentValue++;
            invalidate();
        }
    }

    private void previousValue()
    {
        if( _currentValue > 0 ) {
            _currentValue--;
            invalidate();
        }
    }

    protected boolean invokeAction(int action)
    {
        switch( action ) {
            case ACTION_INVOKE: {
                togglePressed();
                return true;
            }
        }
        return false;
    }

    protected boolean keyChar( char key, int status, int time )
    {
        if( key == Characters.SPACE || key == Characters.ENTER ) {
            togglePressed();
            return true;
        }
        return false;
    }

    protected boolean trackwheelClick( int status, int time )
    {
        togglePressed();
        return true;
    }
    
    private void togglePressed() 
    {
        _pressed = !_pressed;
        invalidate();
    }
    
//#ifndef VER_4.6.1 | VER_4.6.0 | VER_4.5.0 | VER_4.2.1 | VER_4.2.0

    protected boolean touchEvent( TouchEvent message )
    {
        int event = message.getEvent();
        switch( event ) {
            
            case TouchEvent.CLICK:
            case TouchEvent.DOWN:
                // If we currently have the focus, we still get told about a click in a different part of the screen
                if( touchEventOutOfBounds( message ) ) {
                    return false;
                }
                // fall through
                
            case TouchEvent.MOVE:
                _pressed = true;
                setValueByTouchPosition( message.getX( 1 ) );
                fieldChangeNotify( 0 );
                return true;
                
            case TouchEvent.UNCLICK:
            case TouchEvent.UP:
                _pressed = false;
                invalidate();
                return true;
                
            default:
                return false;
        }
    }
    
    private boolean touchEventOutOfBounds( TouchEvent message )
    {
        int x = message.getX( 1 );
        int y = message.getY( 1 );
        return ( x < 0 || y < 0 || x > getWidth() || y > getHeight() );
    }
        
    private void setValueByTouchPosition( int x )
    {
        int newValue = x / _maxGlyphWidth;
        if( x % _maxGlyphWidth > _maxGlyphWidth / 2 ) {
            newValue++;
        }
        
        _currentValue = MathUtilities.clamp( 0, newValue, _maxValue );
        invalidate();
    }

//#endif
}
