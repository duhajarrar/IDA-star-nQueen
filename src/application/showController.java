
package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import com.sun.javafx.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class showController {

	@FXML
	private AnchorPane p;

	@FXML
	private AnchorPane pp;

	@FXML
	private ImageView img;

	@FXML
	private TextField numQueen;

	@FXML
	private TextArea posQueens;
	@FXML
	private Button BtN;
	static int newCutOff = 0;
	static int s[];

	@FXML
	void solveBtn(ActionEvent event) throws IOException {
		int numQueens = Integer.parseInt(numQueen.getText());
		if (numQueens >= 4 && numQueens <= 10) {
			int table[] = new int[numQueens];
			String str = posQueens.getText();
			String numRead[] = str.split(",");
			if (numRead.length < numQueens) {
				System.out.println("ERROR , you put number of position less than number of Queens !!  ");
				Stage stage = new Stage();
				AnchorPane pane = FXMLLoader.load(getClass().getResource("notExtit.fxml"));
				Scene scene = new Scene(pane);
				stage.setScene(scene);
				stage.show();
			} else {
				for (int i = 0; i < numRead.length; i++) {
					if (Integer.parseInt(numRead[i]) < numQueens) {
						table[i] = Integer.parseInt(numRead[i]);
					} else {
						System.out.println("Error !!! ");
						Stage stage = new Stage();
						AnchorPane pane = FXMLLoader.load(getClass().getResource("error1.fxml"));
						Scene scene = new Scene(pane);

						stage.setScene(scene);
						stage.show();
					}
				}
				Queen q = new Queen(table, numQueens);
				Queen sol = IDAStar(q);
				System.out.println("SOLUTION :");
				Print(sol);
				System.out.println();
				Parent p=createBoard(sol,numQueens);
				Stage stage = new Stage();
				Scene scene = new Scene(p);

				stage.setScene(scene);
				stage.show();
			}
			
		  
		        
			
			
			
		} else {
			System.out.println("Solution Doesn't Exit");
			Stage stage = new Stage();
			AnchorPane pane = FXMLLoader.load(getClass().getResource("ERROR.fxml"));
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			stage.show();
		}
	}
	public Parent createBoard(Queen q,int n) {

	    GridPane gameBoard = new GridPane();
	    int[] a=new int [n];
	    System.arraycopy(q.getTable(),0,a,0,n);
	    for (int i = 0; i <n; i++) {
	        for (int j = 0; j < n; j++) {
	        	
	            Rectangle tile = new Rectangle(100, 100);
	            tile.setStroke(Color.BLACK);
	            tile.setFill(Color.WHITE);
	            Text text = new Text("Q");
	            text.setFont(Font.font(30));
	            Image img = new Image("crowns.png",70,70,false,true);
	            ImageView imgView = new ImageView(img);
	            if (a[i]==j)
	            	gameBoard.add(new StackPane(tile,imgView), i, j);
	            else
	            	gameBoard.add(new StackPane(tile), i,j);

	        
	        }
	    }
	    return gameBoard;
	}
	
	public static void Print(Queen q) {

		for (int i = 0; i < q.N; i++) {
			System.out.print(" | " + q.getTable()[i] + " | ");
		}
	}

	public static int pathCost(Queen q1, Queen q2) {
		int p = 0;
		for (int i = 0; i < q1.N; i++) {
			p += Math.abs(q1.getTable()[i] - q2.getTable()[i]);
		}
		return p;
	}

	public static ArrayList<Integer> listLimit = new ArrayList<Integer>();

	public static Queen IDAStar(Queen q) {
		int n = q.getN();
		Queen initial = new Queen(q.table, n);
		s = new int[n];
		int ss[] = new int[n];
		s = ZeroTable(s);
		int cutOff = initial.getHer();
		while (1 == 1) {
			newCutOff = UpdateCutOff(cutOff);
			System.arraycopy(q.table, 0, ss, 0, n);
			if (DepthFirstSearch(new Queen(ss, ss.length), initial, cutOff, 0, 0) == 1) {
				break;
			}
			cutOff = newCutOff;
		}
		Queen solution = new Queen(s, s.length);
		System.out.println();
		System.out.println("CUT OFF : " + cutOff);
		System.out.println();
		return solution;
	}

	public static int UpdateCutOff(int limit) {
		Collections.sort(listLimit);
		for (int i = 0; i < listLimit.size(); i++) {
			if (listLimit.get(i) > limit) {
				limit = listLimit.get(i);
				// System.out.println("Update limit to " + limit);
				break;
			}
		}
		return limit;
	}

	public static int[] ZeroTable(int[] table) {

		for (int i = 0; i < table.length; i++) {
			table[i] = 0;
		}
		return table;
	}

	public static ArrayList<Queen> GenarateQueen(Queen q, int col) {
		ArrayList<Queen> gen = new ArrayList<Queen>();
		for (int i = 0; i < q.N; i++) {
			int g[] = new int[q.N];
			System.arraycopy(q.N, 0, g, 0, g.length);
			g[col] = i;
			gen.add(i, new Queen(g, g.length));
		}
		return gen;
	}

	public static int DepthFirstSearch(Queen state, Queen initial, int cutOff, int depth, int column) {
		int check = 0;
		int TotalCost = state.getHer() + pathCost(state, initial);

		if (state.getHer() == 0) {
			System.arraycopy(state.getTable(), 0, s, 0, s.length);
			return 1;
		}

		else if (TotalCost > cutOff) {
			listLimit.add(TotalCost);
			return check;
		}

		else if (column >= state.N)
			return check;

		for (int i = 0; i < state.N; i++) {
			if (check == 1)
				return check;
			int change[] = new int[state.N];
			System.arraycopy(state.getTable(), 0, change, 0, change.length);
			change[column] = i;
			Queen next = new Queen(change, state.N);
			check = DepthFirstSearch(next, initial, cutOff, depth + 1, column + 1);

		}
		return check;
	}
	
}
