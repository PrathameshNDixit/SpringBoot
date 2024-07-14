package com.week2.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.week2.demo.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDTO {
    private Long id;
    @NotBlank(message = "Name cannot be Blank")
    @Size(min = 3,max = 20)
    private String name;

    @NotBlank(message = "email is mandatory")
    @Email(message = "enter valid email")
    private String email;

    @NotNull
    @Max(value = 80)
    @Min(value = 2)
    private Integer age;

    @PastOrPresent(message = "date of joining cannot be in a future")
    private LocalDate dateOfJoining;

    @AssertTrue
    @JsonProperty("isActive")
    private Boolean isActive;

    @NotNull(message = "Salary of Employee should be not null")
    @Positive(message = "Salary of Employee should be positive")
    @Digits(integer = 6, fraction = 2, message = "The salary can be in the form XXXXX.YY")
    @DecimalMax(value = "100000.99")
    @DecimalMin(value = "100.50")
    private Double salary;

    @NotBlank(message = "Role of the employee cannot be blank")
//    @Pattern(regexp = "^(ADMIN|USER)$", message = "Role of Employee can either be USER or ADMIN")
    @EmployeeRoleValidation
    private String role; //ADMIN, USER
}
