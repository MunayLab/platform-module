package org.munaylab

import org.munaylab.balance.*
import org.munaylab.contacto.*
import org.munaylab.contenido.*
import org.munaylab.direccion.*
import org.munaylab.osc.*
import org.munaylab.planificacion.*
import org.munaylab.user.*
import org.munaylab.utils.EmailService
import org.munaylab.security.ConfirmacionCommand
import org.munaylab.security.Token

class Builder {

    static def DATOS_ORG = [nombre: 'MunayLab', tipo: TipoOrganizacion.FUNDACION,
        fechaConstitucion: new Date() -10, objeto: 'brindar soluciones a las organizaciones sociales']
    static def DATOS_DOMICILIO = [calle: 'Peat 32', numero: '570', barrio: 'San Pedrito',
        localidad: 'San Salvador de Jujuy', provincia: 'Jujuy']

    static RegistroCommand getRegistroCommand() {
        new RegistroCommand(denominacion: 'Fundación MunayLab', tipo: TipoOrganizacion.FUNDACION,
            nombre: 'Augusto', apellido: 'caligares', email: 'mcaligares@gmail.com',
            telefono: '1234567', objeto: 'brindar soluciones a las organizaciones sociales')
    }
    static RegistroCommand getInvalidRegistroCommand() {
        new RegistroCommand(denominacion: 'Fundación MunayLab', tipo: TipoOrganizacion.FUNDACION,
            nombre: 'Augusto', apellido: 'caligares',
            objeto: 'brindar soluciones a las organizaciones sociales')
    }
    static ConfirmacionCommand getConfirmacionCommand() {
        new ConfirmacionCommand(codigo: 'codigo', password1: 'asdQWE123', password2: 'asdQWE123')
    }
    static Organizacion crearOrganizacionConDatos(datos = DATOS_ORG) {
        new Organizacion(nombre: datos.nombre, objeto: datos.objeto, tipo: datos.tipo,
            fechaConstitucion: datos.fechaConstitucion, estado: EstadoOrganizacion.VERIFICADA)
    }
    static Domicilio crearDomicilioConDatos(datos = DATOS_DOMICILIO) {
        new Domicilio(calle: datos.calle, numero: datos.numero, barrio: datos.barrio,
                localidad: datos.localidad, provincia: datos.provincia)
    }
    static OrganizacionCommand getOrganizacionCommand() {
        new OrganizacionCommand(id: 1, fechaConstitucion: new Date() -100,
            tipo: TipoOrganizacion.ASOCIACION_CIVIL, nombre: 'Fundacion Internacional MunayLab',
            objeto: 'brindar soluciones innovadoras a ONGs')
    }
    static DomicilioCommand getDomicilioCommand() {
        new DomicilioCommand(id: 1, calle: 'Reconquista', numero: '1125', barrio: 'Centro',
            localidad: 'Cuidad Autonoma de Buenos Aires', provincia: 'Buenos Aires')
    }
    static OrganizacionCommand getOrganizacionConDomicilioCommand() {
        def command = organizacionCommand
        command.domicilio = domicilioCommand
        return command
    }
    static ContactoCommand getContactoCommand() {
        new ContactoCommand(value: 'mcaligares@gmail.com', tipo: TipoContacto.EMAIL)
    }
    static Contacto crearContacto() {
        new Contacto(value: 'mcaligares@gmail.com', tipo: TipoContacto.EMAIL)
    }
    static UserCommand getUserCommand() {
        new UserCommand(nombre: 'Augusto', apellido: 'Caligares', username: 'mcaligares@gmail.com')
    }
    static UserCommand getAdminCommand() {
        def command = userCommand
        command.tipo = TipoUsuario.findByNombre('ADMINISTRADOR').id
        return command
    }
    static UserCommand getMiembroCommand() {
        def command = userCommand
        command.tipo = TipoUsuario.findByNombre('MIEMBRO').id
        command.cargo = 'Director Ejecutivo'
        return command
    }
    static User crearUser() {
        new User(nombre: 'Augusto', apellido: 'Caligares',
            username: 'mcaligares@gmail.com', password: 'password')
    }
    static UserOrganizacion crearAdminOrganizacion(Organizacion org) {
        new UserOrganizacion(user: crearUser(), organizacion: org,
                tipo: TipoUsuario.findByNombre('ADMINISTRADOR'))
    }
    static UserOrganizacion crearMiembroOrganizacion(Organizacion org) {
        new UserOrganizacion(user: crearUser(), organizacion: org,
                tipo: TipoUsuario.findByNombre('MIEMBRO'), cargo: 'Director Ejecutivo')
    }
    static ArticuloCommand getNosotrosCommand() {
        new ArticuloCommand(autorId: 1, titulo: 'Nosotros', tipo: TipoArticulo.NOSOTROS,
                contenido: 'nosotros contenido', imagen: 'imagen.jpg')
    }
    static Articulo crearArticulo() {
        new Articulo(autor: crearUser(), titulo: 'Nosotros', contenido: 'nosotros contenido',
                tipo: TipoArticulo.NOSOTROS, publicado: true)
    }
    static ProgramaCommand getProgramaCommand() {
        new ProgramaCommand(orgId: 1, nombre: 'mi programa', imagen: 'imagen/de/mi/programa.jpg',
            descripcion: 'descripcion del programa')
    }
    static Programa crearPrograma() {
        new Programa(nombre: 'Innovaciones Sociales', imagen: 'programa/innovacion',
            descripcion: 'Brindar innovaciones a las osc.', publicado: true)
    }
    static ProyectoCommand getProyectoCommand() {
        new ProyectoCommand(programaId: 1, nombre: 'mi proyecto', imagen: 'imagen/de/mi/proyecto.jpg',
            descripcion: 'descripcion del proyecto')
    }
    static Proyecto crearProyecto() {
        new Proyecto(nombre: 'Taller de Innovaciones', imagen: 'proyecto/programa/innovacion',
            descripcion: 'Taller donde se exponen innovaciones para la sociedad', publicado: true)
    }
    static ActividadCommand getActividadCommand() {
        new ActividadCommand(proyectoId: 1, nombre: 'mi actividad', imagen: 'imagen/de/mi/actividad.jpg',
            descripcion: 'descripcion del actividad')
    }
    static Actividad crearActividad() {
        new Actividad(nombre: 'Presentacion Innovacion', imagen: 'proyecto/actividad/innovacion',
            descripcion: 'Presentacion de innovaciones realizadas en el taller', publicado: true)
    }
    static EventoCommand getEventoCommand() {
        new EventoCommand(orgId: 1, nombre: 'mi evento', imagen: 'imagen/de/mi/evento.jpg',
            descripcion: 'descripcion del evento', fechaIni: new Date() + 9,
            fechaDifusion: new Date() + 2, direccion: domicilioCommand)
    }
    static Evento crearEvento() {
        new Evento(nombre: 'Presentacion Plataforma', imagen: 'evento/presentacion.jpg',
            descripcion: 'Presentacion de la plataforma a las OSC locales', publicado: true,
            fechaIni: new Date() +20, fechaFin: new Date() +21, fechaDifusion: new Date() +10)
    }
    static AsientoCommand getEgresoCommand() {
        new AsientoCommand(monto: 100.0, fecha: new Date(), detalle: 'detalle de asiento', esIngreso: false, orgId: 1)
    }
    static AsientoCommand getIngresoCommand() {
        new AsientoCommand(monto: 100.0, fecha: new Date(), detalle: 'detalle de asiento', esIngreso: true, orgId: 1)
    }
    static CategoriaCommand getCategoriaEgresoCommand() {
        new CategoriaCommand(nombre: 'nueva categoria', detalle: 'detalle', tipo: TipoAsiento.EGRESO)
    }
    static CategoriaCommand getCategoriaIngresoCommand() {
        new CategoriaCommand(nombre: 'nueva categoria', detalle: 'detalle', tipo: TipoAsiento.INGRESO)
    }
    static Categoria crearCategoria() {
        new Categoria(nombre: 'nuevaCategoria', tipo: TipoAsiento.INGRESO)
    }
    static Asiento crearEgreso() {
        new Asiento(monto: 10.0, detalle: 'egreso', fecha: new Date(), categoria: crearCategoria(), tipo: TipoAsiento.EGRESO)
    }
    static Asiento crearIngreso() {
        new Asiento(monto: 10.0, detalle: 'ingreso', fecha: new Date(), categoria: crearCategoria(), tipo: TipoAsiento.INGRESO)
    }
    static Organizacion crearOrganizacionCompleta() {
        def actividad = Builder.crearActividad()
        def proyecto = Builder.crearProyecto().addToActividades(actividad)
        def programa = Builder.crearPrograma().addToProyectos(proyecto)
        def evento = Builder.crearEvento()
        evento.direccion = Builder.crearDomicilioConDatos()
        def org = Builder.crearOrganizacionConDatos()
        org.domicilio = Builder.crearDomicilioConDatos()
        org.addToContactos(Builder.crearContacto())
        org.addToArticulos(Builder.crearArticulo())
        org.addToProgramas(programa)
        org.addToEventos(evento)
    }
}
