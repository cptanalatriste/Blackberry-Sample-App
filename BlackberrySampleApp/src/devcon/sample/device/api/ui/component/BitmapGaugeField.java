/*
 * BitmapGaugeField.java
 *
 * Research In Motion Limited proprietary and confidential
 * Copyright Research In Motion Limited, 2009-2009
 */

package devcon.sample.device.api.ui.component;

import net.rim.device.api.system.*;
import net.rim.device.api.ui.*;
import net.rim.device.api.util.*;

public class BitmapGaugeField extends Field
{
    private Bitmap _imageBackground;
    private Bitmap _imageProgress;
    
    private Bitmap _imageCenterBackground;
    private Bitmap _imageCenterProgress;

    private int _leadingBackgroundClip;
    private int _trailingBackgroundClip;
    private int _leadingProgressClip;
    private int _trailingProgressClip;
                
    private int _rop;
    
    private int _maxValue;
    private int _currentValue;
    
    private boolean _selected;
    private boolean _horizontal;
    
    private int _maxImageWidth;
    private int _maxImageHeight;
    
    private int _width;
    private int _height;
    
    /**
     * Bitmap status - The image that is used as the state indicator, it grows to fill the field
     * Bitmap gaugeBackground - the background image is stretched to fit
     * int numStates - the number of increments. NOTE: this DOES NOT include zero position (will be one larger than you say)
     * int initialState - NOTE: this INCLUDES ZERO POSITION.
     *
     * pre: NumStates must be greater or equal to initialState
     * pre: NumStates must be greater than 1
     */
    public BitmapGaugeField( Bitmap background, Bitmap progress,
                             int maxValue, int initialValue, 
                             int leadingBackgroundClip, int trailingBackgroundClip, 
                             int leadingProgressClip,   int trailingProgressClip, 
                             boolean horizontal )
    {
        super( NON_FOCUSABLE );
        
        if( maxValue < 1 || initialValue > maxValue ) {
            throw new IllegalArgumentException( "invalid value parameters" );
        }
        
        if( leadingBackgroundClip < 0  || leadingBackgroundClip >= background.getWidth() 
         || trailingBackgroundClip < 0 || trailingBackgroundClip >= background.getWidth() 
         || leadingProgressClip < 0    || leadingProgressClip >= progress.getWidth() 
         || trailingProgressClip < 0   || trailingProgressClip >= progress.getWidth() ) { 
            throw new IllegalArgumentException( "invalid clip parameter" );
        }
        
        _imageBackground = background;
        _imageProgress   = progress;
        
        _leadingBackgroundClip  = leadingBackgroundClip;
        _trailingBackgroundClip = trailingBackgroundClip;
        _leadingProgressClip    = leadingProgressClip;
        _trailingProgressClip   = trailingProgressClip;
        
        _horizontal = horizontal;   

        if( background.hasAlpha() || progress.hasAlpha() ) {
            _rop = Graphics.ROP_SRC_ALPHA;
        } else {
            _rop = Graphics.ROP_SRC_COPY;
        }
        
        if( _horizontal ) {
            _imageCenterBackground = new Bitmap( _imageBackground.getWidth() - _leadingBackgroundClip - _trailingBackgroundClip , _imageBackground.getHeight() );  
        } else {
            _imageCenterBackground = new Bitmap( _imageBackground.getWidth(), _imageBackground.getHeight() - _leadingBackgroundClip - _trailingBackgroundClip );  
        }
        initTile( _imageBackground, leadingBackgroundClip, trailingBackgroundClip, _imageCenterBackground );
        
        if( _horizontal ) {
            _imageCenterProgress = new Bitmap( _imageProgress.getWidth() - _leadingProgressClip - _trailingProgressClip , _imageProgress.getHeight() );  
        } else {
            _imageCenterProgress = new Bitmap( _imageProgress.getWidth(), _imageProgress.getHeight() - _leadingProgressClip - _trailingProgressClip );  
        }
        initTile( _imageProgress, leadingProgressClip, trailingProgressClip, _imageCenterProgress );
        
        _maxImageWidth  = Math.max( _imageBackground.getWidth(),  _imageProgress.getWidth() );
        _maxImageHeight = Math.max( _imageBackground.getHeight(), _imageProgress.getHeight() );
        
        _maxValue = maxValue;  
        setValue( initialValue );
    }
    
