package com.example.turkuvazoyun;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class TurkuvazOyun extends ApplicationAdapter {

	SpriteBatch batch; // Batch spirete çizmek için yardımcı olan metot ve objeler Bunun vesilesiyle herşeyi çizebiliriz.
	Texture background; // Imagenin patentini aldığı yer
	Texture birds;
	Texture bee; // Arıları oluştur
	Texture bee2; // Arıları oluştur
	Texture bee3; // Arıları oluştur
	float birdX = 0;
	float birdY = 0;
	int gameState = 0; // Oyunun başlayıp başlamadığını belirlemek için bir değişken atıyoruz
	float veloCity= 0f;
	float gravity = 0.1f;
	int numberOfEnemies = 4;
	float [] enemiesX = new float[numberOfEnemies];
	float enemyVelocity = 2;
	float distance = 0 ;
	float [] enemyOfSet = new float[numberOfEnemies];
	float [] enemyOfSet2 = new float[numberOfEnemies];
	float [] enemyOfSet3 = new float[numberOfEnemies];
	Random random;
	Circle birdCircle;
	Circle [] enemyCircles;
	Circle [] enemyCircles2;
	Circle [] enemyCircles3;
	//ShapeRenderer shapeRenderer;
	int score = 0;
	int scoredEnemy = 0;
	BitmapFont font;
	BitmapFont font2;

	/**
	 *
	 * collagen detection = ?
	 *
	 */

	@Override
	public void create () {
		// Oyun başladığında ne olucaksa buraya yazılır.ınitialize kısmı burada yapılmalı
		batch = new SpriteBatch();
		background = new Texture("background.png"); // Texture Initialize edildiği yer.Burada nereden yaratıldığını soracak onu veriyoruz.
		birds = new Texture("bird.png");
		bee = new Texture("bee.png");
		bee2 = new Texture("bee.png");
		bee3 = new Texture("bee.png");
		distance = Gdx.graphics.getWidth()/2;
		birdX = Gdx.graphics.getWidth()/2-birds.getHeight();
		birdY = Gdx.graphics.getHeight()/3;
		random = new Random();

		//shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();
		enemyCircles  = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(6);

		for (int i = 0; i<numberOfEnemies;i++) {

			enemyOfSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOfSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
			enemyOfSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

			enemiesX[i] =Gdx.graphics.getWidth() - (bee.getWidth()/2 + i*distance);

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}

	}

	@Override
	public void render () {

		// her türlü batch burada kullanılır.Oyun devam ettiği sürece çağırılan bir metot.Düşman giemesi vs vs
		batch.begin(); // Batch işlemi başlatıldı
		// Batch ile alakalı herşey bu araya yazılır
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		/*if (Gdx.input.justTouched()){
			gameState=1;
		}*/

		if (gameState==1) {

			if (enemiesX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - birds.getHeight() / 2) {
				score++;

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}

			}

			if (Gdx.input.justTouched()){
				veloCity = -7;
			}

			for (int i = 0 ;i<numberOfEnemies;i++){

				if (enemiesX[i]<0 ){

					enemiesX[i] = enemiesX[i] + numberOfEnemies*distance;

					enemyOfSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);


				} else {
					enemiesX[i] = enemiesX[i]-enemyVelocity;
				}

				batch.draw(bee,enemiesX[i],Gdx.graphics.getHeight()/2+enemyOfSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getWidth()/15);
				batch.draw(bee2,enemiesX[i],Gdx.graphics.getHeight()/2+enemyOfSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getWidth()/15);
				batch.draw(bee3,enemiesX[i],Gdx.graphics.getHeight()/2+enemyOfSet3[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getWidth()/15);

				enemyCircles[i] = new Circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles2[i] = new Circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
				enemyCircles3[i] = new Circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			}

			if (birdY > 0 ) {
				veloCity = veloCity+gravity;
				birdY = birdY-veloCity;
			} else {
				gameState=2;
			}

		} else if (gameState==0){
			if (Gdx.input.justTouched()){
				gameState=1;
			}
		} else if (gameState == 2){

			font2.draw(batch,"Game Over! Tap To Play Again!",100,Gdx.graphics.getHeight() / 2);

			if (Gdx.input.justTouched()){
				gameState=1;

				birdY = Gdx.graphics.getHeight()/3;

				for (int i = 0; i<numberOfEnemies;i++) {

					enemyOfSet[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet2[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);
					enemyOfSet3[i] = (random.nextFloat()-0.5f)*(Gdx.graphics.getHeight()-200);

					enemiesX[i] =Gdx.graphics.getWidth() - (bee.getWidth()/2 + i*distance);

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				veloCity = 0;

			}
		}

		batch.draw(birds,birdX,birdY,Gdx.graphics.getWidth()/15,Gdx.graphics.getWidth()/15);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end(); //  Batch'in bittiği yer

		birdCircle.set(birdX + Gdx.graphics.getWidth()/30,birdY+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for (int i = 0;i<numberOfEnemies;i++){
			//shapeRenderer.circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet2[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);
			//shapeRenderer.circle(enemiesX[i]+Gdx.graphics.getWidth()/30,Gdx.graphics.getHeight()/2+enemyOfSet3[i]+Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth()/30);

			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i]) ){
				gameState = 2;
			}

		}
		//shapeRenderer.end();

	}
	
	@Override
	public void dispose () {

	}
}
