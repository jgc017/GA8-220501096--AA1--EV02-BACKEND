package org.sena.saludcontigo.models.entity;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Pacientes")
public class Paciente implements Serializable {

    /**
     * Entidades
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idpaciente;

    @Column(name = "Nombres", nullable = false)
    private String nombres;

    @Column(name = "Apellidos", nullable = false)
    private String apellidos;

    @Column(name = "Tipo_Documento", nullable = false)
    private String tipoDocumento;

    @Column(name = "Numero_Documento", nullable = false, unique = true)
    private String numeroDocumento;

    @Column(name = "Fecha_Nacimiento", nullable = false)
    private String fechaNacimiento;

    @Column(name = "Sexo", nullable = false)
    private String sexo;

    @Column(name = "Telefono", nullable = false)
    private String telefono;

    @Column(name = "Correo", nullable = false)
    private String correo;

    @Column(name = "Direccion", nullable = false)
    private String direccion;

    @Column(name = "Ocupacion", nullable = false)
    private String ocupacion;

    @Column(name = "Username", nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    private String password;

    @Column(name = "Estado", nullable = false)
    private String estado;


    /**
     * Relaciones externas
     */


    /**
     * Constructor
     */
    public Paciente() {
    }


    /**
     * Métodos Getters
     */
    public Long getIdpaciente() {
        return idpaciente;
    }

    public String getNombres() {
        return nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getSexo() {
        return sexo;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEstado() {
        return estado;
    }


    /**
     * Métodos Setters
     */
    public void setIdpaciente(Long idpaciente) {
        this.idpaciente = idpaciente;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }


    /**
     * *
     */
    private static final long serialVersionUID = 1L;

}