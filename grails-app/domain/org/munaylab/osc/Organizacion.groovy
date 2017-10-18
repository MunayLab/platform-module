package org.munaylab.osc

import org.munaylab.contacto.Contacto
import org.munaylab.contacto.TipoContacto
import org.munaylab.direccion.Domicilio
import org.munaylab.user.User

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Organizacion {

    String nombre
    String objeto
    Date fechaConstitucion
    TipoOrganizacion tipo
    EstadoOrganizacion estado
    // Domicilio domicilio

    Date dateCreated
    Date lastUpdated
    Boolean enabled = Boolean.TRUE

    static hasMany = [
        admins: User,
        miembros: User,
        contactos: Contacto
    ]

    static constraints = {
        nombre size: 3..200, unique: true
        objeto size: 10..500
        fechaConstitucion nullable: true
        // domicilio nullable: true
    }

    void actualizarDatos(OrganizacionCommand command) {
        if (!command || id != command.id) return

        this.nombre = command.nombre
        this.objeto = command.objeto
        this.fechaConstitucion = command.fechaConstitucion
        this.tipo = command.tipo
    }

}
