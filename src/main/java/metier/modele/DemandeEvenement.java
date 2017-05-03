/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package metier.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import util.Moment;

/**
 *
 * @author hmartin2
 */
@Entity
public class DemandeEvenement implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Version
    private int version;
    @Temporal(TemporalType.DATE)
    private Date date;
    private Moment day_moment;
    @ManyToOne
    private Activite activity;
    @ManyToMany
    private List<Adherent> list_adher;
    @OneToOne
    private Evenement event;

    protected DemandeEvenement() {
    }

    public DemandeEvenement(Date date, Moment day_moment, Activite activity, Adherent adherent) {
        this.date = date;
        this.day_moment = day_moment;
        this.activity = activity;
        list_adher = new ArrayList<>();
        list_adher.add(adherent);
    }

    public boolean ajoutAdherent(Adherent adherent) {
        if (!listContains(adherent, list_adher)) {
            list_adher.add(adherent);
            return true;
        }
        return false;
    }

    private boolean listContains(Adherent adherent, List<Adherent> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId() == adherent.getId()) {
                return true;
            }
        }
        return false;
    }

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Moment getDay_moment() {
        return day_moment;
    }

    public Activite getActivity() {
        return activity;
    }

    public List<Adherent> getList_adher() {
        return list_adher;
    }

    public Evenement getEvent() {
        return event;
    }

    public void setEvent(Evenement event) {
        this.event = event;
    }

    public int getListSize() {
        return list_adher.size();
    }

    /*
    public int addAdherent(Adherent a){
        list_adher.add(a);
        return list_adher.size();
    }*/

    @Override
    public String toString() {
        return "DemandeEvenement{" + "id=" + id + ", date=" + date + ", day_moment=" + day_moment + ", activity=" + activity + ", event=" + event + '}';
    }

}
