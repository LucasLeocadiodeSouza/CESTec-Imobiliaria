package com.cestec.cestec.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class gridZoomRequest {
    private String query;
    private List<modelUtilForm> parametros;
}
