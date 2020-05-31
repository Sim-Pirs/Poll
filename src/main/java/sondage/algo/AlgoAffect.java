package sondage.algo;

import java.util.Arrays;
import java.util.Comparator;

public class AlgoAffect {

    /*************calculScore**************/

    /**
     *
     * @param nbRespondents (nombre total des respondents)
     * @param nbItems (nombre total des items)
     * @param tableRespons (tableau qui contient des items dans l'ordre par rapport à l'item le plus demandé jusqu' à l'item  le moins demandé )
     * @return tableOrdonne (tableau qui contient des items dans l'ordre par rapport à l'item le plus demandé jusqu' à l'item  le moins demandé )
     * But : Calculer les scores pour les items
     */

    public static long [][] calculScore(int nbRespondents , int nbItems ,long[][] tableRespons){
        /*Initialiser le tableau ordonne*/
        long[][] tableOrdonne;
        tableOrdonne = new long [nbItems][2];
        for(int i = 0 ; i < nbItems ; i++) {
            for(int j = 0 ; j < 2 ; j++) {
                tableOrdonne[i][j]= 0;
            }
        }
        /*Calculer les scores pour les items*/
        for(int i = 1 ; i <= nbItems ; i++) {
            tableOrdonne[i-1][0] = tableRespons[0][i];
            for(int j = 1 ; j <= nbRespondents ;j++) {
                tableOrdonne[i-1][1] = tableOrdonne[i-1][1] + tableRespons[j][i];
            }
        }
        /*ordonner les items*/
        Arrays.sort(tableOrdonne, new Comparator<long[]>() {
            @Override
            public int compare(long[] o2, long[] o1) {
                return Long.compare(o2[1], o1[1]);
            }
        });

        return tableOrdonne;
    }


    /*************getMin**************/
    /**
     *
     * @param item_id (identifiant de l'item)
     * @param tableCapacite (tableau qui contient les identifiants des items avec la capacité min et max pour chaque item)
     * @param nbItems (nombre total des items)
     * @return  retourne la capacité min de l'identifiant de l'item(item_id)
     * But: trouver la capacité min pour le item_id
     */

    private static long getMin(long item_id, long[][] tableCapacite,long nbItems) {
        for (int i = 0; i < nbItems; i++) {
            if(tableCapacite [i][0]==item_id) {
                return tableCapacite [i][1];
            }
        }
        return 0;
    }


    /*************getMax**************/
    /**
     *
     * @param item_id (identifiant de l'item)
     * @param tableCapacite (tableau qui contient les identifiants des items avec la capacité min et max pour chaque item)
     * @param nbItems (nombre total des items)
     * @return retourne la capacité max de identifiant de l'item(item_id)
     * But: trouver la capacité max pour le item_id
     */

    private static long getMax(long item_id, long[][] tableCapacite,long nbItems) {
        for (int i = 0; i < nbItems; i++) {
            if(tableCapacite [i][0]==item_id) {
                return tableCapacite [i][2];
            }
        }

        return 0;
    }


    /*************notAffected**************/
    /**
     *
     * @param respondent_id (identifiant du respondent)
     * @param tableResultTmp (tableau qui contient les resultat temporaire des affectations)
     * @param nbRespondents (nombre total des respondents)
     * @return true si le respondent_id a déja pris un item false sinon
     * But: vérifier si le respondent a déja pris un item
     */

    private static boolean notAffected(long respondent_id, long[][] tableResultTmp,long nbRespondents) {
        for (int i = 0; i < nbRespondents; i++) {
            if(tableResultTmp [i][1]==respondent_id) {
                return false;
            }
        }

        return true;
    }


    /*************getActual**************/
    /**
     *
     * @param tableActual (tableau qui contient les id des items avec le nombre de réspondent qui se trouve dans chaque item)
     * @param nbItems (nombre total des items)
     * @param item_id (identifiant de l'item)
     * @return l'item dans le tableActual
     * But: trouver la item actual
     */

    private static long getActual(long[][] tableActual, int nbItems, long item_id) {
        for (int i = 0; i < nbItems; i++)
            if(tableActual [i][0]==item_id)
                return tableActual[i][1];
        return 0;
    }


    /*************updateActual**************/

    /**
     *
     * @param tableActual (tableau qui contient les id des items avec le nombre de réspondent qui se trouve dans chaque item)
     * @param nbItems (nombre total des items)
     * @param item_id (identifiant de l'item)
     * @param value la répétation de chaque item
     * But: mise a jour de l'item actual dans le table
     */

    private static void updateActual(long[][] tableActual, int nbItems, long item_id, long value) {
        for (int i = 0; i < nbItems; i++)
            if(tableActual [i][0]==item_id)
                tableActual[i][1]=value;
    }


    /*************affectation**************/
    /**
     *
     * @param nbRespondents (nombre total des respondents)
     * @param nbItems (nombre total des items)
     * @param tableRespons (table qui contient les reponses des respondents pour chaque item)
     * @param tableCapacite (tableau qui contient les id des items avec la capacité min et max pour chaque item)
     * @return tableResultFinal (tableau qui contient les id items et les id respondents)
     * But : chaque respondent a été affecté a un item
     */

