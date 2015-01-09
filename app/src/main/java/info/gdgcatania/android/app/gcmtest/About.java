package info.gdgcatania.android.app.gcmtest;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.widget.ImageView;

import info.gdgcatania.android.app.gcmtest.R;

public class About extends Activity {
	
	private ImageView imViewAndroid;
	private ImageView imViewAndroid2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		imViewAndroid = (ImageView)findViewById(R.id.avatar);
		imViewAndroid.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.avatar), 500));
		
		imViewAndroid2 = (ImageView)findViewById(R.id.avatar_gabriele);
		imViewAndroid2.setImageBitmap(roundCornerImage(BitmapFactory.decodeResource(getResources(), R.drawable.avatar2), 500));

	}

	public Bitmap roundCornerImage(Bitmap src, float round){
		// Source image size
		  int width = src.getWidth();
		  int height = src.getHeight();
		  // create result bitmap output
		  Bitmap result = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		  // set canvas for painting
		  Canvas canvas = new Canvas(result);
		  canvas.drawARGB(0, 0, 0, 0);
		 
		  // configure paint
		  final Paint paint = new Paint();
		  paint.setAntiAlias(true);
		  paint.setColor(Color.BLACK);
		 
		  // configure rectangle for embedding
		  final Rect rect = new Rect(0, 0, width, height);
		  final RectF rectF = new RectF(rect);
		 
		  // draw Round rectangle to canvas
		  canvas.drawRoundRect(rectF, round, round, paint);
		 
		  // create Xfer mode
		  paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		  // draw source image to canvas
		  canvas.drawBitmap(src, rect, rect, paint);
		 
		  // return final image
		  return result;
	};

}
