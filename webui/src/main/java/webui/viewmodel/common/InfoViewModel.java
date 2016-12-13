package webui.viewmodel.common;

/**
 * Created by admin on 08.11.2016.
 */
public class InfoViewModel {

    public String text;

    public int id;

    public InfoViewModel(String text, int id) {
        this.text = text;
        this.id = id;
    }

    public InfoViewModel() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
