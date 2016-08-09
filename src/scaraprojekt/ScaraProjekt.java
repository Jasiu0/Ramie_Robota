/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scaraprojekt;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.pickfast.PickCanvas;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.GraphicsConfiguration;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Enumeration;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.Background;
import javax.media.j3d.Behavior;
import javax.media.j3d.BoundingBox;
import javax.media.j3d.BoundingPolytope;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.ColoringAttributes;
import javax.media.j3d.DirectionalLight;
import javax.media.j3d.Material;
import javax.media.j3d.PickInfo;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.media.j3d.WakeupOnCollisionEntry;
import static javax.media.j3d.WakeupOnCollisionEntry.USE_GEOMETRY;
import javax.media.j3d.WakeupOnCollisionExit;
import javax.swing.Timer;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

/**
 *
 * @author Jarek
 */
class Scara extends Applet implements ActionListener, KeyListener ,MouseListener, MouseMotionListener  {
    // dodanie transformacji
    private Button nagrywaj = new Button("  NAGRYWAJ  ");
    private Button wykonaj = new Button("  WYKONAJ  ");
        private Label napis = new Label("                                                                                                                                                                                                                              ");
        
    private Transform3D trans = new Transform3D(); 
       private Transform3D obrot = new Transform3D();
      private TransformGroup  przesuniecie, przesuniecie2, przesuniecie3,przesuniecie4,przesuniecie5,przeslacz1,przeslacz2,przesuniecie6;
     private TransformGroup przeszaokraglenia1, przeszaokraglenia2,mysz,przesuniecierk1,przesuniecierk2,przesunieciecz;
      private float xloc,zloc,yloc=0.0f,przyb=3f,kolizja=0,blokada=0,last=0;
      private float kat,licz1=0,polaczenie=0,spadanie=0,nagrywanie=0,licz2=0,stan=0;
       private float korekta1=0,korekta2=0,gotowe=0;
      Transform3D pos4,pos3,poslacz1,poslacz2,poszaokraglenia1,poszaokraglenia2,posrk1,posrk2,poscz,pos5,pos6;
       SimpleUniverse u;
      BranchGroup scene;
       Canvas3D canvas;
       Box  karton ;
        Material mat2;
          Box czujnik;
         int[][] tablica=new int[2][30];
           int x;
           float s1=0,s2=0,s3=0,s4=0;
           
