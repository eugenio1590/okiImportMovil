package librerias.componentes;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ViewValidator extends LinearLayout {

    private TextView txtError;

    private ImageView imgEror;

    private float density;

    public ViewValidator(Context context) {
        super(context);
        configurar(context);
    }

    public ViewValidator(Context context, AttributeSet attrs) {
        super(context, attrs);
        configurar(context);
    }

    public ViewValidator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        configurar(context);
    }

    public ViewValidator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        configurar(context);
    }

    private void configurar(Context contex) {
        density = contex.getResources().getDisplayMetrics().density;

        this.setLayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        this.setPadding(0,0,0, (int)(10*density));
        this.setOrientation(HORIZONTAL);
        this.setVisibility(INVISIBLE);
        this.setWeightSum(1f);

        this.txtError = new TextView(contex);
        this.txtError.setTextColor(Color.RED);
        this.txtError.setText("Text Error");
        this.txtError.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, new Float(0.9)));
        this.addView(txtError);

        this.imgEror = new ImageView(contex);
        this.imgEror.setImageResource(android.R.drawable.stat_notify_error);
        this.imgEror.setBackgroundColor(Color.RED);
        this.imgEror.setLayoutParams(new LayoutParams(25,25, new Float(0.1)));
        this.addView(imgEror);
    }

    /**GETTERS Y SETTERS*/
    public TextView getTxtError() {
        return txtError;
    }

    public ImageView getImgEror() {
        return imgEror;
    }

    public int getLWidth(){
        return this.getLayoutParams().width;
    }

    public int getLHeigth(){
        return this.getLayoutParams().height;
    }

    public void setLWidth(int width){
        this.setLayoutParams(new LayoutParams((int) density*width, getLHeigth(), new Float(0.9)));
    }

    public void setLHeight(int height){
        this.setLayoutParams(new LayoutParams(getLWidth(), (int) density*height, new Float(0.9)));
    }

    public void setLayoutParams(int width, int height){
        this.setLayoutParams(new LayoutParams((int) density*width, (int) density*height, new Float(0.9)));
    }

    public void setImageResource(int imageResource){
        getImgEror().setImageResource(imageResource);
    }

    public void setImageBackgroundColor(int imageBackgroundColor){
        getImgEror().setBackgroundColor(imageBackgroundColor);
    }

    public static abstract class TxtValidator implements TextWatcher{

        private EditText editText;
        private ViewValidator viewValidator;

        protected String error = "";

        private Drawable backgrounOrig;

        private int backgroundError;

        public TxtValidator(EditText editText, ViewValidator viewValidator, int backgroundError){
            this.editText = editText;
            this.viewValidator = viewValidator;
            this.backgroundError = backgroundError;
            this.backgrounOrig =this.editText.getBackground();
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //nada
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //nada
        }

        @Override
        public void afterTextChanged(Editable s) {
            String text = editText.getText().toString();
            if(!validateAfterChange(editText, text)) {
                editText.setBackground(editText.getContext().getResources().getDrawable(backgroundError));
                editText.invalidate();
                viewValidator.setVisibility(VISIBLE);
                viewValidator.getTxtError().setText(error);
            }
            else {
                editText.setBackground(backgrounOrig);
                viewValidator.setVisibility(INVISIBLE);
                viewValidator.getTxtError().setText("");
                error = "";
            }
        }

        /**METODOS ABSTRACTOS DE LA CLASE*/
        public abstract boolean validateAfterChange(EditText editText, String text);

        /**METODOS PROPIOS DE LA CLASE*/
        public boolean validate(){
            afterTextChanged(null);
            if(error!=null && error.trim().equalsIgnoreCase(""))
                return true;

            return false;
        }
    }
}
