package com.atividade.cinco.model;

import com.atividade.cinco.model.enuns.StatusMatricula;
import jakarta.persistence.*;


@Entity
@Table(name = "tb_matricula")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
   
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "estudante_id", nullable = false)
    private Estudante estudante;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusMatricula status;

//Start GetterSetterExtension Source Code

    /**GET Method Propertie id*/
    public int getId(){
        return this.id;
    }//end method getId

    /**SET Method Propertie id*/
    public void setId(int id){
        this.id = id;
    }//end method setId

    /**GET Method Propertie estudante*/
    public Estudante getEstudante(){
        return this.estudante;
    }//end method getEstudante

    /**SET Method Propertie estudante*/
    public void setEstudante(Estudante estudante){
        this.estudante = estudante;
    }//end method setEstudante

    /**GET Method Propertie turma*/
    public Turma getTurma(){
        return this.turma;
    }//end method getTurma

    /**SET Method Propertie turma*/
    public void setTurma(Turma turma){
        this.turma = turma;
    }//end method setTurma
    
    /**GET Method Property status*/
    public StatusMatricula getStatus() {
        return this.status;
    }
    
    /**SET Method Property status*/
    public void setStatus(StatusMatricula status) {
        this.status = status;
    }
//End GetterSetterExtension Source Code


}//End class