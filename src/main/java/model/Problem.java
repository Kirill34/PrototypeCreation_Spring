package model;

import javax.persistence.*;

@Entity
@Table
public class Problem {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String text;

    private String funcName;

    private String notice;

    public Long getId() {
        return id;
    }

    public String getText()
    {
        return text;
    }

    public String getNotice() {
        return notice;
    }

    public Problem(String text, String funcName, String notice)
    {
        this.text = text;
        this.funcName = funcName;
        this.notice = notice;
    }

    public Problem() {

    }
}