     public BranchGroup tworzenieGrafuSceny(){
         
        // ----GŁÓWNA CZĘŚĆ GRAFU SCENY----
        BranchGroup grafSceny = new BranchGroup(); 
    przesuniecie=new TransformGroup();
     przesuniecie.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
     
    



     
       
     ///////////////////////////////////// Materialy /////////////////////////////////
      // First setup an appearance for the box
    Appearance app = new Appearance();
    Material mat = new Material();
    mat.setDiffuseColor(new Color3f(.9f, 0.8f,0.8f));
    mat.setSpecularColor(new Color3f(0.3f, 0.3f, 0.3f));
    mat.setShininess(7.0f);
    app.setMaterial(mat);
 
    
     Appearance  app2 = new Appearance();
  mat2 = new Material();
    mat2.setDiffuseColor(new Color3f(.9f, 0.8f,0.8f));
    mat2.setSpecularColor(new Color3f(0.3f, 0.3f, 0.3f));
    mat2.setShininess(7.0f);
   mat2.setCapability(mat2.ALLOW_COMPONENT_WRITE);
    app2.setMaterial(mat2);

       
    
    
     ///////////////////////////////////// Ustawienie Podstawy /////////////////////////////////
    Box podstawa = new Box(0.13f, 0.009f,0.13f,app);
    Vector3f wektor = new Vector3f(0f,-0.18f , 0.0f);
    Transform3D pos1 = new Transform3D();
   pos1.setTranslation(wektor);
    // Create a TransformGroup and make it the parent of the box
   przesuniecie.setTransform(pos1);
  przesuniecie.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 przesuniecie.addChild(podstawa);
    grafSceny.addChild( przesuniecie);
    
    ///////////////////////////////////// Ustawienie Bazy /////////////////////////////////
    
    //pierwszy cylinder 
    Cylinder Baza = new Cylinder(0.06f,0.35f,app);
    Vector3f wektor1 = new Vector3f(0f,0.18f,0.0f);
    Transform3D pos2 = new Transform3D();
    pos2 = new Transform3D();
    pos2.setTranslation(wektor1);
     przesuniecie2= new TransformGroup();
    przesuniecie2.setTransform(pos2);
   przesuniecie2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   przesuniecie2.addChild(Baza);
           przesuniecie.addChild(przesuniecie2);
           
           
    
           ///////////////////////////////////// Lacznik 1 + zaokraglenie 1 /////////////////////////////////
    Cylinder lacznik1 = new Cylinder(0.06f,0.041f,app);
    Vector3f wektorlacz1 = new Vector3f(0f,0.18f,0.0f);
   poslacz1 = new Transform3D();
   poslacz1.setTranslation(wektorlacz1);
    przeslacz1= new TransformGroup();
     przeslacz1.setTransform(poslacz1);
   przeslacz1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    przeslacz1.addChild(lacznik1);  
           przesuniecie2.addChild(przeslacz1);
 
           
           
           
    
     ///////////////////////////////////// Ustawienie ramienia 1 od bazy /////////////////////////////////      
           Box ramie1 = new Box(0.17f, 0.02f,0.0587f,app);
    Vector3f wektor2 = new Vector3f(0.17f,0.00f , 0.0f);
   pos3 = new Transform3D();
    pos3.setTranslation(wektor2);
    
    // Create a TransformGroup and make it the parent of the box
    przesuniecie3= new TransformGroup();
    przesuniecie3.setTransform(pos3); 
  przesuniecie3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 przesuniecie3.addChild(ramie1);
   przeslacz1.addChild(przesuniecie3); 

           ///////////////////////////////////// Zaokraglenie 2 /////////////////////////////////
    Cylinder zaokraglenie1 = new Cylinder(0.06f,0.041f,app);
    Vector3f wektorzaokraglenia1 = new Vector3f(0.17f,0.0f,0.0f);
   poszaokraglenia1 = new Transform3D();
   poszaokraglenia1 = new Transform3D();
   poszaokraglenia1.setTranslation(wektorzaokraglenia1);
    przeszaokraglenia1= new TransformGroup();
     przeszaokraglenia1.setTransform(poszaokraglenia1);
    przeszaokraglenia1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
     przeszaokraglenia1.addChild(zaokraglenie1);
          przesuniecie3.addChild(przeszaokraglenia1); 

 
           ///////////////////////////////////// Lacznik2 +zaokraglenie 3  /////////////////////////////////
    Cylinder lacznik2 = new Cylinder(0.06f,0.042f,app);
    Vector3f wektorlacz2 = new Vector3f(0.f,0.04f,0.0f);
   poslacz2 = new Transform3D();
    poslacz2 = new Transform3D();
   poslacz2.setTranslation(wektorlacz2);
    przeslacz2= new TransformGroup();
     przeslacz2.setTransform(poslacz2);
   przeslacz2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
    przeslacz2.addChild(lacznik2);
           przeszaokraglenia1.addChild(przeslacz2); 
           
           
    
      ///////////////////////////////////// Ustawienie ramienia 2 od bazy /////////////////////////////////      
           Box ramie2 = new Box(0.17f, 0.02f,0.0587f,app);
    Vector3f wektor3 = new Vector3f(0.17f,0.00f , 0.0f);
    pos4 = new Transform3D();
    pos4.setTranslation(wektor3);
    // Create a TransformGroup and make it the parent of the box
    przesuniecie4= new TransformGroup();
    przesuniecie4.setTransform(pos4); 
  przesuniecie4.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 przesuniecie4.addChild(ramie2);
 przeslacz2.addChild(przesuniecie4);
    
 
             ///////////////////////////////////// Zaokraglenie 4 /////////////////////////////////
    Cylinder zaokraglenie2 = new Cylinder(0.06f,0.041f,app);
    Vector3f wektorzaokraglenia2 = new Vector3f(0.17f,0.0f,0.0f);
   poszaokraglenia2 = new Transform3D();
   poszaokraglenia2 = new Transform3D();
   poszaokraglenia2.setTranslation(wektorzaokraglenia2);
    przeszaokraglenia2= new TransformGroup();
     przeszaokraglenia2.setTransform(poszaokraglenia2);
    przeszaokraglenia2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
     przeszaokraglenia2.addChild(zaokraglenie2);
          przesuniecie4.addChild(przeszaokraglenia2); 


          ///////////////////////////////////// Ustawienie Wysiegnik////////////////////////////////  
     
  Cylinder lapacz = new Cylinder(0.01f,0.35f,app);
    Vector3f wektor4 = new Vector3f(0.0f,0.0f,0.0f);
   pos5 = new Transform3D();
   pos5.setTranslation(wektor4);
     przesuniecie5= new TransformGroup();
    przesuniecie5.setTransform(pos5);
   przesuniecie5.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   przesuniecie5.addChild(lapacz);
   przeszaokraglenia2.addChild(przesuniecie5);
           
            ///////////////////////////////////// Ustawienie Wysiegnika Pomoniczego do czujnika////////////////////////////////  
     
  Cylinder lapaczp = new Cylinder(0.005f,0.35f,app);
  Transform3D posp = new Transform3D();
   posp.setTranslation(wektor4);
    TransformGroup przesunieciep= new TransformGroup();
    przesunieciep.setTransform(pos5);
   przesunieciep.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   przesunieciep.addChild(lapaczp);
   przesuniecie5.addChild(przesunieciep);


     ///////////////////////////////////// Ustawienie 1 lacznika z prymitywem////////////////////////////////  
     
   Cylinder rk1 = new Cylinder(0.0f,0.0f,app);
    Vector3f wektorrk1 = new Vector3f(0.0f,0.0f,0.0f);
  posrk1 = new Transform3D();
   posrk1.setTranslation(wektorrk1);
   przesuniecierk1= new TransformGroup();
    przesuniecierk1.setTransform(posrk1);
   przesuniecierk1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   przesuniecierk1.addChild(rk1);
   przesuniecie.addChild(przesuniecierk1);
   
      ///////////////////////////////////// Ustawienie 2 lacznika z prymitywem////////////////////////////////  
     
   Cylinder rk2 = new Cylinder(0.0f,0.0f,app);
    Vector3f wektorrk2 = new Vector3f(0.34f,0.0f,0.0f);
   posrk2 = new Transform3D();
   posrk2.setTranslation(wektorrk2);
   przesuniecierk2= new TransformGroup();
    przesuniecierk2.setTransform(posrk2);
   przesuniecierk2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
   przesuniecierk2.addChild(rk2);
  przesuniecierk1.addChild(przesuniecierk2);
   
   

      ///////////////////////////////////// Prymityw /////////////////////////////////
 karton = new Box(0.06f, 0.06f,0.06f,app2);
    Vector3f wektor5 = new Vector3f(0.34f,0.054f , 0.0f);
  pos6 = new Transform3D();
   pos6.setTranslation(wektor5);
    // Create a TransformGroup and make it the parent of the box
       przesuniecie6= new TransformGroup();
   przesuniecie6.setTransform(pos6);
 przesuniecie6.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 przesuniecie6.addChild(karton);
   przesuniecierk2.addChild( przesuniecie6); 

     ///////////////////////////////////// Czujnik////////////////////////////////  
     
  czujnik = new Box(0.004f, 0.001f,0.004f,app2);
   Vector3f wektorrk3 = new Vector3f(0.f,0.061f,0.0f);
   poscz = new Transform3D();
   poscz.setTranslation(wektorrk3);
  przesunieciecz= new TransformGroup();
    przesunieciecz.setTransform( poscz);
   przesunieciecz.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
 przesunieciecz.addChild(czujnik);
  przesuniecie6.addChild(przesunieciecz);
   
   
   
  //////////////////////////////////////// Tło ///////////////////////////////////////////////////////////
   Background tlo=new Background();
   tlo.setColor(new Color3f(0.3f,.3f,.3f));
      tlo.setCapability(Background.ALLOW_COLOR_WRITE);
    tlo.setApplicationBounds(new BoundingSphere());
   grafSceny.addChild(tlo);
   
   
 
	   BoundingSphere bounds =new BoundingSphere();	   

	
///////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\/Kolizja z czujnikiem/////\//////////////////////////////////////////////
       CollisionDetector cd = new CollisionDetector();
   cd.setSchedulingBounds(new BoundingBox());
 grafSceny.addChild(cd);	
 
 ///////////\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\/Kolizja z prymitywem/////\//////////////////////////////////////////////
       CollisionDetectorp cdp = new CollisionDetectorp();
   cdp.setSchedulingBounds(new BoundingBox());
 grafSceny.addChild(cdp);
        
   //oświetlenie + renederowanie(ograniczenie rysowania - renderowania, żeby nie było niepotrzebnej pracy)
    Color3f light1Color = new Color3f(1.0f,1.0f,1.0f);
    Vector3f light1Direction = new Vector3f(-1.0f,1.0f,-2.0f);
    DirectionalLight light1 = new DirectionalLight(light1Color,light1Direction);
    light1.setInfluencingBounds(bounds);
    grafSceny.addChild(light1);
  
    Color3f light2Color = new Color3f(1.0f,1.0f,1.0f);
    Vector3f light2Direction = new Vector3f(1.0f,-1.0f,2.0f);
    DirectionalLight light2 = new DirectionalLight(light2Color,light2Direction);
    light2.setInfluencingBounds(bounds);
    grafSceny.addChild(light2);
    
 
    
    Color3f ambientColor = new Color3f(1.0f,1.0f,1.0f);
    AmbientLight ambientLightNode= new AmbientLight(ambientColor);
    ambientLightNode.setInfluencingBounds(bounds);
    grafSceny.addChild(ambientLightNode);
        

        return grafSceny;   
     } 
       
     
     
     
 //////////////////////////////////////////Wykrywanie kolizji z czujnikiem/////////////////////////////    
     class CollisionDetector extends Behavior {
 
