package com.poseidoncapitalsolution.trading.model;

import jakarta.persistence.*;

@Entity
@Table(name = "rule")
public class Rule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    private String name;

    private String description;

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSql_part() {
        return sql_part;
    }

    public void setSql_part(String sql_part) {
        this.sql_part = sql_part;
    }

    @Column(name = "json")
    private String json;

    @Column(name = "template")
    private String template;

    @Column(name = "sql_part")
    private String sql_part;

}