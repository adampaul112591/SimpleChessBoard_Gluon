/*
 SimpleChessBoard : just play the moves of a chess game.
 Copyright (C) 2016  Laurent Bernabe

 This program is free software: you can redistribute it and/or modify
 it under the terms of the GNU General Public License as published by
 the Free Software Foundation, either version 3 of the License, or
 (at your option) any later version.

 This program is distributed in the hope that it will be useful,
 but WITHOUT ANY WARRANTY; without even the implied warranty of
 MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 GNU General Public License for more details.

 You should have received a copy of the GNU General Public License
 along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.loloof64.gluon.simple_chess_board;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * The board component class.
 */
class BoardComponent extends Canvas {

    private static final int [] RANKS = new int[]{0,1,2,3,4,5,6,7};
    private static final int [] FILES = new int[]{0,1,2,3,4,5,6,7};

    private static final char [] RANKS_COORDS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
    private static final char [] FILES_COORDS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    BoardComponent(double cellsSize){
        super(9*cellsSize, 9*cellsSize);
        this.cellsSize = cellsSize;

        draw();
    }

    private void draw(){
        drawBackground();
        drawCells();
        drawCoords();
        drawPlayerTurn();
    }

    private void drawBackground(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.setFill(Color.BLUEVIOLET);
        gc.fillRect(0.0, 0.0, 9*cellsSize, 9*cellsSize);
    }

    private void drawCells(){
        GraphicsContext gc = getGraphicsContext2D();
        for (int rank: RANKS){
            for (int file: FILES){
                final boolean isWhiteCell = (file+rank)%2 != 0;
                final double cellX = 0.5*cellsSize + file*cellsSize;
                final double cellY = 0.5*cellsSize + (7-rank)*cellsSize;
                final Color cellColor = isWhiteCell ? Color.LIGHTGOLDENRODYELLOW : Color.SADDLEBROWN;

                gc.setFill(cellColor);
                gc.fillRect(cellX, cellY, cellsSize, cellsSize);
            }
        }
    }

    private void drawCoords(){
        GraphicsContext gc = getGraphicsContext2D();
        final int fontSize = (int) (cellsSize * 0.7);
        final Font font = Font.font(null, FontWeight.BOLD, fontSize);

        gc.setFont(font);
        gc.setFill(Color.BLUE);
        for (int rank: RANKS){
            final char currentCoordChar = RANKS_COORDS[rank];
            final double x1 = 0.045*cellsSize;
            final double x2 = 8.545*cellsSize;
            final double y = 1.2*cellsSize + (7-rank)*cellsSize;

            gc.fillText(String.format("%c", currentCoordChar), x1, y);
            gc.fillText(String.format("%c", currentCoordChar), x2, y);
        }
        for (int file: FILES){
            final char currentCoordChar = FILES_COORDS[file];
            final double x = 0.745*cellsSize + file*cellsSize;
            final double y1 = 0.5*cellsSize;
            final double y2 = 9.0*cellsSize;

            gc.fillText(String.format("%c", currentCoordChar), x, y1);
            gc.fillText(String.format("%c", currentCoordChar), x, y2);
        }
    }

    private void drawPlayerTurn(){
        GraphicsContext gc = getGraphicsContext2D();
        final double x = 8.5*cellsSize;
        final double y = 8.5*cellsSize;

        final boolean whiteTurn = true;
        final Color turnColor = whiteTurn ? Color.WHITE : Color.BLACK;
        gc.setFill(turnColor);
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(0.028*cellsSize);
        gc.fillRect(x, y, cellsSize*0.5, cellsSize*0.5);
        gc.rect(x, y, cellsSize*0.5, cellsSize*0.5);
        gc.stroke();
    }

    private double cellsSize;

}
