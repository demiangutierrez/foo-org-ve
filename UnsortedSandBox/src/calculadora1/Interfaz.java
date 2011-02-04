

package calculadora1;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

	public class Interfaz{
	
	JTextField valor = new JTextField();	
	float a;
	float b;
	int opc;	
	

	public Interfaz () {

	JFrame ventana = new JFrame();
	
	ventana.setTitle("Calculadora Sencilla");
	ventana.setLayout(new FlowLayout());
	
	JLabel lblNombre = new JLabel("Bienvenido",JLabel.CENTER);
	lblNombre.setPreferredSize(new Dimension(240,50));
	ventana.add(lblNombre);
	
	valor.setPreferredSize(new Dimension(270,30));
	ventana.add(valor);
	
	
	JLabel lblEspacio = new JLabel("",JLabel.CENTER);
	lblEspacio.setPreferredSize(new Dimension(300,10));
	ventana.add(lblEspacio);
	
	JButton btnSuma = new JButton("+");
	btnSuma.setPreferredSize(new Dimension(67,30));
	btnSuma.setBackground(Color.magenta);
	btnSuma.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnSumaCliked();
			}
	});
	ventana.add(btnSuma);
 	

	JButton btnResta = new JButton("-");
	btnResta.setPreferredSize(new Dimension(67,30));
	ventana.add(btnResta);
	btnResta.setBackground(Color.cyan);
	btnResta.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnRestaCliked();
			}
	});
		
	JButton btnMulti = new JButton("*");
	btnMulti.setPreferredSize(new Dimension(67,30));
 	ventana.add(btnMulti);
 	btnMulti.setBackground(Color.getHSBColor(100, 70, 20));
 	btnMulti.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnMultiCliked();
			}
	});
 	
 	
 	JButton btnDivision = new JButton("/");
	btnDivision.setPreferredSize(new Dimension(67,30));
 	ventana.add(btnDivision);
 	btnDivision.setBackground(Color.BLUE);
 	btnDivision.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnDivisionCliked();
			}
	});
 	
 	
 	JButton btn1 = new JButton("1");
	btn1.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn1);
	btn1.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn1Cliked();
			}
	}
	);
 	
 	JButton btn2 = new JButton("2");
	btn2.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn2);
	btn2.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn2Cliked();
			}
	}
	);
	
 	JButton btn3 = new JButton("3");
	btn3.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn3);
 	btn3.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn3Cliked();
			}
	});
 	
 	JButton btn4 = new JButton("4");
	btn4.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn4);
 	btn4.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn4Cliked();
			}
	});
 	
 	JButton btn5 = new JButton("5");
	btn5.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn5);
 	btn5.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn5Cliked();
			}
 	});
 	
 	
 	
 	JButton btn6 = new JButton("6");
	btn6.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn6);
 	btn6.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn6Cliked();
			}
 	});
 	
 	
 	JButton btn7 = new JButton("7");
	btn7.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn7);
 	btn7.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn7Cliked();
			}
 	});
 	
 	
 	JButton btn8 = new JButton("8");
	btn8.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn8);
 	btn8.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn8Cliked();
			}
 	});
 	
 	
 	JButton btn9 = new JButton("9");
	btn9.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn9);
 	btn9.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn9Cliked();
			}
 	});
 	
 	JButton btn0 = new JButton("0");
	btn0.setPreferredSize(new Dimension(50,30));
 	ventana.add(btn0);
 	btn0.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btn0Cliked();
			}
 	});
 	
 	
 	JButton btnResultado = new JButton("El Resultado es: ");
	btnResultado.setPreferredSize(new Dimension(250,30));
 	ventana.add(btnResultado);
 	btnResultado.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnResultadoCliked();
			}
 	});
 	
 	JButton btnBorrar = new JButton("Borrar");
	btn0.setPreferredSize(new Dimension(50,30));
 	ventana.add(btnBorrar);
 	btnBorrar.addActionListener(new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent e) {
			btnBorrarCliked();
			}
 	});
 	
 	JLabel lblNombre1 = new JLabel("Creado por Geral",JLabel.CENTER);
	lblNombre1.setPreferredSize(new Dimension(200,50));
	ventana.add(lblNombre1);
	
	
	
ventana.setVisible(true);
ventana.setSize(310, 330);
ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	
}		
private void btn1Cliked() {
		
	valor.setText(valor.getText()+"1");
	}
		
private void btn2Cliked() {
	
	valor.setText(valor.getText()+"2");
	}

private void btn3Cliked() {
	
	valor.setText(valor.getText()+"3");
	}
	
private void btn4Cliked() {
	
	valor.setText(valor.getText()+"4");
	}
	
private void btn5Cliked() {
	
	valor.setText(valor.getText()+"5");
	}
	
private void btn6Cliked() {
	
	valor.setText(valor.getText()+"6");
	}

private void btn7Cliked() {
	
	valor.setText(valor.getText()+"7");
	}

private void btn8Cliked() {
	
	valor.setText(valor.getText()+"8");
	}

private void btn9Cliked() {
	
	valor.setText(valor.getText()+"9");
	}

private void btn0Cliked() {
	
	valor.setText(valor.getText()+"0");
	}
	
private void btnBorrarCliked() {
	
	valor.setText("");
	
	}
	
private void leervalor (){
	
	a = Float.valueOf(valor.getText());
	valor.setText("");
	
}


	private void btnSumaCliked() {
	
	opc=1;
	leervalor();

}
	
	private void btnRestaCliked() {
	
	opc=2;
	leervalor();
}

	private void btnMultiCliked() {
		
		opc=3;
		leervalor();
	}
	
	private void btnDivisionCliked() {
		
		opc=4;
		leervalor();
	}

	private void btnResultadoCliked() {
	b = Float.valueOf(valor.getText());
	
	if (opc == 1){
		a=a+b;
		valor.setText(String.valueOf(a));
		
	}
	
	if (opc == 2){
		a=a-b;
		valor.setText(String.valueOf(a));
		
	}
	
	if (opc == 3){
		a=a*b;
		valor.setText(String.valueOf(a));
		
	}
	
	if (opc == 4){
		a=a/b;
		valor.setText(String.valueOf(a));
		
		
	}
	
	
	
	}
	

	public static void main(String[] args) {
			
			new Interfaz();
			
			
		}		
		

}
