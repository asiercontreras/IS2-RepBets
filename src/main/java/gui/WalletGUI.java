package gui;

import javax.swing.*;

import businessLogic.BLFacade;
import domain.Card;
import domain.User;
import exceptions.ObjectAlreadyExistException;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class WalletGUI extends JFrame {
	private JTextField textFieldAddMoney;
    private BLFacade facade = ApplicationLauncher.getBusinessLogic();
	private User usr = facade.getCurrentUser();
	private JTextField CardNumberTextField;
	private JTextField vccField;
	private JComboBox<Card> cardBox;
    private float money;
    private Font font = new Font("Dialog", Font.BOLD, 16);
    private Font font1 = new Font("Dialog", Font.BOLD, 12);

	public WalletGUI() {
		this.setSize(new Dimension(500, 380));
        this.getContentPane().removeAll();
        this.getContentPane().add(getWalletPanel());
        this.getContentPane().revalidate();
        this.getContentPane().repaint();
	}

	private void close(ActionEvent e) {
		this.setVisible(false);
	}

    private JPanel getWalletPanel(){
        JPanel panel = new JPanel();
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Wallet"));
		panel.setLayout(null);
		this.setSize(new Dimension(500, 380));

		JLabel lblWallet = new JLabel();
		lblWallet.setFont(font);
		lblWallet.setBounds(51, 39, 270, 54);
		panel.add(lblWallet);
		lblWallet.setText(ResourceBundle.getBundle("Etiquetas").getString("WalletMoney")+" " + usr.getWallet());

		JLabel lblDoYouWant = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddMoney"));
		lblDoYouWant.setFont(font);
		lblDoYouWant.setBounds(49, 105, 307, 15);
		panel.add(lblDoYouWant);

		textFieldAddMoney = new JTextField();
		textFieldAddMoney.setBounds(148, 132, 162, 36);
		textFieldAddMoney.setBorder(BorderFactory.createEmptyBorder());
		textFieldAddMoney.setColumns(10);
		textFieldAddMoney.setForeground(new Color(190, 190, 190));
		textFieldAddMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyInEuros"));
		textFieldAddMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldAddMoney.setText("");
				textFieldAddMoney.setForeground(new Color(0, 0, 0));

			}
		});
		textFieldAddMoney.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if (textFieldAddMoney.getText().isEmpty()) {
					textFieldAddMoney.setForeground(new Color(190, 190, 190));
					textFieldAddMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyInEuros"));
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if (textFieldAddMoney.getText().isEmpty()) {
					textFieldAddMoney.setForeground(new Color(190, 190, 190));

					textFieldAddMoney.setText(ResourceBundle.getBundle("Etiquetas").getString("MoneyInEuros"));
				}
			}
		});
		panel.add(textFieldAddMoney);
		
		JLabel lblError = new JLabel();
		lblError.setFont(font1);
		lblError.setBounds(69, 175, 300, 20);
		lblError.setForeground(new Color(255, 0, 0));
		panel.add(lblError);

		JButton btnAddMoney = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddMoney1"));
		btnAddMoney.setBounds(69, 195, 125, 36);
		btnAddMoney.setBorder(BorderFactory.createEmptyBorder());
		btnAddMoney.setBackground(new Color(180, 180, 180));
		btnAddMoney.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnAddMoney.setBackground(new Color(70, 130, 180));
				btnAddMoney.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnAddMoney.setBackground(new Color(180, 180, 180));
			}
		});

		btnAddMoney.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					money = Float.parseFloat(textFieldAddMoney.getText());
                    getContentPane().removeAll();
                    getContentPane().add(getPaymentPanel());
                    getContentPane().revalidate();
                    getContentPane().repaint();
				} catch (IllegalArgumentException e1) {
					lblError.setForeground(new Color(255, 0, 0));
					lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("Thefieldmustbeanumber"));
				}
			}
		});
		panel.add(btnAddMoney);

		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(231, 195, 125, 36);

		btnClose.setBorder(BorderFactory.createEmptyBorder());
		btnClose.setBackground(new Color(180, 180, 180));
		btnClose.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				btnClose.setBackground(new Color(70, 130, 180));
				btnClose.setFocusPainted(false);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				btnClose.setBackground(new Color(180, 180, 180));
			}
		});

		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close(e);
			}
		});
		panel.add(btnClose);
        return panel;
    }

    private JPanel getPaymentPanel(){
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CompletePayment"));
		this.setSize(new Dimension(666, 502));
        JPanel panel = new JPanel();
        panel.setLayout(null);

        JLabel lblCardNumber = new JLabel();
        lblCardNumber.setFont(font);
        lblCardNumber.setBounds(85, 206, 168, 54);
        lblCardNumber.setText(ResourceBundle.getBundle("Etiquetas").getString("CardNumber"));
        panel.add(lblCardNumber);

        JLabel lblDoYouWant = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("CVV"));
        lblDoYouWant.setFont(font);
        lblDoYouWant.setBounds(83, 272, 145, 15);
        panel.add(lblDoYouWant);

        JLabel lblError = new JLabel();
        lblError.setFont(font1);
        lblError.setBounds(174, 350, 300, 20);
        lblError.setForeground(new Color(255, 0, 0));
        panel.add(lblError);

        JButton btnCompletePayment = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CompletePayment"));
        btnCompletePayment.setBounds(320, 379, 230, 36);
        panel.add(btnCompletePayment);

        JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
        btnClose.setBounds(165, 379, 125, 36);

        btnClose.setBorder(BorderFactory.createEmptyBorder());
        btnClose.setBackground(new Color(180, 180, 180));
        btnClose.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnClose.setBackground(new Color(70, 130, 180));
                btnClose.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnClose.setBackground(new Color(180, 180, 180));
            }
        });

        btnClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                getContentPane().add(getWalletPanel());
                getContentPane().revalidate();
                getContentPane().repaint();
            }
        });
        panel.add(btnClose);

        CardNumberTextField = new JTextField();
        CardNumberTextField.setToolTipText("");
        CardNumberTextField.setBounds(271, 217, 307, 36);
        CardNumberTextField.setBorder(BorderFactory.createEmptyBorder());
        panel.add(CardNumberTextField);
        CardNumberTextField.setColumns(10);

        vccField = new JTextField();
        vccField.setColumns(10);
        vccField.setBorder(BorderFactory.createEmptyBorder());
        vccField.setBounds(271, 263, 307, 36);
        panel.add(vccField);

      /*  JLabel lblMoneyToPay = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectSaved")+" " + money);
        lblMoneyToPay.setFont(new Font("Dialog", Font.BOLD, 16));
        lblMoneyToPay.setBounds(85, 322, 300, 32);
        panel.add(lblMoneyToPay);*/

      JLabel lblSelectCard = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectSaved"));
        lblSelectCard.setFont(font);
        lblSelectCard.setBounds(85, 91, 340, 15);
        panel.add(lblSelectCard);

        cardBox = new JComboBox<>();
        cardBox.setBounds(85, 117, 493, 35);
        cardBox.addItem(null);
        cardBox.setBackground(Color.WHITE);
        // cardBox.setSize(WIDTH, 40);
        cardBox.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        cardBox.setFont(new Font("Arial", Font.PLAIN, 16));
        for (Card c : usr.getSavedCards()) {
            cardBox.addItem(c);
        }
        cardBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if(cardBox.getSelectedItem() != null){
                    CardNumberTextField.setEnabled(false);
                    vccField.setEnabled(false);
                }else{
                    CardNumberTextField.setEnabled(true);
                    vccField.setEnabled(true);
                }
            }
        });
        panel.add(cardBox);

        JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("AddACard"));
        lblNewLabel.setFont(font);
        lblNewLabel.setBounds(84, 179, 278, 15);
        panel.add(lblNewLabel);

        JLabel lblKutxa = new JLabel("");
        lblKutxa.setHorizontalAlignment(SwingConstants.RIGHT);
        lblKutxa.setVerticalAlignment(SwingConstants.TOP);
        Image img = new ImageIcon(this.getClass().getResource("/2560px-Kutxabank.svg.png")).getImage();
        Image newimg = img.getScaledInstance(140, 70, java.awt.Image.SCALE_SMOOTH);
        lblKutxa.setIcon(new ImageIcon(newimg));
        lblKutxa.setBounds(372, 20, 206, 94);
        panel.add(lblKutxa);

        btnCompletePayment.setBorder(BorderFactory.createEmptyBorder());
        btnCompletePayment.setBackground(new Color(180, 180, 180));
        btnCompletePayment.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnCompletePayment.setBackground(new Color(70, 130, 180));
                btnCompletePayment.setFocusPainted(false);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnCompletePayment.setBackground(new Color(180, 180, 180));
            }
        });
        btnCompletePayment.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                lblError.setForeground(new Color(255, 0, 0));
                try {
                    String strNumber = CardNumberTextField.getText();
                    String strCvv = vccField.getText();
                    Card card = (Card) cardBox.getSelectedItem();
                    if ((strNumber.length() != 16 || strCvv.length() != 3) && card == null) {
                        lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("CardNumber"));
                        return;
                    }
                    if (card == null) {
                        long num = Long.parseLong(strNumber);
                        int cvv = Integer.parseInt(strCvv);

                        try{
                            usr.addCard(facade.addCard(num, cvv));
                        }catch(NoSuchAlgorithmException e1){
                            lblError.setText(e1.getMessage());
                        }
                    }
                    usr.setWallet(facade.addMoney(money));
                    facade.setCurrentUser(usr);
                    JOptionPane.showMessageDialog(null,ResourceBundle.getBundle("Etiquetas").getString("PaymentDone")+" "+usr.getWallet()+"€");
                    
                    getContentPane().removeAll();
                    getContentPane().add(getWalletPanel());
                    getContentPane().revalidate();
                    getContentPane().repaint();
                } catch (IllegalArgumentException e1) {
                    lblError.setText(ResourceBundle.getBundle("Etiquetas").getString("CardNumber"));
                } catch (ObjectAlreadyExistException e1) {
                    lblError.setText(e1.getMessage());
                }

            }
        });
        return panel;
    }
}
