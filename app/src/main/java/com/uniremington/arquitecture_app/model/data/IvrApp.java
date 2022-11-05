package com.uniremington.arquitecture_app.model.data;


public class IvrApp {
    private Integer idivrurl;
    private String didivr;
    private String nombreivr;
    private String puntoruteo;
    private String urlivr;
    private String entorno;
    private Character estado;

    public IvrApp(Integer idivrurl, String didivr, String nombreivr, String puntoruteo, String urlivr, String entorno, Character estado) {
        this.idivrurl = idivrurl;
        this.didivr = didivr;
        this.nombreivr = nombreivr;
        this.puntoruteo = puntoruteo;
        this.urlivr = urlivr;
        this.entorno = entorno;
        this.estado = estado;
    }


    public Integer getIdivrurl() {
        return idivrurl;
    }

    public void setIdivrurl(Integer idivrurl) {
        this.idivrurl = idivrurl;
    }

    public String getDidivr() {
        return didivr;
    }

    public void setDidivr(String didivr) {
        this.didivr = didivr;
    }

    public String getNombreivr() {
        return nombreivr;
    }

    public void setNombreivr(String nombreivr) {
        this.nombreivr = nombreivr;
    }

    public String getPuntoruteo() {
        return puntoruteo;
    }

    public void setPuntoruteo(String puntoruteo) {
        this.puntoruteo = puntoruteo;
    }

    public String getUrlivr() {
        return urlivr;
    }

    public void setUrlivr(String urlivr) {
        this.urlivr = urlivr;
    }

    public String getEntorno() {
        return entorno;
    }

    public void setEntorno(String entorno) {
        this.entorno = entorno;
    }

    public Character getEstado() {
        return estado;
    }

    public void setEstado(Character estado) {
        this.estado = estado;
    }
}