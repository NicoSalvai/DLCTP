/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package dlc.tp.web.aux;

/**
 *
 * @author scarafia
 */
public class ErrorMsg {
    private String title = null;
    private String text = null;

    public ErrorMsg() {
        super();
    }

    public ErrorMsg(String title, String text) {
        super();
        this.title = title;
        this.text = text;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    public String toString() {
        return this.title + ": " + this.text;
    }


}
