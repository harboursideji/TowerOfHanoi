package com.jixin.towerofhanoi;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jixin.towerofhanoi.RecordDBHelper.RecordDao;

public class GameActivity extends AppCompatActivity {


    private SharedPreferences sp;

    private static int moveAnimationDuration=2000;
    private static int alphaAnimationDuration=1000;

    private static int dishHeight=60;

    private ImageView holdDishView;
    private LinearLayout gameView;
    private TextView levelText;
    private Button btn_Exit;
    private Button btn_Restart;
    private LinearLayout targetLayout;

    private HanoiTower hanoiTowerA;
    private HanoiTower hanoiTowerB;
    private HanoiTower hanoiTowerC;

    private RoundDish roundDish;
    private boolean isHold;
    private int screenWidth;
    private int screenHeight;
    private int hanoiLevel;
    private int roundDishId;
    private int[] layoutY;
    private int[] layoutX;

    private RecordDao recordDao;

    private long startTime;

    private final int[] colors={Color.RED,Color.GREEN,Color.BLUE, Color.YELLOW,Color.BLACK};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameview);
        int gameLevel=getIntent().getIntExtra("GameLevel",Constants.Default_Level);
        setLevel(gameLevel);
        sp=GameActivity.this.getSharedPreferences(Constants.SharedPreferenceName,Context.MODE_PRIVATE);
        recordDao=new RecordDao(GameActivity.this);
        isHold=false;
        initView();
        initTower();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasFocus);
        for(int i=0;i<hanoiLevel;i++){
            layoutY[i]= (int) gameView.findViewById(i).getY();
            Log.d("Jix", i+" is"+layoutY[i]);
        }
    }

    private void initView(){
        WindowManager windowManager=(WindowManager) GameActivity.this.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics=new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        screenWidth=displayMetrics.widthPixels;
        screenHeight=displayMetrics.heightPixels;
        holdDishView=findViewById(R.id.iv_holdDish);
        btn_Exit=findViewById(R.id.btn_exit);
        btn_Restart=findViewById(R.id.btn_restart);
        gameView=(LinearLayout) findViewById(R.id.view_game);
        levelText=findViewById(R.id.tv_level);
        btn_Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitDialog();
            }
        });
        btn_Restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartDialog();
            }
        });
        levelText.setText(getResources().getString(R.string.no)+(hanoiLevel-2));
        levelText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gameFinish();
            }
        });
        hanoiTowerA=new HanoiTower();
        hanoiTowerB=new HanoiTower();
        hanoiTowerC=new HanoiTower();
    }

    private void initTower(){
        LinearLayout.LayoutParams baseParams= (LinearLayout.LayoutParams) gameView.getLayoutParams();
        baseParams.leftMargin=0;
        baseParams.rightMargin=0;
        baseParams.width=screenWidth;
        baseParams.height=screenHeight;
        gameView.setLayoutParams(baseParams);
        layoutX=new int[3];
        layoutY=new int[hanoiLevel];
        for(int i=0;i<hanoiLevel;i++){
            LinearLayout itemLayout=(LinearLayout) LayoutInflater.from(GameActivity.this).inflate(R.layout.item_dish,null);
            itemLayout.setId(i);
            itemLayout.setBackgroundColor(colors[i%colors.length]);
            LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(screenWidth/3-(hanoiLevel-i)*20,dishHeight);
            params.leftMargin=(hanoiLevel-i)*10;
            itemLayout.setLayoutParams(params);
            layoutX[0]= (int) itemLayout.getX();
            layoutX[1]=layoutX[0]+screenWidth/3;
            layoutX[2]=layoutX[0]+2*screenWidth/3;
            gameView.addView(itemLayout);
        }

        for(int i=hanoiLevel;i>0;i--){
            hanoiTowerA.push(new RoundDish(i+2));
        }
        startTime=System.currentTimeMillis();

    }

    private void removeDish(int target){
        targetLayout=gameView.findViewById(target);
        ValueAnimator animator1=null;
        animator1= ObjectAnimator.ofFloat(targetLayout,"y",targetLayout.getY(),165);
        animator1.setDuration(moveAnimationDuration);
        animator1.start();
    }

    @SuppressLint("ResourceType")
    private void moveDish(int target){

        ValueAnimator animator2=null;
        animator2=ObjectAnimator.ofFloat(targetLayout,"x",targetLayout.getX(),layoutX[target]+(hanoiLevel-targetLayout.getId())*10);
        animator2.setDuration(moveAnimationDuration);
        ValueAnimator animator3=null;
        if(target==1){
            animator3=ObjectAnimator.ofFloat(targetLayout,"y",targetLayout.getY(),layoutY[layoutY.length-1]-(hanoiTowerB.getSize()-1)*dishHeight);
        }else if(target==2){
            animator3=ObjectAnimator.ofFloat(targetLayout,"y",targetLayout.getY(),layoutY[layoutY.length-1]-(hanoiTowerC.getSize()-1)*dishHeight);
        }else {
            animator3=ObjectAnimator.ofFloat(targetLayout,"y",targetLayout.getY(),layoutY[layoutY.length-1]-(hanoiTowerA.getSize()-1)*dishHeight);
        }

        animator3.setDuration(moveAnimationDuration);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playSequentially(animator2,animator3);
        animatorSet.start();
    }


    public void setLevel(int i){
        hanoiLevel=i;
    }


    private void sendError(){
        Toast.makeText(GameActivity.this,R.string.error,Toast.LENGTH_LONG).show();
    }

    private void reportError(){
        Toast.makeText(GameActivity.this,R.string.report_error,Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction()==MotionEvent.ACTION_UP){
            int pointX= (int) event.getX();
            int pointY= (int) event.getY();//
            if(!isHold){
                if (pointX<screenWidth/3){  //TowerA
                    if (hanoiTowerA.getSize()!=0){
                        roundDish=hanoiTowerA.getTop();
                        roundDishId=roundDish.getDishSize()-3;
                        removeDish(roundDishId);
                    }else{
                        sendError();
                        return true;
                    }
                }else if(pointX<2*screenWidth/3){       //TowerB
                    if(hanoiTowerB.getSize()==0){
                        sendError();
                        return true;
                    }else {
                        roundDish=hanoiTowerB.getTop();
                        roundDishId=roundDish.getDishSize()-3;
                        removeDish(roundDishId);
                    }

                }else {                     //TowerC
                    if(hanoiTowerC.getSize()!=0){
                        roundDish=hanoiTowerC.getTop();
                        roundDishId=roundDish.getDishSize()-3;
                        removeDish(roundDishId);
                    }else{
                        sendError();
                        return true;
                    }
                }
                isHold=true;
                holdDishView.setBackgroundColor(Color.RED);
                return true;
            }else{
                if(pointX<screenWidth/3){
                    if (hanoiTowerA.getSize()>0&&roundDish.getDishSize()>hanoiTowerA.getPeek().getDishSize()){
                        reportError();
                        return true;
                    }
                    hanoiTowerA.push(roundDish);
                    moveDish(0);
                    if(isFinish()){
                        gameFinish();
                    }
                }else if(pointX<2*screenWidth/3){
                    if (hanoiTowerB.getSize()>0&&roundDish.getDishSize()>hanoiTowerB.getPeek().getDishSize()){
                        reportError();
                        return true;
                    }
                    hanoiTowerB.push(roundDish);
                    moveDish(1);
                    if(isFinish()){
                        gameFinish();
                    }
                }else {
                    if (hanoiTowerC.getSize()>0&&roundDish.getDishSize()>hanoiTowerC.getPeek().getDishSize()){
                        reportError();
                        return true;
                    }
                    hanoiTowerC.push(roundDish);
                    moveDish(2);
                    if(isFinish()){
                        gameFinish();
                    }
                }
                isHold=false;
                holdDishView.setBackgroundColor(Color.WHITE);
                return true;
            }
        }
        return true;
    }

    private void gameFinish(){
        recordGrade();
        showDialog();
    }

    private void recordGrade(){
        long stopTime=System.currentTimeMillis();
        String duration=String.valueOf(stopTime-startTime);
        if(recordDao.find(sp.getString(Constants.UserId,Constants.Default_Id))){
            recordDao.update(sp.getString(Constants.UserId,Constants.Default_Id),duration);
        }else {
            recordDao.add(sp.getString(Constants.UserId,Constants.Default_Id),duration);
        }

    }

    private boolean isFinish(){
        if(hanoiTowerA.getSize()==0&&(hanoiTowerC.getSize()==0||hanoiTowerB.getSize()==0)){
            return true;
        }
        return false;
    }

    private void showDialog(){
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(GameActivity.this);
        alertDialog.setTitle(R.string.dialog_title);
        alertDialog.setMessage(R.string.dialog_message);
        alertDialog.setPositiveButton(R.string.dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(hanoiLevel<17){
                    Intent intent=new Intent();
                    intent.setClass(GameActivity.this,GameActivity.class);
                    intent.putExtra("GameLevel",hanoiLevel+1);
                    intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                    startActivity(intent);
                    finish();
                }else {
                    Intent intent=new Intent();
                    intent.setClass(GameActivity.this,RecordActivity.class);
                    startActivity(intent);
                }
            }
        });
        alertDialog.setNegativeButton(R.string.dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent=new Intent();
                intent.setClass(GameActivity.this,RecordActivity.class);
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    private void exitDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.exit_dialog_title);
        builder.setMessage(R.string.exit_dialog_message);
        builder.setNegativeButton(R.string.exit_dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.exit_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.show();
    }

    private void restartDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(GameActivity.this);
        builder.setTitle(R.string.restart_dialog_title);
        builder.setMessage(R.string.restart_dialog_message);
        builder.setNegativeButton(R.string.restart_dialog_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton(R.string.restart_dialog_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                for(int i=0;i<hanoiLevel;i++){
                    gameView.removeAllViews();
                }
                initView();
                initTower();
                isHold=false;
            }
        });
        builder.show();
    }


}
