package com.tocea.corolla.revisions.services

import org.javers.core.Javers;
import org.javers.core.diff.Diff
import org.junit.Rule;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User

import spock.lang.Specification;

import com.tocea.corolla.requirements.exceptions.*;
import com.tocea.corolla.revisions.domain.Commit
import com.tocea.corolla.revisions.services.IRevisionService
import com.tocea.corolla.test.utils.FunctionalDocRule
import com.tocea.corolla.utils.functests.FunctionalTestDoc

class JaversRevisionServiceTest extends Specification {

	@Rule
	def FunctionalDocRule rule	= new FunctionalDocRule()
	def Javers javers = Mock(Javers)
	def IRevisionService revisionService
	
	class DomainObject {
		def String id;
	}
	
	def setup() {
		revisionService = new JaversRevisionService(
				javers: javers
		)
	}
	
	def "it should call Javers commit method"() {
		
		given:
			def object = new DomainObject(id: "1")
		
		when:
			revisionService.commit(object)
	
		then:
			notThrown(Exception.class)
			1 * javers.commit(_, object)

	}
	
	def "it should retrieve the history of an object"() {
		
		given:
			def object = new DomainObject(id: "1")
		
		when:
			revisionService.getHistory(object.id, object.getClass())
	
		then:
			notThrown(Exception.class)
			1 * javers.findSnapshots(_)
		
	}
	
	def "it should compare to version of the same object"() {
		
		given:
			def old = new DomainObject(id: "1")
			def current = new DomainObject(id: "1")
			def Diff diff = Mock(Diff)
		
		when:
			revisionService.compare(old, current)
	
		then:
			notThrown(Exception.class)
			javers.compare(old, current) >> diff
		
	}
	
}