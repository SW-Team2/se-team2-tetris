package graphics.screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.BorderLayout;


import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import data.setting.SettingInfoDesc;



public class KeySettingMenu extends JPanel {
    
    public class Key{
        public static int mMoveRight=KeyEvent.VK_RIGHT;
        public static int mMoveLeft=KeyEvent.VK_LEFT;
        public static int mMoveDown=KeyEvent.VK_DOWN;
        public static int mMoveToFloor=KeyEvent.VK_UP;
        public static int mRotate=KeyEvent.VK_SPACE;
        public static int mPause=KeyEvent.VK_P;
    }

    private static KeySettingMenu mUniqueInstance = null;

    private KeySettingMenu() {
        SettingInfoDesc sd=SettingInfoDesc.getInstance();

        JPanel btnPanel=new JPanel();
        JPanel btnPanel2=new JPanel();
        JButton[] btn;
        JButton[] btn2;
        JLabel label=new JLabel("Press Enter to change key!");


        btn=new JButton[6];
        btn2=new JButton[6];
        this.setLayout(new BorderLayout());
        btnPanel.setLayout(new BoxLayout(btnPanel, BoxLayout.Y_AXIS));
        btnPanel2.setLayout(new BoxLayout(btnPanel2, BoxLayout.Y_AXIS));

        //설정하기 버튼
        btn[0]=new JButton("LEFT");
        btn[1]=new JButton("RIGHT");
        btn[2]=new JButton("DOWN");
        btn[3]=new JButton("SPIN");
        btn[4]=new JButton("MOVE TO FLOOR");
        btn[5]=new JButton("PAUSE");
        
        //현재 설정된 버튼
        btn2[0]=new JButton(AsciiToString.toString(sd.mKey.mGame.mMoveLeft));
        btn2[1]=new JButton(AsciiToString.toString(sd.mKey.mGame.mMoveRight));
        btn2[2]=new JButton(AsciiToString.toString(sd.mKey.mGame.mMoveDown));
        btn2[3]=new JButton(AsciiToString.toString(sd.mKey.mGame.mRotate));
        btn2[4]=new JButton(AsciiToString.toString(sd.mKey.mGame.mMoveToFloor));
        btn2[5]=new JButton(AsciiToString.toString(sd.mKey.mGame.mPause));


        //왼쪽 버튼
        btnPanel.add(btn[0]);
        btnPanel.add(btn[1]);
        btnPanel.add(btn[2]);
        btnPanel.add(btn[3]);
        btnPanel.add(btn[4]);
        btnPanel.add(btn[5]);

        //오른쪽 버튼
        btnPanel2.add(btn2[0]);
        btnPanel2.add(btn2[1]);
        btnPanel2.add(btn2[2]);
        btnPanel2.add(btn2[3]);
        btnPanel2.add(btn2[4]);
        btnPanel2.add(btn2[5]);



        //상하,엔터 를 통해 메뉴버튼을 조작
        for(int i=0; i<btn.length; i++){
            int curRow = i;
            btn[i].addKeyListener(enter);
            btn[i].addKeyListener(new KeyAdapter(){
                @Override
                public void keyPressed(KeyEvent e) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP:
                            if (curRow > 0)
                                btn[curRow - 1].requestFocus();
                            break;
                        case KeyEvent.VK_DOWN:
                            if (curRow < btn.length - 1)
                                btn[curRow + 1].requestFocus();
                            break;
                        default:
                            break;
                    }
                }
            });
        }

        //MoveLeft
        btn[0].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("LEFT 설정중..");
                btn2[0].requestFocus();
            }
            
        });
        btn2[0].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mMoveLeft=e.getKeyCode();
                btn2[0].setText(AsciiToString.toString(sd.mKey.mGame.mMoveLeft));
                label.setText("LEFT 설정 완료!");
                btn[0].requestFocus();
            }
        });

        //MoveRight
        btn[1].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("RIGHT 설정중..");
                btn2[1].requestFocus();
            }
            
        });
        btn2[1].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mMoveRight=e.getKeyCode();
                btn2[1].setText(AsciiToString.toString(sd.mKey.mGame.mMoveRight));
                label.setText("RIGHT 설정 완료!");
                btn[1].requestFocus();
            }
        });

        //MoveDown
        btn[2].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("DOWN 설정중..");
                btn2[2].requestFocus();
            }
            
        });
        btn2[2].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mMoveDown=e.getKeyCode();
                btn2[2].setText(AsciiToString.toString(sd.mKey.mGame.mMoveDown));
                label.setText("DOWN 설정 완료!");
                btn[2].requestFocus();
            }
        });

        //Rotate
        btn[3].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("SPIN 설정중..");
                btn2[3].requestFocus();
            }
            
        });
        btn2[3].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mRotate=e.getKeyCode();
                btn2[3].setText(AsciiToString.toString(sd.mKey.mGame.mRotate));
                label.setText("SPIN 설정 완료!");
                btn[3].requestFocus();
            }
        });

        //MoveToFloor
        btn[4].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("MOVE TO FLOOR 설정중..");
                btn2[4].requestFocus();
            }
            
        });
        btn2[4].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mMoveToFloor=e.getKeyCode();
                btn2[4].setText(AsciiToString.toString(sd.mKey.mGame.mMoveToFloor));
                label.setText("MOVE TO FLOOR 설정 완료!");
                btn[4].requestFocus();
            }
        });

        //Pause
        btn[5].addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                label.setText("PAUSE 설정중..");
                btn2[5].requestFocus();
            }
            
        });
        btn2[5].addKeyListener(new KeyAdapter(){
            @Override
            public void keyPressed(KeyEvent e){
                sd.mKey.mGame.mPause=e.getKeyCode();
                btn2[5].setText(AsciiToString.toString(sd.mKey.mGame.mPause));
                label.setText("PAUSE 설정 완료!");
                btn[5].requestFocus();
            }
        });       

        this.add(btnPanel2,BorderLayout.EAST);
        this.add(btnPanel,BorderLayout.CENTER);
        this.add(label, BorderLayout.NORTH);     
    }

    private static KeyListener enter = new KeyAdapter() {
        @Override
        public void keyTyped(KeyEvent e) {
            if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                ((JButton) e.getComponent()).doClick();
            }
        }
    };

    private class AsciiToString{
        public static String toString(int input){
            if(input==37){
                return "LEFT";
            }else if(input==38){
                return "UP";
            }else if(input==39){
                return "RIGHT";
            }else if(input==40){
                return "DOWN";
            }else if(input==32){
                return "SPACE";
            }else return (char)input+"";
        }
    };

    public static KeySettingMenu getInstance() {
        if (mUniqueInstance == null) {
            mUniqueInstance = new KeySettingMenu();
        }
        return mUniqueInstance;
    }
}
