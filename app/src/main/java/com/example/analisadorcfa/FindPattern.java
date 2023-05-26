package com.example.analisadorcfa;

import android.graphics.Bitmap;
import android.graphics.Color;

/*
* Codigo do Gabriel Reis
*/

public class FindPattern {
    public static String getPadrao(Bitmap img) {
        img = img.copy(Bitmap.Config.ARGB_8888, true);
        int[][] R = new int[img.getHeight()][img.getWidth()];
        int[][] G = new int[img.getHeight()][img.getWidth()];
        int[][] B = new int[img.getHeight()][img.getWidth()];
        int[][] CFAr = new int[2][2];
        int[][] CFAg = new int[2][2];
        int[][] CFAb = new int[2][2];
        int lin = img.getHeight();
        int col = img.getWidth();
        String matrixString1 = "",matrixString2 = "",matrixString3 = "";

        for (int i = 1; i < lin - 1; i++) {
            for (int j = 1; j < col - 1; j++) {
                int pixel = img.getPixel(j, i);
                R[i][j] = Color.red(pixel);
                G[i][j] = Color.green(pixel);
                B[i][j] = Color.blue(pixel);
            }
        }

        for (int i = 2; i < lin - 1; i++) {
            for (int j = 2; j < col - 1; j++) {
                int[] vizG = {G[i + 1][j], G[i - 1][j], G[i][j + 1], G[i][j - 1]};
                int[] vizR = {R[i - 1][j - 1], R[i - 1][j + 1], R[i + 1][j - 1], R[i + 1][j + 1]};
                int[] vizB = {B[i - 1][j - 1], B[i - 1][j + 1], B[i + 1][j - 1], B[i + 1][j + 1]};

                if ((i + j) % 2 == 1) {
                    if (max(vizG) >= G[i][j] && min(vizG) <= G[i][j]) {
                        CFAg[0][1] += 1;
                        CFAg[1][0] += 1;

                        if (max(vizR) >= R[i][j] && min(vizR) <= R[i][j]) {
                            if (R[i][j] > G[i][j] && R[i][j] > B[i][j]) {
                                CFAr[0][0] += 1;
                                CFAb[1][1] += 1;
                            } else {
                                if (B[i][j] > G[i][j] && R[i][j] < B[i][j]) {
                                    CFAb[0][0] += 1;
                                    CFAr[1][1] += 1;
                                }
                            }
                        }
                    }
                } else {
                    if (max(vizG) >= G[i][j] && min(vizG) <= G[i][j]) {
                        CFAg[0][0] += 1;
                        CFAg[1][1] += 1;

                        if (max(vizR) >= R[i][j] && min(vizR) <= R[i][j]) {
                            if (R[i][j] > G[i][j] && R[i][j] < B[i][j]) {
                                CFAr[0][1] += 1;
                                CFAb[1][0] += 1;
                            } else {
                                if (B[i][j] > G[i][j] && R[i][j] > B[i][j]) {
                                    CFAb[0][1] += 1;
                                    CFAr[1][0] += 1;
                                }
                            }
                        }
                    }
                }
            }
        }

        String padrao = "";
        if ((CFAg[0][0] <= CFAg[0][1] || CFAg[1][0] >= CFAg[1][1]) && ((CFAg[0][0] > CFAr[0][0] || CFAg[0][1] > CFAb[0][0]) || (CFAg[0][1] > CFAr[0][1] || CFAg[0][1] > CFAb[0][1]))) {
            if (CFAr[0][1] > CFAb[0][1]) {
                padrao = "GBRG";
            } else {
                padrao = "GRBG";
            }
        } else {
            if (CFAr[0][0] > CFAb[0][0]) {
                padrao = "BGGR";
            } else {
                padrao = "RGGB";
            }
        }
        return padrao;
    }

    private static int max(int[] array) {
        int max = Integer.MIN_VALUE;
        for (int value : array) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private static int min(int[] array) {
        int min = Integer.MAX_VALUE;
        for (int value : array) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }
}
