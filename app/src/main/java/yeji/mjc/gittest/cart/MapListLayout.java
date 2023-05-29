package yeji.mjc.gittest.cart;

public class MapListLayout {
    private String name;        // 장소명
    private String road;        // 도로명 주소
    private String address;     // 지번 주소
    private String phone;       // 전화번호
    private String x;           // 경도(Longitude)
    private String y;           // 위도(Latitude)

    public MapListLayout(String name, String road, String address, String x, String y) {
        this.name = name;
        this.road = road;
        this.address = address;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public String getRoad() {
        return road;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRoad(String road) {
        this.road = road;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }
}