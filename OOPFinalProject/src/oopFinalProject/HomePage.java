package oopFinalProject;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame {

    // 이미지 레이블 객체 생성
    ImageIcon hollysIcon = new ImageIcon(getClass().getResource("/images/hollys_logo.png"));
    Image img = hollysIcon.getImage();
    Image resizedImg = img.getScaledInstance(300, 300, Image.SCALE_SMOOTH); // 크기 조절 코드
    ImageIcon resizedIcon = new ImageIcon(resizedImg);
    JLabel imageLabel = new JLabel(resizedIcon);
    
    // 텍스트 객체 생성
    JLabel marketname = new JLabel("할리스 시립대 객체지향점", SwingConstants.CENTER);

    // 버튼 객체 생성
    JButton Herebtn = new JButton("매장");
    JButton Outbtn = new JButton("포장");
    
    // 폰트 객체 생성
    Font laFont = new Font("맑은 고딕", Font.BOLD, 15);
    Font btnFont = new Font("맑은 고딕", Font.BOLD, 20);
   

    public HomePage() {
    	
    	// set font
    	marketname.setFont(laFont);
    	Herebtn.setFont(btnFont); 
    	Outbtn.setFont(btnFont); 
        
    	setTitle("HOLLYS 키오스크"); // 프레임 타이틀

        Container c = getContentPane(); // 컨탠트팬을 알아낸다 
        c.setBackground(Color.white); // 배경 설정
        c.setLayout(new BorderLayout(30, 20)); // BorderLayout으로 배치 관리자 설정

        // 중앙에 이미지와 텍스트를 담을 패널 생성
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(Color.white);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0); // 이미지와 텍스트 사이의 간격

        // 이미지 부착
        centerPanel.add(imageLabel, gbc);

        // 텍스트 부착
        gbc.gridy = 1; // y값을 증가시켜서 텍스트를 이미지 아래로 이동함
        centerPanel.add(marketname, gbc);

        // 중앙 패널을 중앙에 배치
        c.add(centerPanel, BorderLayout.CENTER);

        // 버튼을 담을 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout()); // 버튼 사이의 간격 조절을 위해 GridBagLayout 사용
        GridBagConstraints gbcButton = new GridBagConstraints();
        gbcButton.gridx = 0;
        gbcButton.gridy = 0;
        gbcButton.insets = new Insets(0, 10, 0, 10); // 버튼 사이의 간격 설정

        // 버튼 크기 설정
        Dimension buttonSize = new Dimension(200, 100);
        Herebtn.setPreferredSize(buttonSize);
        Outbtn.setPreferredSize(buttonSize);

        // 버튼 부착
        buttonPanel.add(Herebtn, gbcButton);
        gbcButton.gridx = 1;
        buttonPanel.add(Outbtn, gbcButton);

        // 버튼 패널을 남쪽에 배치
        c.add(buttonPanel, BorderLayout.SOUTH);

        // Herebtn 버튼의 ActionListener 추가
        // HomePage 클래스 내에서
        Herebtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	
            	// Menu.java 페이지로 이동하는 코드
            	new Menu();
                setVisible(false);
            }
        });


        // Outbtn 버튼의 ActionListener 추가
        Outbtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
               
                 // Menu.java 페이지로 이동하는 코드
            	 new Menu();
                 setVisible(false);
            }
        });

        setSize(500, 800); // 창 크기 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 설정
        setVisible(true);
    }

    public static void main(String[] args) {
        new HomePage();
    }
}