  private boolean inCollision = false;

  private WakeupOnCollisionEntry wEnter;

  private WakeupOnCollisionExit wExit;

  

  public void initialize() {
  
     wEnter = new WakeupOnCollisionEntry(czujnik,USE_GEOMETRY);
    wExit = new WakeupOnCollisionExit(czujnik,USE_GEOMETRY);
    wakeupOn(wEnter);
  }

  public void processStimulus(Enumeration criteria) {
  inCollision = !inCollision;

    if (inCollision) {
        System.out.println("TAAAAAAAAAAAAAAAA");
        if(polaczenie==0)
    mat2.setDiffuseColor(new Color3f(.0f, .8f,0.0f));
    kolizja=1;
      wakeupOn(wExit);
    } else {
         if(polaczenie==0)
         mat2.setDiffuseColor(new Color3f(.9f, .8f,0.8f));
         System.out.println("NIEEEEEEEEEEEEEEEEE");
         kolizja=0;
      wakeupOn(wEnter);
    }
  }
}
     
     
     
 //////////////////////////////////////////////Wykrywanie kolizji z prymitywem///////////////////////////    
    class CollisionDetectorp extends Behavior {
 
  private boolean inCollision = false;

  private WakeupOnCollisionEntry wEnter;

  private WakeupOnCollisionExit wExit;

  

