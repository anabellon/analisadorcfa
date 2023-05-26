package com.example.analisadorcfa;

import android.graphics.Bitmap;

/*
 * Codigo do Gabriel Reis
 */

public class ImageProcessing {
    public static Bitmap pintarForaDoPadrao(Bitmap img, String padrao) {
        Bitmap imgAlterada = img.copy(img.getConfig(), true);

        int width = img.getWidth();
        int height = img.getHeight();

        int[] pixels = new int[width * height];
        imgAlterada.getPixels(pixels, 0, width, 0, 0, width, height);

        int[][] R = new int[width][height];
        int[][] G = new int[width][height];
        int[][] B = new int[width][height];
        int[][] imgAlteradaR = new int[width][height];
        int[][] imgAlteradaG = new int[width][height];
        int[][] imgAlteradaB = new int[width][height];

        for (int i = 0; i < img.getWidth(); i++) {
            for (int j = 0; j < img.getHeight(); j++) {
                imgAlteradaR[i][j] = 0;
                imgAlteradaG[i][j] = 0;
                imgAlteradaB[i][j] = 0;
            }
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = pixels[j * width + i];
                R[i][j] = (pixel >> 16) & 0xFF;
                G[i][j] = (pixel >> 8) & 0xFF;
                B[i][j] = pixel & 0xFF;
            }
        }

