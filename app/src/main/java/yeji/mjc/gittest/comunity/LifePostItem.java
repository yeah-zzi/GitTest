package yeji.mjc.gittest.comunity;

public class LifePostItem {
    public String title;  // 게시물 제목
    public String script;  // 게시글
    public int resId;   // 이미지 번호

    public LifePostItem(String title, String script, int resId) {
        this.title = title;
        this.script = script;
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getScript() {
        return script;
    }

    public void setScript(String script) {
        this.script = script;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }
}
