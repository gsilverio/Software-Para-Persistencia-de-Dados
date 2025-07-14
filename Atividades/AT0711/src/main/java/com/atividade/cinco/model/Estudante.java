package com.atividade.cinco.model;

import jakarta.persistence.*;

@Entity
@Table(name = "tb_estudante")
public class Estudante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nomeCompleto;

    private String dataDeNascimento;

    private int matricula;
    
    @Override
    public String toString() {
        return this.nomeCompleto+" ("+this.matricula+")";
    }    

//Start GetterSetterExtension Source Code

    /**GET Method Propertie id*/
    public int getId(){
        return this.id;
    }//end method getId

    /**SET Method Propertie id*/
    public void setId(int id){
        this.id = id;
    }//end method setId

    /**GET Method Propertie nomeCompleto*/
    public String getNomeCompleto(){
        return this.nomeCompleto;
    }//end method getNomeCompleto

    /**SET Method Propertie nomeCompleto*/
    public void setNomeCompleto(String nomeCompleto){
        this.nomeCompleto = nomeCompleto;
    }//end method setNomeCompleto

    /**GET Method Propertie dataDeNascimento*/
    public String getDataDeNascimento(){
        return this.dataDeNascimento;
    }//end method getDataDeNascimento

    /**SET Method Propertie dataDeNascimento*/
    public void setDataDeNascimento(String dataDeNascimento){
        this.dataDeNascimento = dataDeNascimento;
    }//end method setDataDeNascimento

    /**GET Method Propertie matricula*/
    public int getMatricula(){
        return this.matricula;
    }//end method getMatricula

    /**SET Method Propertie matricula*/
    public void setMatricula(int matricula){
        this.matricula = matricula;
    }//end method setMatricula

//End GetterSetterExtension Source Code


}//End class