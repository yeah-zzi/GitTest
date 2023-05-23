package yeji.mjc.gittest.firebase;

public class Product {
    String Product_name;

    public Product() {};

    public void setProduct_name(String Product_name){
        this.Product_name = Product_name;
    }

    public String getProduct_name(){
        return Product_name;
    }

    public Product(String Product_name){
        this.Product_name = Product_name;
    }
}
