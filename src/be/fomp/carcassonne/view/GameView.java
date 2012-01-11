package be.fomp.carcassonne.view;

import java.awt.Color;



public interface GameView extends View{
	
	int WINDOW_W = 1200;
	int WINDOW_H = 800;
	
	int MENU_H = 30;
	int TILE_W = 90;
	int TILE_H = 90;
	
	int GAME_AREA_W = 600;
	int GAME_AREA_H = 600;
	int GAME_AREA_X = WINDOW_W - GAME_AREA_W - 50;
	int GAME_AREA_Y = WINDOW_H - GAME_AREA_H - 50;
	
	int SCORE_AREA_W = 350;
	int SCORE_AREA_H = 250;
	int SCORE_AREA_X = 10;
	int SCORE_AREA_Y = 100;
	
	int CONTROL_AREA_W = 450;
	int CONTROL_AREA_H = 350;
	int CONTROL_AREA_X = SCORE_AREA_X;
	int CONTROL_AREA_Y = SCORE_AREA_H + SCORE_AREA_Y + 20;
	
	Color TILE_BACKGROUND = Color.BLACK;
}
