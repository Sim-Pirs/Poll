package sondage.algo;

import sondage.entity.model.Choice;
import sondage.entity.model.Respondent;
import sondage.entity.model.SurveyItem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AlgoAdapter {

    private List<Choice> choiceList;
    private List<Respondent> respondentsList;
    private List<SurveyItem> itemsList;

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

    public void getResult(){
        AlgoAffect algoAffect = new AlgoAffect(getNbRespondents(), getNbItems(), getRespons());

    }


    private long[][] getRespons(){
        long[][] tableRespons = new long[getNbRespondents() + 1][getNbItems() +1];

        for(int i = 0; i < tableRespons.length - 1; ++i){
            for(int j = 0; j < tableRespons[i].length - 1; ++j){
                if(i == 0 && j == 0) continue;
                if(j == 0) tableRespons[i][j] = this.respondentsList.get(i).getId();
                if(i == 0) tableRespons[i][j] = this.itemsList.get(j).getId();
                if(i != 0 && j != 0) break;
            }
        }

        for(int i = 1; i < tableRespons.length - 1; ++i){
            for(int j = 1; j < tableRespons[i].length - 1; ++j){
                long idResp = tableRespons[i][0];
                long idItem = tableRespons[0][j];

                for(Choice c : this.choiceList){
                    if(c.getRespondent().getId() != idResp || c.getItem().getId() != idItem) continue;
                    tableRespons[i][j] = c.getScore();
                }
            }
        }

        return tableRespons;
    }

    private int getNbRespondents(){
        return this.respondentsList.size();
    }

    private int getNbItems(){
        return this.itemsList.size();
    }
}

