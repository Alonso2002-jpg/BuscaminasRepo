package Prueba;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.ButtonModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class FrmJuego extends JFrame {
	
	int numFilas;
	int numCol;
	int numMinas;
	
	JButton[][] botonesTab;
	Tablero tableroJuego;
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
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setIcon(new ImageIcon(getClass().getResource("minaotra.png")));
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBackground(Color.red);
    		botonesTab[casMina.getNumFil()][casMina.getNumCol()].setBorder(null);
    		deshabilitarCasillas();
    	}
    		);
    	});
    	
    	tableroJuego.setCasillaAbierta(new Consumer<Casilla>() {
    		
    		@Override
    		public void accept(Casilla t) {
    			botonesTab[t.getNumFil()][t.getNumCol()].setEnabled(false);
    			botonesTab[t.getNumFil()][t.getNumCol()].setText(t.getMinasCerca()==0?"":t.getMinasCerca()+"");
    		};
		});	
    	
    	tableroJuego.setCasillaAbierta((Casilla t) ->{
    		botonesTab[t.getNumFil()][t.getNumCol()].setEnabled(false);
    		if (!t.isMina()) {
    			botonesTab[t.getNumFil()][t.getNumCol()].setText(Integer.toString(t.getMinasCerca()));
			}
    		
    	});
    	
    	tableroJuego.setPartidaGanada((List<Casilla> t)->{
    		for (Casilla casilla : t) {
    			t.forEach(casMina ->botonesTab[casMina.getNumFil()][casMina.getNumCol()].setText(":)"));
			}
    	});
    	
    	tableroJuego.mostrarTodo();
    }
    
    private void eleccionDifi() {
    	facil = new javax.swing.JButton();
        medio = new javax.swing.JButton();
        Dificil = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
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
				imagen.setIcon(new ImageIcon(getClass().getResource("feli.png")));
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
				imagen.setIcon(new ImageIcon(getClass().getResource("seria.png")));
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
				imagen.setIcon(new ImageIcon(getClass().getResource("asustada.png")));
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				elegirDificultad(3);
			}
		});
        Dificil.setSize(100,100);
        Dificil.setLocation(550,200);
        getContentPane().add(Dificil);
        
        imagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("saludo (1).png")));
    }
    
    private void elegirDificultad(int num) {
    	int dif=num;	
    		switch (dif) {
    		case 1 ->{numFilas=8; numCol=8; numMinas=10;setSize(500,400);}
    		case 2 ->{numFilas=16; numCol=16; numMinas=35;setSize(800,650);}
    		case 3 ->{numFilas=16; numCol=32; numMinas=45;setSize(1250,650);}
    		}
    		botonesTab= new JButton[numFilas][numCol];	
    	juegoNuevo();
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
    	int posXRef=25;
    	int posYRef=25;
    	int ancho=30;
    	int alto=30;
    	
    	
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
				botonesTab[i][j].addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						clickBoton(e);
					}
				
				});
				getContentPane().add(botonesTab[i][j]);
			}
		}
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
    
	private void clickBoton(ActionEvent e) {
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
	    	imagen.setIcon(new ImageIcon(getClass().getResource("saludo (1).png")));
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

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        logoInicio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logoInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("LogoBusc.jpeg"))); // NOI18N
        logoInicio.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        inicioJuego.setText("Nuevo Juego");
        inicioJuego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				limpiarTodo();
				eleccionDifi();
			}
		});

        instrucciones.setText("Intrucciones");
        instrucciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
            	limpiarTodo();
            }
        });

        jMenu1.setText("Juego");

        initNewGame.setText("Juego Nuevo");
        initNewGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
              juegoNuevo();
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
        
        this.setTitle("Buscaminas");
        Image icon=new ImageIcon(getClass().getResource("icono.jpg")).getImage();
        setIconImage(icon);
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
    private javax.swing.JButton inicioJuego;
    private javax.swing.JButton instrucciones;
    private javax.swing.JLabel logoInicio;
    private javax.swing.JButton Dificil;
    private javax.swing.JButton facil;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton medio;
}
