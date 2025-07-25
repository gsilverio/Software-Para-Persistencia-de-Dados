package com.persist.data.entities;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.DataType;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "student")
@XmlAccessorType(XmlAccessType.FIELD)
@DatabaseTable(tableName = "student")
public class Student
{
    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField
    private String fullName;

    @DatabaseField
    public int registration;

    @DatabaseField(dataType=DataType.DATE)
    public Date birthday;
    @ForeignCollectionField(eager = true)
    private ForeignCollection<Nota> listNotas;


    public Student(){}
    public Student(String fullName, int registration, Date birthday) {
        this.fullName = fullName;
        this.registration = registration;
        this.birthday = birthday;
    }

    public String printBirthday() {
        SimpleDateFormat dateFor = new SimpleDateFormat("dd/MM/yyyy");
        return dateFor.format(birthday);
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

    /**GET Method Propertie fullName*/
    public String getFullName(){
        return this.fullName;
    }//end method getFullName

    /**SET Method Propertie fullName*/
    public void setFullName(String fullName){
        this.fullName = fullName;
    }//end method setFullName

    /**GET Method Propertie registration*/
    public int getRegistration(){
        return this.registration;
    }//end method getRegistration

    /**SET Method Propertie registration*/
    public void setRegistration(int registration){
        this.registration = registration;
    }//end method setRegistration

    /**GET Method Propertie birthday*/
    public Date getBirthday(){
        return this.birthday;
    }//end method getBirthday

    /**SET Method Propertie birthday*/
    public void setBirthday(Date birthday){
        this.birthday = birthday;
    }//end method setBirthday


    public ForeignCollection<Nota> getListNotas() {
        return listNotas;
    }

    public void addNota(Nota listNotas) {

        this.listNotas.add(listNotas);
    }

    //End GetterSetterExtension Source Code


}//End class