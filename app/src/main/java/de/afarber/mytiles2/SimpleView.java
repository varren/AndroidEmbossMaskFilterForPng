package de.afarber.mytiles2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Peter (Varren) on 13.06.2015.
 */
public class SimpleView extends View {

    public SimpleView(Context context) {
        super(context);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SimpleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        drawTest(canvas, null);
        drawTest(canvas, new EmbossMaskFilter(new float[]{1, 1, 1}, 0.1f, 0.2f, 1f));
        drawTest(canvas, new EmbossMaskFilter(new float[]{0f, 1f, 0.5f}, 0.8f, 3f, 3f));
        drawTest(canvas, new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 0.6f, 2f));
    }
    private void drawTest(Canvas canvas, EmbossMaskFilter filter){
        canvas.translate(50, 0);
        drawBig(canvas,filter);
        canvas.translate(50, 0);
        drawSmall(canvas,filter);
        //drawEmbossMaskFilter(d,canvas,filter);
    }

    private void drawSmall(Canvas canvas, EmbossMaskFilter filter) {
        draw(canvas,filter,"small_" + 3);
    }

    private void drawBig(Canvas canvas, EmbossMaskFilter filter) {
        draw(canvas,filter,"big_" + 3);
    }

    private void draw(Canvas canvas, EmbossMaskFilter filter, String drawableName){
        int id = getContext().getResources().getIdentifier(drawableName, "drawable", getContext().getPackageName());
        BitmapDrawable d = (BitmapDrawable) getContext().getResources().getDrawable(id);
        d.draw(canvas);
        canvas.drawBitmap(processingBitmap_Emboss(d.getBitmap(),filter),0,0, paintEmboss);
    }



    private static final EmbossMaskFilter filter = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.5f, 0.6f, 2f);
    private static Bitmap dest;
    private static Canvas canvas;
    private static Paint paintEmboss;
    private static int counter = 0;

    public static Bitmap processingBitmap_Emboss(Bitmap src){
        return processingBitmap_Emboss(src, filter);
    }

    public static Bitmap processingBitmap_Emboss(Bitmap src,  EmbossMaskFilter embossMaskFilter){
        Log.e("processingBitmap_Emboss", String.valueOf(counter++));
        if (dest == null) {
            int width = src.getWidth();
            int height = src.getHeight();
            dest = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            canvas = new Canvas(dest);
            paintEmboss = new Paint();
            paintEmboss.setMaskFilter(embossMaskFilter);
        }

        canvas.drawColor(Color.TRANSPARENT);
        Bitmap alpha = src.extractAlpha();
        canvas.drawBitmap(alpha, 0, 0, paintEmboss);
        alpha.recycle();
        return dest;
    }
}
