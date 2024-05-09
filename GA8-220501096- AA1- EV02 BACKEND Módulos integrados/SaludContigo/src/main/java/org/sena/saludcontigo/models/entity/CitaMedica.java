package org.sena.saludcontigo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Citas_Medicas")
public class CitaMedica implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcita;

    @Column(name = "Tipo_Cita", nullable = false)
    private String tipoCita;

    @Column(name = "Fecha_Cita", nullable = false)
    private Date fechaCita;

    @Column(name = "Hora_Cita", nullable = false)
    private Date horaCita;

    @Column(name = "Lugar_Cita", nullable = false)
    private String lugarCita;

    @Column(name = "Estado_Cita", nullable = false)
    private String estadoCita;

    @Column(name = "Observacion_Cita")
    private String observacionCita;


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
    public CitaMedica() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdcita() {
        return idcita;
    }

    public String getTipoCita() {
        return tipoCita;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public Date getHoraCita() {
        return horaCita;
    }

    public String getLugarCita() {
        return lugarCita;
    }

    public String getEstadoCita() {
        return estadoCita;
    }

    public String getObservacionCita() {
        return observacionCita;
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
    public void setIdcita(Long idcita) {
        this.idcita = idcita;
    }

    public void setTipoCita(String tipoCita) {
        this.tipoCita = tipoCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public void setHoraCita(Date horaCita) {
        this.horaCita = horaCita;
    }

    public void setLugarCita(String lugarCita) {
        this.lugarCita = lugarCita;
    }

    public void setEstadoCita(String estadoCita) {
        this.estadoCita = estadoCita;
    }

    public void setObservacionCita(String observacionCita) {
        this.observacionCita = observacionCita;
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