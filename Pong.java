/**
 * Pong.java
 * @author 19266013 Esaki Ikuma
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Pong extends JPanel implements Runnable,KeyListener{
    static final int WORLD_W=500; //��ʂ̉���
    static final int WORLD_H=400; //��ʂ̏c��
    static final int BALL_R=10; //�{�[���̔��a
    static final double dT=0.01; //���ݎ���
    //�{�[���̈ʒu
    private int x=WORLD_W/4;
    private int y=WORLD_H/2; 
    //�p�h���̈ʒu
    private int x_paddle=420;
    private int y_paddle=WORLD_H/2-40;
    //�{�[���̑��x
    private double dx=300.0;
    private double dy=300.0;
    //�J�[�\���L�[�𔻒肷��ϐ�
    private boolean up=false;
    private boolean down=false;

    public Pong(){//�R���X�g���N�^
        setOpaque(false);//�w�i�̓�����
        setPreferredSize(new Dimension(WORLD_W,WORLD_H));//JPanel�̃T�C�Y�w��
        setFocusable(true);//�L�[�{�[�h���󂯕t����
        addKeyListener(this);//<-KeyEvent�̃C�x���g���X�i�[��ǉ�
    }

    public void run(){
        while(true){
            //���˕Ԃ�̏���
            if(x<BALL_R || ((x==x_paddle - BALL_R)&&(y>y_paddle - BALL_R && y<y_paddle +80 + BALL_R))) dx*=-1.0;
            if(y<BALL_R || y>WORLD_H - BALL_R) dy*=-1.0;
            x+=dx*dT;
            y+=dy*dT;

            //�E�ǂƂ̐ڐG�ŏ�����
            if(x>WORLD_W - BALL_R){
                x=WORLD_W/4;
                y=WORLD_H/2;
                y_paddle=WORLD_H/2-40;
            }

            //�オ�����ꂽ��
            if(up){
                if(0<y_paddle){
                    y_paddle-=10;
                }
            }
            //���������ꂽ��
            if(down){
                if(y_paddle+80<WORLD_H){
                    y_paddle+=10;
                }
            }
            repaint();
            try{Thread.sleep((int)(dT*1000));}
            catch(InterruptedException e){}
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.yellow);
        g.fillOval(x-BALL_R, y-BALL_R, BALL_R*2, BALL_R*2);//�{�[���̕`��
        g.fillRect(x_paddle,y_paddle,5,80);//�p�h���̕`��
    }

    public void keyPressed(KeyEvent e){//�L�[�{�[�h�������ꂽ
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
            //�オ�����ꂽ��
                up=true;
                break;
            case KeyEvent.VK_DOWN:
            //���������ꂽ��
                down=true;
                break;
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e){//�L�[�{�[�h���痣���ꂽ
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
                up=false;
                break;
            case KeyEvent.VK_DOWN:
                down=false;
                break;
            default:
                break;
        }
    };
    public void keyTyped(KeyEvent e){};

    public static void main(String[] args){
        JFrame frame = new JFrame("Pong");//JFrame�ɂ��E�B���h�E�̍쐬
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�E�B���h�E����鏈��
        Pong canvas = new Pong();//�`��G���A�̐���
        frame.add(canvas);//Pong�C���X�^���X��JFrame�ɓ\���
        frame.pack();//�t���[���T�C�Y���ܕ��̃T�C�Y�ɂ�莩������
        frame.setVisible(true);//JFrame������
        new Thread(canvas).start();//Thread�̊J�n
    }
}