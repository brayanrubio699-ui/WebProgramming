package com.example.demo.model;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="Tasks")
public class Task implements Serializable {
    /*
     * 	id int auto_increment not null,
        duration int not null,
        descr varchar(250) not null,
        finished boolean not null,
     */

    @Id
    @GeneratedValue
    @Column(name="id")
    private Integer id;

    @Basic

    @Column(name="descr")
    private String description;

    @Column(name="duration")
    private int duration;

    @Column(name="finished")
    private boolean finished;

    public int getId()
    {
        return this.id;
    }

    public void setId(int pId)

    {
        this.id = pId;
    }

    public int getDuration()
    {
        return this.duration;
    }

    public void setDuration(int duration)

    {
        this.id = duration;
    }

    public String getDescr()
    {
        return this.description;
    }

    public void setDescr(String descr)

    {
        this.description = descr;
    }

    public boolean isFinished()
    {
        return this.finished;
    }

    public void setFinished(boolean finished)

    {
        this.finished = finished;
    }

}
