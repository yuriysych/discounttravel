package com.example.ysych.discounttravel.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ysych on 12.11.2015.
 */
@DatabaseTable(tableName = "categories")
public class Country {

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "id", id = true)
    @SerializedName("id")
    @Expose
    int id;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "alias")
    @SerializedName("alias")
    @Expose
    String alias;

    @DatabaseField(canBeNull = false, dataType = DataType.STRING, columnName = "title")
    @SerializedName("title")
    @Expose
    String title;

    @DatabaseField(canBeNull = false, dataType = DataType.INTEGER, columnName = "published")
    @SerializedName("published")
    @Expose
    int published;

    public int getPublished() {
        return published;
    }

    public int getId() {
        return id;
    }

    public String getAlias() {
        return alias;
    }

    public String getTitle() {
        return title;
    }

}