package com.example.atrapalos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private TextView puntaje;
    private TextView botonStart;
    private ImageView box;
    private ImageView caraFeliz;
    private ImageView caraSorprendida;
    private ImageView caraMala;
    private ImageView caraEnferma;
    private ImageView caraMaligna;



    private int puntajeI = 0;
    //tamano

    private int frameHeight;
    private int boxSize;
    private  int screenWidth;
    private  int screenHeight;

   // posicion
    private int boxY;
    private int caraFelizX;
    private int caraFelizY;
    private int caraSorprendidaX;
    private int caraSorprendidaY;
    private int caraMalaX;
    private int caraMalaY;
    private int caraEnfermaX;
    private int caraEnfermaY;
    private int caraMalignaX;
    private int caraMalignaY;


    //iniciador de la clase
    private Handler handler = new Handler();
    private Timer timer = new Timer();
    private Sonido sonido;

    // estado
    private boolean res = false;
    private boolean comenzar = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sonido = new Sonido(this);
        puntaje = (TextView) findViewById(R.id.puntaje);
        botonStart= (TextView) findViewById(R.id.botonStart);
        box = (ImageView)findViewById(R.id.box);
        caraFeliz= (ImageView)findViewById(R.id.caraFeliz);
        caraSorprendida=(ImageView)findViewById(R.id.caraSorprendida);
        caraMala= (ImageView)findViewById(R.id.caraMala);
        caraEnferma= (ImageView)findViewById(R.id.caraEnferma);
        caraMaligna= (ImageView)findViewById(R.id.caraMaligna);

        // obtener el tamaño de la pantalla
        WindowManager wm = getWindowManager();
        Display disp = wm.getDefaultDisplay();
        Point size = new Point();
        disp.getSize(size);
        screenWidth = size.x;
        screenHeight= size.y;

        caraFeliz.setX(-80);
        caraFeliz.setY(-80);

        caraSorprendida.setX(-80);
        caraSorprendida.setY(-80);

        caraMala.setX(-80);
        caraMala.setY(-80);

        caraEnferma.setX(-80);
        caraEnferma.setY(-80);

        caraMaligna.setX(-80);
        caraMaligna.setY(-80);

        puntaje.setText(" puntaje : 0" );




    }
    public void ChekearImpacto() {


        //caraFeliz
        int caraFelizCenterX = caraFelizX + caraFeliz.getWidth() / 2;
        int caraFelizCenterY = caraFelizY + caraFeliz.getHeight() / 2;

        if (0 <= caraFelizCenterX && caraFelizCenterX <= boxSize && boxY <= caraFelizCenterY && caraFelizCenterY <= boxY + boxSize) {
            // impacta y desaparece
            puntajeI += 10;
            caraFelizX = -10;
            sonido.playHitSound();
        }
        //caraSorprendida
        int caraSorprendidaCenterX = caraSorprendidaX + caraSorprendida.getWidth() / 2;
        int caraSorprendidaCenterY = caraSorprendidaY + caraSorprendida.getHeight() / 2;

        if (0 <= caraSorprendidaCenterX && caraSorprendidaCenterX <= boxSize && boxY <= caraSorprendidaCenterY && caraSorprendidaCenterY <= boxY + boxSize) {

            puntajeI += 30;
            caraSorprendidaX = -10;
            sonido.playHitSound();
        }
        //caraEnferma
        int caraEnfermaCenterX = caraEnfermaX + caraEnferma.getWidth() / 2;
        int caraEnfermaCenterY = caraEnfermaY + caraEnferma.getHeight() / 2;

        if (0 <= caraEnfermaCenterX && caraEnfermaCenterX <= boxSize && boxY <= caraEnfermaCenterY && caraEnfermaCenterY <= boxY + boxSize) {

            puntajeI += -20;
            caraEnfermaX = -10;
            sonido.playHitSound();
        }
        //caraMala
        int caraMalaCenterX = caraMalaX + caraMala.getWidth() / 2;
        int caraMalaCenterY = caraMalaY + caraMala.getHeight() / 2;

        if (0 <= caraMalaCenterX && caraMalaCenterX <= boxSize && boxY <= caraMalaCenterY && caraMalaCenterY <= boxY + boxSize) {


            caraSorprendidaX = -10;
            sonido.playOverSound();
            //detener el timer
            timer.cancel();
            timer = null;
            // mostrar los resultados
            Intent intent = new Intent(getApplicationContext(), Resultado.class);
            intent.putExtra("PUNTAJE", puntajeI);
            startActivity(intent);


        }

        //caraMaligna
        int caraMalignaCenterX = caraMalignaX + caraMaligna.getWidth() / 2;
        int caraMalignaCenterY = caraMalignaY + caraMaligna.getHeight() / 2;

        if (0 <= caraMalignaCenterX && caraMalignaCenterX <= boxSize && boxY <= caraMalignaCenterY && caraMalignaCenterY <= boxY + boxSize) {



            sonido.playOverSound();
            //detener el timer
            timer.cancel();
            timer = null;
            // mostrar los resultados
            Intent intent = new Intent(getApplicationContext(), Resultado.class);
            intent.putExtra("PUNTAJE", puntajeI);
            startActivity(intent);


        }
    }



    public void changePos(){
        ChekearImpacto();
        // caraFeliz
        caraFelizX -= 12;
        if(caraFelizX<0){
            caraFelizX = screenWidth +20;
            caraFelizY = (int)Math.floor(Math.random() * (frameHeight - caraFeliz.getHeight()));

        }
        caraFeliz.setX(caraFelizX);
        caraFeliz.setY((caraFelizY));

        //caraSorprendida
        caraSorprendidaX -= 20;
        if(caraSorprendidaX<0){
            caraSorprendidaX = screenWidth + 5000;
            caraSorprendidaY = (int)Math.floor(Math.random() * (frameHeight - caraSorprendida.getHeight()));

        }
        caraSorprendida.setX(caraSorprendidaX);
        caraSorprendida.setY((caraSorprendidaY));

        //caraEnferma
        caraEnfermaX -= 18;
        if(caraEnfermaX<0){
            caraEnfermaX = screenWidth + 5000;
            caraEnfermaY = (int)Math.floor(Math.random() * (frameHeight - caraEnferma.getHeight()));

        }
        caraEnferma.setX(caraEnfermaX);
        caraEnferma.setY((caraEnfermaY));

       //caraMala
        caraMalaX -= 16;
        if(caraMalaX< 0){
            caraMalaX = screenWidth +10;
            caraMalaY = (int)Math.floor(Math.random() * (frameHeight - caraMala.getHeight()));
        }
        caraMala.setX(caraMalaX);
        caraMala.setY((caraMalaY));

     //
            //caraMaligna
            caraMalignaX -= 17;
        if(puntajeI >= 100) {
            if (caraMalignaX < 0) {
                caraMalignaX = screenWidth + 10;
                caraMalignaY = (int) Math.floor(Math.random() * (frameHeight - caraMaligna.getHeight()));
            }
            caraMaligna.setX(caraMalignaX);
            caraMaligna.setY((caraMalignaY));
        }

       if(res== true){
           //cuando presionamos
           boxY -= 20;
       }else{
           //cuando soltamos
           boxY += 20;

       }
       // chekear la posicion del box
        if(boxY < 0) {
            boxY= 0;
        }

        if(boxY> frameHeight - boxSize){
            boxY = frameHeight - boxSize;
        }


       box.setY(boxY);
        puntaje.setText(" puntaje : "+puntajeI);

    }
    public boolean onTouchEvent(MotionEvent me){

        if(comenzar == false){
            comenzar= true;
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);

            frameHeight = frameLayout.getHeight();

            boxY = (int)box.getY();

            boxSize = box.getHeight();

           botonStart.setVisibility(View.GONE);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changePos();
                        }
                    });
                }
            },0,20);

        }else{
            if(me.getAction() == MotionEvent.ACTION_DOWN){
                res = true;
            }else{
                if(me.getAction() == MotionEvent.ACTION_UP){
                    res = false;
                }
            }
        }


        return true;
    }
}
