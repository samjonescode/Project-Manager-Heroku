package com.ppmtool.exceptions;

public class ProjectNotFoundExceptionResponse extends CustomResponseEntityExceptionHandler{
    private String projectIdentifier;

    public ProjectNotFoundExceptionResponse(String projectIdentifier) {
        this.projectIdentifier = projectIdentifier;
    }

    public String getProjectIdentifier() {
        return projectIdentifier;
    }
}
