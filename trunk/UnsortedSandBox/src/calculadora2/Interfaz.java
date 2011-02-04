package calculadora2;



import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Interfaz extends JFrame {
	private JTextField txtNumero;
	private JTextField txtOperacion;
	public Float PubA;
	public Float PubB;
	public int PubOpc;
	public Interfaz(){
		setTitle("Calculadora Clasica");
		setLayout(new FlowLayout());
		
		
		FlowLayout fl = new FlowLayout();
		 
		
		txtNumero= new JTextField();
		txtNumero.setPreferredSize(new Dimension(150, 20));
		add(txtNumero);
		
		
		txtOperacion =new JTextField(" ",JTextField.LEFT);
		txtOperacion.setPreferredSize(new Dimension(20,20));
		add(txtOperacion);
		
		JLabel lblNombre = new JLabel("                                        by Cristian Gonzalez                                            ");
		add(lblNombre);
		
		//boton Ce
		JButton btnCe = new JButton("Ce");
		btnCe.setPreferredSize(new Dimension(80,26));
		add(btnCe);
		btnCe.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCeCliked();
				}
		});
		
		//Bonton C
		
		JButton btnC = new JButton("C");
		btnC.setPreferredSize(new Dimension(80,26));
		add(btnC);
		btnC.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCCliked();
				}
		});
		
		//boton mas "+"
		JButton btnMas = new JButton("+");
		btnMas.setPreferredSize(new Dimension(80,26));
		add(btnMas);
		btnMas.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnMasCliked();
				leerNumero();
				}
		});
		
		//boton menos "-"
		JButton btnMenos = new JButton("-");
		btnMenos.setPreferredSize(new Dimension(80,26));
		add(btnMenos);
		btnMenos.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnMenosCliked();
				}
		});
		//boton multiplicacion "*"
		JButton btnMult = new JButton("*");
		btnMult.setPreferredSize(new Dimension(80,26));
		add(btnMult);
		btnMult.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnMultCliked();
				}
		});
		//boton division "/"
		JButton btnDivision = new JButton("/");
		btnDivision.setPreferredSize(new Dimension(80,26));
		add(btnDivision);
		btnDivision.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnDivisionCliked();
				}
		});
		JButton btnIgual = new JButton("=");
		btnIgual.setPreferredSize(new Dimension(140,26));
		add(btnIgual);
		btnIgual.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnIgualCliked();
				}
		});
	//NUMEROS
		JButton btn1 = new JButton("1");
		add(btn1);
		btn1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn1Cliked();
				}
		});
		JButton btn2 = new JButton("2");
		add(btn2);
		btn2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn2Cliked();
				}
		});
		JButton btn3 = new JButton("3");
		add(btn3);
		btn3.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn3Cliked();
				}
		});
		JButton btn4 = new JButton("4");
		add(btn4);
		btn4.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn4Cliked();
				}
		});
		JButton btn5 = new JButton("5");
		add(btn5);
		btn5.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn5Cliked();
				}
		});
		JButton btn6 = new JButton("6");
		add(btn6);
		btn6.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn6Cliked();
				}
		});
		JButton btn7 = new JButton("7");
		add(btn7);
		btn7.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn7Cliked();
				}
		});
		JButton btn8 = new JButton("8");
		add(btn8);
		btn8.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn8Cliked();
				}
		});
		JButton btn9 = new JButton("9");
		add(btn9);
		btn9.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn9Cliked();
				}
		});
		JButton btn0 = new JButton("0");
		add(btn0);
		btn0.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btn0Cliked();
				}
		});
	//estas lineas son necesarias para que se vea la ventana y se cirre correctamente la ventana...
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(190, 360);
		setVisible(true);
	}
	protected void btnMasCliked() {
		txtOperacion.setText("+");
		leerNumero();
		PubOpc=1;
		
	}
	protected void btnMenosCliked() {
		txtOperacion.setText("-");
		leerNumero();
		PubOpc=2;
	}
	protected void btnMultCliked() {
		txtOperacion.setText("*");
		leerNumero();
		PubOpc=3;
	} 
	protected void btnDivisionCliked() {
		txtOperacion.setText("/");
		leerNumero();
		PubOpc=4;
	}	
	public void leerNumero() {
		PubA=Float.valueOf(txtNumero.getText());
		txtNumero.setText("");
	}
	public void btnIgualCliked() {
		PubB=Float.valueOf(txtNumero.getText());
		System.out.println(PubB);
		Operar();
	} 
	protected void Operar() {
		
		if(PubOpc==1){
			PubA=PubA+PubB;
			txtNumero.setText(String.valueOf(PubA));
		}
		if(PubOpc==2){
			PubA=PubA-PubB;
			txtNumero.setText(String.valueOf(PubA));
		}
		if(PubOpc==3){
			PubA=PubA*PubB;
			txtNumero.setText(String.valueOf(PubA));
		}
		if(PubOpc==4){
			PubA=PubA/PubB;
			txtNumero.setText(String.valueOf(PubA));
		}
		
	}	
	protected void btn1Cliked() {
		txtNumero.setText(txtNumero.getText()+"1");
	}
	protected void btn2Cliked() {
		txtNumero.setText(txtNumero.getText()+"2");
	}
	protected void btn3Cliked() {
		txtNumero.setText(txtNumero.getText()+"3");
	}
	protected void btn4Cliked() {
		txtNumero.setText(txtNumero.getText()+"4");
	}
	protected void btn5Cliked() {
		txtNumero.setText(txtNumero.getText()+"5");
	}
	protected void btn6Cliked() {
		txtNumero.setText(txtNumero.getText()+"6");
	}
	protected void btn7Cliked() {
		txtNumero.setText(txtNumero.getText()+"7");
	}
	protected void btn8Cliked() {
		txtNumero.setText(txtNumero.getText()+"8");
	}
	protected void btn9Cliked() {
		txtNumero.setText(txtNumero.getText()+"9");
	}
	protected void btn0Cliked() {
		txtNumero.setText(txtNumero.getText()+"0");
	}
	protected void btnCeCliked(){
		txtNumero.setText("");
	}
	protected void btnCCliked(){
		txtNumero.setText("");
		txtOperacion.setText("");
		PubOpc=0;
	}
	
public static void main(String[] args) {
	new Interfaz();
}


}
