package com.wilterson.validation.dto;

import com.wilterson.validation.enums.ExportLocationOption;
import com.wilterson.validation.validation.Conditional;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

@Builder
@Getter
@Setter
@Conditional(selected = "locationOption", values = {"SFTP", "FTP"}, required = {"host", "user", "password", "port"})
@Conditional(selected = "locationOption", values = {"EMAIL"}, required = {"emailAddress"})
public class ExportLocationDTO {

    @NotEmpty
    private String name;
    @NotNull
    private ExportLocationOption locationOption;
    private String emailAddress;
    private String host;
    private Integer port;
    private String user;
    private String password;
}
