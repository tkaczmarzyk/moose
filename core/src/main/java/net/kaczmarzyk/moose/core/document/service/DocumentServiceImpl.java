package net.kaczmarzyk.moose.core.document.service;

import org.springframework.stereotype.Service;

import net.kaczmarzyk.moose.core.document.Document;


@Service("documentService")
public class DocumentServiceImpl implements DocumentService {

	@Override
	public Document newDocument() {
		return new Document("tes test");
	}

}
