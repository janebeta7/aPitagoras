package com.janebeta7.aPitagoras;

import java.io.File;

import processing.core.PApplet;
import com.janebeta7.aPitagoras.R;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;



public class aPitagoras extends PApplet {

/*
--------------------------------------------------------------------------------
 Alba G.Corral  (www.albagcorral.com)
 
 --------------------------------------------------------------------------------
 
 */

/* --- android variables   --- */
NotificationManager gNotificationManager;
Notification gNotification;
long[] gVibrate = {0,50};


/* ---  processing variables   ---  */

String COLORES = "set" ;// RUTA donde se encuentra la paleta de colores
Fade fade;
//MovieMaker mm;  // Declare MovieMaker object

int radio = 100;
boolean hazFade= false;

int espacio = 1;
int NUM =1; //NUMERO DE CIRCULOS
Circulo[] w = new Circulo[NUM]; //array de cienpies
int colorFade, colorFadeInv;
Circulo circulo ;
int z = 0;
boolean grabamos = false;
public void setup() 
{
  smooth();
 //ponemos el ancho y el alto de laventana
  println("--SETUP----");
  fade = new Fade();
  inicia();
  background(0);
}
public void inicia() {
  for (int i=0;i<NUM;i++) {

    w[i] = new Circulo(PApplet.parseInt(random(radio)), mouseX, mouseY); //nuevo circulo
  }
}
public void draw() 
{ 
 // frameRate(30);
  fade.render();

  if (grabamos) {  
    //mm.addFrame();
  } // Add window's pixels to movie

  for (int z=0;z<NUM;z++) {

    if (w[z].dibujado==false) {

      w[z].draw(255);//llamamos al metodo de la clase Wurm.draw();
    }
  }
}


//clase que controla el fade in y el fade out
class Fade{
  float fadeTime;
  boolean fadeIn=false,fadeOut=false,on=false;  
  Fade(){
    println("init Fade");
  }
  public void render(){


    if(fadeIn) {
      if(fadeTime<1) fadeTime=fadeTime+1.0f/100.0f;
      else { // done fading in
        fadeTime=1;
        on=true;
        fadeIn=false;
      }
    }
    if(fadeOut) {
      if(fadeTime>0) fadeTime=fadeTime-1.0f/100.0f;
      else { // done fading in
        fadeTime=0;
        on=false;
        fadeOut=false;
      }
    }
    fade();
  }
  public float getFade(){
    return fadeTime;
  }
  public void setFade(){
    if(on==false) fadeIn=true;
    else fadeOut=true;
  }
 private void fade(){
    fill(0,fadeTime*10);
    rect(0,0,width,height);
   }
}

class Circulo {
  //


  float a = 0.0f  ;
  float inc = TWO_PI/90;

  boolean dibujado= false; 
  int radio, Ax, Ay;
  //constructor de la clase
  Circulo (int _radio, int _Ax, int _Ay) {
    this.radio = _radio;
    this.Ax = _Ax;
    this.Ay =_Ay;
  }
  public void draw(int colorr) {
    //variante while dibuja de golpe
    while (a <  TWO_PI) {
      dibujaCirculo();
      //  rotate(0.5);
      dibujaElipse(colorr);
      a = a + inc;
    }
    a =0;
    radio++;

    if (radio > 400) {
      dibujado = true;
    }
  }

  public void dibujaCirculo() {

    // strokeWeight(1); 
    //    ahora recorremos la longitud de r para ir creando puntos que recorran todo el areadel circulo
    for (int i = 0; i < radio; i = i+100) {

      float xx = cos(a)*i+Ax; 
      float yy = sin(a)*i+Ay; 
      //float xx2 = cos(a)*(i+int(random(50)))+Ax; 
      //float yy2 = sin(a)*(i+int(random(100)))+Ay; 
      float xx2 = cos(a)*(i+10)+Ax; 
      float yy2 = sin(a)*(i+10)+Ay; 
      stroke(0xffffffff, 20);
      //strokeWeight(1);
      //  ellipse(xx,yy,10,10);
      //line(xx,yy,xx2,yy2);
      noFill();
      float cx1 =  xx+PApplet.parseInt(random(20));
      float cx2 = xx2+PApplet.parseInt(random(20));
      float cy1 = yy+PApplet.parseInt(random(20));
      float cy2 = yy2+PApplet.parseInt(random(20));
      //rect(xx, yy, xx2, yy2);
      //bezier(xx, yy, cx1, cy1, cx2, cy2, xx2, yy2);

      //   noStroke();
    }
  }
  public void dibujaElipse(int colorr) {
    noStroke();
    int tam = 2;
    fill(colorr);
    ellipse(cos(a)*radio+Ax, sin(a)*radio+Ay, tam, tam);
  }
}



public boolean surfaceTouchEvent(MotionEvent event) {
  // If user touches the screen, trigger vibration notification:
  //gNotificationManager.notify(1, gNotification);
	//getScreen();
  return super.surfaceTouchEvent(event);
}
public void mousePressed() {
 // gNotificationManager.notify(1, gNotification);
  inicia();
}

//-----------------------------------------------------------------------------------------
// Override the parent (super) Activity class:
// States onCreate(), onStart(), and onStop() aren't called by the sketch.  Processing is entered
// at the 'onResume()' state, and exits at the 'onPause()' state, so just override them as needed:

public void onResume() {
  super.onResume();
    println("RESUMED! (Sketch Entered...)");
  // Create our Notification Manager:
  gNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
  // Create our Notification that will do the vibration:
  gNotification = new Notification();
  gNotification.defaults |= Notification.DEFAULT_SOUND;
  gNotification.vibrate = gVibrate;
  

}



/*-----------------------------------------------------------------------------*/
/*------------------------ ADD IN ECLIPSE--------------------------------------*/
/*-----------------------------------------------------------------------------*/

/*------------------------ getScreen-----------------------------------*/
private void getScreen(){
	
	String state = Environment.getExternalStorageState();

	if (Environment.MEDIA_MOUNTED.equals(state)) {
	    /* como podemos leer la tarjeta grabamos*/
	    String folder_path = getResources().getString(R.string.folder_path);
		String name_path = getResources().getString(R.string.name_path);
		
		File extStore = Environment.getExternalStorageDirectory();
		String SD_PATH = extStore.getAbsolutePath()+folder_path+name_path;
		save(SD_PATH);
		println("------salvamos imagen------"+SD_PATH);
		Toast.makeText(getApplicationContext(), 
				getResources().getString(R.string.msg_saveImageOk)+SD_PATH, Toast.LENGTH_LONG).show();
		//TODO para que sea visible en galery



	    /* */
	    
	    
	} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		 /* esta solo modo lectura*/
	    // We can only read the media
		Toast.makeText(getApplicationContext(), 
				getResources().getString(R.string.msg_saveImageFail), Toast.LENGTH_LONG).show();
	} else {
		Toast.makeText(getApplicationContext(), 
				getResources().getString(R.string.msg_saveImageFail), Toast.LENGTH_LONG).show();
	}
	
	
	
	
}

/*------------------------ CREAMOS ACCIONES DE BOTON MENU-----------------------------------*/
@Override
public boolean onCreateOptionsMenu(Menu menu) {
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
	case R.id.info:
		Toast.makeText(this, "aPitagoras 1.0 by janebeta7", Toast.LENGTH_SHORT).show();
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
  public int sketchWidth() { return displayWidth; }
  public int sketchHeight() { return displayHeight; }
}


