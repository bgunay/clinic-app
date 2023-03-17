package com.vngrs.clinic.controller;


import com.vngrs.clinic.config.SwaggerConfiguration;
import com.vngrs.clinic.dto.request.AppointmentRequestDto;
import com.vngrs.clinic.dto.response.AppointmentResponseDto;
import com.vngrs.clinic.exception.forbidden.ForbiddenException;
import com.vngrs.clinic.exception.notfound.NotFoundException;
import com.vngrs.clinic.exception.unauthorized.UnauthorizedException;
import com.vngrs.clinic.service.AppointmentService;
import io.swagger.annotations.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Api(value = AppointmentController.ENDPOINT, tags = {SwaggerConfiguration.SMS_TAG})
@RequestMapping(value = AppointmentController.ENDPOINT)
public class AppointmentController {

    static final String ENDPOINT = "/appointment";

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @ApiOperation(value = "getUserAppointmentList", produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = AppointmentResponseDto.class, responseContainer = "List"),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class)})
    @GetMapping
    public List<AppointmentResponseDto> getUserAppointmentList(
            @ApiParam(value = "userId", required = true) @RequestHeader Long userId) {
        return appointmentService.getUserAppointmentList(userId);
    }

    @ApiOperation(value = "createAppointment", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created", response = AppointmentResponseDto.class),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class)})
    @PostMapping
    public ResponseEntity<AppointmentResponseDto> createAppointment(
            @ApiParam(value = "appointmentCreateRequest", required = true) @RequestBody AppointmentRequestDto appointmentCreateRequest) throws com.vngrs.clinic.exception.notfound.NotFoundException, ForbiddenException {
        AppointmentResponseDto appointmentResponse = appointmentService.createAppointment(appointmentCreateRequest);
        return new ResponseEntity<>(appointmentResponse, HttpStatus.CREATED);
    }

    @ApiOperation(value = "cancelAppointment", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Cancelled"),
            @ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ForbiddenException.class),
            @ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class)})
    @PostMapping("/{appointmentId}/cancel")
    public ResponseEntity<AppointmentResponseDto> cancelAppointment(@PathVariable Long appointmentId) throws com.vngrs.clinic.exception.notfound.NotFoundException, ForbiddenException {
        AppointmentResponseDto response = appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.ok(response);
    }
}