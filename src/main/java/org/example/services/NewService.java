package org.example.services;

import org.example.dtos.NewDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NewService {
    //Получение всех с сайта за день/неделю.
    List<NewDTO> searchAndSaveAll(String query, String day);

    //Получение страницы из 20 новостей с сайта за день/неделю.
    List<NewDTO> searchAndSave(String query, int page, String day);

    //Получение всех из базы данных с сортировкой по полю.
    List<NewDTO> findAll(String query, Sort sort,String day);

    //Получение всех из базы данных.
    List<NewDTO> findAll(String query,String day);

    //Получение страницы из 20 новостей из базы данных с соритровкой по полю.
    List<NewDTO> find(String query, int page, Sort sort,String day);

    //Получение страницы из 20 новостей из базы данных.
    List<NewDTO> find(String query, int page,String day);

    //Получение страницы из 20 новостей из базы данных или с сайта за день/неделю.
    List<NewDTO> get(String query, int page, String day);

    //Получение всех из базы данных или с сайта за день/неделю.
    List<NewDTO> getAll(String query, String day);


}
