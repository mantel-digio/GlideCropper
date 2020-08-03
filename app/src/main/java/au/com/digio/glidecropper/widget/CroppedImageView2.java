package au.com.digio.glidecropper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;

import au.com.digio.glidecropper.R;
import au.com.digio.glidecropper.glide.CroppedImage2;

public class CroppedImageView2 extends AppCompatImageView {

    private Context context;
    private int horizontalOffset = 0;
    private int verticalOffset = 0;
    private int resId = 0;
    private boolean loadRequested = false;

    public CroppedImageView2(Context context) {
        super(context);
    }

    public CroppedImageView2(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        setScaleType(ScaleType.FIT_XY);
        TypedArray ats = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CroppedImageView, 0, 0);
        horizontalOffset = ats.getDimensionPixelOffset(R.styleable.CroppedImageView_horizontalOffset, 0);
        verticalOffset = ats.getDimensionPixelOffset(R.styleable.CroppedImageView_verticalOffset, 0);
        ats.recycle();
    }

    public CroppedImageView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (resId != 0) {
            loadCroppedImage();
        }
    }

    @Override
    public void setImageResource(int resId) {
        this.resId = resId;
        loadRequested = false;
        if (getHeight() != 0 && getWidth() != 0) {
            loadCroppedImage();
        }
    }

    private void loadCroppedImage() {
        if (resId == 0 || loadRequested){
            return;
        }
        loadRequested = true; // Don't trigger multiple loads for the same resource
        CroppedImage2 model = new CroppedImage2(resId, getWidth(), getHeight(), horizontalOffset,
                verticalOffset);
        Glide.with(context)
                .load(model)
                .into(this);
    }

    void clear() {
        // Stop any in-progress image loading
        Glide.with(context).clear(this);
    }
}