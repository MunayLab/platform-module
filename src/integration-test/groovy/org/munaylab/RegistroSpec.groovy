package org.munaylab

import org.munaylab.categoria.TipoUsuario

import grails.testing.mixin.integration.Integration
import grails.transaction.*
import org.springframework.beans.factory.annotation.*
import spock.lang.Specification

@Integration
@Rollback
class RegistroSpec extends Specification {

    @Autowired
    OrganizacionService organizacionService

    def setup() {
    }

    def cleanup() {
    }

    void "registro de organizacion"() {
        given:
        def command = Builder.registroCommand
        new TipoUsuario(nombre: 'ADMINISTRADOR').save(flush: true)
        when:
        def org = organizacionService.registrar(command)
        then:
        org != null && !org.hasErrors()
    }
}
