package model;

import javax.persistence.*;

@Entity
@Table
public class Answer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

}
