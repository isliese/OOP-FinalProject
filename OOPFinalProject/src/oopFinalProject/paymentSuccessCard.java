package oopFinalProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class paymentSuccessCard extends JFrame {

    private JLabel instLabel; // 결제 지시 label 저장용 필드
    private Timer timer; // 타이머 필드
    private JFrame frame; // 프레임 필드

    public paymentSuccessCard() {
        frame = new JFrame("HOLLYS 키오스크");

        // 프레임 설정
        frame.setTitle("HOLLYS 키오스크"); // 프레임 타이틀
        Container c = frame.getContentPane(); // 컨텐트팬을 알아낸다
        c.setBackground(Color.white); // 배경 설정
        c.setLayout(new BorderLayout(30, 20));
        
        // 폰트 설정
        Font font = new Font("맑은 고딕", Font.BOLD, 15);

        // card_insertion.png 원본 이미지 아이콘 불러오기
        ImageIcon originalImage = new ImageIcon(getClass().getResource("/images/card_insertion.png"));
        Image resizedImage = originalImage.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH); // 원하는 크기로 이미지 아이콘 조절
        ImageIcon cardInsertion = new ImageIcon(resizedImage);

        // 이미지 레이블 설정
        JLabel cardimageLabel = new JLabel(cardInsertion);
        c.add(cardimageLabel, BorderLayout.CENTER);


        // 주문 완료 label 초기 설정
        String orderComplete = "<html><div style='text-align: center;'>주문이 완료되었습니다.<br>음료를 곧 준비해드릴게요.<br>5초 뒤 자동으로 홈 화면으로 이동합니다.</html>";
        instLabel = new JLabel(orderComplete, SwingConstants.CENTER);
        instLabel.setFont(font);
        c.add(instLabel, BorderLayout.SOUTH);

        // 타이머 설정 - 5초 후에 다른 페이지로 전환
        timer = new Timer(1000, new ActionListener() {
            int remainingTime = 5; // 초기 남은 시간 설정

            @Override
            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                if (remainingTime > 0) {
                    updateInstruction(remainingTime);
                } else {
                    timer.stop(); // 타이머 멈춤
                    frame.dispose(); // 현재 프레임 닫기
                    new HomePage(); // HomePage 페이지로 전환하는 코드
                }
            }
        });

        timer.start();

        frame.setSize(500, 800); // 창 크기 설정
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 창 닫기 설정
        frame.setVisible(true);
    }

    // 홈 화면 이동 label 업데이트 메서드
    private void updateInstruction(int remainingTime) {
        if (instLabel != null) {
            String text = "<html><div style='text-align: center;'>주문이 완료되었습니다.<br>음료를 곧 준비해드릴게요.<br>";
            text += remainingTime + "초 뒤 자동으로 홈 화면으로 이동합니다.</html>";
            instLabel.setText(text);
        }
    }

}