  public void initialize() {
    wEnter = new WakeupOnCollisionEntry(karton,USE_GEOMETRY);
    wExit = new WakeupOnCollisionExit(karton,USE_GEOMETRY);
    wakeupOn(wEnter);
  }

  public void processStimulus(Enumeration criteria) {
if(polaczenie==0){  
      inCollision = !inCollision;
    if (inCollision) {
        blokada=1;
        System.out.println("prawie");
         if(last==1)
              {
                  yloc = yloc + .01f;  
                       System.out.println(yloc);
         trans.setTranslation(new Vector3f(0.0f, +0.01f,0));
         pos5.mul(trans);
         przesuniecie5.setTransform(pos5);   
              }      
        if(last==2)
        {  
            kat=0.01f;
            korekta1-=kat;  
               licz2+=kat;
     obrot.rotY(kat);
    poslacz1.mul(obrot);
    przeslacz1.setTransform(poslacz1);
        }
          if(last==3)
        {  
            kat=-0.01f;
            korekta1-=kat;  
               licz2+=kat;
     obrot.rotY(kat);
    poslacz1.mul(obrot);
    przeslacz1.setTransform(poslacz1);
        }
        
          if(last==4)
     {   
         kat=0.01f;
         licz1+=kat;
          korekta2+=kat;  
      obrot.rotY(kat);
    poslacz2.mul(obrot);
    przeslacz2.setTransform(poslacz2);
     }
          
       if(last==5){
           kat=-0.01f;
            korekta2+=kat;  
     licz1+=kat;
     obrot.rotY(kat);
     poslacz2.mul(obrot);
     przeslacz2.setTransform(poslacz2);     
       }
  
          
      wakeupOn(wExit);
    } else 
    {  
        System.out.println("good");
       blokada=0;
      wakeupOn(wEnter);}
    } else
    {  
        System.out.println("good");
       blokada=0;
      wakeupOn(wEnter);}

  }
    }
  

     
     
     
     
     
     
     
     
     
     

     
     
        
     ///////////// Utworzenie Sceny  ///////////////////////
      public Scara(){
         
        setLayout(new BorderLayout()); // ustawienie layoutu
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        
        // stworzenie kanwy(?)
       Canvas3D canvas = new Canvas3D(config);
        add(BorderLayout.CENTER, canvas);
  canvas.addKeyListener(this);

    
      
        scene = tworzenieGrafuSceny();
        
        // utworzenie wszechświata
       u = new SimpleUniverse(canvas);
      //  u.getViewingPlatform().setNominalViewingTransform();
     //positionViewer();
        u.addBranchGraph(scene);
        
        
        ////////////\\\\\\\\\\\\\\\\\\\\\\\Poczatkowe przyblizenie obserwatora \//////////////////////////
           Transform3D przesuniecie_obserwatora = new Transform3D();
        przesuniecie_obserwatora.set(new Vector3f(0f,0f,3.0f));
   
      u.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
 
        
	
		canvas.addMouseMotionListener(this);
		canvas.addMouseListener(this);
        
                
                
                 Panel p = new Panel();
        p.add(nagrywaj); 
        p.add(wykonaj);
        p.add(napis);
       add("North", p);
                nagrywaj.addActionListener(this);
        nagrywaj.addKeyListener(this);
                wykonaj.addActionListener(this);
        wykonaj.addKeyListener(this);
          napis.setAlignment(napis.CENTER);
                
                
 /////////////////////////////////////  Operowanie widokiem poprzez mysz (przyblizenie,oddalenie,translacja,obrot//////////////////////
     OrbitBehavior obrotwidokiem=new OrbitBehavior(canvas,OrbitBehavior.REVERSE_ALL);
 obrotwidokiem.setSchedulingBounds(new BoundingSphere());  
         u.getViewingPlatform().setViewPlatformBehavior(obrotwidokiem);
        

      
    }     
       
 
  
    
    
