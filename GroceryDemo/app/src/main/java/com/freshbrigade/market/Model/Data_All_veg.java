package com.freshbrigade.market.Model;


 public class Data_All_veg {
   String imageView;
    String name,btn,rbtn,code,add_kart,sn;
    String price;


     public String getSn() {
         return sn;
     }

     public void setSn(String sn) {
         this.sn = sn;
     }

     public Data_All_veg(String imageView, String name, String btn, String rbtn, String code, String add_kart, String sn , String price) {
        this.imageView = imageView;
        this.name = name;
        this.btn = btn;
        this.rbtn = rbtn;
        this.code = code;
        this.add_kart=add_kart;
        this.sn=sn;
        this.price=price;
    }

     public String getPrice() {
         return price;
     }

     public void setPrice(String price) {
         this.price = price;
     }

     public String getImageView() {
        return imageView;
    }

    public void setImageView(String imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBtn() {
        return btn;
    }

    public void setBtn(String btn) {
        this.btn = btn;
    }

    public String getRbtn() {
        return rbtn;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setRbtn(String rbtn) {
        this.rbtn = rbtn;
    }
    public String getAdd_kart() {
        return add_kart;
    }

    public void setAdd_kart(String add_kart) {
        this.add_kart = add_kart;
    }
}
