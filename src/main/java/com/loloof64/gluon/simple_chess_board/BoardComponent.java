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

import com.alonsoruibal.chess.Board;
import com.alonsoruibal.chess.bitboard.BitboardUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.transform.Scale;

import java.io.IOException;
import java.util.HashMap;

/**
 * The board component class.
 */
class BoardComponent extends Canvas {

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
        drawPieces();
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
            final char currentCoordChar = RANKS_COORDS_CHARS[rank];
            final double x1 = 0.045*cellsSize;
            final double x2 = 8.545*cellsSize;
            final double y = 1.2*cellsSize + (7-rank)*cellsSize;

            gc.fillText(String.format("%c", currentCoordChar), x1, y);
            gc.fillText(String.format("%c", currentCoordChar), x2, y);
        }
        for (int file: FILES){
            final char currentCoordChar = FILES_COORDS_CHARS[file];
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

    private void drawPieces(){
        Board board = BoardManager.getInstance().getBoard();
        for (int rank: RANKS){
            for (int file: FILES){
                String algebraicCoords = String.format("%c%c", 'a'+file, '1'+rank);
                char pieceAtThisCell = board.getPieceAt(BitboardUtils.algebraic2Square(algebraicCoords));
                boolean thereIsAPieceThere = fenToPiecePictureRef.containsKey(pieceAtThisCell);
                if (thereIsAPieceThere){
                    drawPieceAt(pieceAtThisCell, file, rank);
                }
            }
        }
    }

    /**
     * Draws a piece at a particular cell.
     * @param pieceFen - char - Forsyth-Edwards Notation of the piece.
     * @param cellBigX - int - the cell coordinate : in [0,7], where 0 stands for file 'A'.
     * @param cellBigY - int - the cell coordinate : in [0,7], where 0 stands for rank '1'.
     */
    private void drawPieceAt(char pieceFen, int cellBigX, int cellBigY){
        GraphicsContext gc = getGraphicsContext2D();
        if (!fenToPiecePictureRef.containsKey(pieceFen)) return;
        final String pieceImageRef = fenToPiecePictureRef.get(pieceFen);
        try {
            final Node pieceSvg = FXMLLoader.load(getClass().getClassLoader().getResource(String.format("%s.fxml", pieceImageRef)));
            final SnapshotParameters snapshotParameters = new SnapshotParameters();
            final double pieceImageRatio = cellsSize * 1.0 / PIECE_IMAGES_SIZE;
            snapshotParameters.setTransform(new Scale(pieceImageRatio, pieceImageRatio));
            snapshotParameters.setFill(Color.TRANSPARENT);
            final Image pieceImage = pieceSvg.snapshot(snapshotParameters, null);
            final double x = 0.60*cellsSize + cellBigX*cellsSize;
            final double y = 0.60*cellsSize + (7-cellBigY)*cellsSize;

            gc.drawImage(pieceImage, x, y);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final int PIECE_IMAGES_SIZE = 45;

    private static final int [] RANKS = new int[]{0,1,2,3,4,5,6,7};
    private static final int [] FILES = new int[]{0,1,2,3,4,5,6,7};

    private static final char [] RANKS_COORDS_CHARS = new char[]{'1', '2', '3', '4', '5', '6', '7', '8'};
    private static final char [] FILES_COORDS_CHARS = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

    private static final HashMap<Character, String> fenToPiecePictureRef;

    static {
        fenToPiecePictureRef = new HashMap<>(12);
        fenToPiecePictureRef.put('P', "pl");
        fenToPiecePictureRef.put('N', "nl");
        fenToPiecePictureRef.put('B', "bl");
        fenToPiecePictureRef.put('R', "rl");
        fenToPiecePictureRef.put('Q', "ql");
        fenToPiecePictureRef.put('K', "kl");

        fenToPiecePictureRef.put('p', "pd");
        fenToPiecePictureRef.put('n', "nd");
        fenToPiecePictureRef.put('b', "bd");
        fenToPiecePictureRef.put('r', "rd");
        fenToPiecePictureRef.put('q', "qd");
        fenToPiecePictureRef.put('k', "kd");
    }

    private double cellsSize;
}