 ///\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Ruch wysiegnikiem w gore i w dol\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    @Override
     public void keyPressed(KeyEvent e){
        if(e.getKeyChar() == 'e')
            if(yloc<0.13f)
            {  
           yloc = yloc + .01f; 
                System.out.println(yloc);
             trans.setTranslation(new Vector3f(0.0f, 0.01f,0));
               pos5.mul(trans);
         przesuniecie5.setTransform(pos5);
        if(polaczenie==1)
        {  
            pos6.mul(trans);
            przesuniecie6.setTransform(pos6);
        }
        
  //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==1)
        {
s1+=0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s1+=0.01f;
       stan=1;
       x=x+1;
       tablica[0][x]=1;
        tablica[1][x]+=1;
        }
        
            } 
        if(e.getKeyChar() == 'd')
        {
              if(yloc>-0.13f && polaczenie==0) 
                  {last=1;
                  
                  yloc = yloc - .01f;  
                       System.out.println(yloc);
         trans.setTranslation(new Vector3f(0.0f, -0.01f,0));
         pos5.mul(trans);
         przesuniecie5.setTransform(pos5);
         
                  }
      
        if(polaczenie==1 && yloc>-0.12f) 
        { 
            yloc = yloc - .01f;  
                       System.out.println(yloc);
         trans.setTranslation(new Vector3f(0.0f, -0.01f,0));
         pos5.mul(trans);
         przesuniecie5.setTransform(pos5);
             pos6.mul(trans);
            przesuniecie6.setTransform(pos6);
            
    //////////////////////////////////////////NAGRYWANIE////////////////////////////////////////         
          if(nagrywanie==1)
        if(stan==2)
        {
s1+=-0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s1+=-0.01f;
       stan=2;
       x=x+1;
       tablica[0][x]=2;
        tablica[1][x]+=1;
        }    
            
            
        }
  
                  }
              
      
        
         
        
        
         ///\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Obrot o kat ramienia 1\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
     if(e.getKeyChar() == 'q')
         if(licz2>-2)
         { 
  if(polaczenie==0)  korekta1+=kat;           
         last=2;
     kat=-0.01f;
     licz2+=kat;
             System.out.println(licz2);
     obrot.rotY(kat);
    poslacz1.mul(obrot);
    przeslacz1.setTransform(poslacz1); 
    
     if(polaczenie==1){
              obrot.rotY(kat);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1); 
    
    //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==3)
        {
s2+=-0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s2+=-0.01f;
       stan=3;
       x=x+1;
       tablica[0][x]=3;
        tablica[1][x]+=1;
        }
    
         }
     }
     