        switch (padrao) {
            case "RGGB":
                // Verde
                for (int i = 1; i < width - 1; i++) {
                    for (int j = 1; j < height - 1; j++) {
                        if ((i + j) % 2 == 0) {
                            int[] vizG = {G[i + 1][j], G[i - 1][j], G[i][j + 1], G[i][j - 1]};
                            if ((max(vizG) >= G[i][j]) && (min(vizG) <= G[i][j])) {
                                imgAlteradaG[i][j] = 0;
                            } else {
                                imgAlteradaG[i][j] = 255;
                            }
                        }
                    }
                }

                // Vermelho
                for (int i = 3; i < width - 1; i += 2) {
                    for (int j = 3; j < height - 1; j += 2) {
                        if ((i + j) % 2 == 0) {
                            int[] vizR = {R[i - 1][j - 1], R[i - 1][j + 1], R[i + 1][j - 1], R[i + 1][j + 1]};
                            if ((max(vizR) >= R[i][j]) && (min(vizR) <= R[i][j])) {
                                imgAlteradaR[i][j] = 0;
                            } else {
                                imgAlteradaR[i][j] = 255;
                            }
                        }
                    }
                }

                // Azul
                for (int i = 2; i < width - 1; i += 2) {
                    for (int j = 2; j < height - 1; j += 2) {
                        if ((i + j) % 2 == 0) {
                            int[] vizB = {B[i - 1][j - 1], B[i - 1][j + 1], B[i + 1][j - 1], B[i + 1][j + 1]};
                            if ((max(vizB) >= B[i][j]) && (min(vizB) <= B[i][j])) {
                                imgAlteradaB[i][j] = 0;
                            } else {
                                imgAlteradaB[i][j] = 255;
                            }
                        }
                    }
                }
                break;

            case "BGGR":
                // Verde
                for (int i = 1; i < width - 1; i++) {
                    for (int j = 1; j < height - 1; j++) {
                        if ((i + j) % 2 == 0) {
                            int[] vizG = {G[i + 1][j], G[i - 1][j], G[i][j + 1], G[i][j - 1]};
                            if ((max(vizG) >= G[i][j]) && (min(vizG) <= G[i][j])) {
                                imgAlteradaG[i][j] = 0;
                            } else {
                                imgAlteradaG[i][j] = 255;
                            }
                        }
                    }
                }

                // Vermelho
                for (int i = 2; i < width - 1; i += 2) {
                    for (int j = 2; j < height - 1; j += 2) {
                        int[] vizR = {R[i - 1][j - 1], R[i - 1][j + 1], R[i + 1][j - 1], R[i + 1][j + 1]};
                        if ((max(vizR) >= R[i][j]) && (min(vizR) <= R[i][j])) {
                            imgAlteradaR[i][j] = 0;
                        } else {
                            imgAlteradaR[i][j] = 255;
                        }
                    }
                }

                // Azul
                for (int i = 1; i < width - 1; i += 2) {
                    for (int j = 1; j < height - 1; j += 2) {
                        int[] vizB = {B[i - 1][j - 1], B[i - 1][j + 1], B[i + 1][j - 1], B[i + 1][j + 1]};
                        if ((max(vizB) >= B[i][j]) && (min(vizB) <= B[i][j])) {
                            imgAlteradaB[i][j] = 0;
                        } else {
                            imgAlteradaB[i][j] = 255;
                        }
                    }
                }
                break;

            case "GBRG":
                // Verde
                for (int i = 1; i < width - 1; i++) {
                    for (int j = 2; j < height - 1; j++) {
                        if ((i + j) % 2 == 0) {
                            int[] vizG = {G[i + 1][j], G[i][j + 1], G[i][j - 1]};
                            if ((max(vizG) >= G[i][j]) && (min(vizG) <= G[i][j])) {
                                imgAlteradaG[i][j] = 0;
                            } else {
                                imgAlteradaG[i][j] = 255;
                            }
                        }
                    }
                }

                // Vermelho
                for (int i = 2; i < width - 1; i += 2) {
                    for (int j = 1; j < height - 1; j += 2) {
                        int[] vizR = {R[i - 1][j - 1], R[i - 1][j + 1], R[i + 1][j - 1], R[i + 1][j + 1]};
                        if ((max(vizR) >= R[i][j]) && (min(vizR) <= R[i][j])) {
                            imgAlteradaR[i][j] = 0;
                        } else {
                            imgAlteradaR[i][j] = 255;
                        }
                    }
                }

                // Azul
                for (int i = 1; i < width - 1; i += 2) {
                    for (int j = 2; j < height - 1; j += 2) {
                        int[] vizB = {B[i - 1][j - 1], B[i - 1][j + 1], B[i + 1][j - 1], B[i + 1][j + 1]};
                        if ((max(vizB) >= B[i][j]) && (min(vizB) <= B[i][j])) {
                            imgAlteradaB[i][j] = 0;
                        } else {
                            imgAlteradaB[i][j] = 255;
                        }
                    }
                }
                break;

            case "GRBG":
                // Verde
                for (int i = 2; i < width - 1; i +=2) {
                    for (int j = 3; j < height - 1; j +=2) {
                        if ((i + j) % 2 == 1) {
                            int[] vizG = { G[i + 1][j], G[i - 1][j], G[i][j + 1], G[i][j - 1] };
                            if ((max(vizG) >= G[i][j]) && (min(vizG) <= G[i][j])) {
                                imgAlteradaG[i][j] = 0;
                            } else {
                                imgAlteradaG[i][j] = 255;
                            }
                        }
                    }
                }

                // Vermelho
                for (int i = 2; i < width - 1; i +=2) {
                    for (int j = 3; j < height - 1; j +=2) {
                            int[] vizR = {R[i - 1][j - 1], R[i - 1][j + 1], R[i + 1][j - 1], R[i + 1][j + 1]};
                            if ((max(vizR) >= R[i][j]) && (min(vizR) <= R[i][j])) {
                                imgAlteradaR[i][j] = 0;
                            } else {
                                imgAlteradaR[i][j] = 255;
                            }
                    }
                }

                // Azul
                for (int i = 3; i < width - 1; i +=2) {
                    for (int j = 2; j < height - 1; j +=2) {
                            int[] vizB = {B[i - 1][j - 1], B[i - 1][j + 1], B[i + 1][j - 1], B[i + 1][j + 1]};
                            if ((max(vizB) >= B[i][j]) && (min(vizB) <= B[i][j])) {
                                imgAlteradaB[i][j] = 0;
                            } else {
                                imgAlteradaB[i][j] = 255;
                            }
                    }
                }
                break;

            default:
                throw new IllegalArgumentException("Padrão inválido");
        }

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = (imgAlteradaR[i][j] << 16) | (imgAlteradaG[i][j] << 8) | imgAlteradaB[i][j];
                imgAlterada.setPixel(i, j, pixel);
            }
        }

        return imgAlterada;
    }

    private static int max(int[] array) {
        int max = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    private static int min(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }
}