    private void initTile( Bitmap baseImage, int leadingMargin, int trailingMargin, Bitmap targetImage )
    {
        int height = baseImage.getHeight();
        int width  = baseImage.getWidth();
        
        int marginSize = leadingMargin + trailingMargin;
                                                  
        if( _horizontal ) {
            copy( baseImage, leadingMargin, 0, width - marginSize, height, targetImage );
        } else {
            // This is a bit tricky -- the "leading" margin for a vertical gauge is actually at the bottom
            copy( baseImage, 0, trailingMargin, width, height - marginSize, targetImage );
        }
    }

    private void copy(Bitmap src, int x, int y, int width, int height, Bitmap dest) 
    {
        int[] argbData = new int[width * height];
        src.getARGB(argbData, 0, width, x, y, width, height);
        for(int tx = 0; tx < dest.getWidth(); tx += width) {
            for(int ty = 0; ty < dest.getHeight(); ty += height) {
                dest.setARGB(argbData, 0, width, tx, ty, width, height);
            }
        }
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
        return _horizontal ? Integer.MAX_VALUE : _maxImageWidth;
    }

    public int getPreferredHeight() 
    {
        return _horizontal ? _maxImageHeight : Integer.MAX_VALUE;
    }

    protected void layout( int width, int height ) 
    {
        _width  = Math.min( getPreferredWidth(),  width  );
        _height = Math.min( getPreferredHeight(), height );

        setExtent( _width, _height );
    }
    
    public void paint( Graphics g )
    {       
        if( _horizontal ) { 
            int progressWidth = _width * _currentValue / _maxValue;
            drawHorizontal( g, _imageBackground, _imageCenterBackground, _leadingBackgroundClip, _trailingBackgroundClip, 0, _width );
            drawHorizontal( g, _imageProgress,   _imageCenterProgress,   _leadingProgressClip,   _trailingProgressClip,   0, progressWidth );
        } else {
            int progressHeight = _height * _currentValue / _maxValue;
            drawVertical( g, _imageBackground, _imageCenterBackground, _leadingBackgroundClip, _trailingBackgroundClip, 0,                        _height );
            drawVertical( g, _imageProgress,   _imageCenterProgress,   _leadingProgressClip,   _trailingProgressClip,   _height - progressHeight, progressHeight );
        }
    }
    
    private void drawHorizontal( Graphics g, Bitmap fullImage, Bitmap centerTile, int clipLeft, int clipRight, int x, int width )
    {
        int imageWidth  = fullImage.getWidth();
        int imageHeight = fullImage.getHeight();
        int y = ( _height - imageHeight ) / 2;
        
        width = Math.max( width, clipLeft + clipRight );
        
        // Left
        g.drawBitmap( x, y, clipLeft, imageHeight, fullImage, 0, 0 );

        // Middle
        g.tileRop( _rop, x + clipLeft, y, Math.max( 0, width - clipLeft - clipRight ), imageHeight, centerTile, 0, 0 );
        
        // Right
        g.drawBitmap( x + width - clipRight, y, clipRight, imageHeight, fullImage, imageWidth - clipRight, 0 );
    }
    
    private void drawVertical( Graphics g, Bitmap fullImage, Bitmap centerTile, int clipBottom, int clipTop, int y, int height )
    {
        int imageWidth  = fullImage.getWidth();
        int imageHeight = fullImage.getHeight();
        int x = ( _width - imageWidth ) / 2;
        
        height = Math.max( height, clipBottom + clipTop );
        
        // Bottom
        g.drawBitmap( x, y + height - clipBottom, imageWidth, clipBottom, fullImage, 0, imageHeight - clipBottom );

        // Middle
        g.tileRop( _rop, x, y + clipTop, imageWidth, Math.max( 0, height - clipBottom - clipTop ), centerTile, 0, 0 );
        
        // Top
        g.drawBitmap( x, y, imageWidth, clipTop, fullImage, 0, 0 );
    }        

    public void decrementValue()
    {
        if( _currentValue > 0 ) {
            _currentValue--;
            fieldChangeNotify( FieldChangeListener.PROGRAMMATIC );
            invalidate();
        }
    }

    public void incrementValue()
    {
        if( _currentValue < _maxValue ) {
            _currentValue++;
            fieldChangeNotify( FieldChangeListener.PROGRAMMATIC );
            invalidate();
        }
    }

}
