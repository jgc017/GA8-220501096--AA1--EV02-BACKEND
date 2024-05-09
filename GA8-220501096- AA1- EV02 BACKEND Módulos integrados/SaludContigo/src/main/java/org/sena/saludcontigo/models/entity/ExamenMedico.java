package org.sena.saludcontigo.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity
@Table(name = "Examenes_Medicos")
public class ExamenMedico implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idexamen;

    @Column(name = "Tipo_Examen", nullable = false)
    private String tipoExamen;

    @Column(name = "Fecha_Examen", nullable = false)
    private Date fechaExamen;

    @Column(name = "Hora_Examen", nullable = false)
    private Date horaExamen;

    @Column(name = "Lugar_Examen", nullable = false)
    private String lugarExamen;

    @Column(name = "Estado_Examen", nullable = false)
    private String estadoExamen;

    @Column(name = "Observacion_Examen")
    private String observacionExamen;


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
    public ExamenMedico() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdexamen() {
        return idexamen;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public Date getFechaExamen() {
        return fechaExamen;
    }

    public Date getHoraExamen() {
        return horaExamen;
    }

    public String getLugarExamen() {
        return lugarExamen;
    }

    public String getEstadoExamen() {
        return estadoExamen;
    }

    public String getObservacionExamen() {
        return observacionExamen;
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
    public void setIdexamen(Long idexamen) {
        this.idexamen = idexamen;
    }

    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }

    public void setFechaExamen(Date fechaExamen) {
        this.fechaExamen = fechaExamen;
    }

    public void setHoraExamen(Date horaExamen) {
        this.horaExamen = horaExamen;
    }

    public void setLugarExamen(String lugarExamen) {
        this.lugarExamen = lugarExamen;
    }

    public void setEstadoExamen(String estadoExamen) {
        this.estadoExamen = estadoExamen;
    }

    public void setObservacionExamen(String observacionExamen) {
        this.observacionExamen = observacionExamen;
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