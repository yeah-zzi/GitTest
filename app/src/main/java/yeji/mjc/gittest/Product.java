package yeji.mjc.gittest;

public class Product {
    String PRDT_NM;
    String Img;

    public Product() {}

    public Product(String PRDT_NM,String Img){
        this.PRDT_NM = PRDT_NM;
        this.Img = Img;
    }

    public String getPRDT_NM(){
        return PRDT_NM;
    }

    public void setPRDT_NM(String PRDT_NM){
        this.PRDT_NM = PRDT_NM;
    }

    public String getImg() {return Img;}

    public void setImg(String Img) {this.Img = Img;}
}
