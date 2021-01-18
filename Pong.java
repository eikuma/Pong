/**
 * Pong.java
 * @author 19266013 Esaki Ikuma
*/

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class Pong extends JPanel implements Runnable,KeyListener{
    static final int WORLD_W=500; //画面の横幅
    static final int WORLD_H=400; //画面の縦幅
    static final int BALL_R=10; //ボールの半径
    static final double dT=0.01; //刻み時間
    //ボールの位置
    private int x=WORLD_W/4;
    private int y=WORLD_H/2; 
    //パドルの位置
    private int x_paddle=420;
    private int y_paddle=WORLD_H/2-40;
    //ボールの速度
    private double dx=300.0;
    private double dy=300.0;
    //カーソルキーを判定する変数
    private boolean up=false;
    private boolean down=false;

    public Pong(){//コンストラクタ
        setOpaque(false);//背景の透明に
        setPreferredSize(new Dimension(WORLD_W,WORLD_H));//JPanelのサイズ指定
        setFocusable(true);//キーボードを受け付ける
        addKeyListener(this);//<-KeyEventのイベントリスナーを追加
    }

    public void run(){
        while(true){
            //跳ね返りの処理
            if(x<BALL_R || ((x==x_paddle - BALL_R)&&(y>y_paddle - BALL_R && y<y_paddle +80 + BALL_R))) dx*=-1.0;
            if(y<BALL_R || y>WORLD_H - BALL_R) dy*=-1.0;
            x+=dx*dT;
            y+=dy*dT;

            //右壁との接触で初期化
            if(x>WORLD_W - BALL_R){
                x=WORLD_W/4;
                y=WORLD_H/2;
                y_paddle=WORLD_H/2-40;
            }

            //上が押された時
            if(up){
                if(0<y_paddle){
                    y_paddle-=10;
                }
            }
            //下が押された時
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
        g.fillOval(x-BALL_R, y-BALL_R, BALL_R*2, BALL_R*2);//ボールの描画
        g.fillRect(x_paddle,y_paddle,5,80);//パドルの描写
    }

    public void keyPressed(KeyEvent e){//キーボードが押された
        int key = e.getKeyCode();
        switch(key){
            case KeyEvent.VK_UP:
            //上が押された時
                up=true;
                break;
            case KeyEvent.VK_DOWN:
            //下が押された時
                down=true;
                break;
            default:
                break;
        }
    }
    public void keyReleased(KeyEvent e){//キーボードから離された
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
        JFrame frame = new JFrame("Pong");//JFrameによるウィンドウの作成
        frame.getContentPane().setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//ウィンドウを閉じる処理
        Pong canvas = new Pong();//描画エリアの生成
        frame.add(canvas);//PongインスタンスをJFrameに貼りつけ
        frame.pack();//フレームサイズを包含物のサイズにより自動決定
        frame.setVisible(true);//JFrameを可視化
        new Thread(canvas).start();//Threadの開始
    }
}