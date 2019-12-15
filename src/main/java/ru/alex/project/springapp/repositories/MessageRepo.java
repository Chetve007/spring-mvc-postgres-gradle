package ru.alex.project.springapp.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.alex.project.springapp.entities.Message;

import java.util.List;

public interface MessageRepo extends CrudRepository<Message, Integer> {

    List<Message> findByTag(String tag);
}