    public static long [][] affectation(int nbRespondents , int nbItems ,long[][] tableRespons , long[][] tableCapacite) {

        long[][] tableOrdonne = calculScore( nbRespondents , nbItems , tableRespons); /*stock le score dans le tableOrdonne*/
        long[][] tableResultFinal;

        /*	tableau qui contient les id des items avec le nombre de réspondent qui se trouve dans chaque item*/
        long[][] tableActual;

        /*indice pour le tableResultFinal*/
        int index = 0;

        tableResultFinal = new long [nbRespondents][2];
        tableActual = new long [nbItems][2];

        /*Initialiser le tableau actual*/
        for (int i = 0; i < nbItems; i++) {
            tableActual[i][0]=tableOrdonne[i][0];
            tableActual[i][1]=0;
        }
        int ordre = 1;
        while (ordre<=nbItems){
            /* pour le tableau tableOrdonne */
            for(int i=0 ; i < nbItems ; i++) {
                /* pour le tableau tableRespons */
                for(int j=0 ; j < nbItems ; j++) {
                    /*condition pour lier le tableau tableOrdonne avec le tableau tableRespons */
                    if(tableOrdonne[i][0] == tableRespons[0][j+1] ){
                        /*on boucle sur tous les respondents*/
                        for(int k=1 ; k <= nbRespondents ; k++) {
                            /*condition1 : on cherche s'il existe des respondents non affectés */
                            /*condition2 : si condition1 oui , on vérifie les respondents non affectés dans table_Respons par rapport l'ordre de choix */
                            /*condition3 : si condition2 oui , on vérifie pour chaque item si la capacité min est respectée*/
                            if(notAffected(tableRespons[k][0],tableResultFinal,nbRespondents) && tableRespons[k][j+1] == ordre  && tableActual[i][1]<getMin(tableOrdonne[i][0],tableCapacite,nbItems)) {
                                /*remplir les id_item dans le tableResultFinal*/
                                tableResultFinal [index][0] = tableOrdonne[i][0];
                                /*remplir les id_respondents dans le tableResultFinal*/
                                tableResultFinal [index][1] = tableRespons[k][0];
                                index++;
                                /*on incrémente le nombre de respondent qui se trouve dans chaque item */
                                tableActual[i][1]=tableActual[i][1]+1;

                            }

                        }
                    }
                }
            }
            ordre++;
        }


        /* supprime les items qui n'acceptent pas la capacité min et on commence par le projet le moins demandées*/
        for (int i = nbItems-1; i >= 0; i--) {
            if(tableActual[i][1]<getMin(tableActual[i][0],tableCapacite,nbItems)) {
                cancelProject(nbRespondents,nbItems,tableRespons,tableCapacite,tableResultFinal, tableActual,tableActual[i][0]);

            }
        }

       /* Arrays.sort(tableResultFinal , new Comparator<long[]>() {
            @Override
            public int compare(long[] o2, long[] o1) {
                return Long.compare(o2[0], o1[0]);
            }
        }); */

        return tableResultFinal;
    }


    /*************cancelProject**************/
    /**
     *
     * @param nbRespondents (nombre total des respondents)
     * @param nbItems (nombre total des items)
     * @param tableRespons (table qui contient les reponses des respondents pour chaque item )
     * @param tableCapacite (tableau qui contient les id des items avec la capacité min et max pour chaque item)
     * @param tableResultFinal (tableau qui contient les id items et les id respondents)
     * @param tableActual (tableau qui contient les id des items avec la répétation de chaque item)
     * @param item_id (identifiant de l'item)
     * But : supprimer les item qui ne sont pas acceptés par la capacité min
     */

    private static void cancelProject(int nbRespondents, int nbItems, long[][] tableRespons, long[][] tableCapacite,
                                      long[][] tableResultFinal,long[][] tableActual, long item_id) {
        for(int k=0 ; k < nbRespondents ; k++) {
            if(tableResultFinal[k][0]==item_id) {
                moveStudentToNewProject(nbRespondents,nbItems,tableRespons,tableCapacite,tableResultFinal,tableActual,item_id,k);
            }
        }

    }

    /*************moveStudentToNewProject**************/
    /**
     *
     * @param nbRespondents (nombre total des respondents)
     * @param nbItems (nombre total des items)
     * @param tableRespons (table qui contient les reponses des respondents pour chaque item )
     * @param tableCapacite (tableau qui contient les id des items avec la capacité min et max pour chaque item)
     * @param tableResultFinal (tableau qui contient les id items et les id respondents)
     * @param tableActual (tableau qui contient les id des items avec la répétation de chaque item)
     * @param oldItem (l'item qui a été supprimé)
     * @param idx (indice pour passer sur tous les respondents qui n'ont pas pris l'item)
     * But : déplacer les respondents qui étaient dans l'item qui doit être supprimé dans d'autres items
     */

    private static void moveStudentToNewProject(int nbRespondents, int nbItems, long[][] tableRespons, long[][] tableCapacite,
                                                long[][] tableResultFinal,long[][] tableActual, long oldItem,int idx) {
        int level = 1;
        while(level <=nbItems) {

            for(int i=1 ; i <= nbRespondents ; i++) {
                if(tableRespons[i][0]==tableResultFinal[idx][1]) {
                    for(int j=0 ; j < nbItems ; j++) {
                        long new_project_actual = getActual(tableActual,nbItems,tableRespons[0][j+1]);
                        long old_project_actual = getActual(tableActual,nbItems,oldItem);
                        long new_project_id= tableRespons[0][j+1];
                        if(new_project_id!=oldItem && new_project_actual<getMax(new_project_id,tableCapacite,nbItems)&&new_project_actual != 0 && tableRespons[i][j+1] == level ) {
                            tableResultFinal[idx][0]=new_project_id;
                            updateActual(tableActual,nbItems,new_project_id,new_project_actual+1);
                            updateActual(tableActual,nbItems,oldItem,old_project_actual-1);
                            return;

                        }
                    }

                }
            }
            level ++;
        }
    }
}