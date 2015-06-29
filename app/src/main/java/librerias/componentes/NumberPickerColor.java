package librerias.componentes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by Usuario on 28/06/2015.
 */
public class NumberPickerColor extends NumberPicker {
    public NumberPickerColor(Context context) {
        super(context);
        setTextColor(Color.BLACK);
    }

    public NumberPickerColor(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTextColor(Color.BLACK);
    }

    public NumberPickerColor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTextColor(Color.BLACK);
    }

    public NumberPickerColor(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTextColor(Color.BLACK);
    }

    /**METODOS PROPIOS DE LA CLASE*/
    public void setTextColor(int color){
        setNumberPickerTextColor(this, color);
    }

    public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color)
    {
        final int count = numberPicker.getChildCount();
        for(int i = 0; i < count; i++){
            View child = numberPicker.getChildAt(i);
            Log.e("Clase", child.getClass().getCanonicalName());
            if(child instanceof EditText){
                try{
                    Field selectorWheelPaintField = numberPicker.getClass()
                            .getDeclaredField("mSelectorWheelPaint");
                    selectorWheelPaintField.setAccessible(true);
                    ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);
                    ((EditText)child).setTextColor(color);
                    numberPicker.invalidate();
                    return true;
                }
                catch(NoSuchFieldException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalAccessException e){
                    Log.w("setNumberPickerTextColor", e);
                }
                catch(IllegalArgumentException e){
                    Log.w("setNumberPickerTextColor", e);
                }
            }
        }
        return false;
    }
}
