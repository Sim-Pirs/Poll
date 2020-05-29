package sondage.algo;

import java.util.Arrays;
import java.util.Comparator;

public class AlgoAffect {

    private int NbreEtudiants;
    private int NbreOptions;
    private long[][] T_Resultats;
    private long[][] T_Choix;

    /*************Constructeur**************/

    public AlgoAffect(int nb_etudiant , int nb_option ,long[][] Table_rep) {
        this.NbreEtudiants = nb_etudiant;
        this.NbreOptions = nb_option;
        this.T_Resultats = Table_rep;

    }

    /*************Calcul_Score**************/

    public static long [][] Calcul_Score(int nb_etudiant , int nb_option ,long[][] Table_rep){

        long[][] T_Ordonne;
        T_Ordonne = new long [nb_option][2];
        for(int i = 0 ; i < nb_option ; i++) {
            for(int j = 0 ; j < 2 ; j++) {
                T_Ordonne[i][j]= 0;
            }
        }

        for(int i = 1 ; i <= nb_option ; i++) {
            T_Ordonne[i-1][0] = Table_rep[0][i];
            for(int j = 1 ; j <= nb_etudiant ;j++) {
                T_Ordonne[i-1][1] = T_Ordonne[i-1][1] + Table_rep[j][i];
            }
        }

        Arrays.sort(T_Ordonne, new Comparator<long[]>() {
            @Override
            public int compare(long[] o2, long[] o1) {
                return Long.compare(o2[1], o1[1]);
            }
        });

        return T_Ordonne;
    }





    /*************Affectation**************/
    public static long [][] affectation(int nb_etudiant , int nb_option ,long[][] Table_rep , long[][] Table_choi) {

        long[][] T_Ordonne2 = Calcul_Score( nb_etudiant , nb_option , Table_rep);
        long[][] T_ResF;
        long[][] actual;
        int index = 0;
        T_ResF = new long [nb_etudiant][2];
        actual = new long [nb_option][2];
        for (int i = 0; i < nb_option; i++) {
            actual[i][0]=T_Ordonne2[i][0];
            actual[i][1]=0;
        }
        int level = 1;
        while (level<=nb_option){
            for(int i=0 ; i < nb_option ; i++) {	// pour le tableau T_Ordonne2
                for(int j=0 ; j < nb_option ; j++) { // pour le tableau Table_rep
                    if(T_Ordonne2[i][0] == Table_rep[0][j+1] ){ //condition pour lier le tableau d'ordonne avec tableau de r�sultat
                        for(int k=1 ; k <= nb_etudiant ; k++) {// pour passer sur tous les �tudiants
                            if(notAffected(Table_rep[k][0],T_ResF,nb_etudiant)&&Table_rep[k][j+1] == level  &&actual[i][1]<getMin(T_Ordonne2[i][0],Table_choi,nb_option)) { // 1ere choix
                                T_ResF [index][0] = T_Ordonne2[i][0]; //projet
                                T_ResF [index][1] = Table_rep[k][0];//etudiant
                                index++;
                                actual[i][1]=actual[i][1]+1;

                            }

                        }
                    }
                }
            }

            level++;
        }
        System.out.println("---------------Actual before canceling------------------");
        // actual
        for (int i = nb_option-1; i >= 0; i--)
            System.out.println(actual[i][0]+":"+actual[i][1]+":"+getMin(actual[i][0],Table_choi,nb_option));

        System.out.println("--------------------------------------------------------");

        for(int i = 0 ;i < nb_etudiant  ; i++) {

            for(int j =0 ; j <= 1 ;j++) {

                System.out.print("Tab_d["+i+"]["+j+"]="+T_ResF[i][j]+" ");
            }
            System.out.println();
        }


        for (int i = nb_option-1; i >= 0; i--) {
            if(actual[i][1]<getMin(actual[i][0],Table_choi,nb_option)) {
                //System.out.println(actual[i][0]+":"+actual[i][1]+":"+getMin(actual[i][0],Table_choi,nb_option));
                cancelProject(nb_etudiant,nb_option,Table_rep,Table_choi,T_ResF, actual,actual[i][0]);

            }
        }

        System.out.println("---------------Actual after canceling-------------------");
        // actual
        for (int i = nb_option-1; i >= 0; i--)
            System.out.println(actual[i][0]+":"+actual[i][1]+":"+getMin(actual[i][0],Table_choi,nb_option));

        System.out.println("--------------------------------------------------------");


        //}

        return T_ResF;
    }


