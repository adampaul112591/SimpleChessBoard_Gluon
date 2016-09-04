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

/**
 * A singleton class that manage board state.
 */
class BoardManager {

    public static BoardManager getInstance(){
        return INSTANCE;
    }

    public Board getBoard(){
        return board;
    }

    private Board board;

    private BoardManager(){
        board = new Board();
        board.startPosition();
    }

    private static BoardManager INSTANCE = new BoardManager();

}
