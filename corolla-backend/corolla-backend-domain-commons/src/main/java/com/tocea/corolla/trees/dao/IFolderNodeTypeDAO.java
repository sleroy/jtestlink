package com.tocea.corolla.trees.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.tocea.corolla.trees.domain.FolderNodeType;

public interface IFolderNodeTypeDAO extends MongoRepository<FolderNodeType, String>{

}