    private static void cancelProject(int nb_etudiant, int nb_option, long[][] table_rep, long[][] table_choi,
                                      long[][] t_ResF,long[][] actual, long project) {
        for(int k=0 ; k < nb_etudiant ; k++) {
            if(t_ResF[k][0]==project) {
                //System.out.println(t_ResF[k][1]);
                //move this std to another project
                moveStudentToNewProject(nb_etudiant,nb_option,table_rep,table_choi,t_ResF,actual,project,k);

            }
        }

    }

    private static void moveStudentToNewProject(int nb_etudiant, int nb_option, long[][] table_rep, long[][] table_choi,
                                                long[][] t_ResF,long[][] actual, long old_project,int idx) {
        int level = 1;
        while(level <=nb_option) {

            for(int i=1 ; i <= nb_etudiant ; i++) {
                if(table_rep[i][0]==t_ResF[idx][1]) {
                    for(int j=0 ; j < nb_option ; j++) {
                        long new_project_actual = getActual(actual,nb_option,table_rep[0][j+1]);
                        long old_project_actual = getActual(actual,nb_option,old_project);
                        long new_project_id= table_rep[0][j+1];
                        if(new_project_id!=old_project && new_project_actual<getMax(new_project_id,table_choi,nb_option)&&new_project_actual != 0 && table_rep[i][j+1] == level ) {
                            t_ResF[idx][0]=new_project_id;
                            updateActual(actual,nb_option,new_project_id,new_project_actual+1);
                            updateActual(actual,nb_option,old_project,old_project_actual-1);
                            return;

                        }
                    }

                }
            }
            level ++;
        }
    }



    private static long getActual(long[][] actual, int nb_option, long project) {
        for (int i = 0; i < nb_option; i++)
            if(actual [i][0]==project)
                return actual[i][1];
        return 0;
    }

    private static void updateActual(long[][] actual, int nb_option, long project, long value) {
        for (int i = 0; i < nb_option; i++)
            if(actual [i][0]==project)
                actual[i][1]=value;
    }


    private static boolean notAffected(long etudiant_id, long[][] t_ResF,long nb_etudiant) {
        for (int i = 0; i < nb_etudiant; i++) {
            if(t_ResF [i][1]==etudiant_id) {
                return false;
            }
        }

        return true;
    }

    private static long getMin(long projetc_id, long[][] Table_choi,long nb_option) {
        for (int i = 0; i < nb_option; i++) {
            if(Table_choi [i][0]==projetc_id) {
                return Table_choi [i][1];
            }
        }

        return 0;
    }

    private static long getMax(long projetc_id, long[][] Table_choi,long nb_option) {
        for (int i = 0; i < nb_option; i++) {
            if(Table_choi [i][0]==projetc_id) {
                return Table_choi [i][2];
            }
        }

        return 0;
    }
    /*************getter**************/

    public int getNbreEtudiants() {
        return NbreEtudiants;
    }
    public int getNbreOptions() {
        return NbreOptions;
    }
    public long[][] getT_Resultats() {
        return T_Resultats;
    }
    public long[][] getT_Choix() {
        return T_Choix;
    }

    /*************setter**************/

