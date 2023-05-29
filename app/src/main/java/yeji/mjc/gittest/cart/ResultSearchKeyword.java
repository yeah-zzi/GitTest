package yeji.mjc.gittest.cart;

// 검색 결과를 담는 클래스
import java.util.List;

public class ResultSearchKeyword{
    private PlaceMeta meta;                 // 장소 메타데이터
    private List<Place> documents;          // 검색 결과

    public PlaceMeta getMeta() {
        return meta;
    }

    public List<Place> getDocuments() {
        return documents;
    }

    public void setMeta(PlaceMeta meta) {
        this.meta = meta;
    }

    public void setDocuments(List<Place> documents) {
        this.documents = documents;
    }

    @Override
    public String toString() {
        return "ResultSearchKeyword{" +
                "meta=" + meta +
                ", documents=" + documents +
                '}';
    }
}

class PlaceMeta{
    private int total_count;                // 검색어에 검색된 문서 수
    private int pageable_count;             // total_count 중 노출 가능 문서 수, 최대 45 (API에서 최대 45개 정보만 제공)
    private Boolean is_end;                 // 현재 페이지가 마지막 페이지인지 여부, 값이 false면 page를 증가시켜 다음 페이지를 요청할 수 있음
    private RegionInfo same_name;           // 질의어의 지역 및 키워드 분석 정보

    public int getTotal_count() {
        return total_count;
    }

    public int getPageable_count() {
        return pageable_count;
    }

    public Boolean getIs_end() {
        return is_end;
    }

    public RegionInfo getSame_name() {
        return same_name;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public void setPageable_count(int pageable_count) {
        this.pageable_count = pageable_count;
    }

    public void setIs_end(Boolean is_end) {
        this.is_end = is_end;
    }

    public void setSame_name(RegionInfo same_name) {
        this.same_name = same_name;
    }

    @Override
    public String toString() {
        return "PlaceMeta{" +
                "total_count=" + total_count +
                ", pageable_count=" + pageable_count +
                ", is_end=" + is_end +
                ", same_name=" + same_name +
                '}';
    }
}

class RegionInfo{
    private List<String> region;             // 질의어에서 인식된 지역의 리스트, ex) '중앙로 맛집' 에서 중앙로에 해당하는 지역 리스트
    private String keyword;                  // 질의어에서 지역 정보를 제외한 키워드, ex) '중앙로 맛집' 에서 '맛집'
    private String selected_region;          // 인식된 지역 리스트 중, 현재 검색에 사용된 지역 정보

    public List<String> getRegion() {
        return region;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getSelected_region() {
        return selected_region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setSelected_region(String selected_region) {
        this.selected_region = selected_region;
    }

    @Override
    public String toString() {
        return "RegionInfo{" +
                "region=" + region +
                ", keyword='" + keyword + '\'' +
                ", selected_region='" + selected_region + '\'' +
                '}';
    }
}

class Place{
    private String id;                      // 장소 ID
    private String place_name;              // 장소명, 업체명
    private String category_name;           // 카테고리 이름
    private String category_group_code;     // 중요 카테고리만 그룹핑한 카테고리 그룹 코드
    private String category_group_name;     // 중요 카테고리만 그룹핑한 카테고리 그룹명
    private String phone;                   // 전화번호
    private String address_name;            // 전체 지번 주소
    private String road_address_name;       // 전체 도로명 주소
    private String x;                       // X 좌표값 혹은 longitude
    private String y;                       // Y 좌표값 혹은 latitude
    private String place_url;               // 장소 상세페이지 URL
    private String distanc;                 // 중심좌표까지의 거리. 단, x,y 파라미터를 준 경우에만 존재. 단위는 meter

    public String getId() {
        return id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public String getCategory_name() {
        return category_name;
    }

    public String getCategory_group_code() {
        return category_group_code;
    }

    public String getCategory_group_name() {
        return category_group_name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress_name() {
        return address_name;
    }

    public String getRoad_address_name() {
        return road_address_name;
    }

    public String getX() {
        return x;
    }

    public String getY() {
        return y;
    }

    public String getPlace_url() {
        return place_url;
    }

    public String getDistanc() {
        return distanc;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setCategory_group_code(String category_group_code) {
        this.category_group_code = category_group_code;
    }

    public void setCategory_group_name(String category_group_name) {
        this.category_group_name = category_group_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress_name(String address_name) {
        this.address_name = address_name;
    }

    public void setRoad_address_name(String road_address_name) {
        this.road_address_name = road_address_name;
    }

    public void setX(String x) {
        this.x = x;
    }

    public void setY(String y) {
        this.y = y;
    }

    public void setPlace_url(String place_url) {
        this.place_url = place_url;
    }

    public void setDistanc(String distanc) {
        this.distanc = distanc;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", place_name='" + place_name + '\'' +
                ", category_name='" + category_name + '\'' +
                ", category_group_code='" + category_group_code + '\'' +
                ", category_group_name='" + category_group_name + '\'' +
                ", phone='" + phone + '\'' +
                ", address_name='" + address_name + '\'' +
                ", road_address_name='" + road_address_name + '\'' +
                ", x='" + x + '\'' +
                ", y='" + y + '\'' +
                ", place_url='" + place_url + '\'' +
                ", distanc='" + distanc + '\'' +
                '}';
    }
}