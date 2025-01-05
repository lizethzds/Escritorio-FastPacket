
package escritoriofastpacket.modelo.pojo;

public class Colaborador {
    private Integer idColaborador;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String noPersonal;
    private String correo;
    private String curp;
    private String password;
    private Integer idRol;
    private String rol;
    private String fotografia;
    private String noLicencia;
    private boolean asignado;
    
    public Colaborador() {
    }

    public Colaborador(Integer idColaborador, String nombre, String apellidoPaterno, String apellidoMaterno, String noPersonal, String correo, String curp, String password, Integer idRol, String rol, String fotografia, String noLicencia, boolean asignado) {
        this.idColaborador = idColaborador;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.noPersonal = noPersonal;
        this.correo = correo;
        this.curp = curp;
        this.password = password;
        this.idRol = idRol;
        this.rol = rol;
        this.fotografia = fotografia;
        this.noLicencia = noLicencia;
        this.asignado = asignado;
    }

    public boolean isAsignado() {
        return asignado;
    }

    public void setAsignado(boolean asignado) {
        this.asignado = asignado;
    }
        
    public String getNoLicencia() {
        return noLicencia;
    }

    public void setNoLicencia(String noLicencia) {
        this.noLicencia = noLicencia;
    }
    
    
    public Integer getIdColaborador() {
        return idColaborador;
    }

    public void setIdColaborador(Integer idColaborador) {
        this.idColaborador = idColaborador;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getNoPersonal() {
        return noPersonal;
    }

    public void setNoPersonal(String noPersonal) {
        this.noPersonal = noPersonal;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getIdRol() {
        return idRol;
    }

    public void setIdRol(Integer idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getFotografia() {
        return fotografia;
    }

    public void setFotografia(String fotografia) {
        this.fotografia = fotografia;
    }

    @Override
    public String toString() {
        return " - "+noPersonal +" "+ nombre + " " + apellidoPaterno + " " + apellidoMaterno;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Colaborador colaborador = (Colaborador) obj;
        return noPersonal != null && noPersonal.equals(colaborador.noPersonal); // Comparar por un atributo único
    }

    @Override
    public int hashCode() {
        return noPersonal != null ? noPersonal.hashCode() : 0;
    }
}