    public void setNbreEtudiants(int nbreEtudiants) {
        NbreEtudiants = nbreEtudiants;
    }
    public void setNbreOptions(int nbreOptions) {
        NbreOptions = nbreOptions;
    }
    public void setT_Resultats(long[][] t_Resultats) {
        T_Resultats = t_Resultats;
    }
    public void setT_Choix(long[][] t_Choix) {
        T_Choix = t_Choix;
    }



    public static void main(String[] args){

        int nb_etudiant = 12;
        int nb_choix = 5;

        /**************** tableau de repondre ******************/

        long[][] Table_repondre;
        Table_repondre= new long [nb_etudiant+1][nb_choix+1];

        for(int i = 0 ; i <= nb_etudiant ; i++)
            for(int j = 0 ; j <= nb_choix ; j++) {
                Table_repondre[i][j]= 0;
            }

        Table_repondre[0][0] = 0;

        // lES ID POUR LES ETUDIANTS
        Table_repondre[1][0] = 1;
        Table_repondre[2][0] = 2;
        Table_repondre[3][0] = 3;
        Table_repondre[4][0] = 4;
        Table_repondre[5][0] = 5;
        Table_repondre[6][0] = 6;
        Table_repondre[7][0] = 7;
        Table_repondre[8][0] = 8;
        Table_repondre[9][0] = 9;
        Table_repondre[10][0] = 10;
        Table_repondre[11][0] = 11;
        Table_repondre[12][0] = 12;


        // lES ID POUR LES PROJETS
        Table_repondre[0][1] = 1;
        Table_repondre[0][2] = 2;
        Table_repondre[0][3] = 3;
        Table_repondre[0][4] = 4;
        Table_repondre[0][5] = 5;

        /*1 etudiant */
        Table_repondre[1][1] = 4;
        Table_repondre[1][2] = 3;
        Table_repondre[1][3] = 1;
        Table_repondre[1][4] = 2;
        Table_repondre[1][5] = 5;

        /*2 etudiant */
        Table_repondre[2][1] = 2;
        Table_repondre[2][2] = 3;
        Table_repondre[2][3] = 4;
        Table_repondre[2][4] = 1;
        Table_repondre[2][5] = 5;

        /*3 etudiant */
        Table_repondre[3][1] = 3;
        Table_repondre[3][2] = 2;
        Table_repondre[3][3] = 1;
        Table_repondre[3][4] = 4;
        Table_repondre[3][5] = 5;

        /*4 etudiant */
        Table_repondre[4][1] = 1;
        Table_repondre[4][2] = 4;
        Table_repondre[4][3] = 3;
        Table_repondre[4][4] = 2;
        Table_repondre[4][5] = 5;

        /*5 etudiant */
        Table_repondre[5][1] = 3;
        Table_repondre[5][2] = 4;
        Table_repondre[5][3] = 1;
        Table_repondre[5][4] = 2;
        Table_repondre[5][5] = 5;

        /*6 etudiant */
        Table_repondre[6][1] = 2;
        Table_repondre[6][2] = 3;
        Table_repondre[6][3] = 1;
        Table_repondre[6][4] = 4;
        Table_repondre[6][5] = 5;

        /*7 etudiant */
        Table_repondre[7][1] = 4;
        Table_repondre[7][2] = 1;
        Table_repondre[7][3] = 3;
        Table_repondre[7][4] = 2;
        Table_repondre[7][5] = 5;

        /*8 etudiant */
        Table_repondre[8][1] = 2;
        Table_repondre[8][2] = 3;
        Table_repondre[8][3] = 1;
        Table_repondre[8][4] = 4;
        Table_repondre[8][5] = 5;

        /*9 etudiant */
        Table_repondre[9][1] = 3;
        Table_repondre[9][2] = 4;
        Table_repondre[9][3] = 1;
        Table_repondre[9][4] = 2;
        Table_repondre[9][5] = 5;

        /*10 etudiant */
        Table_repondre[10][1] = 4;
        Table_repondre[10][2] = 3;
        Table_repondre[10][3] = 1;
        Table_repondre[10][4] = 2;
        Table_repondre[10][5] = 5;

        /*11 etudiant */
        Table_repondre[11][1] = 4;
        Table_repondre[11][2] = 1;
        Table_repondre[11][3] = 3;
        Table_repondre[11][4] = 2;
        Table_repondre[11][5] = 5;

        /*12 etudiant */
        Table_repondre[11][1] = 4;
        Table_repondre[11][2] = 5;
        Table_repondre[11][3] = 3;
        Table_repondre[11][4] = 2;
        Table_repondre[12][5] = 1;


		/*
		// 1eme test pour v�rifier le tableau de repondre
		for(int i = 0 ;i <nb_etudiant  ; i++) {

			for(int j =0 ; j <= nb_choix ;j++) {

				System.out.print("Tab_d["+i+"]["+j+"]="+Table_repondre[i][j]+" " );
			}
			System.out.println();
		}
		*/

        /****	tableau de score 	***/
        long[][] Table_score;
        Table_score= new long [nb_choix+1][2];
        for(int i = 0 ; i <= nb_choix ; i++)
            for(int j = 0 ; j <= 1 ; j++) {
                Table_score[i][j]= 0;
            }

        AlgoAffect algo=new AlgoAffect(nb_etudiant , nb_choix ,Table_repondre);
        Table_score = algo.Calcul_Score( nb_etudiant , nb_choix , Table_repondre);

		/*
		 // 2eme test
	    // pour afficher le tablaeau de repondre
		for(int i = 0 ;i <=nb_etudiant  ; i++) {

			for(int j =0 ; j <= nb_choix ;j++) {

				System.out.print("Tab_repondre["+i+"]["+j+"]="+Table_repondre[i][j]+" " );
			}
			System.out.println();
		}*/

		/*
		// 3eme test
		// pour afficher le tableau score
		for(int i = 0 ;i < nb_choix  ; i++) {

			for(int j =0 ; j <= 1 ;j++) {

				System.out.print("Tab_repondre["+i+"]["+j+"]="+Table_score[i][j]+" " );
			}
			System.out.println();
		}
		*/

        /****************tableau de choix  ******************/

        long[][] Table_option;
        Table_option = new long [nb_choix][3];
        for(int i = 0 ; i < nb_choix ; i++)
            for(int j = 0 ; j <= 2 ; j++) {
                Table_option[i][j]= 0;
            }

        // les ids pour les projets
        Table_option[0][0]=1;
        Table_option[1][0]=2;
        Table_option[2][0]=3;
        Table_option[3][0]=4;
        Table_option[4][0]=5;

        //capacit� min
        Table_option[0][1]=2;
        Table_option[1][1]=3;
        Table_option[2][1]=4;
        Table_option[3][1]=3;
        Table_option[4][1]=3;

        //capacit� max
        Table_option[0][2]=6;
        Table_option[1][2]=5;
        Table_option[2][2]=6;
        Table_option[3][2]=5;
        Table_option[4][2]=4;

		/*
		//4eme test
		for(int i = 0 ; i < nb_choix ; i++) {
			for(int j = 0 ; j <= 2 ; j++) {
				System.out.print("Tab_option["+i+"]["+j+"]="+Table_option[i][j]+" ");
			}
			System.out.println();
		}
		*/

        long [][] T_res;
        T_res= new long [nb_etudiant][2];




        AlgoAffect algo1=new AlgoAffect(nb_etudiant , nb_choix ,Table_repondre);
        T_res=algo1.affectation(nb_etudiant, nb_choix, Table_repondre,Table_option);

        for(int i = 0 ;i < nb_etudiant  ; i++) {

            for(int j =0 ; j <= 1 ;j++) {

                System.out.print("Tab_d["+i+"]["+j+"]="+T_res[i][j]+" ");
            }
            System.out.println();
        }


    }
}