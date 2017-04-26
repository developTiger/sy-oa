package com.sunesoft.lemon.deanery.deliverables.application.criteria;

import com.sunesoft.lemon.fr.results.PagedCriteria;

/**
 * Created by swb on 2016/9/12.
 */
public class DeliverResearchCriteria extends PagedCriteria{
    private String categoryPro;

    private String achievements;

    private String category;

    private String company;

    private String cooperate;

    public String getCategoryPro() {
        return categoryPro;
    }

    public void setCategoryPro(String categoryPro) {
        this.categoryPro = categoryPro;
    }

    public String getAchievements() {
        return achievements;
    }

    public void setAchievements(String achievements) {
        this.achievements = achievements;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCooperate() {
        return cooperate;
    }

    public void setCooperate(String cooperate) {
        this.cooperate = cooperate;
    }
}
