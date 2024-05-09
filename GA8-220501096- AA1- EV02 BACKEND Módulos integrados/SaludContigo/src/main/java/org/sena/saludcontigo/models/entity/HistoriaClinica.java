package org.sena.saludcontigo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Historias_Clinicas")
public class HistoriaClinica implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idhistoria;

    @Column(name = "Fecha_Historia", nullable = false)
    private Date fechaHistoria;

    @Column(name = "Antecedentes_Historia", length = 500)
    private String antecedentesHistoria;

    @Column(name = "Examenes_Historia", length = 500)
    private String examenesHistoria;

    @Column(name = "Diagnostico_Historia", length = 500)
    private String diagnosticoHistoria;

    @Column(name = "Tratamiento_Historia", length = 500)
    private String tratamientoHistoria;


    /**
     * Relaciones externas
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Pacientes_Idpaciente", referencedColumnName = "idpaciente")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Medicos_Idmedico", referencedColumnName = "idmedico")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Medico medico;


    /**
     * Constructor
     */
    public HistoriaClinica() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdhistoria() {
        return idhistoria;
    }

    public Date getFechaHistoria() {
        return fechaHistoria;
    }

    public String getAntecedentesHistoria() {
        return antecedentesHistoria;
    }

    public String getExamenesHistoria() {
        return examenesHistoria;
    }

    public String getDiagnosticoHistoria() {
        return diagnosticoHistoria;
    }

    public String getTratamientoHistoria() {
        return tratamientoHistoria;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public Medico getMedico() {
        return medico;
    }


    /**
     * Métodos Setters
     */
    public void setIdhistoria(Long idhistoria) {
        this.idhistoria = idhistoria;
    }

    public void setFechaHistoria(Date fechaHistoria) {
        this.fechaHistoria = fechaHistoria;
    }

    public void setAntecedentesHistoria(String antecedentesHistoria) {
        this.antecedentesHistoria = antecedentesHistoria;
    }

    public void setExamenesHistoria(String examenesHistoria) {
        this.examenesHistoria = examenesHistoria;
    }

    public void setDiagnosticoHistoria(String diagnosticoHistoria) {
        this.diagnosticoHistoria = diagnosticoHistoria;
    }

    public void setTratamientoHistoria(String tratamientoHistoria) {
        this.tratamientoHistoria = tratamientoHistoria;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setMedico(Medico medico) {
        this.medico = medico;
    }


    /**
     * *
     */
    private static final long serialVersionUID = 1L;
}