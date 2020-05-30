package algo;

import sondage.algo.AlgoAffect;

/*exemple du prof*/
public class testAlgo {

    public static void main(String[] args){

        int nbRespondents = 32;
        int nbItems = 10;

        /**************** tableau de réponse ******************/
        long[][] tableRespons;
        tableRespons= new long [nbRespondents+1][nbItems+1];

        for(int i = 0 ; i <= nbRespondents ; i++)
            for(int j = 0 ; j <= nbItems ; j++) {
                tableRespons[i][j]= 0;
            }

        tableRespons[0][0] = 0;

        // lES ID POUR LES ETUDIANTS
        tableRespons[1][0] = 1;
        tableRespons[2][0] = 2;
        tableRespons[3][0] = 3;
        tableRespons[4][0] = 4;
        tableRespons[5][0] = 5;
        tableRespons[6][0] = 6;
        tableRespons[7][0] = 7;
        tableRespons[8][0] = 8;
        tableRespons[9][0] = 9;
        tableRespons[10][0] = 10;
        tableRespons[11][0] = 11;
        tableRespons[12][0] = 12;
        tableRespons[13][0] = 13;
        tableRespons[14][0] = 14;
        tableRespons[15][0] = 15;
        tableRespons[16][0] = 16;
        tableRespons[17][0] = 17;
        tableRespons[18][0] = 18;
        tableRespons[19][0] = 19;
        tableRespons[20][0] = 20;
        tableRespons[21][0] = 21;
        tableRespons[22][0] = 22;
        tableRespons[23][0] = 23;
        tableRespons[24][0] = 24;
        tableRespons[25][0] = 25;
        tableRespons[26][0] = 26;
        tableRespons[27][0] = 27;
        tableRespons[28][0] = 28;
        tableRespons[29][0] = 29;
        tableRespons[30][0] = 30;
        tableRespons[31][0] = 31;
        tableRespons[32][0] = 32;



        // lES ID POUR LES PROJETS
        tableRespons[0][1] = 1;
        tableRespons[0][2] = 2;
        tableRespons[0][3] = 3;
        tableRespons[0][4] = 4;
        tableRespons[0][5] = 5;
        tableRespons[0][6] = 6;
        tableRespons[0][7] = 7;
        tableRespons[0][8] = 8;
        tableRespons[0][9] = 9;
        tableRespons[0][10] = 10;

        /* les choix du 1er etudiant */
        tableRespons[1][1] = 8;
        tableRespons[1][2] = 9;
        tableRespons[1][3] = 6;
        tableRespons[1][4] = 1;
        tableRespons[1][5] = 2;
        tableRespons[1][6] = 4;
        tableRespons[1][7] = 3;
        tableRespons[1][8] = 10;
        tableRespons[1][9] = 7;
        tableRespons[1][10] = 5;

        /*les choix du 2eme etudiant */
        tableRespons[2][1] = 8;
        tableRespons[2][2] = 5;
        tableRespons[2][3] = 10;
        tableRespons[2][4] = 1;
        tableRespons[2][5] = 4;
        tableRespons[2][6] = 2;
        tableRespons[2][7] = 3;
        tableRespons[2][8] = 6;
        tableRespons[2][9] = 9;
        tableRespons[2][10] = 7;

        /*les choix du 3eme etudiant */
        tableRespons[3][1] = 7;
        tableRespons[3][2] = 3;
        tableRespons[3][3] = 10;
        tableRespons[3][4] = 9;
        tableRespons[3][5] = 5;
        tableRespons[3][6] = 4;
        tableRespons[3][7] = 1;
        tableRespons[3][8] = 8;
        tableRespons[3][9] = 6;
        tableRespons[3][10] = 2;

        /*les choix du 4eme etudiant */
        tableRespons[4][1] = 7;
        tableRespons[4][2] = 8;
        tableRespons[4][3] = 6;
        tableRespons[4][4] = 1;
        tableRespons[4][5] = 3;
        tableRespons[4][6] = 4;
        tableRespons[4][7] = 2;
        tableRespons[4][8] = 5;
        tableRespons[4][9] = 9;
        tableRespons[4][10] = 10;

        /*les choix du 5eme etudiant */
        tableRespons[5][1] = 8;
        tableRespons[5][2] = 7;
        tableRespons[5][3] = 10;
        tableRespons[5][4] = 5;
        tableRespons[5][5] = 6;
        tableRespons[5][6] = 1;
        tableRespons[5][7] = 2;
        tableRespons[5][8] = 4;
        tableRespons[5][9] = 9;
        tableRespons[5][10] = 3;

        /*les choix du 6eme etudiant */
        tableRespons[6][1] = 9;
        tableRespons[6][2] = 10;
        tableRespons[6][3] = 8;
        tableRespons[6][4] = 1;
        tableRespons[6][5] = 6;
        tableRespons[6][6] = 3;
        tableRespons[6][7] = 2;
        tableRespons[6][8] = 5;
        tableRespons[6][9] = 7;
        tableRespons[6][10] = 4;

        /*les choix du 7eme etudiant */
        tableRespons[7][1] = 9;
        tableRespons[7][2] = 8;
        tableRespons[7][3] = 4;
        tableRespons[7][4] = 3;
        tableRespons[7][5] = 6;
        tableRespons[7][6] = 7;
        tableRespons[7][7] = 10;
        tableRespons[7][8] = 5;
        tableRespons[7][9] = 2;
        tableRespons[7][10] = 1;

        /*les choix du 8eme etudiant */
        tableRespons[8][1] = 7;
        tableRespons[8][2] = 10;
        tableRespons[8][3] = 5;
        tableRespons[8][4] = 2;
        tableRespons[8][5] = 1;
        tableRespons[8][6] = 8;
        tableRespons[8][7] = 9;
        tableRespons[8][8] = 6;
        tableRespons[8][9] = 3;
        tableRespons[8][10] = 4;

        /*les choix du 9eme etudiant */
        tableRespons[9][1] = 7;
        tableRespons[9][2] = 10;
        tableRespons[9][3] = 5;
        tableRespons[9][4] = 1;
        tableRespons[9][5] = 2;
        tableRespons[9][6] = 8;
        tableRespons[9][7] = 9;
        tableRespons[9][8] = 6;
        tableRespons[9][9] = 3;
        tableRespons[9][10] = 4;

        /*les choix du 10eme etudiant */
        tableRespons[10][1] = 7;
        tableRespons[10][2] = 4;
        tableRespons[10][3] = 9;
        tableRespons[10][4] = 10;
        tableRespons[10][5] = 5;
        tableRespons[10][6] = 3;
        tableRespons[10][7] = 1;
        tableRespons[10][8] = 8;
        tableRespons[10][9] = 6;
        tableRespons[10][10] = 2;

        /*les choix du 11eme etudiant */
        tableRespons[11][1] = 8;
        tableRespons[11][2] = 10;
        tableRespons[11][3] = 9;
        tableRespons[11][4] = 2;
        tableRespons[11][5] = 3;
        tableRespons[11][6] = 5;
        tableRespons[11][7] = 4;
        tableRespons[11][8] = 7;
        tableRespons[11][9] = 6;
        tableRespons[11][10] = 1;

        /*les choix du 12eme etudiant */
        tableRespons[12][1] = 8;
        tableRespons[12][2] = 9;
        tableRespons[12][3] = 6;
        tableRespons[12][4] = 1;
        tableRespons[12][5] = 2;
        tableRespons[12][6] = 4;
        tableRespons[12][7] = 3;
        tableRespons[12][8] = 10;
        tableRespons[12][9] = 7;
        tableRespons[12][10] = 5;

        /*les choix du 13eme etudiant */
        tableRespons[13][1] = 6;
        tableRespons[13][2] = 7;
        tableRespons[13][3] = 3;
        tableRespons[13][4] = 1;
        tableRespons[13][5] = 8;
        tableRespons[13][6] = 2;
        tableRespons[13][7] = 10;
        tableRespons[13][8] = 9;
        tableRespons[13][9] = 4;
        tableRespons[13][10] = 5;

        /*les choix du 14eme etudiant */
        tableRespons[14][1] = 9;
        tableRespons[14][2] = 5;
        tableRespons[14][3] = 4;
        tableRespons[14][4] = 3;
        tableRespons[14][5] = 1;
        tableRespons[14][6] = 7;
        tableRespons[14][7] = 6;
        tableRespons[14][8] = 8;
        tableRespons[14][9] = 10;
        tableRespons[14][10] = 2;

        /*les choix du 15eme etudiant */
        tableRespons[15][1] = 7;
        tableRespons[15][2] = 8;
        tableRespons[15][3] = 6;
        tableRespons[15][4] = 1;
        tableRespons[15][5] = 2;
        tableRespons[15][6] = 3;
        tableRespons[15][7] = 4;
        tableRespons[15][8] = 10;
        tableRespons[15][9] = 9;
        tableRespons[15][10] = 5;

        /*les choix du 16eme etudiant */
        tableRespons[16][1] = 10;
        tableRespons[16][2] = 9;
        tableRespons[16][3] = 8;
        tableRespons[16][4] = 1;
        tableRespons[16][5] = 5;
        tableRespons[16][6] = 6;
        tableRespons[16][7] = 7;
        tableRespons[16][8] = 2;
        tableRespons[16][9] = 3;
        tableRespons[16][10] = 4;

        /*les choix du 17eme etudiant */
        tableRespons[17][1] = 8;
        tableRespons[17][2] = 10;
        tableRespons[17][3] = 9;
        tableRespons[17][4] = 2;
        tableRespons[17][5] = 3;
        tableRespons[17][6] = 5;
        tableRespons[17][7] = 4;
        tableRespons[17][8] = 7;
        tableRespons[17][9] = 6;
        tableRespons[17][10] = 1;

        /*les choix du 18eme etudiant */
        tableRespons[18][1] = 10;
        tableRespons[18][2] = 8;
        tableRespons[18][3] = 9;
        tableRespons[18][4] = 5;
        tableRespons[18][5] = 6;
        tableRespons[18][6] = 3;
        tableRespons[18][7] = 2;
        tableRespons[18][8] = 4;
        tableRespons[18][9] = 7;
        tableRespons[18][10] = 1;

        /*les choix du 19eme etudiant */
        tableRespons[19][1] = 7;
        tableRespons[19][2] = 9;
        tableRespons[19][3] = 5;
        tableRespons[19][4] = 1;
        tableRespons[19][5] = 2;
        tableRespons[19][6] = 8;
        tableRespons[19][7] = 10;
        tableRespons[19][8] = 6;
        tableRespons[19][9] = 3;
        tableRespons[19][10] = 4;

        /*les choix du 20eme etudiant */
        tableRespons[20][1] = 8;
        tableRespons[20][2] = 10;
        tableRespons[20][3] = 5;
        tableRespons[20][4] = 1;
        tableRespons[20][5] = 2;
        tableRespons[20][6] = 4;
        tableRespons[20][7] = 3;
        tableRespons[20][8] = 9;
        tableRespons[20][9] = 7;
        tableRespons[20][10] = 6;

        /*les choix du 21eme etudiant */
        tableRespons[21][1] = 10;
        tableRespons[21][2] = 7;
        tableRespons[21][3] = 9;
        tableRespons[21][4] = 4;
        tableRespons[21][5] = 6;
        tableRespons[21][6] = 3;
        tableRespons[21][7] = 2;
        tableRespons[21][8] = 5;
        tableRespons[21][9] = 8;
        tableRespons[21][10] = 1;

        /*les choix du 22eme etudiant */
        tableRespons[22][1] = 10;
        tableRespons[22][2] = 8;
        tableRespons[22][3] = 9;
        tableRespons[22][4] = 5;
        tableRespons[22][5] = 6;
        tableRespons[22][6] = 3;
        tableRespons[22][7] = 2;
        tableRespons[22][8] = 5;
        tableRespons[22][9] = 8;
        tableRespons[22][10] = 1;

        /*les choix du 23eme etudiant */
        tableRespons[23][1] = 4;
        tableRespons[23][2] = 6;
        tableRespons[23][3] = 8;
        tableRespons[23][4] = 9;
        tableRespons[23][5] = 10;
        tableRespons[23][6] = 2;
        tableRespons[23][7] = 1;
        tableRespons[23][8] = 7;
        tableRespons[23][9] = 5;
        tableRespons[23][10] = 3;

        /*les choix du 24eme etudiant */
        tableRespons[24][1] = 7;
        tableRespons[24][2] = 10;
        tableRespons[24][3] = 5;
        tableRespons[24][4] = 2;
        tableRespons[24][5] = 1;
        tableRespons[24][6] = 8;
        tableRespons[24][7] = 9;
        tableRespons[24][8] = 6;
        tableRespons[24][9] = 3;
        tableRespons[24][10] = 4;

        /*les choix du 25eme etudiant */
        tableRespons[25][1] = 6;
        tableRespons[25][2] = 10;
        tableRespons[25][3] = 9;
        tableRespons[25][4] = 2;
        tableRespons[25][5] = 3;
        tableRespons[25][6] = 8;
        tableRespons[25][7] = 4;
        tableRespons[25][8] = 5;
        tableRespons[25][9] = 7;
        tableRespons[25][10] = 1;

        /*les choix du 26eme etudiant */
        tableRespons[26][1] = 4;
        tableRespons[26][2] = 7;
        tableRespons[26][3] = 8;
        tableRespons[26][4] = 5;
        tableRespons[26][5] = 6;
        tableRespons[26][6] = 2;
        tableRespons[26][7] = 1;
        tableRespons[26][8] = 10;
        tableRespons[26][9] = 9;
        tableRespons[26][10] = 3;

        /*les choix du 27eme etudiant */
        tableRespons[27][1] = 8;
        tableRespons[27][2] = 9;
        tableRespons[27][3] = 1;
        tableRespons[27][4] = 2;
        tableRespons[27][5] = 6;
        tableRespons[27][6] = 4;
        tableRespons[27][7] = 5;
        tableRespons[27][8] = 3;
        tableRespons[27][9] = 10;
        tableRespons[27][10] = 7;

        /*les choix du 28eme etudiant */
        tableRespons[28][1] = 10;
        tableRespons[28][2] = 8;
        tableRespons[28][3] = 9;
        tableRespons[28][4] = 5;
        tableRespons[28][5] = 6;
        tableRespons[28][6] = 3;
        tableRespons[28][7] = 2;
        tableRespons[28][8] = 4;
        tableRespons[28][9] = 7;
        tableRespons[28][10] = 1;

        /*les choix du 29eme etudiant */
        tableRespons[29][1] = 9;
        tableRespons[29][2] = 3;
        tableRespons[29][3] = 4;
        tableRespons[29][4] = 2;
        tableRespons[29][5] = 1;
        tableRespons[29][6] = 8;
        tableRespons[29][7] = 7;
        tableRespons[29][8] = 5;
        tableRespons[29][9] = 10;
        tableRespons[29][10] = 6;

        /*les choix du 30eme etudiant */
        tableRespons[30][1] = 10;
        tableRespons[30][2] = 1;
        tableRespons[30][3] = 6;
        tableRespons[30][4] = 3;
        tableRespons[30][5] = 2;
        tableRespons[30][6] = 5;
        tableRespons[30][7] = 4;
        tableRespons[30][8] = 8;
        tableRespons[30][9] = 9;
        tableRespons[30][10] = 7;

        /*les choix du 31eme etudiant */
        tableRespons[31][1] = 8;
        tableRespons[31][2] = 7;
        tableRespons[31][3] = 10;
        tableRespons[31][4] = 5;
        tableRespons[31][5] = 6;
        tableRespons[31][6] = 1;
        tableRespons[31][7] = 2;
        tableRespons[31][8] = 4;
        tableRespons[31][9] = 9;
        tableRespons[31][10] = 3;

        /*les choix du 32eme etudiant */
        tableRespons[32][1] = 10;
        tableRespons[32][2] = 8;
        tableRespons[32][3] = 9;
        tableRespons[32][4] = 5;
        tableRespons[32][5] = 6;
        tableRespons[32][6] = 3;
        tableRespons[32][7] = 2;
        tableRespons[32][8] = 4;
        tableRespons[32][9] = 7;
        tableRespons[32][10] = 1;


        /****************tableau de la capacité ******************/

        long[][] tableCapacite;
        tableCapacite = new long [nbItems][3];
        for(int i = 0 ; i < nbItems ; i++)
            for(int j = 0 ; j <= 2 ; j++) {
                tableCapacite[i][j]= 0;
            }

        // les ids pour les projets
        tableCapacite[0][0]=1;
        tableCapacite[1][0]=2;
        tableCapacite[2][0]=3;
        tableCapacite[3][0]=4;
        tableCapacite[4][0]=5;
        tableCapacite[5][0]=6;
        tableCapacite[6][0]=7;
        tableCapacite[7][0]=8;
        tableCapacite[8][0]=9;
        tableCapacite[9][0]=10;

        //capacite min
        tableCapacite[0][1]=8;
        tableCapacite[1][1]=8;
        tableCapacite[2][1]=8;
        tableCapacite[3][1]=3;
        tableCapacite[4][1]=3;
        tableCapacite[5][1]=3;
        tableCapacite[6][1]=3;
        tableCapacite[7][1]=3;
        tableCapacite[8][1]=3;
        tableCapacite[9][1]=3;

        //capacite max
        tableCapacite[0][2]=12;
        tableCapacite[1][2]=12;
        tableCapacite[2][2]=12;
        tableCapacite[3][2]=6;
        tableCapacite[4][2]=6;
        tableCapacite[5][2]=6;
        tableCapacite[6][2]=7;
        tableCapacite[7][2]=7;
        tableCapacite[8][2]=6;
        tableCapacite[9][2]=5;

        long [][] tableResult ;
        tableResult = new long [nbRespondents][2];


        tableResult = AlgoAffect.affectation(nbRespondents, nbItems, tableRespons,tableCapacite);




        for(int i = 0 ;i < nbRespondents  ; i++) {

            for(int j =0 ; j <= 1 ;j++) {

                System.out.print("Tab_d["+i+"]["+j+"]="+tableResult [i][j]+" ");
            }
            System.out.println();
        }

    }

}
