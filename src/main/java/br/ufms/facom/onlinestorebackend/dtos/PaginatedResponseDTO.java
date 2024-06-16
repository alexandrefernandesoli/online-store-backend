package br.ufms.facom.onlinestorebackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponseDTO<T> {
    private long pages;
    private long total;
    private int page;
    private int limit;
    private List<T> results;
}
