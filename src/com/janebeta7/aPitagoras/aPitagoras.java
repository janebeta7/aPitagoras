package com.janebeta7.aPitagoras;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import processing.core.PApplet;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class aPitagoras extends PApplet  implements ColorPickerDialog.OnColorChangedListener{
	private int mInitialColor = 0xFFFFFFFF;
	protected static final int EDIT_PREFS = 1;
	/*
	 * --------------------------------------------------------------------------
	 * ------ Alba G.Corral (www.albagcorral.com)
	 * 
	 * --------------------------------------------------------------------------
	 * ------
	 */

	/* --- android variables --- */
	NotificationManager gNotificationManager;
	Notification gNotification;
	long[] gVibrate = { 0, 50 };

	/* --- processing variables --- */

	Fade fade;
	
	int radio = 100;
	boolean hazFade = false;
	int Ax;
	int Ay;
	int espacio = 1;
	int NUM = 4; // NUMERO DE CIRCULOS
	Circulo[] w = new Circulo[NUM]; // array de cienpies
	int colorFade, colorFadeInv;
	Circulo circulo;
	int z = 0;
	int contCirculos = 0;
	boolean iniciamos = false;
	boolean dibuja = true;
	public void setup() {
		smooth();
		Ax = displayWidth;
		Ay = displayHeight;
		fade = new Fade();
		background(0);
	}

	public void inicia() {
		if (contCirculos == (NUM))
			contCirculos = 0;
		w[contCirculos] = new Circulo(PApplet.parseInt(random(radio)),
				PApplet.parseInt(mouseX), PApplet.parseInt(mouseY)); // nuevo
																		// circulo
		contCirculos++; // note the use of modulo
	}

	public void draw() {
		if (dibuja){
			fade.render();
			for (int z = 0; z < contCirculos; z++) {
				if (w[z].dibujado == false)
					w[z].draw(mInitialColor);
			}
		}
		
	}
	
	// clase que controla el fade in y el fade out
	class Fade {
		float fadeTime;
		boolean fadeIn = false, fadeOut = false, on = false;

		Fade() {
			println("init Fade");
		}

		public void render() {

			if (fadeIn) {
				if (fadeTime < 1)
					fadeTime = fadeTime + 1.0f / 100.0f;
				else { // done fading in
					fadeTime = 1;
					on = true;
					fadeIn = false;
				}
			}
			if (fadeOut) {
				if (fadeTime > 0)
					fadeTime = fadeTime - 1.0f / 100.0f;
				else { // done fading in
					fadeTime = 0;
					on = false;
					fadeOut = false;
				}
			}
			fade();
		}

		public float getFade() {
			return fadeTime;
		}

		public void setFade() {
			if (on == false)
				fadeIn = true;
			else
				fadeOut = true;
		}

		private void fade() {
			fill(0, fadeTime * 10);
			rect(0, 0, width, height);
		}
	}

	class Circulo {
		  //


		  float a = 0.0f  ;
		  float inc = TWO_PI/90;
		  int rRadio ;
		  int rradio = 400;
		  boolean dibujado= false; 
		  int radio,Ax,Ay;
		  //constructor de la clase
		  Circulo (int _radio,int _Ax,int _Ay) {
		    this.radio = _radio;
		    this.Ax = _Ax;
		    this.Ay =_Ay;
		    this.rRadio = PApplet.parseInt(random(radio,rradio));
		  }
		  public void draw(int colorr) {
		    //variante while dibuja de golpe
		    while (a <  TWO_PI){
		    dibujaCirculo();
		     //  rotate(0.5);
		    dibujaElipse(colorr);
		      a = a + inc;
		    }
		    a =0;
		    radio++;

		    if (radio > rradio) {
		      dibujado = true;
		    }
		  }

		  public void dibujaCirculo(){

		    // strokeWeight(1); 
		    //    ahora recorremos la longitud de r para ir creando puntos que recorran todo el areadel circulo
		    for (int i = 0; i < radio; i = i+100) {

		      float xx = cos(a)*i+Ax; 
		      float yy = sin(a)*i+Ay; 
		      float xx2 = cos(a)*(i+10)+Ax; 
		      float yy2 = sin(a)*(i+10)+Ay; 
		      fill(0,30);
		      noStroke();
		      ellipse(xx,yy,5,5);
		     
		       }

		  }
		  public void dibujaElipse(int colorr){
		   noStroke();
		    int tam = 2;
		    fill(colorr);
		    ellipse(cos(a)*radio+Ax, sin(a)*radio+Ay,tam,tam);
		  }
		}

	/*public boolean surfaceTouchEvent(MotionEvent event) {
		return super.surfaceTouchEvent(event);
	}*/

	public void mousePressed() {
			inicia();
	}

	// -----------------------------------------------------------------------------------------
	// Override the parent (super) Activity class:
	// States onCreate(), onStart(), and onStop() aren't called by the sketch.
	// Processing is entered
	// at the 'onResume()' state, and exits at the 'onPause()' state, so just
	// override them as needed:

	public void onResume() {
		super.onResume();
		println("RESUMED! (Sketch Entered...)");
	}
	public void keyPressed() {
		  if (key == CODED) {
		 
		    if (keyCode == MENU) {
		      // user hit the menu key, take action
		    	//dibuja = false;
		      
		    }
		  }
		}
	/*-----------------------------------------------------------------------------*/
	/*------------------------ ADD IN ECLIPSE--------------------------------------*/
	/*-----------------------------------------------------------------------------*/

	/* pick a color */
	private void showColor() {
	}

	/*------------------------ getScreen-----------------------------------*/
	private void getScreen() {
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			/* como podemos leer la tarjeta grabamos */
			String folder_path = getResources().getString(R.string.folder_path);
			String name_path = getResources().getString(R.string.name_path);
			String ext_path = getResources().getString(R.string.ext_path);
			File extStore = Environment.getExternalStorageDirectory();

			java.util.Date date = Calendar.getInstance().getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
			String today = sdf.format(date);

			String SD_PATH = extStore.getAbsolutePath() + folder_path
					+ name_path + today + "." + ext_path;
			save(SD_PATH);
			// reescaneamos la sdcard para que aparezca en la galeria
			sendStickyBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://"+ Environment.getExternalStorageDirectory())));
			
			println("------salvamos imagen------" + SD_PATH);
			Toast.makeText(
					getApplicationContext(),
					getResources().getString(R.string.msg_saveImageOk)
							+ SD_PATH, Toast.LENGTH_LONG).show();
			// TODO para que sea visible en galery

			/* */

		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			/* esta solo modo lectura */
			// We can only read the media
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.msg_saveImageFail),
					Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(getApplicationContext(),
					getResources().getString(R.string.msg_saveImageFail),
					Toast.LENGTH_LONG).show();
		}
		dibuja = true;

	}

	/*------------------------ CREAMOS ACCIONES DE BOTON MENU-----------------------------------*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*otra manera de hacerlo sin usar el res ni xml > probar directamente en Processing
		 * menu.add(0, SRCATOP_MENU_ID, 0, "SrcATop").setShortcut('5', 'z');
		 * menu.add(0, COLOR_MENU_ID, 0, "Color").setShortcut('3', 'c');
		 private static final int COLOR_MENU_ID = Menu.FIRST;
		 */
		
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.app_menu, menu);
		return true;
	}

	/**
	 * Opens the file manager to pick a directory.
	 */

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		/*
		 * case R.id.settings: Intent intent2 = new Intent(this,
		 * Preferences.class); startActivityForResult(intent2, EDIT_PREFS);
		 * return true;
		 */
		//COLOR_MENU_ID
		case R.id.color:
			//dibujar = false;
			// new ColorPickerDialog(this, this, mInitialColor).show();
			dibuja = false;
			new ColorPickerDialog(this, this, mInitialColor).show();
			//Toast.makeText(getApplicationContext(),"Not implemented yet! Sorry :(",Toast.LENGTH_LONG).show();
			return true;
		case R.id.fade:
			
			fade.setFade();
			
			return true;
		case R.id.save:
			
			getScreen();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
		
	}

	public int sketchWidth() {
		return displayWidth;
	}

	public int sketchHeight() {
		return displayHeight;
	}

	public class Preferences extends PreferenceActivity {

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.preferences);
		}

	}

	@Override
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		println("colorChanged:"+color);
		mInitialColor = color;
		dibuja = true;
	}
    
}
