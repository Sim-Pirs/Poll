package sondage.algo;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import sondage.entity.model.Choice;
import sondage.entity.model.Respondent;
import sondage.entity.model.SurveyItem;

public class AlgoAdapter {

    private List<Choice> choiceList;
    private List<Respondent> respondentsList;
    private List<SurveyItem> itemsList;

    /*****consrtucteur****/
    /**
     *
     * @param choices
     */
    public AlgoAdapter(List<Choice> choices){
        this.choiceList = choices;

        Set<Respondent> respondents = new HashSet<>();
        Set<SurveyItem> items = new HashSet<>();
        for(Choice c : choices) {
            respondents.add(c.getRespondent());
            items.add(c.getItem());
        }

        this.respondentsList = new ArrayList<>(respondents);
        this.itemsList = new ArrayList<>(items);
    }

    /**
     *
     * @return le résultat final d'affectation
     */
    public List<Choice> getResult(){
        long[][] result = AlgoAffect.affectation(getNbRespondents(), getNbItems(), getRespons(), getChoix());

        List<Choice> finalChoices = new ArrayList<>();

        for(int i = 0; i < getNbRespondents(); ++i){
            for(Choice c : choiceList){
                if(c.getItem().getId() != result[i][0]) continue;
                if(c.getRespondent().getId() != result[i][1]) continue;
                finalChoices.add(c);

            }

        }
        return finalChoices;
    }

    /**
     *
     * @return un tableau qui contient les id items et les id respondents et l'ordre les choix des respondents de chaque projet
     */
    private long[][] getRespons(){
        long[][] tableRespons = new long[getNbRespondents() + 1][getNbItems() +1];

        /* pour récupérer les id repondents et les id items*/
        for(int i = 0; i < tableRespons.length; ++i){
            for(int j = 0; j < tableRespons[i].length; ++j){
                if(i == 0 && j == 0) continue;
                if(j == 0) tableRespons[i][j] = this.respondentsList.get(i-1).getId();
                if(i == 0) tableRespons[i][j] = this.itemsList.get(j-1).getId();
                if(i != 0 && j != 0) break;
            }
        }

        /* pour récupérer  ordre les choix des respondents de chaque projet*/
        for(int i = 1; i < tableRespons.length; ++i){
            for(int j = 1; j < tableRespons[i].length; ++j){
                long idResp = tableRespons[0][i];
                long idItem = tableRespons[j][0];

                for(Choice c : this.choiceList){
                    if(c.getRespondent().getId() != idResp || c.getItem().getId() != idItem) continue;
                    tableRespons[i][j] = c.getScore();
                }
            }
        }

        return tableRespons;
    }

    /**
     *
     * @return un tableau qui contient les id items avec les capacités min et max de chaque item
     */
    private long[][] getChoix(){
        long [][] tab = new long[getNbItems()][3];

        for(int i=0; i<tab.length; i++) {
            for(int j=0; j<2; j++) {
                if(j==0)tab[i][j] = this.itemsList.get(i).getId();
                if(j==1)tab[i][j] = this.itemsList.get(j).getNbPersMin();
                if(j==2)tab[i][j] = this.itemsList.get(j).getNbPersMax();
            }
        }

        return tab;
    }

    /**
     *
     * @return le nombre des respondents
     */
    public int getNbRespondents(){
        return this.respondentsList.size();
    }

    /**
     *
     * @return le nombre des items
     */
    private int getNbItems(){
        return this.itemsList.size();
    }
}