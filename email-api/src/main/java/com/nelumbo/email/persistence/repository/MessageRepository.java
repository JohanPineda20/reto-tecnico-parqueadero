package com.nelumbo.email.persistence.repository;

import com.nelumbo.email.persistence.document.EmailCount;
import com.nelumbo.email.persistence.document.Message;
import com.nelumbo.email.persistence.document.TopLicensePlate;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageRepository extends MongoRepository<Message,String> {
    @Aggregation(pipeline = {
            "{ '$group': { '_id': '$email', 'count': { '$sum': 1 } } }",
            "{ '$sort': { 'count': -1 } }",
            "{ '$limit': 1 }",
            "{ '$project': { '_id': 0, 'email': '$_id', 'count': '$count' } }"
    })
    EmailCount findMostFrequentEmail();
    @Aggregation(pipeline = {
            "{ '$match': { 'dateSent': { '$gte': ?0, '$lt': ?1 } } }",
            "{ '$count': 'messageCount' }"
    })
    Long countMessagesByDate(LocalDateTime startOfDay, LocalDateTime endOfDay);
    @Aggregation(pipeline = {
            "{ '$match': { '$expr': { '$and': [ { '$eq': [ { '$month': '$dateSent' }, ?0 ] }, { '$eq': [ { '$year': '$dateSent' }, ?1 ] } ] } } }",
            "{ '$group': { '_id': '$licensePlate', 'count': { '$sum': 1 } } }",
            "{ '$sort': { 'count': -1 } }",
            "{ '$limit': 10 }",
            "{ '$project': { '_id': 0, 'licensePlate': '$_id', 'count': '$count' } }"
    })
    List<TopLicensePlate> findTop10LicensePlatesByMonthAndYear(int month, int year);
}
