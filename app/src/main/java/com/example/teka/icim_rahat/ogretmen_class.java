package com.example.teka.icim_rahat;

/**
 * Created by teka on 8.8.2017.
 */

public class ogretmen_class {
    public String danisman;
    public String istecrubesi;
    public String mezunolduguokul;
    public String ogretmenisim;
public String kullaniciadi;
    public ogretmen_class()
    {}
    public ogretmen_class(String isim,String mezunolduguokul,String istecrubesi, String danisman,String kullaniciadi)
    {this.danisman=danisman;
        this.istecrubesi=istecrubesi;
        this.mezunolduguokul=mezunolduguokul;
        ogretmenisim = isim;
this.kullaniciadi=kullaniciadi;

    }
}