       if(e.getKeyChar() == 'w')
           if(licz2<2){
                 if(polaczenie==0)  korekta1+=kat;    
         last=3;
     kat=0.01f;
     licz2+=kat;
         System.out.println(licz2);
     obrot.rotY(kat);
     poslacz1.mul(obrot);
     przeslacz1.setTransform(poslacz1); 
     
       if(polaczenie==1){
              obrot.rotY(kat);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1); 
    
    //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==4)
        {
s2+=0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s2+=0.01f;
       stan=4;
       x=x+1;
       tablica[0][x]=4;
        tablica[1][x]+=1;
        }
         }

     
     
     }
       
   ///\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Obrot o kat ramienia 2\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
          if(e.getKeyChar() == 'a'){
    
        
     if(licz1-kat>-2)
     {   last=4;
         kat=-0.01f;
         licz1+=kat;
      obrot.rotY(kat);
      //System.out.println(licz1);
    poslacz2.mul(obrot);
    przeslacz2.setTransform(poslacz2);
       if(polaczenie==0)  korekta2+=kat;    
      if(polaczenie==1){
              obrot.rotY(kat);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2); 
    
     
    //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==5)
        {
s3+=-0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s3+=-0.01f;
       stan=5;
       x=x+1;
       tablica[0][x]=5;
        tablica[1][x]+=1;
        }
         }

     }

     }
   
       if(e.getKeyChar() == 's'){
 
   
     if(licz1+kat<2){
         last=5;
           kat=0.01f;
         //System.out.println(licz1);
     licz1+=kat;
     obrot.rotY(kat);
     poslacz2.mul(obrot);
     przeslacz2.setTransform(poslacz2); 
       if(polaczenie==0)  korekta2+=kat;    
      if(polaczenie==1){
              obrot.rotY(kat);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2); 
    
     
    //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==6)
        {
s3+=0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s3+=0.01f;
       stan=6;
       x=x+1;
       tablica[0][x]=6;
        tablica[1][x]+=1;
        }
    
    
         }
     }
    
     
     }

         ///\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\Obrot o kat wysiegnika\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
        if(e.getKeyChar() == 'z'){
     kat=-0.01f;
     obrot.rotY(kat);
    pos5.mul(obrot);
    przesuniecie5.setTransform(pos5); 
    
     if(polaczenie==1){
             obrot.rotY(kat);
   pos6.mul(obrot);
   przesuniecie6.setTransform(pos6); 
   //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==7)
        {
s4+=-0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s4+=-0.01f;
       stan=7;
       x=x+1;
       tablica[0][x]=7;
        tablica[1][x]+=1;
        }
         }

     }
       if(e.getKeyChar() == 'x'){
         
     kat=0.01f;
     obrot.rotY(kat);
     pos5.mul(obrot);
     przesuniecie5.setTransform(pos5); 
     
       if(polaczenie==1){
              obrot.rotY(kat);
    pos6.mul(obrot);
    przesuniecie6.setTransform(pos6);
    //////////////////////////////////////////NAGRYWANIE/////////////////////////////////////////      
        if(nagrywanie==1)
        if(stan==8)
        {
s4+=0.01f;
         tablica[1][x]+=1;  
        }else
        {
            s4+=0.01f;
       stan=8;
       x=x+1;
       tablica[0][x]=8;
        tablica[1][x]+=1;
        }
         }
     }
       

       
                 
   //////////////////////////\\\\\\\\\\\\\Powrot do postaci zerowej przyblizenia \\\\\\\\\\\\\\\\\////              
                            if(e.getKeyChar() == ' '){
                   Transform3D przyblizenie = new Transform3D();
        przyblizenie.set(new Vector3f(0.0f,0.0f,3f));
        przyb=3f;
   u.getViewingPlatform().getViewPlatformTransform().setTransform(przyblizenie);
     }
                            
                            
   ////////////////////////////////////////////////Podlaczenie/////////////////////////////////////////                             
                            
                 if(e.getKeyChar() == 'n' && polaczenie==0 && kolizja==1){
      polaczenie=1;
      mat2.setDiffuseColor(new Color3f(.9f, .0f,0.0f));
      yloc = yloc - .01f;  
       trans.setTranslation(new Vector3f(0.0f, -0.01f,0));
      pos5.mul(trans);
            przesuniecie5.setTransform(pos5);
                     System.out.println(yloc);
                     
          ///////Korekta1           
            obrot.rotY(korekta1);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1);  
    //////korekta 2
          obrot.rotY(korekta2);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2);            
                     
                     
     }
  ////////////////////////////////////////////////Odlaczenie/////////////////////////////////////////          
                              if(e.getKeyChar() == 'm' && polaczenie==1){
        korekta1=0;  
        korekta2=0;          
        if(nagrywanie==1){ gotowe=1; napis.setText("ZAKOŃCZENIE NAGRYWANIA"); nagrywanie=0; }
      polaczenie=0;
      spadanie=yloc;
       mat2.setDiffuseColor(new Color3f(.9f, .8f,0.8f));
      while(spadanie>-0.13f)
      { 
             spadanie+=-0.00005f;
             if(spadanie<-0.13f) spadanie=-0.13f; else
             {   System.out.println(yloc);
trans.setTranslation(new Vector3f(0.0f, -0.00005f,0));
             pos6.mul(trans);
            przesuniecie6.setTransform(pos6);
      }}
      if(yloc>=-0.13f && yloc<-0.125)
      {yloc = yloc + .02f;  
     trans.setTranslation(new Vector3f(0.0f, +0.02f,0));
      pos5.mul(trans);
           przesuniecie5.setTransform(pos5);
              System.out.println(yloc);
              
               
      }
      }
       
                 
                 
                 
     }
     
     
 
      
     
        public void keyReleased(KeyEvent e){
        
    }
    

    public void keyTyped(KeyEvent e){
        
    }
     
           
