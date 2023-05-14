package Prueba;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class FrmJuego extends JFrame {
	
	int numFilas;
	int numCol;
	int numMinas;
	int contadorSeg=0;
	
	ArrayList<Jugador> jugadores=new ArrayList<>();
	
	String jugActual="";
	int puntajeActual=0;
	JButton[][] botonesTab;
	Tablero tableroJuego;
	Timer timer;
    /**
     * Creates new form FrmJuego
     */
    public FrmJuego() {
        initComponents();

    }

    public void juegoNuevo() {
    	//Metodo para descargar los botones al hacer un juego nuevo
    	descargarBotones();
    	//Metodo para cargar los botones
    	cargarBotones();
    	//Metodo para pintar el tablero
        crearTableroJuego();
        
        //Metodo para refrescar los graficos en pantalla
        repaint();
    }
    
    
    private void crearTableroJuego() {
    	
    	tableroJuego=new Tablero(numFilas,numCol,numMinas);
    	
//    	tableroJuego.setPartidaPerdida(new Consumer<List<Casilla>>() {
//			
//			@Override
//			public void accept(List<Casilla> t) {
//				for (Casilla casMina : t) {
//					botonesTab[casMina.getNumFil()][casMina.getNumCol()].setText("*");
//					botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBackground(Color.red);
//		    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBorder(null);
//		    		deshabilitarCasillas();
//				}
//			}
//		});

    	tableroJuego.setPartidaPerdida((List<Casilla> t) -> {
    		t.forEach(casMina ->{
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setIcon(new ImageIcon(getClass().getResource("img/minaotra.png")));
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBackground(Color.red);
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBorder(null);
    		
    	}
    		
    		);
    		deshabilitarCasillas();
    		timer.stop();
    		Jugador j=existeJugador(jugActual);
    		j.setPuntuacion(puntajeActual);
    		contadorSeg=0;
    		puntajeActual=0;
    	});
    	
//    	tableroJuego.setCasillaAbierta(new Consumer<Casilla>() {
//    		
//    		@Override
//    		public void accept(Casilla t) {
//    			botonesTab[t.getNumFil()][t.getNumCol()].setEnabled(false);
//    			botonesTab[t.getNumFil()][t.getNumCol()].setText(t.getMinasCerca()==0?"":t.getMinasCerca()+"");
//    		};
//		});	
    	
    	tableroJuego.setCasillaAbierta((Casilla t) ->{
    		botonesTab[t.getNumFil()][t.getNumCol()].setEnabled(false);
    		if (!t.isMina()) {
    			botonesTab[t.getNumFil()][t.getNumCol()].setText(Integer.toString(t.getMinasCerca()));
    			puntajeActual++;
			}
    		
    	});
    	
    	tableroJuego.setPartidaGanada((List<Casilla> t)->{
    		for (Casilla casilla : t) {
    			t.forEach(casMina ->botonesTab[casMina.getNumFil()][casMina.getNumCol()].setText(":)"));
			}
    		timer.stop();
    		Jugador j=existeJugador(jugActual);
    		j.setPuntuacion(puntajeActual);
    		contadorSeg=0;
    		puntajeActual=0;
    	});
    	
    	tableroJuego.mostrarTodo();
    }
    
    private void eleccionDifi() {
    	facil = new javax.swing.JButton();
        medio = new javax.swing.JButton();
        Dificil = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        JLabel dificultad=new JLabel("ELIGE LA DIFICULTAD");
        dificultad.setSize(300,100);
        dificultad.setLocation((getWidth()/2)-100,-40);
        dificultad.setFont(new Font("Arial",Font.BOLD,20));
        
        JLabel jugador=new JLabel("Ingresa tu nombre!");
        nomJugador=new JTextField("Jugador "+(jugadores.size()+1));
        
        jugador.setSize(300,50);
        jugador.setLocation(350,300);
        jugador.setFont(new Font("Arial", Font.BOLD,20));
        
        nomJugador.setSize(180,30);
        nomJugador.setLocation(350,350);
        
        getContentPane().add(dificultad);
        getContentPane().add(nomJugador);
        getContentPane().add(jugador);
        getContentPane().add(imagen);
        imagen.setSize(500,500);
        imagen.setLocation(370, -150);
       
        
        facil.setText("Facil B)");
        facil.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				regresarImagen();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				imagen.setIcon(new ImageIcon(getClass().getResource("img/feli.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				elegirDificultad(1);
			}
		});
        facil.setSize(100,100);
        facil.setLocation(250,200);
        getContentPane().add(facil);
        
        medio.setText("Normal -_-");
        medio.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				regresarImagen();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				imagen.setIcon(new ImageIcon(getClass().getResource("img/seria.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				elegirDificultad(2);
			}
		});
        medio.setSize(100,100);
        medio.setLocation(400,200);
        getContentPane().add(medio);
        
        Dificil.setText("Muy Dificil >:)");
        Dificil.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				regresarImagen();
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				imagen.setIcon(new ImageIcon(getClass().getResource("img/asustada.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				elegirDificultad(3);
			}
		});
        Dificil.setSize(100,100);
        Dificil.setLocation(550,200);
        getContentPane().add(Dificil);
        
        imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/saludo.png")));
    }
    
    private void elegirDificultad(int num) {
    	int dif=num;	
    		switch (dif) {
    		case 1 ->{numFilas=8; numCol=8; numMinas=10;setSize(450,400);}
    		case 2 ->{numFilas=16; numCol=16; numMinas=35;setSize(700,650);}
    		case 3 ->{numFilas=16; numCol=32; numMinas=45;setSize(1150,650);}
    		}
    		botonesTab= new JButton[numFilas][numCol];
    		jugActual=nomJugador.getText();
    		
    		if (existeJugador(jugActual)==null) {
				jugadores.add(new Jugador(jugActual));
			}
    	juegoNuevo();
    }
    
    private Jugador existeJugador(String nomJugador) {
    	for (Jugador j:jugadores) {
			if (j.nombre.equals(nomJugador)) {
				return j;
			}
		}
    	return null;
    }
    private void deshabilitarCasillas() {
    	for (int i = 0; i < botonesTab.length; i++) {
			for (int j = 0; j < botonesTab[i].length; j++) {
				botonesTab[i][j].setEnabled(false);
			}
		}
    }
    private void cargarBotones() {
    	limpiarTodo();
    	int posXRef=100;
    	int posYRef=85;
    	int ancho=30;
    	int alto=30;
    	
    	JLabel principal=new JLabel();
    	principal.setSize(getWidth(),80);
    	principal.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
    	
    	contador=new JLabel();
    	contador.setSize(60,60);
    	contador.setLocation(10,10);
    	contador.setText("0");
    	contador.setBackground(Color.BLACK);
    	contador.setForeground(Color.RED);
    	contador.setHorizontalAlignment(SwingConstants.CENTER);
    	contador.setOpaque(true);
    	contador.setFont(new Font("Verdana",Font.BOLD,30));
    	
    	imagenPar=new JLabel();
    	imagenPar.setSize(80, 80);
    	imagenPar.setLocation((getWidth()/2)-50,0);
    	imagenPar.setIcon(new ImageIcon(getClass().getResource("img/felizPar.png")));
    	
    	principal.add(imagenPar);
    	principal.add(contador);
    	getContentPane().add(principal);
    	
    	for (int i = 0; i < botonesTab.length; i++) {
			for (int j = 0; j < botonesTab[i].length; j++) {
				botonesTab[i][j]=new JButton();
				botonesTab[i][j].setName(i + "," + j);
				botonesTab[i][j].setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
				
				if(i==0 && j==0) {
					botonesTab[i][j].setBounds(posXRef,posYRef,ancho,alto);
					
				}else if(i==0 && j!=0) {
					botonesTab[i][j].setBounds(
							botonesTab[i][j-1].getX()+botonesTab[i][j-1].getWidth(),
							posYRef,ancho,alto);
				}else {
					botonesTab[i][j].setBounds(
							botonesTab[i-1][j].getX(),botonesTab[i-1][j].getY()+botonesTab[i-1][j].getHeight(),ancho,alto);
				}
				botonesTab[i][j].addMouseListener(new MouseListener() {

					@Override
					public void mousePressed(MouseEvent e) {
						// TODO Auto-generated method stub
						imagenPar.setIcon(new ImageIcon(getClass().getResource("img/sorprendidapar.png")));
					}
					
					@Override
					public void mouseExited(MouseEvent e) {
						// TODO Auto-generated method stub
						if (!tableroJuego.juegoTerminado) {
							imagenPar.setIcon(new ImageIcon(getClass().getResource("img/felizPar.png")));
						}else if(tableroJuego.partidaGanada()){
							imagenPar.setIcon(new ImageIcon(getClass().getResource("img/caritaGano.png")));
						}else {
							imagenPar.setIcon(new ImageIcon(getClass().getResource("img/caritaPerdio.png")));
						}
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						clickBoton(e);
					}

					@Override
					public void mouseReleased(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						// TODO Auto-generated method stub
						
					}
				});
				getContentPane().add(botonesTab[i][j]);
			}
		}
    	timer=new Timer(1000, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				contadorSeg++;
				cambiarTemp(contadorSeg);
			}
		});
    	timer.start();
    }
    
    private void cambiarTemp(int tiempo) {
    	contador.setText(""+tiempo);
    }
    
    private void descargarBotones(){
        for (int i = 0; i < botonesTab.length; i++) {
            for (int j = 0; j < botonesTab[i].length; j++) {
                if (botonesTab[i][j]!=null) {
                    getContentPane().remove(botonesTab[i][j]);
                }
            }
        }
    }
    
    private void puntuaciones() {
    	JLabel titPuntos=new JLabel();
    	JLabel jugPuntuaciones[]=new JLabel[10];
    	int posXRef=230;
    	int posYRef=10;
    	
    	titPuntos.setSize(400, 100);
    	titPuntos.setLocation(230,0);
    	titPuntos.setText("P U N T U A C I O N E S");
    	titPuntos.setFont(new Font("Comic Sans MS",Font.BOLD,30));
    	titPuntos.setForeground(Color.RED);
    	ordenarPorPuntos();
    	for (int i = 0; i < jugPuntuaciones.length; i++) {
    		JLabel jlb=new JLabel();
    		posYRef+=30;
    		if(jugadores.size()>i) {
    			Jugador n = jugadores.get(i);
    			jlb=new JLabel((i+1) +"       " + n.nombre + "       -       " + n.getPuntuacion());
    		}else {
				jlb=new JLabel((i+1) + "       Empty " + "        -       " + " Empty ");
			}
    		jlb.setSize(400,100);
    		jlb.setLocation(posXRef,posYRef);
    		jlb.setFont(new Font("Comic Sans Ms",Font.BOLD,17));
			jugPuntuaciones[i]=jlb;
		}
    	
    	for (JLabel jLabel : jugPuntuaciones) {
			getContentPane().add(jLabel);
		}
    	getContentPane().add(titPuntos);
    }
    
    private void ordenarPorPuntos() {
    	Comparator<Jugador> comPuntos=new Comparator<Jugador>() {
    		
    		@Override
    		public int compare(Jugador o1, Jugador o2) {
    			// TODO Auto-generated method stub
    			return o2.getPuntuacion()-o1.getPuntuacion();
    		}
		};
		
		Collections.sort(jugadores,comPuntos);
    }
	private void clickBoton(MouseEvent e) {
		JButton boton=(JButton) e.getSource();
		String[] cordenada= boton.getName().split(",");
		int posFila=Integer.parseInt(cordenada[0]);
		int posColum=Integer.parseInt(cordenada[1]);
		tableroJuego.seleccionarCasilla(posFila, posColum);
	}
	
	
	public void limpiarTodo() {
	    	getContentPane().removeAll();
	    	getContentPane().repaint();
	    }
	
	
	public void regresarImagen() {
	    	imagen.setIcon(new ImageIcon(getClass().getResource("img/saludo.png")));
	    }
	

    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {
    	
    	logoInicio = new javax.swing.JLabel();
    	inicioJuego = new javax.swing.JButton();
        instrucciones = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        initNewGame = new javax.swing.JMenuItem();
        chooseDif= new javax.swing.JMenuItem();
        inicio=new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logoInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("img/LogoBusc.jpeg"))); // NOI18N
        logoInicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        inicioJuego.setText("Nuevo Juego");
        inicioJuego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarTodo();
				eleccionDifi();
			}
		});

        instrucciones.setText("Puntuaciones");
        instrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	limpiarTodo();
            	puntuaciones();
            }
        });

        jMenu1.setText("Juego");

        initNewGame.setText("Juego Nuevo");
        initNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	if (botonesTab!=null) {
            		juegoNuevo();
				}else {
					eleccionDifi();
				}
            }
        });
        
        chooseDif.setText("Cambiar Dificultad");
        chooseDif.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	setSize(847,469);
            	limpiarTodo();
            	eleccionDifi();
            }
        });
        
        inicio.setText("Inicio");
        inicio.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarTodo();
				initComponents();
			}
		});
        this.setTitle("Buscaminas");
        Image icon=new ImageIcon(getClass().getResource("img/icono.jpg")).getImage();
        setIconImage(icon);
        jMenu1.add(inicio);
        jMenu1.add(initNewGame);
        jMenu1.add(chooseDif);
        
        
        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
        		 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                 .addGroup(layout.createSequentialGroup()
                     .addContainerGap()
                     .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                             .addComponent(logoInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                             .addContainerGap())
                         .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                             .addGap(0, 321, Short.MAX_VALUE)
                             .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                 .addComponent(inicioJuego, javax.swing.GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
                                 .addComponent(instrucciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                             .addGap(321, 321, 321))))
        );
        layout.setVerticalGroup(
        		 layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                 .addGroup(layout.createSequentialGroup()
                     .addGap(22, 22, 22)
                     .addComponent(logoInicio)
                     .addGap(75, 75, 75)
                     .addComponent(inicioJuego, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                     .addComponent(instrucciones, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                     .addContainerGap(136, Short.MAX_VALUE))
        );
        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmJuego.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmJuego().setVisible(true);
            }
        });
    }
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem initNewGame;
    private javax.swing.JMenuItem chooseDif;
    private javax.swing.JMenuItem inicio;
    private javax.swing.JButton inicioJuego;
    private javax.swing.JButton instrucciones;
    private javax.swing.JLabel logoInicio;
    private javax.swing.JButton Dificil;
    private javax.swing.JButton facil;
    private javax.swing.JLabel imagen;
    private javax.swing.JLabel imagenPar;
    private javax.swing.JLabel contador;
    private javax.swing.JButton medio;
    private javax.swing.JTextField nomJugador;
    
}
