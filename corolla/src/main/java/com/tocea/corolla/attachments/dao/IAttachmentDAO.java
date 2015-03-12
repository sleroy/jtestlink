/**
 *
 */
package com.tocea.corolla.attachments.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.tocea.corolla.attachments.domain.Attachment;

@RepositoryRestResource(path = "/attachments", collectionResourceRel = "attachments")
public interface IAttachmentDAO extends CrudRepository<Attachment, Integer> {

}
