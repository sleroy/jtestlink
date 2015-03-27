/**
 *
 */
package com.tocea.corolla.attachments.dao;

import org.springframework.data.repository.CrudRepository;


import com.tocea.corolla.attachments.domain.Attachment;


public interface IAttachmentDAO extends CrudRepository<Attachment, Integer> {

}
