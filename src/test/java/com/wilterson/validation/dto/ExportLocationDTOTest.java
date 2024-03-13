package com.wilterson.validation.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.wilterson.validation.enums.ExportLocationOption;
import java.util.Set;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ExportLocationDTOTest {

    private Validator validator;
    private ExportLocationDTO dto;

    @BeforeEach
    public void setUp() throws Exception {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();

        dto = ExportLocationDTO.builder()
                .name("validName")
                .build();
    }

    @Test
    public void ShouldFailOnMissingEmailAddressField() {
        dto.setLocationOption(ExportLocationOption.EMAIL);
        Set<ConstraintViolation<ExportLocationDTO>> violations = validator.validate(dto);
        assertEquals(1, violations.size());
    }

    @Test
    public void ShouldFailOnMissingFTPCredentials() {
        dto.setLocationOption(ExportLocationOption.FTP);
        Set<ConstraintViolation<ExportLocationDTO>> violations = validator.validate(dto);
        assertEquals(4, violations.size());
    }

    @Test
    public void ShouldFailOnMissingSFTPCredentials() {
        dto.setLocationOption(ExportLocationOption.SFTP);
        Set<ConstraintViolation<ExportLocationDTO>> violations = validator.validate(dto);
        assertEquals(4, violations.size());
    }
}