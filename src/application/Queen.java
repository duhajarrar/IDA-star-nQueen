/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;


public class Queen {
        public int table[];
        int N;
        int h;

    public Queen(int[] table, int N) {
        this.table = table;
        this.N = N;
    }
    public Queen() {

	}
    public int getHer() {
    	int h = 0;
		for (int i = 0; i < table.length; i++) {
			for (int j = 0; j < table.length; j++) {
				if (i != j && table[i] == table[j]
						|| (float) (Math.abs(i - j)) / (Math.abs(table[i] -table[j])) == 1) {
					h++;
				}
			}
		}
		return h;
    }
    public int[] getTable() {
        return table;
    }

    public void setTable(int[] table) {
        this.table = table;
    }

    public int getN() {
        return N;
    }

    public void setN(int N) {
        this.N = N;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }
  
}
