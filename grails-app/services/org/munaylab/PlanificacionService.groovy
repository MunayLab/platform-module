package org.munaylab

import org.munaylab.osc.Organizacion
import org.munaylab.planificacion.Programa
import org.munaylab.planificacion.ProgramaCommand
import org.munaylab.planificacion.Proyecto
import org.munaylab.planificacion.ProyectoCommand

import grails.gorm.transactions.Transactional

@Transactional
class PlanificacionService {

    Programa actualizarPrograma(ProgramaCommand command) {
        if (!command || !command.validate()) return null

        Organizacion org = Organizacion.get(command.orgId)
        if (!org) return null

        Programa programa = command.id ? Programa.get(command.id) : null
        if (programa) {
            programa.actualizarDatos(command)
        } else {
            programa = new Programa(command.properties)
            org.addToProgramas(programa)
            org.save()
        }
        return programa
    }

    void eliminarPrograma(Programa programa) {
        if (!programa || !programa.organizacion) return

        Organizacion org = programa.organizacion
        org.removeFromProgramas(programa)
        programa.delete()
        org.programas.clear()
    }

    Proyecto actualizarProyecto(ProyectoCommand command) {
        if (!command || !command.validate()) return null

        Programa programa = Programa.get(command.programaId)
        if (!programa) return null

        Proyecto proyecto = command.id ? Proyecto.get(command.id) : null
        if (proyecto) {
            proyecto.actualizarDatos(command)
        } else {
            proyecto = new Proyecto(command.properties)
            programa.addToProyectos(proyecto)
            programa.save()
        }
        return proyecto
    }

    void eliminarProyecto(Proyecto proyecto) {
        if (!proyecto || !proyecto.programa) return

        Programa programa = proyecto.programa
        programa.removeFromProyectos(proyecto)
        proyecto.delete()
        programa.proyectos.clear()
    }
}