/////////////////////////////Operacje po wcisnieciu przyciskow //////////////////////////////
    public void actionPerformed(ActionEvent e){
           if(e.getSource() ==nagrywaj)
           {
        if(polaczenie==0)
        {  
            if(kolizja==0) napis.setText("PRZYŁÓŻ WYSIĘGNIK DO PRYMITYWU"); 
            if(kolizja==1) {  napis.setText("NAGRYWANIE");
            s1=0; s2=0; s3=0;
            nagrywanie=1;
        polaczenie=1;
        gotowe=0;
        stan=0;
    mat2.setDiffuseColor(new Color3f(.9f, .0f,0.0f));
     yloc = yloc - .01f;  
     trans.setTranslation(new Vector3f(0.0f, -0.01f,0));
     pos5.mul(trans);
           przesuniecie5.setTransform(pos5);
           
             ///////Korekta1           
            obrot.rotY(korekta1);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1);  
    //////korekta 2
          obrot.rotY(korekta2);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2);   
         
    
    ////////////////////////////////////////////// Wypelnienie tablicy zerami //////////////////////////////////////////////////////
      for(int i=0;i<30;i++) 
      {  tablica[0][i]=0;
           tablica[1][i]=0;
      }
            }
         }
        
        if(polaczenie==1)
        {
            if(nagrywanie==1) napis.setText("NAGRYWANIE JUŻ TRWA");
            if(nagrywanie==0) napis.setText("ABY ZACZAĆ NAGRYWAĆ OBIEKT MUSI BYĆ NIEPOŁĄCZONY");
            
        }
           }

           
   if(e.getSource() ==wykonaj)    
    if(gotowe==0) {napis.setText("NIC NIE JEST NAGRANE");}else
   if(gotowe==1) 
   { int ok=0;
      
          System.out.println("s1");
        System.out.println(s1);
           System.out.println("s1");
         System.out.println(s2);
            System.out.println("s1");
          System.out.println(s3);
             System.out.println("s1");
           System.out.println(s4);
       if(yloc<-0.12 )
           if(licz2+s2>-2 && licz2+s2<2)
           if(licz1+s3>-2 && licz1+s3<2) ok=1;
            if(polaczenie==0) napis.setText("PODŁĄCZ ELEMENT");
    if(polaczenie==1 && ok==0) napis.setText("PRZESUŃ ELEMENT GDYZ WYCHODZI POZA ZAKRES");
        if(polaczenie==1 && ok==1) napis.setText("WYKONYWANIE SEKWENCJI");
           
           if(ok==1)
           {          if(polaczenie==1)  
             
     for(int i=0;i<30;i++) 
     { ok=0; 
         if(tablica[0][i]==1)
          for(int j=0;j<tablica[1][i]*500;j++)
          {
                 System.out.println("przesuniecie w gore");
           yloc = yloc + 0.00002f; 
             trans.setTranslation(new Vector3f(0.0f, 0.00002f,0));
               pos5.mul(trans);
         przesuniecie5.setTransform(pos5);  
            pos6.mul(trans);
            przesuniecie6.setTransform(pos6);
         }
    
         
         
          if(tablica[0][i]==2)
                 for(int j=0;j<tablica[1][i]*500;j++)
          {      System.out.println("przesuniecie w dol");
           yloc = yloc - 0.00002f;  
         trans.setTranslation(new Vector3f(0.0f, -0.00002f,0));
         pos5.mul(trans);
         przesuniecie5.setTransform(pos5);
             pos6.mul(trans);
            przesuniecie6.setTransform(pos6);
          }
         
         if(tablica[0][i]==3)
                 for(int j=0;j<tablica[1][i]*500;j++)
                 {
                          System.out.println("przesuniecie w lewo 1");
         kat=- 0.00002f; 
     licz2+=kat;
     obrot.rotY(kat);
    poslacz1.mul(obrot);
    przeslacz1.setTransform(poslacz1); 
              obrot.rotY(kat);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1); 
     }   
          
         if(tablica[0][i]==4)
                 for(int j=0;j<tablica[1][i]*500;j++)   
                 {
                          System.out.println("przesuniecie w prawo 1");
       kat=0.00002f; 
     licz2+=kat;
     obrot.rotY(kat);
     poslacz1.mul(obrot);
     przeslacz1.setTransform(poslacz1); 
              obrot.rotY(kat);
    posrk1.mul(obrot);
    przesuniecierk1.setTransform(posrk1);     
       }     
          
     if(tablica[0][i]==5)
                 for(int j=0;j<tablica[1][i]*500;j++) 
                 {
       System.out.println("przesuniecie w prawo 2");
               kat=-.00002f; 
         licz1+=kat;
      obrot.rotY(kat);
    poslacz2.mul(obrot);
    przeslacz2.setTransform(poslacz2);
              obrot.rotY(kat);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2);        
                     
      }        
         
   
         
     if(tablica[0][i]==6)
                 for(int j=0;j<tablica[1][i]*500;j++) 
                 {
                      System.out.println("przesuniecie w prawo 2");
       kat=0.00002f; 
     licz1+=kat;
     obrot.rotY(kat);
     poslacz2.mul(obrot);
     przeslacz2.setTransform(poslacz2);   
              obrot.rotY(kat);
    posrk2.mul(obrot);
    przesuniecierk2.setTransform(posrk2);   
      }
         
       
          if(tablica[0][i]==7)
                 for(int j=0;j<tablica[1][i]*500;j++) 
                 {
                      System.out.println("obrot prymitywu 1");
         kat=-0.00002f; 
     obrot.rotY(kat);
    pos5.mul(obrot);
    przesuniecie5.setTransform(pos5); 
             obrot.rotY(kat);
   pos6.mul(obrot);
   przesuniecie6.setTransform(pos6);    
     } 
         
        if(tablica[0][i]==8)
                 for(int j=0;j<tablica[1][i]*500;j++) 
                 {
                     System.out.println("obrot prymitywu 2");
         kat=+0.00002f; 
     obrot.rotY(kat);
    pos5.mul(obrot);
    przesuniecie5.setTransform(pos5); 
             obrot.rotY(kat);
   pos6.mul(obrot);
   przesuniecie6.setTransform(pos6);    
     }          
          
          
   } 
           }
  
     
     
     
     
     
     
     
     
     
   
            
           for(int p=0;p<30;p++) 
      {  System.out.println(tablica[0][p]);
         System.out.print("             ");
           System.out.println(tablica[1][p]);
      }  
         
   
   
   }
           
           
           
}
    

	public void mouseClicked(MouseEvent arg0) {
	}

	
	public void mouseEntered(MouseEvent arg0) {
	}

	
	public void mouseExited(MouseEvent arg0) {
	}

	
	public void mousePressed(MouseEvent event) {
	
	}

	
	public void mouseReleased(MouseEvent arg0) {		
	}

        
        

	public void mouseDragged(MouseEvent event) {		
		
	}
	

	public void mouseMoved(MouseEvent arg0) {	
	}   
    
    
}   
    public class ScaraProjekt {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
          Scara bb = new Scara();
        bb.addKeyListener(bb);
        MainFrame mf = new MainFrame(bb, 900, 600); 
        
    }
    
}
