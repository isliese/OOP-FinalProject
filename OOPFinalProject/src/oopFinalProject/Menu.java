package menu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;





public class Menu {	
	
	private JFrame frame; 
	private JTable table; 
	private DefaultTableModel model;
	private JLabel totalLabel; 
	private int total= 0;
	
	private String[] coffeeNames= {"블랙아리아 아메리카노", "블랙아리아 딥라떼", "돌체라떼", "디카페인 아메리카노", "디카페인 카페라떼", "디카페인 바닐라딜라이트", "콜드브루 딜라이트", "콜드브루 라떼", "콜드 브루"}; // 메뉴명 
	private int[]  coffeePrices= {3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000, 3000}; // 가격 
	
	public Menu() { 
		frame= new JFrame("HOLLYS 키오스크"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setLayout(new BorderLayout());
	
		
		JTabbedPane tabbedPane= new JTabbedPane();
		JPanel coffeePanel= createMenuPanel(coffeeNames, coffeePrices, "coffee");
		
		tabbedPane.addTab("커피", coffeePanel); 
		frame.add(tabbedPane, BorderLayout.NORTH); 
		
		// 스크롤패너 
		JScrollPane scrollPane= new JScrollPane(tabbedPane); //스크롤 패너 추가 
		scrollPane.setPreferredSize(new Dimension(800, 600)); // 스크롤 패널의 선호 크기 설정
		frame.add(scrollPane, BorderLayout.CENTER); 
		
        frame.pack(); // 컴포넌트 크기에 맞춰 프레임 크기 자동 조절
        frame.setVisible(true);
    }


	
	
	
	private JPanel createMenuPanel(String[] names, int[] prices, String imagePrefix) {
	    JPanel panel = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new Insets(10, 10, 10, 10); // 여백 설정
	    
	    int itemsPerRow = 3; // 한 행에 표시할 아이템 개수
	    int rowCount = (int) Math.ceil((double) names.length / itemsPerRow); // 행 개수 계산

	    
	    int index = 0;
	    for (int row = 0; row < rowCount; row++) {
	        gbc.gridy = row;
	
	        for (int col = 0; col < itemsPerRow; col++) {
	            gbc.gridx = col;
	            
	            if (index < names.length) { // 범위를 벗어나지 않도록 수정
	                JPanel itemPanel = createMenuItem(names[index], prices[index], imagePrefix + index);
	                panel.add(itemPanel, gbc);
	                index++;
	            } else {
	                // 빈 공간을 채우기 위한 빈 JPanel 추가
	                panel.add(new JPanel(), gbc); 
	            }
	        }
	    }

	    return panel;
	}
	
	
	   private JPanel createMenuItem(String name, int price, String imageName) {
	        JPanel panel = new JPanel();
	        panel.setLayout(new GridBagLayout()); // GridBagLayout으로 변경
	        GridBagConstraints gbc = new GridBagConstraints();
	        gbc.insets = new Insets(5, 5, 5, 5);

	        // 이미지
	        JLabel imageLabel = new JLabel();
	        ImageIcon icon = new ImageIcon(new ImageIcon("images/" + imageName + ".png").getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH));
	        imageLabel.setIcon(icon);
	        gbc.gridx = 0;
	        gbc.gridy = 0;
	        gbc.gridwidth = 1;
	        gbc.weightx = 1.0; 
	        gbc.weighty = 0.8; 
	        gbc.anchor = GridBagConstraints.CENTER;
	        panel.add(imageLabel, gbc);

	        // 메뉴 이름과 가격
	        JLabel nameLabel = new JLabel("<html>" + name + "<br>" + price + "원</html>", SwingConstants.CENTER);
	        gbc.gridx = 0;
	        gbc.gridy = 1;
	        gbc.gridwidth = 1;
	        gbc.weighty = 0.2;
	        panel.add(nameLabel, gbc);
	        
	        // 컨트롤 패널 (수량 조절, 확인 버튼)
	        JPanel controlPanel = new JPanel();
	        controlPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
	        JButton minusButton = new JButton("-");
	        JTextField quantityField = new JTextField("0", 2);
	        quantityField.setEditable(false);
	        JButton plusButton = new JButton("+");
	        JButton confirmButton = new JButton("확인");

	        minusButton.addActionListener(e -> {
	            int count = Integer.parseInt(quantityField.getText());
	            if (count > 0) {
	                count--;
	                quantityField.setText(String.valueOf(count));
	            }
	        });

	        plusButton.addActionListener(e -> {
	            int count = Integer.parseInt(quantityField.getText());
	            count++;
	            quantityField.setText(String.valueOf(count));
	        });

	        confirmButton.addActionListener(e -> {
	            int count = Integer.parseInt(quantityField.getText());
	            if (count > 0) {
	                int sum = price * count;
	                total += sum;
	                totalLabel.setText("총 금액: " + total + "원");
	                model.addRow(new Object[]{name, price, count, sum});
	                quantityField.setText("0");
	            }
	        });
	        
	        // 컨트롤패너 버튼 
	        controlPanel.add(minusButton);
	        controlPanel.add(quantityField);
	        controlPanel.add(plusButton);
	        controlPanel.add(confirmButton);

	        gbc.gridx = 0;
	        gbc.gridy = 2;
	        gbc.gridwidth = 1;
	        gbc.weighty = 0;
	        panel.add(controlPanel, gbc);

	        return panel;
	    }

	  
	    public static void main(String[] args) {
	        new Menu();
	    }
	}

