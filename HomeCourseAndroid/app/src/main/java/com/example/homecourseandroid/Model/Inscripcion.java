package com.example.homecourseandroid.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Inscripcion {
    private int idInscripcion;
    private String cursoId;
    private String usuarioId;
    private String insFecha;
    private String codOpe;

    public Inscripcion(int idInscripcion, String cursoId, String usuarioId, String insFecha, String codOpe) {
        this.idInscripcion = idInscripcion;
        this.cursoId = cursoId;
        this.usuarioId = usuarioId;
        this.insFecha = insFecha;
        this.codOpe = codOpe;
    }

    public Inscripcion() {
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int id) {
        this.idInscripcion = id;
    }

    public String getCursoId() {
        return cursoId;
    }

    public void setCursoId(String cursoId) {
        this.cursoId = cursoId;
    }

    public String getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(String usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getInsFecha() {
        return insFecha;
    }

    public void setInsFecha(String insFecha) {
        this.insFecha = insFecha;
    }

    public String getCodOpe() {
        return codOpe;
    }

    public void setCodOpe(String codOpe) {
        this.codOpe = codOpe;
    }
}
