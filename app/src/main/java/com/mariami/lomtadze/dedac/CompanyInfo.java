package com.mariami.lomtadze.dedac;

public class CompanyInfo {
    private String Name = "იტვირთება"; // კომპანიის სახელი
    private String Id = "-1"; //კომპანიის აიდი
    private String LogoUrl = "";
    private String Category = "იტვირთება";
    private String Description = "იტვირთება";
    private String New = "";

    public CompanyInfo() {
    }

    public CompanyInfo(String Name, String Id, String LogoUrl, String Category, String Description, String New) {
        if (!Id.equals("null")) this.Id = Id;
        if (!LogoUrl.equals("null")) this.LogoUrl = LogoUrl;
        if (!Name.equals("null")) this.Name = Name;
        if (!Category.equals("null")) this.Category = Category;
        if (!Description.equals("null")) this.Description = Description;
        if (!New.equals("null")) this.New = New;
    }

    public String getName() {
        return Name;
    }

    public String getId() {
        return Id;
    }

    public String getLogoUrl() {
        return LogoUrl;
    }

    public String getCategory() {
        return Category;
    }

    public String getDescription() {
        return Description;
    }

    public String getNew() {
        return New;
    }
}
