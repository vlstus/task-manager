package com.study.taskmanagement.controller.rest;

public interface MappingService<Model, TransferObject> {
    Model convertToModel(TransferObject transferObject);
    TransferObject convertToTransferObject(Model model);
}
