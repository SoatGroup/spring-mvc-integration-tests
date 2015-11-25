package fr.soat.java.dao;


import fr.soat.java.model.OrderEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface IOrderRepository extends MongoRepository<OrderEntity, String> {
}
